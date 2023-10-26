public abstract class Biblioteca extends Livro {
    protected int Valor;

    public Biblioteca(int codigo, String nome, String autor, String editora, int quantidade, int valor) {
        super(codigo, nome, autor, editora, quantidade);
        Valor = valor;
    }

    void cadastrar(int codigo){}
    void remover(int codigo){}
    void adicionar(int codigo){}
    void consultar(int codigo){}
    void comprar(int codigo){}
}
