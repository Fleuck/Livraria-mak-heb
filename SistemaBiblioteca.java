import javax.swing.*;

public class SistemaBiblioteca {
    private JFrame loginFrame;
    private LoginUsuario loginUsuario;
    private TelaBibliotecario telaBibliotecario;
    private TelaComprador telaComprador;


    public SistemaBiblioteca() {
        loginFrame = new JFrame("Sistema de Biblioteca");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(400, 200);
        loginFrame.setLocationRelativeTo(null);

        loginUsuario = new LoginUsuario();
        loginUsuario.setSistemaBiblioteca(this);

        loginFrame.add(loginUsuario);
        loginFrame.setVisible(true);
    }

    public void abrirTelaBibliotecario() {
        loginFrame.setVisible(false);
        telaBibliotecario = new TelaBibliotecario();
        telaBibliotecario.setSistemaBiblioteca(this);
    }

    public void abrirTelaComprador() {
        loginFrame.setVisible(false);
        telaComprador = new TelaComprador();
        telaComprador.setSistemaBiblioteca(this);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SistemaBiblioteca();
        });
    }

}
