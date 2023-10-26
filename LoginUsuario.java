import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginUsuario extends JFrame {
    private JTextField nomeField;
    private JPasswordField senhaField;
    private JComboBox<String> categoriaBox;
    private JButton loginButton;

    public LoginUsuario() {
        setTitle("Login de Usuário");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        JLabel nomeLabel = new JLabel("Nome de Usuário:");
        nomeField = new JTextField(20);

        JLabel senhaLabel = new JLabel("Senha:");
        senhaField = new JPasswordField(20);

        JLabel categoriaLabel = new JLabel("Categoria:");
        String[] categorias = {"Gerente", "Comprador", "Bibliotecário"};
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
                        // Abra a página do gerente
                    } else if (categoria.equals("Comprador")) {
                        // Abra a página do comprador
                    } else if (categoria.equals("Bibliotecário")) {
                        new TelaBibliotecario();
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

        return false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginUsuario();
        });
    }
}
