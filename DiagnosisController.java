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

public class DiagnosisController {
	AnchorPane anchorPane;
	TextField typeField;
	Button insertButton;
	Button deleteButton;
	Button updateButton;
	TableView<Diagnosis> tableView;

	@SuppressWarnings("unchecked")
	DiagnosisController() {
		// AnchorPane
		anchorPane = new AnchorPane();
		anchorPane.setPrefSize(600, 400);

		// Label
		Label label = new Label("Diagnosis");
		label.setLayoutX(14);
		label.setLayoutY(14);
		label.setPrefSize(161, 27);
		label.setFont(new Font(22));

		// Pane 1 (Add Diagnosis Section)
		Pane pane1 = new Pane();
		pane1.setLayoutX(16);
		pane1.setLayoutY(49);
		pane1.setPrefSize(286, 226);
		pane1.setStyle("-fx-background-color: #FF8C00; -fx-background-radius: 15;");

		Text addDiagnosisText = new Text("Add Diagnosis");
		addDiagnosisText.setFill(javafx.scene.paint.Color.WHITE);
		addDiagnosisText.setLayoutX(14);
		addDiagnosisText.setLayoutY(34);
		addDiagnosisText.setFont(new Font(18));

		VBox vbox = new VBox(15);
		vbox.setAlignment(javafx.geometry.Pos.TOP_CENTER);
		vbox.setLayoutX(12);
		vbox.setLayoutY(46);
		vbox.setPrefSize(171, 193);

		typeField = new TextField();
		typeField.setPromptText("Diagnosis Type");
		typeField.setPrefSize(194, 26);

		vbox.getChildren().addAll(typeField);

		HBox hbox = new HBox(10);
		hbox.setAlignment(javafx.geometry.Pos.CENTER);
		hbox.setLayoutX(14);
		hbox.setLayoutY(165);
		hbox.setPrefSize(266, 61);

		insertButton = new Button("Insert/ادخال");
		deleteButton = new Button("Delete/حذف");
		updateButton = new Button("Update/تعديل");

		hbox.getChildren().addAll(insertButton, deleteButton, updateButton);
		pane1.getChildren().addAll(addDiagnosisText, vbox, hbox);

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

		TableColumn<Diagnosis, String> typeColumn = new TableColumn<>("Type");
		typeColumn.setPrefWidth(75);
		typeColumn.setCellValueFactory(cellData -> cellData.getValue().getType());

		TableColumn<Diagnosis, Integer> id = new TableColumn<>("id");
		id.setPrefWidth(75);
		id.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());

		tableView.getColumns().addAll(id, typeColumn);
		pane2.getChildren().add(tableView);

		// Adding all components to the anchor pane
		anchorPane.getChildren().addAll(label, pane1, pane2);

	}

	public TableView<Diagnosis> getTableView() {
		return tableView;
	}

	public void setTableView(TableView<Diagnosis> tableView) {
		this.tableView = tableView;
	}

	void insert() throws SQLException {
		String type = typeField.getText();

		String sql = "INSERT INTO diagnosis (Did, Dtype) VALUES (?, ?)";
		try (Connection conn = DriverManager.getConnection(App.JDBC_URL, App.USERNAME, App.PASSWORD);
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, getNextId());
			pstmt.setString(2, type);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	void update() throws SQLException {
		String type = typeField.getText();
		int id = tableView.getSelectionModel().getSelectedItem().getdID().get();

		String sql = "UPDATE diagnosis SET Dtype = ? WHERE Did = ?";
		try (Connection conn = DriverManager.getConnection(App.JDBC_URL, App.USERNAME, App.PASSWORD)) {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, type);
			pstmt.setInt(2, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	void delete() throws SQLException {
		int id = tableView.getSelectionModel().getSelectedItem().getdID().get();
		System.out.println(id);
		String sql = "DELETE FROM diagnosis WHERE Did = " + id;
		try (Connection conn = DriverManager.getConnection(App.JDBC_URL, App.USERNAME, App.PASSWORD);
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private int getNextId() throws SQLException {
		String sql = "SELECT MAX(Did) + 1 AS next_id FROM diagnosis";
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
