import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaPrincipal extends JFrame {
    private JButton cadastroButton;
    private JButton loginButton;

    public TelaPrincipal() {
        setTitle("Tela Principal");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));

        cadastroButton = new JButton("Cadastro");
        loginButton = new JButton("Login");

        panel.add(cadastroButton);
        panel.add(loginButton);

        cadastroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirTelaCadastro();
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirTelaLogin();
            }
        });

        add(panel);
        setVisible(true);
    }

    private void abrirTelaCadastro() {
        new CadastroForm();
    }

    private void abrirTelaLogin() {
        new LoginUsuario();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaPrincipal();
        });
    }
}
