package br.com.anthonny.hoteljpa.reserva;

import br.com.anthonny.hoteljpa.cliente.Cliente;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "reservas")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Column(name = "data_chegada", nullable = false)
    private LocalDate dataChegada;

    @Column(name = "numero_noites", nullable = false)
    private Integer numeroNoites;

    @Column(name = "numero_hospedes", nullable = false)
    private Integer numeroHospedes;

    @Column(length = 2000)
    private String mensagem;

    protected Reserva() {}

    public Reserva(Cliente cliente, LocalDate dataChegada, Integer numeroNoites,
                   Integer numeroHospedes, String mensagem) {
        atualizar(cliente, dataChegada, numeroNoites, numeroHospedes, mensagem);
    }

    public Long getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public LocalDate getDataChegada() { return dataChegada; }
    public Integer getNumeroNoites() { return numeroNoites; }
    public Integer getNumeroHospedes() { return numeroHospedes; }
    public String getMensagem() { return mensagem; }

    public void atualizar(Cliente cliente, LocalDate dataChegada, Integer numeroNoites,
                         Integer numeroHospedes, String mensagem) {
        this.cliente = cliente;
        this.dataChegada = dataChegada;
        this.numeroNoites = numeroNoites;
        this.numeroHospedes = numeroHospedes;
        this.mensagem = mensagem;
    }
}
