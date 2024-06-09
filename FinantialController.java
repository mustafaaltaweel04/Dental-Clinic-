import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import java.sql.*;

public class FinantialController {
	TableView<Financial> financialTableView;
	private ObservableList<Financial> financialList;
	private TextField amountField;
	private ComboBox<String> fTypeComboBox;
	TextField pIdField;
	Button payButton;
	GridPane root;

	public FinantialController() {
		initialize();
	}

	@SuppressWarnings("unchecked")
	public void initialize() {
		// Initialize Financial TableView
		financialTableView = new TableView<>();
		TableColumn<Financial, Integer> fIdColumn = new TableColumn<>("F_id");
		fIdColumn.setCellValueFactory(new PropertyValueFactory<>("fId"));

		TableColumn<Financial, Integer> amountColumn = new TableColumn<>("Amount");
		amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

		TableColumn<Financial, String> fTypeColumn = new TableColumn<>("F_type");
		fTypeColumn.setCellValueFactory(new PropertyValueFactory<>("fType"));

		TableColumn<Financial, Integer> pIdColumn = new TableColumn<>("P_id");
		pIdColumn.setCellValueFactory(new PropertyValueFactory<>("pId"));

		financialTableView.getColumns().addAll(fIdColumn, amountColumn, fTypeColumn, pIdColumn);
		financialList = FXCollections.observableArrayList();
		financialTableView.setItems(financialList);

		// TextFields for financial details
		amountField = new TextField();
		fTypeComboBox = new ComboBox<>();
		fTypeComboBox.getItems().addAll("Cash", "Check", "Insurance");
		pIdField = new TextField();

		// Pay Button
		payButton = new Button("Pay");
		payButton.setOnAction(e -> handlePay());

		// Layout setup
		root = new GridPane();
		root.setHgap(10);
		root.setVgap(10);

		root.add(new Label("Amount:"), 0, 0);
		root.add(amountField, 1, 0);

		root.add(new Label("F_type:"), 0, 1);
		root.add(fTypeComboBox, 1, 1);

		root.add(new Label("P_id:"), 0, 2);
		root.add(pIdField, 1, 2);

		root.add(payButton, 1, 3);

		root.add(new Label("Financial Records"), 0, 4, 2, 1);
		root.add(financialTableView, 0, 5, 2, 1);

		root.add(new Label("Patients"), 2, 4, 2, 1);
	
	}


	void handlePay() {
	    try {
	        int amount = Integer.parseInt(amountField.getText());
	        String fType = fTypeComboBox.getValue();
	        int pId = Integer.parseInt(pIdField.getText());

	        // Generate next financial ID using getNextFinancialId()
	        int fId = getNextFinancialId();

	        // Insert financial record directly into the database
	        insertFinancialRecord(fId, amount, fType, pId);

	        // Clear input fields
	        amountField.clear();
	        fTypeComboBox.getSelectionModel().clearSelection();
	        pIdField.clear();
	    } catch (NumberFormatException | SQLException e) {
	        e.printStackTrace();
	        // Handle invalid input (e.g., show error message)
	    }
	}

	private void insertFinancialRecord(int fId, int amount, String fType, int pId) throws SQLException {
	    String sql = "INSERT INTO financial (F_id, amount, balance, F_type, f_date,P_id) VALUES (?, ?, ?, ?, CURDATE(),?)";
	    try (Connection conn = DriverManager.getConnection(App.JDBC_URL, App.USERNAME, App.PASSWORD);
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setInt(1, fId);
	        pstmt.setInt(2, amount);
	        pstmt.setInt(3, 0); // Balance is set to 0 for simplicity
	        pstmt.setString(4, fType);
	        pstmt.setInt(5, pId);
	        pstmt.executeUpdate();
	    }
	}


	private int getNextFinancialId() throws SQLException {
		String sql = "SELECT MAX(F_id) + 1 AS next_id FROM financial";
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

	public void loadFinancialData() throws SQLException {
		ObservableList<Financial> financialList = FXCollections.observableArrayList();

		String sql = "SELECT * FROM financial";
		try (Connection conn = DriverManager.getConnection(App.JDBC_URL, App.USERNAME, App.PASSWORD);
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {

			while (rs.next()) {
				int fId = rs.getInt("F_id");
				int amount = rs.getInt("amount");
				int balance = rs.getInt("balance");
				String fType = rs.getString("F_type");
				String fdate = rs.getString("F_date");
				int pId = rs.getInt("P_id");
				financialList.add(new Financial(fId, amount, balance, fType,fdate, pId));
			}
		}

		financialTableView.setItems(financialList);
	}
	public ObservableList<Financial> getFinancialDataForPatient(int patientId) throws SQLException {
	    ObservableList<Financial> financialList = FXCollections.observableArrayList();

	    String sql = "SELECT * FROM financial WHERE P_id = ?";
	    try (Connection conn = DriverManager.getConnection(App.JDBC_URL, App.USERNAME, App.PASSWORD);
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setInt(1, patientId);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                int fId = rs.getInt("F_id");
	                int amount = rs.getInt("amount");
	                int balance = rs.getInt("balance");
	                String fType = rs.getString("F_type");
					String fdate = rs.getString("F_date");
	                int pId = rs.getInt("P_id");
	                financialList.add(new Financial(fId, amount, balance, fType,fdate, pId));
	            }
	        }
	    }
	    return financialList;
	}

	public GridPane getRoot() {
		return root;
	}
}
