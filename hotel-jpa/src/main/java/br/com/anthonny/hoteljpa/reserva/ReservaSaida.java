package br.com.anthonny.hoteljpa.reserva;

import br.com.anthonny.hoteljpa.cliente.ClienteSaida;
import java.time.LocalDate;

public record ReservaSaida(Long id, ClienteSaida cliente, LocalDate dataChegada,
                           Integer numeroNoites, Integer numeroHospedes, String mensagem) {
    public static ReservaSaida de(Reserva reserva) {
        return new ReservaSaida(reserva.getId(), ClienteSaida.de(reserva.getCliente()),
                reserva.getDataChegada(), reserva.getNumeroNoites(),
                reserva.getNumeroHospedes(), reserva.getMensagem());
    }
}
