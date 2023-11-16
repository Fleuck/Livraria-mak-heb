import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class LoginUsuario extends JFrame {
    private JTextField nomeField;
    private JPasswordField senhaField;
    private JComboBox<String> categoriaBox;
    private JButton loginButton;

    public LoginUsuario() {
        setTitle("Login de Usuário");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        JLabel nomeLabel = new JLabel("Nome de Usuário:");
        nomeField = new JTextField(20);

        JLabel senhaLabel = new JLabel("Senha:");
        senhaField = new JPasswordField(20);

        JLabel categoriaLabel = new JLabel("Categoria:");
        String[] categorias = {"Gerente", "Bibliotecário"};
        categoriaBox = new JComboBox<>(categorias);

        loginButton = new JButton("Login");

        panel.add(nomeLabel);
        panel.add(nomeField);
        panel.add(senhaLabel);
        panel.add(senhaField);
        panel.add(categoriaLabel);
        panel.add(categoriaBox);
        panel.add(new JLabel());
        panel.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomeUsuario = nomeField.getText();
                char[] senha = senhaField.getPassword();
                String categoria = (String) categoriaBox.getSelectedItem();

                if (autenticarUsuario(nomeUsuario, senha, categoria)) {
                    JOptionPane.showMessageDialog(LoginUsuario.this, "Login bem-sucedido como " + categoria + "!");
                    if (categoria.equals("Gerente")) {
                        new TelaGerente();
                    } else if (categoria.equals("Bibliotecário")) {
                        new TelaCadastroLivros();
                    }
                } else {
                    JOptionPane.showMessageDialog(LoginUsuario.this, "Falha na autenticação. Tente novamente.");
                }
            }
        });

        add(panel);
        setVisible(true);
    }

    private boolean autenticarUsuario(String nomeUsuario, char[] senha, String categoria) {
        String url = "jdbc:mysql://localhost:3306/makheb";
        String usuarioBD = "root";
        String senhaBD = "PUC@1234";

        try (Connection connection = DriverManager.getConnection(url, usuarioBD, senhaBD)) {
            String query = "SELECT * FROM funcionarios WHERE nomeFuncionario = ? AND senhaFuncionario = ? AND cargo = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, nomeUsuario);
                preparedStatement.setString(2, new String(senha));
                preparedStatement.setString(3, categoria);

                ResultSet resultSet = preparedStatement.executeQuery();
                return resultSet.next();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginUsuario();
        });
    }
}
