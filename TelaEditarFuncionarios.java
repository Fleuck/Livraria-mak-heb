import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class TelaEditarFuncionarios extends JFrame {
    private JComboBox<String> funcionariosBox;
    private JTextField nomeFuncionarioField;
    private JComboBox<String> cargoFuncionarioBox;
    private JButton editarButton;

    public TelaEditarFuncionarios() {
        setTitle("Editar Funcionários");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel funcionarioLabel = new JLabel("Funcionário:");
        funcionariosBox = new JComboBox<>();
        carregarFuncionarios();

        JLabel nomeLabel = new JLabel("Novo Nome:");
        nomeFuncionarioField = new JTextField(20);

        JLabel cargoLabel = new JLabel("Novo Cargo:");
        cargoFuncionarioBox = new JComboBox<>();
        cargoFuncionarioBox.addItem("Gerente");
        cargoFuncionarioBox.addItem("Bibliotecário");

        editarButton = new JButton("Editar");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(funcionarioLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(funcionariosBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(nomeLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(nomeFuncionarioField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(cargoLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(cargoFuncionarioBox, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        panel.add(editarButton, gbc);

        funcionariosBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carregarDetalhesFuncionario((String) funcionariosBox.getSelectedItem());
            }
        });

        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarFuncionario((String) funcionariosBox.getSelectedItem());
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

    private void carregarDetalhesFuncionario(String nomeFuncionario) {
        String url = "jdbc:mysql://localhost:3306/makheb";
        String usuarioBD = "root";
        String senhaBD = "PUC@1234";

        try (Connection connection = DriverManager.getConnection(url, usuarioBD, senhaBD)) {
            String query = "SELECT * FROM funcionarios WHERE nomeFuncionario = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, nomeFuncionario);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    nomeFuncionarioField.setText(resultSet.getString("nomeFuncionario"));
                    cargoFuncionarioBox.setSelectedItem(resultSet.getString("cargo"));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void editarFuncionario(String nomeFuncionario) {
        String novoNome = nomeFuncionarioField.getText();
        String novoCargo = (String) cargoFuncionarioBox.getSelectedItem();

        if (!novoNome.isEmpty() && !novoCargo.isEmpty()) {
            realizarEdicaoFuncionario(nomeFuncionario, novoNome, novoCargo);
        } else {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
        }
    }

    private void realizarEdicaoFuncionario(String nomeFuncionario, String novoNome, String novoCargo) {
        String url = "jdbc:mysql://localhost:3306/makheb";
        String usuarioBD = "root";
        String senhaBD = "PUC@1234";

        try (Connection connection = DriverManager.getConnection(url, usuarioBD, senhaBD)) {
            String queryAtualizacao = "UPDATE funcionarios SET nomeFuncionario = ?, cargo = ? WHERE nomeFuncionario = ?";
            try (PreparedStatement atualizacaoStatement = connection.prepareStatement(queryAtualizacao)) {
                atualizacaoStatement.setString(1, novoNome);
                atualizacaoStatement.setString(2, novoCargo);
                atualizacaoStatement.setString(3, nomeFuncionario);
                int linhasAfetadas = atualizacaoStatement.executeUpdate();

                if (linhasAfetadas > 0) {
                    JOptionPane.showMessageDialog(this, "Funcionário atualizado com sucesso.");
                    funcionariosBox.removeItem(nomeFuncionario);
                    funcionariosBox.addItem(novoNome);
                    funcionariosBox.setSelectedItem(novoNome);
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao atualizar o funcionário.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaEditarFuncionarios();
        });
    }
}
