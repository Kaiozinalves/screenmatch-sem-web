package br.com.Alura.screenmatch.models;

public enum Categoria {
    CRIME("Crime", "Crime"),
    DRAMA("Drama", "Drama"),
    ACAO("Action", "Ação"),
    COMEDIA("Comedy", "Comédia"),
    ROMANCE("Romance", "Romance"),;

    private String categoriaEmPT;
    private String categoriaOmdb;
    Categoria(String categoriaOmdb, String categoriaEmPT) {
        this.categoriaEmPT = categoriaEmPT;
        this.categoriaOmdb = categoriaOmdb;
    }

    public static Categoria fromString(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaOmdb.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }

    public static Categoria fromPT(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaEmPT.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }

}
