package br.com.Alura.screenmatch.models;

import br.com.Alura.screenmatch.services.traducao.ConsultaMyMemory;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "series")
public class Serie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private long id;
     private String titulo;
     private Integer totalTemporadas;
     private Double avaliacao;
     @Enumerated(EnumType.STRING)
     private Categoria genero;
     private String actors;
     private String poster;
     private  String sinopse;

     @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<Episodio> episodios = new ArrayList<>();

     public Serie(){};

     public Serie(DadosSerie d){
         this.titulo = d.titulo();
         this.totalTemporadas = d.totalTemporadas();
         this.avaliacao = converteAvaliacao(d.avaliacao());
         this.genero = Categoria.fromString(d.genero().split(",")[0].trim());
         this.actors = d.actors();
         this.poster = d.poster();
         this.sinopse = ConsultaMyMemory.obterTraducao(d.sinopse());
     }

    private Double converteAvaliacao(String avaliacao) {
        try {
            return Double.valueOf(avaliacao);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Episodio> getEpisodios() {
        return episodios;
    }

    public void setEpisodios(List<Episodio> episodios) {
         episodios.forEach(e -> e.setSerie(this));
         this.episodios = episodios;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getTotalTemporadas() {
        return totalTemporadas;
    }

    public void setTotalTemporadas(Integer totalTemporadas) {
        this.totalTemporadas = totalTemporadas;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Categoria getGenero() {
        return genero;
    }

    public void setGenero(Categoria genero) {
        this.genero = genero;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    @Override
    public String toString() {
        return "Serie{" +
                "genero=" + getGenero() +
                ", 1titulo='" + getTitulo() + '\'' +
                ", totalTemporadas=" + getTotalTemporadas() +
                ", avaliacao=" + getAvaliacao() +
                ", actors='" + getActors() + '\'' +
                ", poster='" + getPoster() + '\'' +
                ", sinopse='" + getSinopse() + '\'' +
                ", episódios='" + getEpisodios() + '\'' +
                '}';
    }
}
