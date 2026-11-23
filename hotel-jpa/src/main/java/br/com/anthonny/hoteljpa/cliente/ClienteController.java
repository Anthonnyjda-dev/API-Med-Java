package br.com.anthonny.hoteljpa.cliente;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    private final ClienteService service;
    public ClienteController(ClienteService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<ClienteSaida> criar(@Valid @RequestBody ClienteEntrada entrada) {
        ClienteSaida saida = service.criar(entrada);
        return ResponseEntity.created(URI.create("/api/clientes/" + saida.id())).body(saida);
    }

    @GetMapping
    public List<ClienteSaida> listar() { return service.listar(); }

    @GetMapping("/{id}")
    public ClienteSaida buscar(@PathVariable Long id) { return service.buscar(id); }

    @PutMapping("/{id}")
    public ClienteSaida atualizar(@PathVariable Long id, @Valid @RequestBody ClienteEntrada entrada) {
        return service.atualizar(id, entrada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
