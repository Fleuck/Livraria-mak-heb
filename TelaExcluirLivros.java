import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class TelaExcluirLivros extends JFrame {
    private JComboBox<String> livrosBox;
    private JButton excluirButton;

    public TelaExcluirLivros() {
        setTitle("Excluir Livros");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 150);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel livroLabel = new JLabel("Livro:");
        livrosBox = new JComboBox<>();
        carregarLivros();

        excluirButton = new JButton("Excluir");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(livroLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(livrosBox, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(excluirButton, gbc);

        livrosBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Atualiza o botão de exclusão com base na seleção do livro
                excluirButton.setEnabled(livrosBox.getItemCount() > 0);
            }
        });

        excluirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluirLivro((String) livrosBox.getSelectedItem());
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

    private void excluirLivro(String nomeLivro) {
        int confirmacao = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja excluir o livro?",
                "Confirmação de Exclusão",
                JOptionPane.YES_NO_OPTION);

        if (confirmacao == JOptionPane.YES_OPTION) {
            String url = "jdbc:mysql://localhost:3306/makheb";
            String usuarioBD = "root";
            String senhaBD = "PUC@1234";

            try (Connection connection = DriverManager.getConnection(url, usuarioBD, senhaBD)) {
                String query = "DELETE FROM Livro WHERE nome = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, nomeLivro);
                    int linhasAfetadas = preparedStatement.executeUpdate();

                    if (linhasAfetadas > 0) {
                        JOptionPane.showMessageDialog(this, "Livro excluído com sucesso.");
                        livrosBox.removeItem(nomeLivro);
                        excluirButton.setEnabled(false);
                    } else {
                        JOptionPane.showMessageDialog(this, "Erro ao excluir o livro.");
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaExcluirLivros();
        });
    }
}
