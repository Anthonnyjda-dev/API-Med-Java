package br.com.anthonny.hoteljpa.reserva;

import br.com.anthonny.hoteljpa.cliente.Cliente;
import br.com.anthonny.hoteljpa.cliente.ClienteRepository;
import br.com.anthonny.hoteljpa.cliente.ClienteService;
import br.com.anthonny.hoteljpa.erro.RecursoNaoEncontrado;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservaService {
    private final ReservaRepository reservas;
    private final ClienteRepository clientes;

    public ReservaService(ReservaRepository reservas, ClienteRepository clientes) {
        this.reservas = reservas;
        this.clientes = clientes;
    }

    @Transactional
    public ReservaSaida criar(ReservaEntrada entrada) {
        Cliente cliente = obterOuCriarCliente(entrada);
        Reserva reserva = new Reserva(cliente, entrada.dataChegada(), entrada.numeroNoites(),
                entrada.numeroHospedes(), entrada.mensagem());
        return ReservaSaida.de(reservas.save(reserva));
    }

    @Transactional(readOnly = true)
    public List<ReservaSaida> listarPorEmail(String email) {
        return reservas.findByClienteEmailIgnoreCaseOrderByIdAsc(ClienteService.normalizarEmail(email))
                .stream().map(ReservaSaida::de).toList();
    }

    @Transactional(readOnly = true)
    public ReservaSaida buscar(Long id) { return ReservaSaida.de(obter(id)); }

    @Transactional
    public ReservaSaida atualizar(Long id, ReservaEntrada entrada) {
        Reserva reserva = obter(id);
        Cliente cliente = obterOuCriarCliente(entrada);
        reserva.atualizar(cliente, entrada.dataChegada(), entrada.numeroNoites(),
                entrada.numeroHospedes(), entrada.mensagem());
        return ReservaSaida.de(reserva);
    }

    @Transactional
    public void excluir(Long id) { reservas.delete(obter(id)); }

    private Reserva obter(Long id) {
        return reservas.findById(id).orElseThrow(() -> new RecursoNaoEncontrado("Reserva não encontrada: " + id));
    }

    private Cliente obterOuCriarCliente(ReservaEntrada entrada) {
        String email = ClienteService.normalizarEmail(entrada.email());
        return clientes.findByEmailIgnoreCase(email).orElseGet(() ->
                clientes.save(new Cliente(entrada.nome().trim(), entrada.sexo().trim(), email)));
    }
}
