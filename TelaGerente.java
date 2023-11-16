import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaGerente extends JFrame {
    private JButton editarLivrosButton;
    private JButton excluirLivrosButton;
    private JButton editarFuncionariosButton;
    private JButton excluirFuncionariosButton;
    private JButton historicoButton; // Novo botão para o histórico

    public TelaGerente() {
        setTitle("Tela do Gerente");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250); // Ajustei a altura para acomodar o novo botão
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1)); // Ajustei para incluir o novo botão

        editarLivrosButton = new JButton("Editar Livros");
        excluirLivrosButton = new JButton("Excluir Livros");
        editarFuncionariosButton = new JButton("Editar Funcionários");
        excluirFuncionariosButton = new JButton("Excluir Funcionários");
        historicoButton = new JButton("Histórico");

        panel.add(editarLivrosButton);
        panel.add(excluirLivrosButton);
        panel.add(editarFuncionariosButton);
        panel.add(excluirFuncionariosButton);
        panel.add(historicoButton);

        editarLivrosButton.addActionListener(e -> abrirTelaEdicaoLivros());
        excluirLivrosButton.addActionListener(e -> abrirTelaExclusaoLivros());
        editarFuncionariosButton.addActionListener(e -> abrirTelaEdicaoFuncionarios());
        excluirFuncionariosButton.addActionListener(e -> abrirTelaExclusaoFuncionarios());
        historicoButton.addActionListener(e -> abrirTelaHistorico());

        add(panel);
        setVisible(true);
    }

    private void abrirTelaEdicaoLivros() {
        new TelaEditarLivros();
    }

    private void abrirTelaExclusaoLivros() {
        new TelaExcluirLivros();
    }

    private void abrirTelaEdicaoFuncionarios() {
        new TelaEditarFuncionarios();
    }

    private void abrirTelaExclusaoFuncionarios() {
        new TelaExcluirFuncionarios();
    }

    private void abrirTelaHistorico() {
        new TelaHistorico();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaGerente::new);
    }
}
