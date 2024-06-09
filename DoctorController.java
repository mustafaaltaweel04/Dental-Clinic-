import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

class DoctorController {
    AnchorPane anchorPane;
    TextField doctorNameField;
    TextField phoneNumberField;
    TextField specialityField;
    Button insertButton;
    Button deleteButton;
    Button updateButton;
    TableView<Doctor> tableView;

    @SuppressWarnings("unchecked")
    DoctorController() {
        // AnchorPane
        anchorPane = new AnchorPane();
        anchorPane.setPrefSize(600, 400);

        // Label
        Label label = new Label("Doctors");
        label.setLayoutX(14);
        label.setLayoutY(14);
        label.setPrefSize(161, 27);
        label.setFont(new Font(22));

        // Pane 1 (Add Doctor Section)
        Pane pane1 = new Pane();
        pane1.setLayoutX(16);
        pane1.setLayoutY(49);
        pane1.setPrefSize(286, 226);
        pane1.setStyle("-fx-background-color: #191970; -fx-background-radius: 15;");

        Text addDoctorText = new Text("Add Doctor");
        addDoctorText.setFill(javafx.scene.paint.Color.WHITE);
        addDoctorText.setLayoutX(14);
        addDoctorText.setLayoutY(34);
        addDoctorText.setFont(new Font(18));

        VBox vbox = new VBox(15);
        vbox.setAlignment(javafx.geometry.Pos.TOP_CENTER);
        vbox.setLayoutX(12);
        vbox.setLayoutY(46);
        vbox.setPrefSize(171, 193);

        doctorNameField = new TextField();
        doctorNameField.setPromptText("Doctor Name");
        doctorNameField.setPrefSize(194, 26);

        phoneNumberField = new TextField();
        phoneNumberField.setPromptText("Phone Number");
        phoneNumberField.setPrefSize(158, 26);

        specialityField = new TextField();
        specialityField.setPromptText("Speciality");
        specialityField.setPrefSize(181, 26);

        vbox.getChildren().addAll(doctorNameField, phoneNumberField, specialityField);

        HBox hbox = new HBox(10);
        hbox.setAlignment(javafx.geometry.Pos.CENTER);
        hbox.setLayoutX(14);
        hbox.setLayoutY(165);
        hbox.setPrefSize(266, 61);

        insertButton = new Button("insert/ادخال");
        deleteButton = new Button("delete/حذف");
        updateButton = new Button("update/تعديل");

        hbox.getChildren().addAll(insertButton, deleteButton, updateButton);
        pane1.getChildren().addAll(addDoctorText, vbox, hbox);

        // Pane 2 (TableView Section)
        Pane pane2 = new Pane();
        pane2.setLayoutX(312);
        pane2.setLayoutY(143);
        pane2.setPrefSize(278, 226);
        pane2.setStyle("-fx-background-color: #FF8C00; -fx-background-radius: 15;");

        tableView = new TableView<>();
        tableView.setLayoutX(9);
        tableView.setLayoutY(13);
        tableView.setPrefSize(260, 200);

        TableColumn<Doctor, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setPrefWidth(75);
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getName());

        TableColumn<Doctor, String> phoneColumn = new TableColumn<>("Phone");
        phoneColumn.setPrefWidth(75);
        phoneColumn.setCellValueFactory(cellData -> cellData.getValue().getPhoneNumber());

        TableColumn<Doctor, String> specialityColumn = new TableColumn<>("Speciality");
        specialityColumn.setPrefWidth(75);
        specialityColumn.setCellValueFactory(cellData -> cellData.getValue().getSpeciality());

        tableView.getColumns().addAll(nameColumn, phoneColumn, specialityColumn);
        pane2.getChildren().add(tableView);

        // Adding all components to the anchor pane
        anchorPane.getChildren().addAll(label, pane1, pane2);
    }

    void insert() throws SQLException {
        String name = doctorNameField.getText();
        String phone = phoneNumberField.getText();
        String speciality = specialityField.getText();

        String sql = "INSERT INTO doctor VALUES (?, ?, ?, ?, ?,?)";
        try (Connection conn = DriverManager.getConnection(App.JDBC_URL, App.USERNAME, App.PASSWORD);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, getNextId());
            pstmt.setString(2, name);
            pstmt.setString(3, speciality);
            pstmt.setString(4, phone);
            pstmt.setInt(5, 1);
            pstmt.setString(6, "Y");

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void update() throws SQLException {
        String name = doctorNameField.getText();
        String phone = phoneNumberField.getText();
        String speciality = specialityField.getText();
        int id = tableView.getSelectionModel().getSelectedItem().getId().get();

        String sql = "UPDATE doctor SET D_name = ?, D_phonenumber = ?, D_speciality = ? WHERE D_id = ?";
        try (Connection conn = DriverManager.getConnection(App.JDBC_URL, App.USERNAME, App.PASSWORD)) {
            ;
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, phone);
            pstmt.setString(3, speciality);
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void delete() throws SQLException {
        int id = tableView.getSelectionModel().getSelectedItem().getId().get();
        System.out.println(id);
        String sql = "UPDATE doctor SET d_active = 'N' where D_id = " + id;
        try (Connection conn = DriverManager.getConnection(App.JDBC_URL, App.USERNAME, App.PASSWORD);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            updateAppointment();
            updateClinic();
            updateRoom();
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void updateAppointment() {// must change doctor for future appointments
        int id = tableView.getSelectionModel().getSelectedItem().getId().get();
        try {
            String sql = "UPDATE Appointments a SET a.D_id = (SELECT MIN(D_id) FROM doctor WHERE D_id != " + id+ ") where a.D_id =" + id + " and a.A_Date > CURDATE();";
            Connection conn = DriverManager.getConnection(App.JDBC_URL, App.USERNAME, App.PASSWORD);
            Statement statement = conn.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void updateClinic() {
        int id = tableView.getSelectionModel().getSelectedItem().getId().get();
        try {
            String sql = "UPDATE Clinic c SET c.D_id = (SELECT MIN(D_id) FROM doctor WHERE D_id != " + id
                    + ") where c.D_id =" + id;
            Connection conn = DriverManager.getConnection(App.JDBC_URL, App.USERNAME, App.PASSWORD);
            Statement statement = conn.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void updateRoom() {
        int id = tableView.getSelectionModel().getSelectedItem().getId().get();
        try {
            String sql = "UPDATE Room r SET r.D_id = (SELECT MIN(D_id) FROM doctor WHERE D_id != " + id
                    + ") where r.D_id =" + id;
            Connection conn = DriverManager.getConnection(App.JDBC_URL, App.USERNAME, App.PASSWORD);
            Statement statement = conn.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int getNextId() throws SQLException {
        String sql = "SELECT MAX(D_id) + 1 AS next_id FROM doctor";
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