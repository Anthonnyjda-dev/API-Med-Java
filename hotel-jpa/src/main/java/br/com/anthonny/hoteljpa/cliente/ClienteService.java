package br.com.anthonny.hoteljpa.cliente;

import br.com.anthonny.hoteljpa.erro.Conflito;
import br.com.anthonny.hoteljpa.erro.RecursoNaoEncontrado;
import br.com.anthonny.hoteljpa.reserva.ReservaRepository;
import java.util.List;
import java.util.Locale;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {
    private final ClienteRepository clientes;
    private final ReservaRepository reservas;

    public ClienteService(ClienteRepository clientes, ReservaRepository reservas) {
        this.clientes = clientes;
        this.reservas = reservas;
    }

    public static String normalizarEmail(String email) {
        return email.trim().toLowerCase(Locale.ROOT);
    }

    @Transactional
    public ClienteSaida criar(ClienteEntrada entrada) {
        String email = normalizarEmail(entrada.email());
        if (clientes.findByEmailIgnoreCase(email).isPresent()) throw new Conflito("E-mail já cadastrado");
        return ClienteSaida.de(clientes.save(new Cliente(entrada.nome().trim(), entrada.sexo().trim(), email)));
    }

    @Transactional(readOnly = true)
    public List<ClienteSaida> listar() {
        return clientes.findAll().stream().map(ClienteSaida::de).toList();
    }

    @Transactional(readOnly = true)
    public ClienteSaida buscar(Long id) { return ClienteSaida.de(obter(id)); }

    @Transactional
    public ClienteSaida atualizar(Long id, ClienteEntrada entrada) {
        Cliente cliente = obter(id);
        String email = normalizarEmail(entrada.email());
        if (clientes.existsByEmailIgnoreCaseAndIdNot(email, id)) throw new Conflito("E-mail já cadastrado");
        cliente.atualizar(entrada.nome().trim(), entrada.sexo().trim(), email);
        return ClienteSaida.de(cliente);
    }

    @Transactional
    public void excluir(Long id) {
        Cliente cliente = obter(id);
        if (reservas.existsByClienteId(id)) throw new Conflito("Cliente possui reservas; exclua-as primeiro");
        clientes.delete(cliente);
    }

    private Cliente obter(Long id) {
        return clientes.findById(id).orElseThrow(() -> new RecursoNaoEncontrado("Cliente não encontrado: " + id));
    }
}
