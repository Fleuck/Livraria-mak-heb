public abstract class Pessoas {
    protected String nome;
    protected String fone;
    protected String email;

    public Pessoas(String nome, String fone, String email) {
        this.nome = nome;
        this.fone = fone;
        this.email = email;
    }

    abstract void consultar();
    abstract void pegar();
}
