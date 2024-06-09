import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Report {
    private Pane root;
    DatePicker datePicker;;
    TableView<Financial> profitsTable;
    TableView<Payment> spendingTable;

    @SuppressWarnings("unchecked")
    public Report() {
        root = new Pane();
        root.setCache(true);
        root.setPrefSize(800, 600);

        Pane profitsPane = new Pane();
        profitsPane.setLayoutX(4);
        profitsPane.setLayoutY(280);
        profitsPane.setPrefSize(404, 309);
        profitsPane.setStyle("-fx-background-radius: 15; -fx-background-color: #191970;");

        profitsTable = new TableView<>();
        profitsTable.setLayoutX(9);
        profitsTable.setLayoutY(56);
        profitsTable.setPrefSize(385, 241);

        TableColumn<Financial, String> profitsColumn = new TableColumn<>("Patient");
        profitsColumn.setCellValueFactory(cellValue -> cellValue.getValue().PatientName());
        profitsColumn.setPrefWidth(252.27);

        TableColumn<Financial, Integer> valueColumn1 = new TableColumn<>("Value");
        valueColumn1.setPrefWidth(132.44);
        valueColumn1.setCellValueFactory(cellValue -> cellValue.getValue().amountProperty().asObject());


        profitsTable.getColumns().addAll(profitsColumn, valueColumn1);

        Label revenueLabel = new Label("Revenue:");
        revenueLabel.setLayoutX(12);
        revenueLabel.setLayoutY(3);
        revenueLabel.setPrefSize(361, 54);
        revenueLabel.setStyle("-fx-font-family: Arial;");
        revenueLabel.setTextFill(javafx.scene.paint.Color.WHITE);
        revenueLabel.setFont(new Font(25));

        profitsPane.getChildren().addAll(profitsTable, revenueLabel);

        datePicker = new DatePicker();
        datePicker.setLayoutX(353);
        datePicker.setLayoutY(45);
        datePicker.setPrefSize(354, 33);
        datePicker.setPromptText("Choose the day");

        Text printReportText = new Text("Print report/كشف سجل");
        printReportText.setFill(javafx.scene.paint.Color.web("#131122"));
        printReportText.setLayoutX(46);
        printReportText.setLayoutY(194);
        printReportText.setStyle("-fx-font-family: Arial;");
        printReportText.setFont(new Font(32));
        printReportText.setWrappingWidth(307);

        Pane spendingPane = new Pane();
        spendingPane.setLayoutX(417);
        spendingPane.setLayoutY(137);
        spendingPane.setPrefSize(376, 396);
        spendingPane.setStyle("-fx-background-radius: 15; -fx-background-color: #FF8C00;");

        spendingTable = new TableView<>();
        spendingTable.setLayoutX(17);
        spendingTable.setLayoutY(54);
        spendingTable.setPrefSize(343, 330);

        TableColumn<Payment, String> paymentsColumn = new TableColumn<>("Payments");
        paymentsColumn.setCellValueFactory(cellValue -> cellValue.getValue().product);
        paymentsColumn.setPrefWidth(237.47);

        TableColumn<Payment, Integer> valueColumn2 = new TableColumn<>("Value");
        valueColumn2.setCellValueFactory(cellValue -> cellValue.getValue().amount.asObject());
        valueColumn2.setPrefWidth(139.36);

        spendingTable.getColumns().addAll(paymentsColumn, valueColumn2);

        Label spendingLabel = new Label("Spending");
        spendingLabel.setLayoutX(12);
        spendingLabel.setPrefSize(361, 54);
        spendingLabel.setStyle("-fx-font-family: Arial;");
        spendingLabel.setTextFill(javafx.scene.paint.Color.WHITE);
        spendingLabel.setFont(new Font(25));

        spendingPane.getChildren().addAll(spendingTable, spendingLabel);

        Text chooseDayText = new Text("Choose day/اختار اليوم");
        chooseDayText.setFill(javafx.scene.paint.Color.web("#131122"));
        chooseDayText.setLayoutX(46);
        chooseDayText.setLayoutY(72);
        chooseDayText.setStyle("-fx-font-family: Arial;");
        chooseDayText.setFont(new Font(32));
        chooseDayText.setWrappingWidth(307);

        root.getChildren().addAll(profitsPane, datePicker, printReportText, spendingPane, chooseDayText);

        ColorAdjust colorAdjust = new ColorAdjust();
        root.setEffect(colorAdjust);
    }

    public ArrayList<Financial> getProfit(String date) {
        ArrayList<Financial> list = new ArrayList<>();
        String sql = "select * from financial where f_date = '" + date + "'";
        try (Connection conn = DriverManager.getConnection(App.JDBC_URL, App.USERNAME, App.PASSWORD);
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                int fId = rs.getInt("F_id");
                int amount = rs.getInt("amount");
                int pId = rs.getInt("P_id");
                String fdate = rs.getString("f_date");
                String fType = rs.getString("F_type");
                list.add(new Financial(fId, amount, 0, fType, fdate, pId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
    public ArrayList<Payment> getPayments(String date) {
        ArrayList<Payment> list = new ArrayList<>();
        String sql = "select l.product, p.amount from payment p, laboratory l where p.L_id = l.Lid and l.statusFlag = 'B' and p.p_date = '" + date + "'";
        try (Connection conn = DriverManager.getConnection(App.JDBC_URL, App.USERNAME, App.PASSWORD);
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String product = rs.getString("product");
                int amount = rs.getInt("amount");
                list.add(new Payment(product,amount));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public Pane getRoot() {
        return root;
    }

}