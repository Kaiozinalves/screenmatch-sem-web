package br.com.Alura.screenmatch.services;

public interface IObterDados {
    public <T> T obterDados(String json, Class<T> classe);
}
