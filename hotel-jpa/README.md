# ME 8 — CRUD de hotel com JPA

API Spring Boot para clientes e reservas. Usa Spring Data JPA/Hibernate e MySQL 8.4. O Compose deste projeto cria o banco `hotel_jpa_db` em `localhost:3308`; o JPA cria e atualiza as tabelas `clientes` e `reservas`. A API usa a porta `8081`, separada da ME 7.

## Executar

Requisitos: Java 21 e Docker com Compose. Na raiz `hotel-jpa`:

```bash
docker compose -f compose.yaml up -d
./mvnw spring-boot:run
```

As credenciais padrão (`hotel_jpa` / `hotel_jpa_dev`) servem somente ao desenvolvimento local. Para usar outras, exporte `HOTEL_JPA_USER` e `HOTEL_JPA_PASSWORD` no terminal antes de iniciar o Compose e a API; `HOTEL_JPA_ROOT_PASSWORD` configura apenas o Compose. O Compose também lê um `.env` local, ignorado pelo Git, mas a API precisa das variáveis exportadas no ambiente. Para empacotar: `./mvnw clean package`.

## Rotas

O corpo de cliente usa `nome`, `sexo` e `email`. O corpo de reserva usa esses três campos mais `dataChegada` (`AAAA-MM-DD`), `numeroNoites`, `numeroHospedes` e `mensagem` (opcional). `POST /api/reservas` reutiliza o cliente pelo e-mail, se já existir; nesse caso, `nome` e `sexo` previamente cadastrados permanecem no cliente. `PUT /api/reservas/{id}` aceita o mesmo corpo completo e também pode associar a reserva a outro e-mail.

| Método | Rota | Resultado |
| --- | --- | --- |
| POST | `/api/reservas` | Cria reserva e cliente, se necessário (201) |
| GET | `/api/reservas?email=ana@example.com` | Lista reservas do e-mail |
| GET | `/api/reservas/{id}` | Busca reserva |
| PUT | `/api/reservas/{id}` | Atualiza reserva |
| DELETE | `/api/reservas/{id}` | Exclui reserva (204) |
| POST | `/api/clientes` | Cria cliente (201) |
| GET | `/api/clientes` | Lista clientes |
| GET | `/api/clientes/{id}` | Busca cliente |
| PUT | `/api/clientes/{id}` | Atualiza cliente |
| DELETE | `/api/clientes/{id}` | Exclui cliente sem reservas (204) |

Entrada inválida retorna 400; ID inexistente, 404; e-mail duplicado ou exclusão de cliente com reservas, 409. Exclua as reservas antes de excluir o cliente. E-mails são normalizados para minúsculas.

## Testar com curl

Substitua os IDs dos exemplos pelos IDs retornados nos respectivos `POST`.

```bash
curl -i -X POST http://localhost:8081/api/reservas \
  -H 'Content-Type: application/json' \
  -d '{"nome":"Ana Silva","sexo":"Feminino","email":"ana@example.com","dataChegada":"2027-01-15","numeroNoites":3,"numeroHospedes":2,"mensagem":"Quarto silencioso"}'
curl -i 'http://localhost:8081/api/reservas?email=ana@example.com'
curl -i -X PUT http://localhost:8081/api/reservas/1 \
  -H 'Content-Type: application/json' \
  -d '{"nome":"Ana Silva","sexo":"Feminino","email":"ana@example.com","dataChegada":"2027-01-16","numeroNoites":4,"numeroHospedes":2,"mensagem":"Chegada à tarde"}'
curl -i -X DELETE http://localhost:8081/api/reservas/1
curl -i -X POST http://localhost:8081/api/clientes \
  -H 'Content-Type: application/json' \
  -d '{"nome":"Bruno Costa","sexo":"Masculino","email":"bruno@example.com"}'
curl -i http://localhost:8081/api/clientes
curl -i http://localhost:8081/api/clientes/2
curl -i -X PUT http://localhost:8081/api/clientes/2 \
  -H 'Content-Type: application/json' \
  -d '{"nome":"Bruno C. Costa","sexo":"Masculino","email":"bruno@example.com"}'
curl -i -X DELETE http://localhost:8081/api/clientes/2
```

Importe `postman/hotel-jpa.postman_collection.json` no Postman para exemplos das rotas de clientes e reservas. A consulta `sql/verificacao.sql` é manual e opcional: o Compose já cria o banco, e o JPA cria as tabelas. Para inspecionar com o cliente MySQL:

```bash
docker compose -f compose.yaml exec -T mysql mysql -u hotel_jpa -photel_jpa_dev < sql/verificacao.sql
```
