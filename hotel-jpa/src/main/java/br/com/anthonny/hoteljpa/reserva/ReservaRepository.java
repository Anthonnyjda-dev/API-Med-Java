package br.com.anthonny.hoteljpa.reserva;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByClienteEmailIgnoreCaseOrderByIdAsc(String email);
    boolean existsByClienteId(Long clienteId);
}
