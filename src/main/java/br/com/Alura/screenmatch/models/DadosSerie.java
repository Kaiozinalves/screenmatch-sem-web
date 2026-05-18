package br.com.Alura.screenmatch.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosSerie(@JsonAlias("Title") String titulo,
                         @JsonAlias("totalSeasons") Integer totalTemporadas,
                         @JsonAlias("imdbRating") String avaliacao) {

    @Override
    public String toString() {
        return String.format("""
1
                Serie encontrada
                Titulo: %s
                Total de temporadas: %d
                Avaliacao: %s
                """, titulo, totalTemporadas, avaliacao);
    }
}
