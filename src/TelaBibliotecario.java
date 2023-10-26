import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class TelaBibliotecario extends JFrame {
    private JTextArea estoqueTextArea;
    private JButton venderLivroButton;
    private JTextArea historicoVendasTextArea;

    private List<Livro> estoque;
    private List<Venda> historicoVendas;

    public TelaBibliotecario() {
        setTitle("Tela do Bibliotecário");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        estoque = new ArrayList<>();
        estoque.add(new Livro("Livro 1", 10, 20.0));
        estoque.add(new Livro("Livro 2", 5, 15.0));
        estoque.add(new Livro("Livro 3", 8, 18.0));

        historicoVendas = new ArrayList<>();

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        estoqueTextArea = new JTextArea(10, 30);
        estoqueTextArea.setEditable(false);

        venderLivroButton = new JButton("Vender Livro");

        historicoVendasTextArea = new JTextArea(10, 30);
        historicoVendasTextArea.setEditable(false);

        panel.add(new JLabel("Livros no Estoque:"));
        panel.add(new JLabel("Histórico de Vendas:"));
        panel.add(new JScrollPane(estoqueTextArea));
        panel.add(new JScrollPane(historicoVendasTextArea));
        panel.add(venderLivroButton);

        exibirEstoque();

        venderLivroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                venderLivro();
            }
        });

        add(panel);
        setVisible(true);
    }

    private void exibirEstoque() {
        StringBuilder estoqueStr = new StringBuilder();
        for (Livro livro : estoque) {
            estoqueStr.append(livro.toString()).append("\n");
        }
        estoqueTextArea.setText(estoqueStr.toString());
    }

    private void venderLivro() {
        String livroSelecionado = JOptionPane.showInputDialog(this, "Digite o nome do livro que deseja vender:");
        for (Livro livro : estoque) {
            if (livro.getNome().equalsIgnoreCase(livroSelecionado)) {
                int quantidade = livro.getQuantidade();
                if (quantidade > 0) {
                    livro.setQuantidade(quantidade - 1);
                    historicoVendas.add(new Venda(livro, 1, livro.getPreco()));
                    exibirEstoque();
                    exibirHistoricoVendas();
                    JOptionPane.showMessageDialog(this, "Venda realizada com sucesso!");
                } else {
                    JOptionPane.showMessageDialog(this, "Livro fora de estoque.");
                }
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Livro não encontrado no estoque.");
    }

    private void exibirHistoricoVendas() {
        StringBuilder historicoStr = new StringBuilder();
        for (Venda venda : historicoVendas) {
            historicoStr.append(venda.toString()).append("\n");
        }
        historicoVendasTextArea.setText(historicoStr.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaBibliotecario();
        });
    }

    private class Livro {
        private String nome;
        private int quantidade;
        private double preco;

        public Livro(String nome, int quantidade, double preco) {
            this.nome = nome;
            this.quantidade = quantidade;
            this.preco = preco;
        }

        public String getNome() {
            return nome;
        }

        public int getQuantidade() {
            return quantidade;
        }

        public void setQuantidade(int quantidade) {
            this.quantidade = quantidade;
        }

        public double getPreco() {
            return preco;
        }

        @Override
        public String toString() {
            return nome + " - Quantidade: " + quantidade + " - Preço: R$" + preco;
        }
    }

    private class Venda {
        private Livro livro;
        private int quantidadeVendida;
        private double valorTotal;

        public Venda(Livro livro, int quantidadeVendida, double valorTotal) {
            this.livro = livro;
            this.quantidadeVendida = quantidadeVendida;
            this.valorTotal = valorTotal;
        }

        @Override
        public String toString() {
            return "Livro: " + livro.getNome() + " - Quantidade Vendida: " + quantidadeVendida + " - Valor Total: R$" + valorTotal;
        }
    }
}
