public abstract class Funcionarios extends Pessoas{
    protected String cargo;
    protected double salario;
    protected double id;
    public Funcionarios(String nome, String endereco, String Sobrenome, int cpf, String cargo, double salario, double id) {
        super(nome, endereco, cpf, Sobrenome);
        this.cargo = cargo;
        this.salario = salario;
        this.id = id;
    }
    abstract double vender(double valor);
    abstract void enviarLivro();

    @Override
    abstract void consultar();
    @Override
    abstract void pegar();




}