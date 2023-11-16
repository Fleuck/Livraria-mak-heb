public abstract class Funcionario implements Usuario {
    private String nome;
    private String cargo;

    public Funcionario(String nome, String cargo) {
        this.nome = nome;
        this.cargo = cargo;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public String getCargo() {
        return cargo;
    }

    // Método da interface
    @Override
    public void realizarLogin(String nome, char[] senha) {
        // Lógica de login para funcionários
    }

    // Método abstrato para ser implementado nas subclasses
    public abstract void realizarOperacao();
}
