import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

class RoomsController {
    AnchorPane anchorPane;
    TextField doctorIdField;
    Button insertButton;
    Button deleteButton;
    Button updateButton;
    TableView<Room> tableView;
    TableView<Doctor> doctortableview;

    @SuppressWarnings("unchecked")
    RoomsController() {
        // AnchorPane
        anchorPane = new AnchorPane();
        anchorPane.setPrefSize(600, 600);

        // Label
        Label label = new Label("Rooms");
        label.setLayoutX(14);
        label.setLayoutY(14);
        label.setPrefSize(161, 27);
        label.setFont(new Font(22));

        // Pane 1 (Add Room Section)
        Pane pane1 = new Pane();
        pane1.setLayoutX(16);
        pane1.setLayoutY(49);
        pane1.setPrefSize(286, 226);
        pane1.setStyle("-fx-background-color: #FF8C00; -fx-background-radius: 15;");

        Text addRoomText = new Text("Add Room");
        addRoomText.setFill(javafx.scene.paint.Color.WHITE);
        addRoomText.setLayoutX(14);
        addRoomText.setLayoutY(34);
        addRoomText.setFont(new Font(18));

        VBox vbox = new VBox(15);
        vbox.setAlignment(javafx.geometry.Pos.TOP_CENTER);
        vbox.setLayoutX(12);
        vbox.setLayoutY(46);
        vbox.setPrefSize(171, 193);

        doctorIdField = new TextField();
        doctorIdField.setPromptText("Doctor ID");
        doctorIdField.setPrefSize(158, 26);

        vbox.getChildren().addAll(addRoomText,doctorIdField);

        HBox hbox = new HBox(10);
        hbox.setAlignment(javafx.geometry.Pos.CENTER);
        hbox.setLayoutX(14);
        hbox.setLayoutY(165);
        hbox.setPrefSize(266, 61);

        insertButton = new Button("insert/ادخال");
        deleteButton = new Button("delete/حذف");
        updateButton = new Button("update/تعديل");

        hbox.getChildren().addAll(insertButton, deleteButton, updateButton);
        pane1.getChildren().addAll( vbox, hbox);

        // VBox for TableViews
        VBox tableVBox = new VBox(20);
        tableVBox.setLayoutX(312);
        tableVBox.setLayoutY(49);
        tableVBox.setPrefSize(278, 400);

        // Pane for TableView 1
        Pane tablePane1 = new Pane();
        tablePane1.setPrefSize(278, 200);

        tableView = new TableView<>();
        tableView.setLayoutX(9);
        tableView.setLayoutY(13);
        tableView.setPrefSize(260, 200);

        TableColumn<Room, Integer> roomIdColumn = new TableColumn<>("Room ID");
        roomIdColumn.setPrefWidth(75);
        roomIdColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());

        TableColumn<Room, Integer> doctorIdColumn = new TableColumn<>("Doctor ID");
        doctorIdColumn.setPrefWidth(75);
        doctorIdColumn.setCellValueFactory(cellData -> cellData.getValue().dIDProperty().asObject());

        tableView.getColumns().addAll(roomIdColumn, doctorIdColumn);

        tablePane1.getChildren().add(tableView);

        // Pane for TableView 2
        Pane tablePane2 = new Pane();
        tablePane2.setPrefSize(278, 200);

        doctortableview = new TableView<>();
        doctortableview.setLayoutX(9);
        doctortableview.setLayoutY(80);
        doctortableview.setPrefSize(300, 250);
        
        
        TableColumn<Doctor, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setPrefWidth(75);
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getName());

        TableColumn<Doctor, String> phoneColumn = new TableColumn<>("Phone");
        phoneColumn.setPrefWidth(75);
        phoneColumn.setCellValueFactory(cellData -> cellData.getValue().getPhoneNumber());

        TableColumn<Doctor, String> specialityColumn = new TableColumn<>("Speciality");
        specialityColumn.setPrefWidth(75);
        specialityColumn.setCellValueFactory(cellData -> cellData.getValue().getSpeciality());

        
        TableColumn<Doctor, Integer> doctorId = new TableColumn<>("Doctor ID");
        doctorId.setPrefWidth(75);
        doctorId.setCellValueFactory(cellData -> cellData.getValue().getId().asObject());
        
        
        doctortableview.getColumns().addAll( doctorId ,nameColumn, phoneColumn, specialityColumn);
        
        tablePane2.getChildren().add(doctortableview);

        // Add both TableView panes to VBox
        tableVBox.getChildren().addAll(tablePane1, tablePane2);

        // Adding all components to the anchor pane
        anchorPane.getChildren().addAll(label, pane1, tableVBox);
    }

    void insert() throws SQLException {
        int roomId = getNextRoomId(); // Get the next unique room ID
        int doctorId = Integer.parseInt(doctorIdField.getText().trim());

        String sql = "INSERT INTO room (R_id, d_id) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(App.JDBC_URL, App.USERNAME, App.PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, roomId);
            pstmt.setInt(2, doctorId);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void update() throws SQLException {
        int roomId = getNextRoomId(); // Get the next unique room ID
        int doctorId = Integer.parseInt(doctorIdField.getText());
        int id = tableView.getSelectionModel().getSelectedItem().getId();

        String sql = "UPDATE room SET R_id = ?, d_id = ? WHERE R_id = ?";
        try (Connection conn = DriverManager.getConnection(App.JDBC_URL, App.USERNAME, App.PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, roomId);
            pstmt.setInt(2, doctorId);
            pstmt.setInt(3, id);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void delete() throws SQLException {
        int id = tableView.getSelectionModel().getSelectedItem().getId();
        String sql = "DELETE FROM room WHERE R_id = ?";
        try (Connection conn = DriverManager.getConnection(App.JDBC_URL, App.USERNAME, App.PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // private void loadTableData() throws SQLException {
    //     ObservableList<Room> roomList = FXCollections.observableArrayList();

    //     String sql = "SELECT * FROM room";
    //     try (Connection conn = DriverManager.getConnection(App.JDBC_URL, App.USERNAME, App.PASSWORD);
    //          PreparedStatement pstmt = conn.prepareStatement(sql);
    //          ResultSet rs = pstmt.executeQuery()) {

    //         while (rs.next()) {
    //             int roomId = rs.getInt("R_id");
    //             int doctorId = rs.getInt("d_id");
    //             roomList.add(new Room(roomId, doctorId));
    //         }
    //     }

    //     tableView.setItems(roomList);
    // }

    private int getNextRoomId() throws SQLException {
        String sql = "SELECT MAX(R_id) + 1 AS next_id FROM room";
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
