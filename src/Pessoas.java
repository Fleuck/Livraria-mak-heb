public abstract class Pessoas {
    protected String nome;
    protected String endereco;
    protected int cpf;
    protected String Sobrenome;


    public Pessoas(String nome, String endereco, int cpf, String Sobrenome) {
        this.nome = nome;
        this.endereco = endereco;
        this.cpf = cpf;
        this.Sobrenome = Sobrenome;
    }

    abstract void consultar();
    abstract void pegar();
}
