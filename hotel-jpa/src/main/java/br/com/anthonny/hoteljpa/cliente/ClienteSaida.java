package br.com.anthonny.hoteljpa.cliente;

public record ClienteSaida(Long id, String nome, String sexo, String email) {
    public static ClienteSaida de(Cliente cliente) {
        return new ClienteSaida(cliente.getId(), cliente.getNome(), cliente.getSexo(), cliente.getEmail());
    }
}
