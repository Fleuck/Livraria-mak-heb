import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class TelaExcluirFuncionarios extends JFrame {
    private JComboBox<String> funcionariosBox;
    private JButton excluirButton;

    public TelaExcluirFuncionarios() {
        setTitle("Excluir Funcionários");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 150);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel funcionarioLabel = new JLabel("Funcionário:");
        funcionariosBox = new JComboBox<>();
        carregarFuncionarios();

        excluirButton = new JButton("Excluir");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(funcionarioLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(funcionariosBox, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(excluirButton, gbc);

        excluirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluirFuncionario((String) funcionariosBox.getSelectedItem());
            }
        });

        add(panel);
        setVisible(true);
    }

    private void carregarFuncionarios() {
        String url = "jdbc:mysql://localhost:3306/makheb";
        String usuarioBD = "root";
        String senhaBD = "PUC@1234";

        try (Connection connection = DriverManager.getConnection(url, usuarioBD, senhaBD)) {
            String query = "SELECT nomeFuncionario FROM funcionarios";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    funcionariosBox.addItem(resultSet.getString("nomeFuncionario"));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void excluirFuncionario(String nomeFuncionario) {
        int opcao = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir o funcionário?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (opcao == JOptionPane.YES_OPTION) {
            String url = "jdbc:mysql://localhost:3306/makheb";
            String usuarioBD = "root";
            String senhaBD = "PUC@1234";

            try (Connection connection = DriverManager.getConnection(url, usuarioBD, senhaBD)) {
                String queryExclusao = "DELETE FROM funcionarios WHERE nomeFuncionario = ?";
                try (PreparedStatement exclusaoStatement = connection.prepareStatement(queryExclusao)) {
                    exclusaoStatement.setString(1, nomeFuncionario);
                    int linhasAfetadas = exclusaoStatement.executeUpdate();

                    if (linhasAfetadas > 0) {
                        JOptionPane.showMessageDialog(this, "Funcionário excluído com sucesso.");
                        funcionariosBox.removeItem(nomeFuncionario);
                    } else {
                        JOptionPane.showMessageDialog(this, "Erro ao excluir o funcionário.");
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaExcluirFuncionarios();
        });
    }
}
