import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TelaHistorico extends JFrame {
    private JLabel tituloLabel;
    private JTable historicoTable;
    private JTextField totalField;

    public TelaHistorico() {
        setTitle("Histórico de Vendas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        tituloLabel = new JLabel("Histórico de Vendas");
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(tituloLabel, BorderLayout.NORTH);

        historicoTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(historicoTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        totalField = new JTextField();
        totalField.setEditable(false);
        panel.add(new JLabel("Total Gasto: "), BorderLayout.SOUTH);
        panel.add(totalField, BorderLayout.SOUTH);

        preencherTabela();
        calcularTotal();

        add(panel);
        setVisible(true);

    }

    private void preencherTabela() {
        try {

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/makheb", "root", "PUC@1234");

            String query = "SELECT nomeCliente, contatoCliente, aquisicao, gasto FROM Cliente";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            String[] colunas = {"Nome", "Contato", "Aquisição", "Gasto"};
            DefaultTableModel model = new DefaultTableModel(colunas, 0);

            while (resultSet.next()) {
                String nomeCliente = resultSet.getString("nomeCliente");
                String contatoCliente = resultSet.getString("contatoCliente");
                String aquisicao = resultSet.getString("aquisicao");
                float gasto = resultSet.getFloat("gasto");

                Object[] rowData = {nomeCliente, contatoCliente, aquisicao, gasto};
                model.addRow(rowData);
            }

            historicoTable.setModel(model);

            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void calcularTotal() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/makheb", "root", "PUC@1234");

            String query = "SELECT SUM(gasto) AS totalGasto FROM Cliente";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                float totalGasto = resultSet.getFloat("totalGasto");
                totalField.setText(String.valueOf(totalGasto));
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaHistorico::new);
    }
}
