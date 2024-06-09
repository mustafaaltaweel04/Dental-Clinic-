import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Needs {
    static int id = 1;
    ArrayList<Labortary> list;
    AnchorPane root;
    Button addButton;
    Button buyButton;
    Button removeButton;
    Text totalValue;
    TextField productField;
    TextField companyField;
    TextField priceSpinner;
    Spinner<Integer> quantitySpinner;
    TableView<Labortary> tableView;

    @SuppressWarnings("unchecked")
    Needs() {
        list = new ArrayList<>();
        root = new AnchorPane();
        root.setPrefSize(800, 600);

        Separator separator = new Separator();
        separator.setLayoutX(338);
        separator.setLayoutY(9);
        separator.setOrientation(Orientation.VERTICAL);
        separator.setPrefHeight(578);
        separator.setPrefWidth(0);

        Pane productPane = new Pane();
        productPane.setLayoutX(10);
        productPane.setLayoutY(10);
        productPane.setPrefSize(319, 401);
        productPane.setStyle("-fx-background-radius: 15; -fx-background-color: #FF8C00;");
        Insets productPanePadding = new Insets(10);
        productPane.setPadding(productPanePadding);

        Text productText = new Text("Product/المنتج");
        productText.setFill(javafx.scene.paint.Color.WHITE);
        productText.setLayoutX(25);
        productText.setLayoutY(29);
        productText.setFont(new Font(14));

        productField = new TextField();
        productField.setLayoutX(25);
        productField.setLayoutY(41);
        productField.setPrefSize(230, 30);

        Text companyText = new Text("Company/الشركة");
        companyText.setFill(javafx.scene.paint.Color.WHITE);
        companyText.setLayoutX(25);
        companyText.setLayoutY(125);
        companyText.setFont(new Font(14));

        companyField = new TextField();
        companyField.setLayoutX(25);
        companyField.setLayoutY(140);
        companyField.setPrefSize(230, 30);

        Text priceText = new Text("Price/السعر");
        priceText.setFill(javafx.scene.paint.Color.WHITE);
        priceText.setLayoutX(25);
        priceText.setLayoutY(218);
        priceText.setFont(new Font(14));

        priceSpinner = new TextField();
        priceSpinner.setLayoutX(25);
        priceSpinner.setLayoutY(232);
        priceSpinner.setPrefSize(230, 30);

        Text quantityText = new Text("Quantity/العدد");
        quantityText.setFill(javafx.scene.paint.Color.WHITE);
        quantityText.setLayoutX(25);
        quantityText.setLayoutY(302);
        quantityText.setFont(new Font(14));

        quantitySpinner = new Spinner<>();
        quantitySpinner.setLayoutX(25);
        quantitySpinner.setLayoutY(316);
        quantitySpinner.setPrefSize(230, 30);
        quantitySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20));

        addButton = new Button("Add/اضافة");
        addButton.setLayoutX(121);
        addButton.setLayoutY(363);

        productPane.getChildren().addAll(productText, productField, companyText, companyField, priceText, priceSpinner,
                quantityText, quantitySpinner, addButton);

        Pane tablePane = new Pane();
        tablePane.setLayoutX(355);
        tablePane.setLayoutY(11);
        tablePane.setPrefSize(441, 554);
        tablePane.setStyle("-fx-background-radius: 15; -fx-background-color: #191970;");
        Insets tablePanePadding = new Insets(10);
        tablePane.setPadding(tablePanePadding);

        tableView = new TableView<>();
        tableView.setLayoutX(28);
        tableView.setLayoutY(25);
        tableView.setPrefSize(385, 361);

        TableColumn<Labortary, String> productColumn = new TableColumn<>("Product");
        productColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productColumn.setPrefWidth(75);


        TableColumn<Labortary, String> companyColumn = new TableColumn<>("Company");
        companyColumn.setCellValueFactory(cellData -> cellData.getValue().getCompanyName());
        companyColumn.setPrefWidth(75);

        TableColumn<Labortary, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceColumn.setPrefWidth(75);

        TableColumn<Labortary, Integer> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        quantityColumn.setPrefWidth(75);

        tableView.getColumns().addAll(productColumn, companyColumn, priceColumn, quantityColumn);

        buyButton = new Button("Buy/شراء");
        buyButton.setLayoutX(155);
        buyButton.setLayoutY(403);
        buyButton.setPrefSize(53, 26);

        removeButton = new Button("Remove/حذف");
        removeButton.setLayoutX(221);
        removeButton.setLayoutY(404);

        tablePane.getChildren().addAll(tableView, buyButton, removeButton);

        Pane totalPane = new Pane();
        totalPane.setLayoutX(8);
        totalPane.setLayoutY(424);
        totalPane.setPrefSize(319, 159);
        totalPane.setStyle("-fx-background-color: #E0B0FF; -fx-background-radius: 15;");
        Insets totalPanePadding = new Insets(10);
        totalPane.setPadding(totalPanePadding);

        Text totalText = new Text("TOTAL");
        totalText.setLayoutX(14);
        totalText.setLayoutY(46);
        totalText.setStyle("-fx-font-family: Helvetica; -fx-font-weight: 600;");
        totalText.setFont(new Font(30));

        totalValue = new Text("123");
        totalValue.setLayoutX(217);
        totalValue.setLayoutY(136);
        totalValue.setFont(new Font("System Bold", 29));

        Text nisText = new Text("nis");
        nisText.setLayoutX(268);
        nisText.setLayoutY(136);
        nisText.setFont(new Font(18));

        totalPane.getChildren().addAll(totalText, totalValue, nisText);

        root.getChildren().addAll(separator, productPane, tablePane, totalPane);

    }

    void insert() throws SQLException {
        String companyName = companyField.getText();
        String productName = productField.getText();
        int price = Integer.parseInt(priceSpinner.getText());
        int quantity = quantitySpinner.getValue();

        String sql = "INSERT INTO laboratory (Lid, Cid, Lname, Lprice,statusFlag,product) VALUES (?, ?, ?, ?, ?,?)";
        try (Connection conn = DriverManager.getConnection(App.JDBC_URL, App.USERNAME, App.PASSWORD);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < quantity; i++) {
                pstmt.setInt(1, getNextId());
                pstmt.setInt(2, 1);
                pstmt.setString(3, companyName);
                pstmt.setInt(4, price);
                pstmt.setString(5, "E");
                pstmt.setString(6, productName);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void buy(int id) throws SQLException{
        String sql1 = "UPDATE laboratory SET statusFlag = 'B' WHERE Lid = " + id;
        Connection conn = DriverManager.getConnection(App.JDBC_URL, App.USERNAME, App.PASSWORD);
        PreparedStatement pstmt1 = conn.prepareStatement(sql1);
        pstmt1.executeUpdate();
        
    }

    void populateTableView(TableView<Labortary> tableView) {
        tableView.getItems().clear();
        updateList();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).flag == 'E') {
                tableView.getItems().add(list.get(i));
            }
        }
    }

    int totalValue() {
        String sql = "SELECT SUM(Lprice) AS total_price FROM laboratory where statusFlag = 'E'";

        try (Connection conn = DriverManager.getConnection(App.JDBC_URL, App.USERNAME, App.PASSWORD);
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total_price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    void updateList() {
        list.clear();
        totalValue.setText(totalValue() + "");
        String sql = "SELECT Lname, Lid, Lprice, COUNT(*) AS quantity,statusFlag,product FROM laboratory GROUP BY Lname, Lid, Lprice,statusFlag,product";

        try (Connection conn = DriverManager.getConnection(App.JDBC_URL, App.USERNAME, App.PASSWORD);
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String companyName = rs.getString("Lname");
                String productName = rs.getString("product");
                int companyId = rs.getInt("Lid");
                double price = rs.getDouble("Lprice");
                int quantity = rs.getInt("quantity");
                char flag = rs.getString("statusFlag").charAt(0);

                Labortary labortary = new Labortary(companyName,productName, companyId, price, quantity, flag);
                list.add(labortary);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // write to database
    void updateData(ArrayList<Labortary> list) {

    }

    private int getNextId() throws SQLException {
        String sql = "SELECT MAX(Lid) + 1 AS next_id FROM laboratory";
        try (Connection conn = DriverManager.getConnection(App.JDBC_URL, App.USERNAME, App.PASSWORD);
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("next_id");
            } else {
                return 1;
            }
        }
    }

}
