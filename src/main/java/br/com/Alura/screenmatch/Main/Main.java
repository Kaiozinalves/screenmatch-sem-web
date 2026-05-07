package br.com.Alura.screenmatch.Main;

import br.com.Alura.screenmatch.models.DadosEpisodio;
import br.com.Alura.screenmatch.models.DadosSerie;
import br.com.Alura.screenmatch.models.DadosTemporada;
import br.com.Alura.screenmatch.models.Episodio;
import br.com.Alura.screenmatch.services.ConsumoApi;
import br.com.Alura.screenmatch.services.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    Scanner leitura = new Scanner(System.in);
    ConsumoApi consumo = new ConsumoApi();
    ConverteDados converte = new ConverteDados();
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=8ce78692";



    public void exibeMenu(){

        System.out.println("Digite o nome da série que deseja pesquisar: ");
        String nomeSerie = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);

        DadosSerie dados = converte.obterDados(json, DadosSerie.class);
        System.out.println(dados);

        List<DadosTemporada> temporadas = new ArrayList<>();
        for(int i = 1; i <= dados.totalTemporadas(); i++){
            json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + "&season=" + i + API_KEY);
            DadosTemporada dadosTemporada = converte.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);

        }
//
//        temporadas.forEach(System.out::println);
//
//        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));


        List<DadosEpisodio> dadosEpisodios = temporadas.stream().flatMap(t -> t.episodios().stream())
                .toList();


        System.out.println("\nOs 5 episódios mais bem avaliados: ");
        dadosEpisodios.stream().sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                .limit(5)
                .forEach(System.out::println);

//        dadosEpisodios.forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                .map(d -> new Episodio(Integer.parseInt(t.numero()), d)))
                .collect(Collectors.toList());

        episodios.forEach(System.out::println);


    }


}
