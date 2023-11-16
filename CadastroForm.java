import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CadastroForm extends JFrame {
    private JTextField nomeField;
    private JPasswordField senhaField;
    private JComboBox<String> tipoUsuarioBox;
    private JButton cadastrarButton;

    public CadastroForm() {
        setTitle("Cadastro de Usuário");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        JLabel nomeLabel = new JLabel("Nome:");
        nomeField = new JTextField(20);

        JLabel senhaLabel = new JLabel("Senha:");
        senhaField = new JPasswordField(20);

        JLabel tipoUsuarioLabel = new JLabel("Tipo de Usuário:");
        String[] tiposUsuario = {"Gerente", "Bibliotecário"};
        tipoUsuarioBox = new JComboBox<>(tiposUsuario);

        cadastrarButton = new JButton("Cadastrar");

        panel.add(nomeLabel);
        panel.add(nomeField);
        panel.add(senhaLabel);
        panel.add(senhaField);
        panel.add(tipoUsuarioLabel);
        panel.add(tipoUsuarioBox);
        panel.add(new JLabel());
        panel.add(cadastrarButton);

        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = nomeField.getText();
                char[] senhaChars = senhaField.getPassword();
                String senha = new String(senhaChars);
                String tipoUsuario = (String) tipoUsuarioBox.getSelectedItem();

                adicionarFuncionario(nome, senha, tipoUsuario);

                nomeField.setText("");
                senhaField.setText("");
            }
        });

        add(panel);
        setVisible(true);
    }

    private void adicionarFuncionario(String nome, String senha, String tipoUsuario) {

        String url = "jdbc:mysql://localhost:3306/makheb";
        String usuarioBD = "root";
        String senhaBD = "PUC@1234";

        try (Connection connection = DriverManager.getConnection(url, usuarioBD, senhaBD)) {
            String query = "INSERT INTO funcionarios (nomeFuncionario, senhaFuncionario, cargo) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, nome);
                preparedStatement.setString(2, senha);
                preparedStatement.setString(3, tipoUsuario);
                preparedStatement.executeUpdate();
                System.out.println("Funcionário adicionado com sucesso!");
                new LoginUsuario();
                dispose();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CadastroForm();
        });
    }
}
