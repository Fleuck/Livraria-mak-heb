import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class TelaVendaLivros extends JFrame {
    private JComboBox<String> livrosBox;
    private JComboBox<String> clientesBox;
    private JButton venderButton;
    private JButton adicionarClienteButton;

    public TelaVendaLivros() {
        setTitle("Venda de Livros");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel livroLabel = new JLabel("Livro:");
        livrosBox = new JComboBox<>();
        carregarLivros();

        JLabel clienteLabel = new JLabel("Cliente:");
        clientesBox = new JComboBox<>();
        carregarClientes();

        venderButton = new JButton("Vender");
        adicionarClienteButton = new JButton("Adicionar Cliente");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(livroLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(livrosBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(clienteLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(clientesBox, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(venderButton, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        panel.add(adicionarClienteButton, gbc);

        venderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                venderLivro();
            }
        });

        adicionarClienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirTelaAdicionarCliente();
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
            String query = "SELECT nome, preco FROM Livro";
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

    private void carregarClientes() {
        String url = "jdbc:mysql://localhost:3306/makheb";
        String usuarioBD = "root";
        String senhaBD = "PUC@1234";

        try (Connection connection = DriverManager.getConnection(url, usuarioBD, senhaBD)) {

            String query = "SELECT nomeCliente FROM Cliente WHERE aquisicao IS NULL OR aquisicao = ''";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                clientesBox.removeAllItems();
                while (resultSet.next()) {
                    clientesBox.addItem(resultSet.getString("nomeCliente"));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    private void venderLivro() {
        String livroSelecionado = (String) livrosBox.getSelectedItem();
        String clienteSelecionado = (String) clientesBox.getSelectedItem();

        if (livroSelecionado != null && !livroSelecionado.isEmpty() &&
                clienteSelecionado != null && !clienteSelecionado.isEmpty()) {
            if (realizarVenda(livroSelecionado, clienteSelecionado)) {
                venderButton.setEnabled(false);
                JOptionPane.showMessageDialog(this, "Venda realizada com sucesso.");
                dispose();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um livro e um cliente para realizar a venda.");
        }
    }


    private boolean realizarVenda(String livro, String cliente) {
        String url = "jdbc:mysql://localhost:3306/makheb";
        String usuarioBD = "root";
        String senhaBD = "PUC@1234";

        try (Connection connection = DriverManager.getConnection(url, usuarioBD, senhaBD)) {

            // Retrieve the price of the book
            String queryPrecoLivro = "SELECT preco FROM Livro WHERE nome = ?";
            try (PreparedStatement precoLivroStatement = connection.prepareStatement(queryPrecoLivro)) {
                precoLivroStatement.setString(1, livro);
                ResultSet precoLivroResult = precoLivroStatement.executeQuery();
                if (precoLivroResult.next()) {
                    float precoLivro = precoLivroResult.getFloat("preco");

                    String queryAtualizacaoGasto = "UPDATE Cliente SET aquisicao = ?, gasto = COALESCE(gasto, 0) + ? WHERE nomeCliente = ?";
                    try (PreparedStatement atualizacaoGastoStatement = connection.prepareStatement(queryAtualizacaoGasto)) {
                        atualizacaoGastoStatement.setString(1, livro);
                        atualizacaoGastoStatement.setFloat(2, precoLivro);
                        atualizacaoGastoStatement.setString(3, cliente);
                        atualizacaoGastoStatement.executeUpdate();
                    }
                }
            }
            String queryRemocao = "DELETE FROM Livro WHERE nome = ?";
            try (PreparedStatement remocaoStatement = connection.prepareStatement(queryRemocao)) {
                remocaoStatement.setString(1, livro);
                remocaoStatement.executeUpdate();
            }

            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }


    private void abrirTelaAdicionarCliente() {
        JFrame frame = new JFrame("Adicionar Cliente");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JTextField nomeField = new JTextField(20);
        JTextField contatoField = new JTextField(20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Nome do Cliente:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(nomeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Contato do Cliente:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(contatoField, gbc);

        JButton adicionarButton = new JButton("Adicionar");

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(adicionarButton, gbc);

        adicionarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomeCliente = nomeField.getText();
                String contatoCliente = contatoField.getText();

                if (!nomeCliente.isEmpty() && !contatoCliente.isEmpty()) {
                    adicionarCliente(nomeCliente, contatoCliente);
                    carregarClientes();
                    JOptionPane.showMessageDialog(frame, "Cliente adicionado com sucesso.");
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Preencha todos os campos.");
                }
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    private void adicionarCliente(String nomeCliente, String contatoCliente) {
        String url = "jdbc:mysql://localhost:3306/makheb";
        String usuarioBD = "root";
        String senhaBD = "PUC@1234";

        try (Connection connection = DriverManager.getConnection(url, usuarioBD, senhaBD)) {
            String query = "INSERT INTO Cliente (nomeCliente, contatoCliente) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, nomeCliente);
                preparedStatement.setString(2, contatoCliente);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaVendaLivros();
        });
    }
}
