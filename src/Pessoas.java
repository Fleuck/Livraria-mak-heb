public abstract class Pessoas {
    protected String nome;
    protected String endereco;
    protected int cpf;

    public Pessoas(String nome, String endereco, int cpf) {
        this.nome = nome;
        this.endereco = endereco;
        this.cpf = cpf;
    }

    abstract void consultar();
    abstract void pegar();




}

