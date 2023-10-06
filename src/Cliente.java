public class Cliente extends Pessoas {

    protected String email;
    protected String senha;
    protected String telefone;
    protected String cep;

    public Cliente(String nome, String endereco, int cpf, String email, String senha, String telefone, String cep) {
        super(nome, endereco, cpf);
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.cep = cep;
    }

    @Override
    void consultar() {

    }

    @Override
    void pegar() {

    }
    public void cadastrar(){

    }
    public void efetuarPedidoCliente(){

    }

    public void efutarPagamento(){

    }
}
