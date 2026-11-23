package br.com.anthonny.hoteljpa.reserva;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/reservas")
public class ReservaController {
    private final ReservaService service;
    public ReservaController(ReservaService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<ReservaSaida> criar(@Valid @RequestBody ReservaEntrada entrada) {
        ReservaSaida saida = service.criar(entrada);
        return ResponseEntity.created(URI.create("/api/reservas/" + saida.id())).body(saida);
    }

    @GetMapping
    public List<ReservaSaida> listar(@RequestParam @NotBlank @Email String email) {
        return service.listarPorEmail(email);
    }

    @GetMapping("/{id}")
    public ReservaSaida buscar(@PathVariable Long id) { return service.buscar(id); }

    @PutMapping("/{id}")
    public ReservaSaida atualizar(@PathVariable Long id, @Valid @RequestBody ReservaEntrada entrada) {
        return service.atualizar(id, entrada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
