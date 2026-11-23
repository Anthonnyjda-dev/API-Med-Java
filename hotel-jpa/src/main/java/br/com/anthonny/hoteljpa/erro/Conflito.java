package br.com.anthonny.hoteljpa.erro;

public class Conflito extends RuntimeException {
    public Conflito(String mensagem) { super(mensagem); }
}
