package br.com.anthonny.hoteljpa.cliente;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false, length = 30)
    private String sexo;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    protected Cliente() {}

    public Cliente(String nome, String sexo, String email) {
        this.nome = nome;
        this.sexo = sexo;
        this.email = email;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getSexo() { return sexo; }
    public String getEmail() { return email; }

    public void atualizar(String nome, String sexo, String email) {
        this.nome = nome;
        this.sexo = sexo;
        this.email = email;
    }
}
