import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TelaCadastroLivros extends JFrame {
    private JTextField tituloField;
    private JTextField autorField;
    private JTextField precoField;
    private JTextField capaField;
    private JButton cadastrarLivroButton;
    private JButton abrirTelaVendasButton;

    public TelaCadastroLivros() {
        this.setTitle("Cadastro de Livros");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(400, 300);
        this.setLocationRelativeTo((Component) null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel tituloLabel = new JLabel("Título:");
        tituloField = new JTextField(20);

        JLabel autorLabel = new JLabel("Autor:");
        autorField = new JTextField(20);

        JLabel precoLabel = new JLabel("Preço:");
        precoField = new JTextField(10);

        JLabel capaLabel = new JLabel("URL da Capa (opcional):");
        capaField = new JTextField(30);

        cadastrarLivroButton = new JButton("Cadastrar Livro");
        abrirTelaVendasButton = new JButton("Abrir Tela de Vendas");

        panel.add(tituloLabel);
        panel.add(tituloField);
        panel.add(autorLabel);
        panel.add(autorField);
        panel.add(precoLabel);
        panel.add(precoField);
        panel.add(capaLabel);
        panel.add(capaField);
        panel.add(cadastrarLivroButton);
        panel.add(abrirTelaVendasButton);

        cadastrarLivroButton.addActionListener(e -> cadastrarLivro());
        abrirTelaVendasButton.addActionListener(e -> abrirTelaVendas());

        this.add(panel);
        this.setVisible(true);
    }

    private void cadastrarLivro() {
        String titulo = tituloField.getText();
        String autor = autorField.getText();
        String precoStr = precoField.getText();
        String capaUrl = capaField.getText();

        if (titulo.isEmpty() || autor.isEmpty() || precoStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha pelo menos os campos obrigatórios (Título, Autor, Preço).");
            return;
        }

        try {
            double preco = Double.parseDouble(precoStr);

            // Lógica para cadastrar o livro no estoque
            if (capaUrl.isEmpty()) {
                // Inserir sem URL da capa
                if (inserirLivro(titulo, autor, preco, null)) {
                    JOptionPane.showMessageDialog(this, "Livro cadastrado com sucesso.");
                    this.dispose();  // Fecha a janela após cadastrar o livro
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao cadastrar o livro.");
                }
            } else {
                // Inserir com URL da capa
                if (inserirLivro(titulo, autor, preco, capaUrl)) {
                    JOptionPane.showMessageDialog(this, "Livro cadastrado com sucesso.");
                    this.dispose();  // Fecha a janela após cadastrar o livro
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao cadastrar o livro.");
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Formato inválido para preço.");
        }
    }

    private boolean inserirLivro(String titulo, String autor, double preco, String capaUrl) {
        String url = "jdbc:mysql://localhost:3306/makheb";
        String usuarioBD = "root";
        String senhaBD = "PUC@1234";

        try (Connection connection = DriverManager.getConnection(url, usuarioBD, senhaBD)) {
            String query;
            if (capaUrl == null) {
                query = "INSERT INTO Livro (nome, autor, preco) VALUES (?, ?, ?)";
            } else {
                query = "INSERT INTO Livro (nome, autor, preco, capa_url) VALUES (?, ?, ?, ?)";
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, titulo);
                preparedStatement.setString(2, autor);
                preparedStatement.setDouble(3, preco);
                if (capaUrl != null) {
                    preparedStatement.setString(4, capaUrl);
                }
                preparedStatement.executeUpdate();
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private void abrirTelaVendas() {
        new TelaVendaLivros();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaCadastroLivros();
        });
    }
}
