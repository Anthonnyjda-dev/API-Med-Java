-- Execução manual opcional: o Compose já cria hotel_jpa_db, e o JPA cria as tabelas.
CREATE DATABASE IF NOT EXISTS hotel_jpa_db;
USE hotel_jpa_db;
SHOW TABLES;
SELECT r.id AS reserva_id, c.nome, c.email, r.data_chegada,
       r.numero_noites, r.numero_hospedes, r.mensagem
FROM reservas r
JOIN clientes c ON c.id = r.cliente_id
ORDER BY r.id;
