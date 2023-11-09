import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class TelaComprador extends JFrame {
    private JComboBox<Fornecedor> fornecedoresBox; // ComboBox para selecionar fornecedores
    private JTextArea livrosDisponiveisTextArea; // TextArea para mostrar livros disponíveis
    private JButton comprarLivroButton; // Botão para comprar livros
    private JTextArea historicoComprasTextArea; // TextArea para mostrar histórico de compras
    private List<Fornecedor> fornecedores; // Lista de fornecedores
    private List<Compra> historicoCompras; // Lista de compras

    public TelaComprador() {
        // Configuração da janela principal
        setTitle("Tela do Comprador");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Inicialização das listas
        fornecedores = new ArrayList<>();
        fornecedores.add(new Fornecedor("Fornecedor A"));
        fornecedores.add(new Fornecedor("Fornecedor B"));
        fornecedores.add(new Fornecedor("Fornecedor C"));

        historicoCompras = new ArrayList<>();

        // Configuração do painel principal
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        // Componentes de interface
        fornecedoresBox = new JComboBox<>(fornecedores.toArray(new Fornecedor[0]));
        livrosDisponiveisTextArea = new JTextArea(10, 30);
        livrosDisponiveisTextArea.setEditable(false);
        comprarLivroButton = new JButton("Comprar Livro");
        historicoComprasTextArea = new JTextArea(10, 30);
        historicoComprasTextArea.setEditable(false);

        // Adiciona os componentes ao painel
        panel.add(new JLabel("Selecione o Fornecedor:"));
        panel.add(fornecedoresBox);
        panel.add(new JLabel("Livros Disponíveis:"));
        panel.add(new JScrollPane(livrosDisponiveisTextArea));
        panel.add(comprarLivroButton);
        panel.add(new JLabel("Histórico de Compras:"));
        panel.add(new JScrollPane(historicoComprasTextArea));

        // Adiciona ouvintes de ação para os componentes
        fornecedoresBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exibirLivrosDisponiveis();
            }
        });

        comprarLivroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comprarLivro();
            }
        });

        // Adiciona o painel à janela
        add(panel);
        setVisible(true);
        setVisible(true);
    }

    // Exibe os livros disponíveis para o fornecedor selecionado
    private void exibirLivrosDisponiveis() {
        Fornecedor fornecedorSelecionado = (Fornecedor) fornecedoresBox.getSelectedItem();
        if (fornecedorSelecionado != null) {
            StringBuilder livrosDisponiveisStr = new StringBuilder();
            for (Livro livro : fornecedorSelecionado.getLivrosDisponíveis()) {
                livrosDisponiveisStr.append(livro.toString()).append("\n");
            }
            livrosDisponiveisTextArea.setText(livrosDisponiveisStr.toString());
        }
    }

    // Permite ao usuário comprar um livro
    private void comprarLivro() {
        Fornecedor fornecedorSelecionado = (Fornecedor) fornecedoresBox.getSelectedItem();
        if (fornecedorSelecionado != null) {
            Livro livroSelecionado = (Livro) JOptionPane.showInputDialog(this, "Selecione o livro que deseja comprar:", "Compra de Livro", JOptionPane.QUESTION_MESSAGE, null, fornecedorSelecionado.getLivrosDisponíveis().toArray(), null);

            if (livroSelecionado != null) {
                fornecedorSelecionado.removerLivroDisponível(livroSelecionado);
                historicoCompras.add(new Compra(livroSelecionado));
                exibirLivrosDisponiveis();
                exibirHistoricoCompras();
                JOptionPane.showMessageDialog(this, "Compra realizada com sucesso!");
            }
        }
    }

    // Exibe o histórico de compras
    private void exibirHistoricoCompras() {
        StringBuilder historicoComprasStr = new StringBuilder();
        for (Compra compra : historicoCompras) {
            historicoComprasStr.append(compra.toString()).append("\n");
        }
        historicoComprasTextArea.setText(historicoComprasStr.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaComprador();
        });
    }

    public void setSistemaBiblioteca(SistemaBiblioteca sistemaBiblioteca) {
    }

    // Classe interna que representa um fornecedor
    private class Fornecedor {
        private String nome;
        private List<Livro> livrosDisponiveis;

        public Fornecedor(String nome) {
            this.nome = nome;
            livrosDisponiveis = new ArrayList<>();
            // Adicione alguns livros de exemplo
            livrosDisponiveis.add(new Livro("Livro 1", 20.0));
            livrosDisponiveis.add(new Livro("Livro 2", 15.0));
            livrosDisponiveis.add(new Livro("Livro 3", 18.0));
        }

        public String getNome() {
            return nome;
        }

        public List<Livro> getLivrosDisponíveis() {
            return livrosDisponiveis;
        }

        public void removerLivroDisponível(Livro livro) {
            livrosDisponiveis.remove(livro);
        }

        @Override
        public String toString() {
            return nome;
        }
    }

    // Classe interna que representa um livro
    private class Livro {
        private String nome;
        private double preco;

        public Livro(String nome, double preco) {
            this.nome = nome;
            this.preco = preco;
        }

        public String getNome() {
            return nome;
        }

        public double getPreço() {
            return preco;
        }

        @Override
        public String toString() {
            return nome + " - Preço: R$" + preco;
        }
    }

    // Classe interna que representa uma compra
    private class Compra {
        private Livro livro;

        public Compra(Livro livro) {
            this.livro = livro;
        }

        @Override
        public String toString() {
            return "Livro Comprado: " + livro.getNome() + " - Preço: R$" + livro.getPreço();
        }
    }
}
