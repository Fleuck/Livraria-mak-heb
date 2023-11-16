public class Livro {
    private String titulo;
    private String autor;
    private double preco;
    private String capaUrl;

    public Livro(String titulo, String autor, double preco, String capaUrl) {
        this.titulo = titulo;
        this.autor = autor;
        this.preco = preco;
        this.capaUrl = capaUrl;
    }

    // Getters e Setters
    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public double getPreco() {
        return preco;
    }

    public String getCapaUrl() {
        return capaUrl;
    }
}
