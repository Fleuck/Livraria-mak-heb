public abstract class Livro {
    protected int Codigo;
    protected String Nome;
    protected String Autor;
    protected String Editora;
    protected int Quantidade;
    public Livro(int codigo, String nome, String autor, String editora, int quantidade) {
        Codigo = codigo;
        Nome = nome;
        Autor = autor;
        Editora = editora;
        Quantidade = quantidade;
    }
    abstract void cadastrar();
    abstract void remover();
    abstract void adicionar();
    abstract void consultar();
}