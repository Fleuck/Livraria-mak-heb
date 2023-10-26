public class Gerente extends Funcionarios{

    public Gerente(String nome, String fone, String email, double salario, String cargo, int id) {
        super(nome, fone, email, salario, cargo, id);
    }

    @Override
    double vender(double valor) {
        return 0;
    }

    @Override
    void enviarLivro() {

    }

    @Override
    void consultar() {

    }

    @Override
    void pegar() {

    }
}
