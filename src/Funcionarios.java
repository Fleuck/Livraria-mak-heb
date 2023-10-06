public abstract class Funcionarios extends Pessoas{
    protected String cargo;
    protected double salario;
    protected double id;
    public Funcionarios(String nome, String endereco, int cpf, String cargo, double salario) {
        super(nome, endereco, cpf);
        this.cargo = cargo;
        this.salario = salario;
    }
    abstract double vender(double valor);
    abstract void enviarLivro();

    @Override
    abstract void consultar();
    @Override
    abstract void pegar();




}
