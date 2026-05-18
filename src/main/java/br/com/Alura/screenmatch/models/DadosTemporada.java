package br.com.Alura.screenmatch.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosTemporada(@JsonAlias("Season") String numero,
                             @JsonAlias("Episodes") List<DadosEpisodio> episodios) {

    @Override
    public String toString() {
        var episodiosFormatados = episodios == null || episodios.isEmpty()
                ? "Nenhum episodio encontrado."
                : episodios.stream()
                .map(episodio -> "  " + episodio)
                .collect(Collectors.joining("\n"));

        return String.format("""
                
                Temporada %s
                %s
                """, numero, episodiosFormatados);
    }
}
