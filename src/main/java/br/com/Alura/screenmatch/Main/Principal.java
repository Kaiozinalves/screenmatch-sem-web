package br.com.Alura.screenmatch.Main;

import br.com.Alura.screenmatch.models.*;
import br.com.Alura.screenmatch.repository.SerieRepository;
import br.com.Alura.screenmatch.services.ConsumoApi;
import br.com.Alura.screenmatch.services.ConverteDados;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=6585022c";
    private List<DadosSerie>  dadosSeries = new ArrayList<>();
    private SerieRepository repositorio;
    private Optional<Serie> serieBusca;
    List<Serie> series = new ArrayList<>();

    public Principal(SerieRepository repositorio) {
        this.repositorio = repositorio;
    }

    public void exibeMenu() {

        var opcao = -1;

        while (opcao != 0) {
            var menu = """
                    1 - Buscar séries
                    2 - Buscar episódios
                    3 - Listar séries
                    4 - Buscar série por título
                    5 - Buscar série por ator
                    6 - Buscar top5 séries 
                    7 - Buscar série por categoria
                    8 - Buscar série por número máximo de temporadas
                    9 - Buscar episódio por trecho de título
                    10 - Buscar top5 episódios da série
                    
                    0 - Sair
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    listarSeriesBuscadas();
                    break;
                case 4:
                    buscarSerieTitulo();
                    break;
                case 5:
                    buscarSeriePorAtor();
                    break;
                case 6:
                    buscarTop5Series();
                    break;
                case 7:
                    buscarSeriePOrCategoria();
                    break;
                case 8:
                    buscarPorQntdTemporadas();
                    break;
                case 9:
                    buscarSeriePorTrechoTitulo();
                    break;
                case 10:
                    buscarTop5Episodios();
                    break;

                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void buscarTop5Episodios() {
        buscarSerieTitulo();
        if(serieBusca.isPresent()){
            Serie serie = serieBusca.get();
            List<Episodio> top5Episodios = repositorio.buscarTop5(serie);
            top5Episodios.forEach(e -> System.out.println("Série: " + e.getSerie().getTitulo() + " | Temporada: " + e.getTemporada() + " | Episódio: " + e.getNumeroEpisodio() + " | Título: " + e.getTitulo() + e.getAvaliacao()));
        }
    }

    private void buscarSeriePorTrechoTitulo() {
        System.out.println("Digite o trecho do título da série para busca");
        var trechoTitulo = leitura.nextLine();
        List<Episodio> episodiosEncontrados = repositorio.episodiosPorTrecho(trechoTitulo);
        episodiosEncontrados.forEach(
                e -> {
                    System.out.println("Série: " + e.getSerie().getTitulo() + " | Temporada: " + e.getTemporada() + " | Episódio: " + e.getNumeroEpisodio() + " | Título: " + e.getTitulo());
                }
        );
    }

    private void buscarPorQntdTemporadas() {
        System.out.println("Digite o número máximo de temporadas: ");
        int maxTemporadas = leitura.nextInt();
        System.out.println("Digite a avaliação mínima: ");
        double avaliacaoMinima = leitura.nextDouble();
        List<Serie> seriesEncontradas = repositorio.filtrarPorAvaliacaoETemp(maxTemporadas, avaliacaoMinima);
        seriesEncontradas.forEach(s -> System.out.println(s.getTitulo() + " | Temporadas: " + s.getTotalTemporadas() + " | Avaliação: " + s.getAvaliacao()));

    }

    private void buscarSeriePOrCategoria() {
        System.out.println("Digite a categoria para a busca");
        var categoria = leitura.nextLine();
        Categoria categoria1 = Categoria.fromPT(categoria);
        List<Serie> seriesEncontradas = repositorio.findByGenero(categoria1);

        if (seriesEncontradas.isEmpty()) {
            System.out.println("Nenhuma série encontrada para a categoria informada.");
            return;
        }

        seriesEncontradas.forEach(s -> System.out.println(s.getTitulo() + " | Gênero: " + s.getGenero()));
    }

    private void buscarTop5Series() {
        List<Serie> topSeries = repositorio.findTop5ByOrderByAvaliacaoDesc();
        System.out.println("Top 5 séries com maior avaliação:");
        topSeries.forEach(s -> System.out.println(s.getTitulo() + " | Avaliação: " + s.getAvaliacao()));

    }


    private void listarSeriesBuscadas() {
        series = repositorio.findAll();

        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }

    private void buscarSerieWeb() {
        DadosSerie dados = getDadosSerie();
        Serie serie = new Serie(dados);
        System.out.println(dados);
        repositorio.save(serie);
    }

    private DadosSerie getDadosSerie() {
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        dadosSeries.add(dados);
        return dados;
    }

    private void buscarEpisodioPorSerie() {
        listarSeriesBuscadas();
        System.out.println("Digite o nome da série para buscar os episódios");
        var nomeSerie = leitura.nextLine();
        Optional<Serie> serie = repositorio.findByTituloContainingIgnoreCase(nomeSerie);

        if(serie.isPresent()){
            var serieEncontrada = serie.get();
            List<DadosTemporada> temporadas = new ArrayList<>();
            for(int i = 1; i < serieEncontrada.getTotalTemporadas(); i++){
                var json = consumo.obterDados(ENDERECO + serieEncontrada.getTitulo().replace(" ", "+") + "&Season=" + i + API_KEY);
                var dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
                temporadas.add(dadosTemporada);
            }
            List<Episodio> episodios = temporadas.stream().flatMap(t -> t.episodios().stream()
                    .map(e -> new Episodio(e.numero(), e)))
                    .collect(Collectors.toList());

            episodios.forEach(System.out::println);

            serieEncontrada.setEpisodios(episodios);
            repositorio.save(serieEncontrada);
    }

    }

    private void buscarSerieTitulo() {
        System.out.println("Digite o nome da série para busca");
        series = repositorio.findAll();
        series.forEach(s -> System.out.println(s.getTitulo()));
        var nomeSerieBuscada = leitura.nextLine();


        serieBusca = repositorio.findByTituloContainingIgnoreCase(nomeSerieBuscada);

        if(serieBusca.isPresent()){
            System.out.println("Dados da série: ");
            System.out.println(serieBusca.get());
        }else{
            System.out.println("Série não encontrada!");
        }

    }

    private void buscarSeriePorAtor() {
        System.out.println("Digite o nome do ator para busca");
        var nomeAtor = leitura.nextLine();
        System.out.println("Deseja buscar a partir de qual avaliação? ");
        var avaliacao = leitura.nextDouble();
        List<Serie> seriesEncontradas = repositorio.findByActorsContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(nomeAtor, avaliacao);
        seriesEncontradas.forEach(s ->
                System.out.println(s.getTitulo() + " | Avaliação: " + s.getAvaliacao()));
    }

}
