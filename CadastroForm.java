import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CadastroForm extends JFrame {
    private JTextField nomeField, sobrenomeField;
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

        JLabel sobrenomeLabel = new JLabel("Sobrenome:");
        sobrenomeField = new JTextField(20);

        JLabel tipoUsuarioLabel = new JLabel("Tipo de Usuário:");
        String[] tiposUsuario = {"Gerente", "Comprador", "Bibliotecário"};
        tipoUsuarioBox = new JComboBox<>(tiposUsuario);

        cadastrarButton = new JButton("Cadastrar");

        panel.add(nomeLabel);
        panel.add(nomeField);
        panel.add(sobrenomeLabel);
        panel.add(sobrenomeField);
        panel.add(tipoUsuarioLabel);
        panel.add(tipoUsuarioBox);
        panel.add(new JLabel());
        panel.add(cadastrarButton);

        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = nomeField.getText();
                String sobrenome = sobrenomeField.getText();
                String tipoUsuario = (String) tipoUsuarioBox.getSelectedItem();

                System.out.println("Nome: " + nome);
                System.out.println("Sobrenome: " + sobrenome);
                System.out.println("Tipo de Usuário: " + tipoUsuario);
            }
        });

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CadastroForm();
        });
    }
}
