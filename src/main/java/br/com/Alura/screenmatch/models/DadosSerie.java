package br.com.Alura.screenmatch.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosSerie(@JsonAlias("Title") String titulo,
                         @JsonAlias("totalSeasons") Integer totalTemporadas,
                         @JsonAlias("imdbRating") String avaliacao,
                         @JsonAlias("Genre") String genero,
                         @JsonAlias("Actors") String actors,
                         @JsonAlias("Poster") String poster,
                         @JsonAlias("Plot") String sinopse) {

    @Override
    public String toString() {
        return String.format("""

                Serie encontrada
                Titulo: %s
                Total de temporadas: %d
                Avaliacao: %s
                Sinopse: %s
                """, titulo, totalTemporadas, avaliacao, sinopse);
    }
}
