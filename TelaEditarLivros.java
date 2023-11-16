import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class TelaEditarLivros extends JFrame {
    private JComboBox<String> livrosBox;
    private JTextField nomeLivroField;
    private JTextField autorLivroField;
    private JTextField precoLivroField;
    private JButton editarButton;

    public TelaEditarLivros() {
        setTitle("Editar Livros");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel livroLabel = new JLabel("Livro:");
        livrosBox = new JComboBox<>();
        carregarLivros();

        JLabel nomeLabel = new JLabel("Nome:");
        nomeLivroField = new JTextField();
        nomeLivroField.setEditable(false);
        nomeLivroField.setPreferredSize(new Dimension(200, 25));

        JLabel autorLabel = new JLabel("Autor:");
        autorLivroField = new JTextField();
        autorLivroField.setPreferredSize(new Dimension(200, 25));

        JLabel precoLabel = new JLabel("Preço:");
        precoLivroField = new JTextField();
        precoLivroField.setPreferredSize(new Dimension(200, 25));

        editarButton = new JButton("Editar");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(livroLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(livrosBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(nomeLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(nomeLivroField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(autorLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(autorLivroField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        panel.add(precoLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(precoLivroField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(editarButton, gbc);

        livrosBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carregarDetalhesLivro((String) livrosBox.getSelectedItem());
            }
        });

        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarLivro();
            }
        });

        add(panel);
        setVisible(true);
    }

    private void carregarLivros() {
        String url = "jdbc:mysql://localhost:3306/makheb";
        String usuarioBD = "root";
        String senhaBD = "PUC@1234";

        try (Connection connection = DriverManager.getConnection(url, usuarioBD, senhaBD)) {
            String query = "SELECT nome FROM Livro";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    livrosBox.addItem(resultSet.getString("nome"));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void carregarDetalhesLivro(String nomeLivro) {
        String url = "jdbc:mysql://localhost:3306/makheb";
        String usuarioBD = "root";
        String senhaBD = "PUC@1234";

        try (Connection connection = DriverManager.getConnection(url, usuarioBD, senhaBD)) {
            String query = "SELECT * FROM Livro WHERE nome = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, nomeLivro);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    nomeLivroField.setText(resultSet.getString("nome"));
                    autorLivroField.setText(resultSet.getString("autor"));
                    precoLivroField.setText(String.valueOf(resultSet.getFloat("preco")));

                    // Habilitar a edição do campo de nome
                    nomeLivroField.setEditable(true);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void editarLivro() {
        String nomeLivro = nomeLivroField.getText();
        String novoAutor = autorLivroField.getText();
        String novoPrecoStr = precoLivroField.getText();

        if (!nomeLivro.isEmpty() && !novoAutor.isEmpty() && !novoPrecoStr.isEmpty()) {
            try {
                float novoPreco = Float.parseFloat(novoPrecoStr);
                realizarEdicao(nomeLivro, novoAutor, novoPreco);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, insira um preço válido.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
        }
    }

    private void realizarEdicao(String nomeLivro, String novoAutor, float novoPreco) {
        String url = "jdbc:mysql://localhost:3306/makheb";
        String usuarioBD = "root";
        String senhaBD = "PUC@1234";

        try (Connection connection = DriverManager.getConnection(url, usuarioBD, senhaBD)) {
            String query = "UPDATE Livro SET autor = ?, preco = ? WHERE nome = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, novoAutor);
                preparedStatement.setFloat(2, novoPreco);
                preparedStatement.setString(3, nomeLivro);
                int linhasAfetadas = preparedStatement.executeUpdate();

                if (linhasAfetadas > 0) {
                    JOptionPane.showMessageDialog(this, "Livro editado com sucesso.");
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao editar o livro.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaEditarLivros();
        });
    }
}
