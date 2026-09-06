package br.com.Alura.screenmatch.repository;

import br.com.Alura.screenmatch.models.Categoria;
import br.com.Alura.screenmatch.models.Episodio;
import br.com.Alura.screenmatch.models.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {

    Optional<Serie> findByTituloContainingIgnoreCase(String titulo);

    List<Serie> findByActorsContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(String nomeAtor, Double avaliacao);

    List<Serie> findTop5ByOrderByAvaliacaoDesc();

    List<Serie> findByGenero(Categoria categoria);

    List<Serie> findByTotalTemporadasLessThanEqualAndAvaliacaoGreaterThanEqual(int maxTemporadas, double avaliacaoMinima);

    @Query("SELECT s FROM Serie s WHERE s.totalTemporadas <= :maxTemporadas AND s.avaliacao >= :avaliacaoMinima")
    List<Serie> filtrarPorAvaliacaoETemp(int maxTemporadas, double avaliacaoMinima);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE e.titulo ILIKE %:trechoTitulo%")
    List<Episodio> episodiosPorTrecho(String trechoTitulo);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie ORDER BY e.avaliacao DESC LIMIT 5")
    List<Episodio> buscarTop5(Serie serie);

}
