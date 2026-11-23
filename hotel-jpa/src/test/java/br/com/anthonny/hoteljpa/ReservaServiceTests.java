package br.com.anthonny.hoteljpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.anthonny.hoteljpa.cliente.Cliente;
import br.com.anthonny.hoteljpa.cliente.ClienteRepository;
import br.com.anthonny.hoteljpa.reserva.Reserva;
import br.com.anthonny.hoteljpa.reserva.ReservaEntrada;
import br.com.anthonny.hoteljpa.reserva.ReservaRepository;
import br.com.anthonny.hoteljpa.reserva.ReservaService;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class ReservaServiceTests {
    @Test
    void reutilizaClientePeloEmailAoCriarReserva() {
        ClienteRepository clientes = mock(ClienteRepository.class);
        ReservaRepository reservas = mock(ReservaRepository.class);
        Cliente cliente = new Cliente("Ana", "Feminino", "ana@example.com");
        when(clientes.findByEmailIgnoreCase("ana@example.com")).thenReturn(Optional.of(cliente));
        when(reservas.save(any(Reserva.class))).thenAnswer(chamada -> chamada.getArgument(0));

        var entrada = new ReservaEntrada("Outro nome", "Feminino", "ANA@example.com",
                LocalDate.of(2027, 1, 15), 3, 2, "Quarto silencioso");
        var saida = new ReservaService(reservas, clientes).criar(entrada);

        assertEquals("Ana", saida.cliente().nome());
        assertEquals(3, saida.numeroNoites());
        verify(clientes).findByEmailIgnoreCase("ana@example.com");
    }
}
