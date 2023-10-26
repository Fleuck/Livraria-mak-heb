public abstract class Funcionarios extends Pessoas{
    protected double salario;
    protected String cargo;
    protected int id;

    public Funcionarios(String nome, String fone, String email, double salario, String cargo, int id) {
        super(nome, fone, email);
        this.salario = salario;
        this.cargo = cargo;
        this.id = id;
    }

    abstract double vender(double valor);
    abstract void enviarLivro();

    @Override
    abstract void consultar();
    @Override
    abstract void pegar();




}