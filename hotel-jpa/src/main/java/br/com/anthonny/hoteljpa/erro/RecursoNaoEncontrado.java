package br.com.anthonny.hoteljpa.erro;

public class RecursoNaoEncontrado extends RuntimeException {
    public RecursoNaoEncontrado(String mensagem) { super(mensagem); }
}
