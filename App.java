import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class App extends Application {
	static final String JDBC_URL = "jdbc:mysql://localhost:3306/Clinic";
	static final String USERNAME = "root";
	static final String PASSWORD = "Firefly1122@";
	public static int id = 0;
	public static int appointment_ID = 0;
	static ArrayList<Patients> list = new ArrayList<>();
	static ArrayList<Appointments> appointmentsList = new ArrayList<>();
	static ArrayList<Appointments> dashBoardList = new ArrayList<>();
	static ArrayList<Doctor> doctorsList = new ArrayList<>();
	static ArrayList<Room> roomList = new ArrayList<>();
	static ArrayList<Diagnosis> diagonislist = new ArrayList<>();
	static ArrayList<Financial> FinantialList = new ArrayList<>();

	ObservableList<Patients> list_ObservableList;
	ObservableList<Appointments> appointments_ObservableList;
	ObservableList<Doctor> doctor_ObservableList;
	ObservableList<Room> room_ObservableList;
	ObservableList<Appointments> dashBoard_ObservableList;
	ObservableList<Diagnosis> diagnosislist;
	ObservableList<Financial> finantiallist;

	TableView<Patients> patientsTableView;
	TableView<Appointments> appointmentsTableView;
	TableView<Doctor> doctorTableView;
	TableView<Room> roomTableView;
	TableView<Appointments> dashBoardTableView;
	TableView<Diagnosis> diagnosistableviow;
	TableView<Financial> finantialTableview;

	PatientsController patientsController;
	AppointmentsController appointmentsController;
	DoctorController doctorController;
	RoomsController roomcontroller;
	DiagnosisController diagnosisController;
	FinantialController finantialcontroller;
	Dashboard dashboard;
	Needs needs;
	TreatmentHistory treatmentHistory;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Phase III");
		primaryStage.setResizable(false);
		patientsController = new PatientsController();
		appointmentsController = new AppointmentsController();
		diagnosislist = FXCollections.observableArrayList();

		getPaitentData();
		getDoctorsData();
		getRoomsData();
		getAppointmentData();
		getDiagonosisData();
		getFinancialData();
		dashboard = new Dashboard();
		needs = new Needs();
		treatmentHistory = new TreatmentHistory();
		getDashBoardAppData();
		Parent root = dashboard.root;
		updateTotalNumbers(dashboard);
		list.clear();
		appointmentsList.clear();

		dashboard.patientsButton.setOnAction(e -> {// done
			patientsController.getTableView().getItems().clear();
			openPatientsWindow();
		});
		dashboard.doctorButton.setOnAction(e -> {// done
			openDoctorsWindow();
		});
		dashboard.appointmentsButton.setOnAction(e -> {// done
			openAppointmentsWindow();
		});
		dashboard.needsButton.setOnAction(e -> { // progress
			openNeedsWindow();
		});
		dashboard.reportButton.setOnAction(e -> {
			openReportWindow();
		});
		dashboard.Rooms.setOnAction(e -> { // done
			openRoomWindow();
		});
		dashboard.diagnosiss.setOnAction(e -> {// done
			opendiagonisisWindow();
		});

		primaryStage.setScene(new Scene(root, 800, 600));
		primaryStage.show();
	}

	void getPaitentData() throws Exception {
		Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
		Class.forName("com.mysql.cj.jdbc.Driver");
		String sql = "SELECT * FROM patients ;";
		Statement statement = conn.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);

		list.clear(); // Clear the existing list to avoid duplicates
		while (resultSet.next()) {
			int id = resultSet.getInt("p_id");
			int age = resultSet.getInt("p_age");
			String name = resultSet.getString("p_name");
			String number = resultSet.getString("p_phonenumber");
			LocalDate localDate = resultSet.getDate("p_bdate").toLocalDate();
			list.add(new Patients(id, age, name, number, localDate));
		}

		patientsTableView = patientsController.getTableView();
		list_ObservableList = FXCollections.observableArrayList(list);
		patientsTableView.setItems(list_ObservableList);
	}

	void getDashBoardAppData() throws Exception {
		Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
		Class.forName("com.mysql.cj.jdbc.Driver");
		String sql = "select * from appointments a, doctor d, room r, patients p where p.P_id = a.P_id and r.R_id = a.R_id and d.d_id = a.D_id  AND a.A_Date >= curdate() AND a.A_Date = current_date() order by a.A_date, a.A_time;";
		Statement statement = conn.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);

		dashBoardList.clear(); // Clear the existing list to avoid duplicates
		while (resultSet.next()) {
			int aid = resultSet.getInt("a.Appointment_id");
			int pid = resultSet.getInt("p.p_id");
			int rid = resultSet.getInt("r.R_id");
			int did = resultSet.getInt("d_id");
			LocalDate aDate = resultSet.getDate("a.A_Date").toLocalDate();
			Time aTime = resultSet.getTime("a.A_time");
			String pName = resultSet.getString("P_name");
			String dName = resultSet.getString("D_name");
			int price = resultSet.getInt("price");

			dashBoardList.add(new Appointments(aid, rid, did, pid, aDate, aTime, pName, dName, price));
		}

		dashBoardTableView = dashboard.tableView;
		dashBoard_ObservableList = FXCollections.observableArrayList(dashBoardList);
		dashBoardTableView.setItems(dashBoard_ObservableList);
		ArrayList<Patients> debtPatients = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).totalDebt() > 0)
				debtPatients.add(list.get(i));
		}
		dashboard.debtTable.setItems(FXCollections.observableArrayList(debtPatients));
		needs.populateTableView(dashboard.barChart);
	}

	void getAppointmentData() throws Exception {
		appointmentsController.getDoctorComboBox().getItems().clear();
		;
		appointmentsController.getRoomComboBox().getItems().clear();
		Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
		Class.forName("com.mysql.cj.jdbc.Driver");
		String sql = "";
		if (appointmentsController.filterComboBox.getValue().equals("week")) {
			sql = "select * from appointments a, doctor d, room r, patients p where p.P_id = a.P_id and r.R_id = a.R_id and d.d_id = a.D_id  AND a.A_Date >= curdate() AND a.A_Date < current_date() + 6 order by a.A_date, a.A_time;";
		} else if (appointmentsController.filterComboBox.getValue().equals("month")) {
			sql = "select * from appointments a, doctor d, room r, patients p where p.P_id = a.P_id and r.R_id = a.R_id and d.d_id = a.D_id  AND a.A_Date >= curdate() AND a.A_Date < current_date() + 30 order by a.A_date, a.A_time;";
		} else if (appointmentsController.filterComboBox.getValue().equals("all")) {
			sql = "select * from appointments a, doctor d, room r, patients p where p.P_id = a.P_id and r.R_id = a.R_id and d.d_id = a.D_id order by a.A_date, a.A_time;";
		}
		Statement statement = conn.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);

		appointmentsList.clear(); // Clear the existing list to avoid duplicates
		while (resultSet.next()) {
			int aid = resultSet.getInt("a.Appointment_id");
			int pid = resultSet.getInt("p.p_id");
			int rid = resultSet.getInt("r.R_id");
			int did = resultSet.getInt("d_id");
			LocalDate aDate = resultSet.getDate("a.A_Date").toLocalDate();
			Time aTime = resultSet.getTime("a.A_time");
			String pName = resultSet.getString("P_name");
			String dName = resultSet.getString("D_name");
			int price = resultSet.getInt("price");

			appointmentsList.add(new Appointments(aid, rid, did, pid, aDate, aTime, pName, dName, price));
		}

		appointmentsTableView = appointmentsController.getAppointmentsTableView();

		appointments_ObservableList = FXCollections.observableArrayList(appointmentsList);
		appointmentsTableView.setItems(appointments_ObservableList);

		TableView<Patients> pTableView;
		pTableView = appointmentsController.getPatientsTableView();
		getPaitentData();
		pTableView.setItems(list_ObservableList);
		for (int i = 0; i < doctorsList.size(); i++) {
			appointmentsController.getDoctorComboBox().getItems().addAll(doctorsList.get(i).getName().getValue());
		}
		for (int i = 0; i < roomList.size(); i++) {
			appointmentsController.getRoomComboBox().getItems().addAll(roomList.get(i).getRoomNumber());
		}

	}

	void getDoctorsData() throws Exception {
		doctorsList.clear();
		doctor_ObservableList = FXCollections.observableArrayList(doctorsList);
		Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
		Class.forName("com.mysql.cj.jdbc.Driver");
		String sql = "select * from doctor ";
		Statement statement = conn.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);

		while (resultSet.next()) {
			int cID = resultSet.getInt("C_id");
			int id = resultSet.getInt("D_id");
			String name = resultSet.getString("D_name");
			String phoneNumber = resultSet.getString("D_phonenumber");
			String speciality = resultSet.getString("D_speciality");
			char active = resultSet.getString("D_active").charAt(0);

			if (active == 'Y')
				doctorsList.add(new Doctor(id, cID, name, phoneNumber, speciality, active));
		}
	}

	void getRoomsData() throws Exception {
		roomList.clear();
		room_ObservableList = FXCollections.observableArrayList(roomList);
		Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
		Class.forName("com.mysql.cj.jdbc.Driver");
		String sql = "select * from room ";
		Statement statement = conn.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);

		while (resultSet.next()) {
			int id = resultSet.getInt("r_id");
			int dID = resultSet.getInt("D_id");
			roomList.add(new Room(id, dID));
		}
	}

	void getDiagonosisData() throws Exception {
		Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
		Class.forName("com.mysql.cj.jdbc.Driver");
		String sql = "select * from diagnosis ";
		Statement statement = conn.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);

		diagnosislist.clear(); // تأكد من مسح القائمة قبل ملئها لتجنب التكرار
		while (resultSet.next()) {
			int id = resultSet.getInt("Did");
			String type = resultSet.getString("Dtype");
			diagnosislist.add(new Diagnosis(id, type));
		}
	}

	void insertPatient(String name, LocalDate date, String phone) throws Exception {
		int age = calculateAge(date);
		Date sqlDate = Date.valueOf(date);
		int id = list.get(list.size() - 1).getId() + 1;
		String sql = "insert into patients(p_id,p_age,p_name,p_phonenumber,p_bdate) values (" + id + ", " + age + ", '"
				+ name + "', '" + phone + "', '" + sqlDate + "')";
		Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
		Statement statement = conn.createStatement();
		statement.executeUpdate(sql);
		updateList();
	}

	void update(int id, String name, LocalDate date, String phone) throws Exception {
		int age = calculateAge(date);
		Date sqlDate = Date.valueOf(date);
		String sql1 = "update patients p set p.p_name = '" + name + "' , p.p_age = " + age + ", p.p_phonenumber = '"
				+ phone + "', p.p_bdate = '" + sqlDate + "' where p.p_id =" + id;

		Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
		Statement statement = conn.createStatement();
		statement.executeUpdate(sql1);
		updateList();
	}

	void delete(int id) throws Exception {
		String sql = "delete from patients p where p.p_id = " + id;
		Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
		Statement statement = conn.createStatement();
		statement.executeUpdate(sql);
		updateList();
	}

	void updateObservableList() {
		list_ObservableList = FXCollections.observableArrayList(list);
		patientsController.getTableView().getItems().clear();
		patientsController.getTableView().getItems().addAll(list_ObservableList);
	}

	void updateAppointmentsObservableList() {
		appointments_ObservableList.clear();
		appointments_ObservableList = FXCollections.observableArrayList(appointmentsList);
		appointmentsController.getAppointmentsTableView().getItems().clear();
		appointmentsController.getAppointmentsTableView().getItems().addAll(appointments_ObservableList);
	}

	void updateList() {
		list.clear();
		try {
			Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
			Class.forName("com.mysql.cj.jdbc.Driver");
			String sql = "SELECT * FROM patients";
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				int id = resultSet.getInt("p_id");
				int age = resultSet.getInt("p_age");
				String name = resultSet.getString("p_name");
				String number = resultSet.getString("p_phonenumber");
				LocalDate localDate = resultSet.getDate("p_bdate").toLocalDate();
				list.add(new Patients(id, age, name, number, localDate));
			}
			list_ObservableList = FXCollections.observableArrayList(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int calculateAge(LocalDate birthDate) {
		LocalDate currentDate = LocalDate.now();
		Period period = Period.between(birthDate, currentDate);
		return period.getYears();
	}

	public ObservableList<Patients> searchPatientList(String name) {
		ObservableList<Patients> result = FXCollections.observableArrayList();
		for (Patients patient : list) {
			if (patient.getName().toLowerCase().contains(name.toLowerCase())) {
				result.add(patient);
			}
		}
		return result;
	}

	public Patients findPatients(int id) {
		for (Patients patient : list) {
			if (patient.getId() == id) {
				return patient;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	void openPatientsWindow() {
		patientsController = new PatientsController();
		try {
			getPaitentData();
			updateObservableList();
			getDashBoardAppData();
			updateTotalNumbers(dashboard);
		} catch (Exception e) {
			System.out.println("Error");
		}
		patientsController.getInsert().setOnAction(e -> {
			try {
				String name = patientsController.getNameT().getText();
				LocalDate date = patientsController.getDatePicker().getValue();
				String phone = patientsController.getPhoneT().getText();
				insertPatient(name, date, phone);
				updateObservableList();
				updateTotalNumbers(dashboard);
			} catch (Exception insert) {
				insert.printStackTrace();
			}
		});
		patientsController.getFinantial().setOnAction(e -> {
			Patients selectedPatient = patientsController.getTableView().getSelectionModel().getSelectedItem();

			try {
				openFinancialWindow(selectedPatient);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});

		patientsController.getUpdate().setOnAction(e -> {
			try {
				String name = patientsController.getNameT().getText();
				LocalDate date = patientsController.getDatePicker().getValue();
				String phone = patientsController.getPhoneT().getText();
				update(id, name, date, phone);
				updateObservableList();
			} catch (Exception update) {
				update.printStackTrace();
			}
		});

		patientsController.getDelete().setOnAction(e -> {
			try {
				delete(id);
				updateObservableList();
				updateTotalNumbers(dashboard);
			} catch (Exception delete) {
				delete.printStackTrace();
			}
		});

		patientsController.getTableView().setOnMouseClicked(event -> {
			if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
				Patients selectedPatient = patientsController.getTableView().getSelectionModel().getSelectedItem();
				if (selectedPatient != null) {
					patientsController.getNameT().setText(selectedPatient.getName());
					patientsController.getDatePicker().setValue(selectedPatient.getDate());
					patientsController.getPhoneT().setText(selectedPatient.getPhoneNumber());
					id = selectedPatient.getId();
				}
			}
		});
		patientsController.getDetails().setOnAction(event2 -> {
			Patients selectedPatient = patientsController.getTableView().getSelectionModel().getSelectedItem();
			if (selectedPatient != null) {
				try {
					ObservableList<Financial> financialData = new FinantialController()
							.getFinancialDataForPatient(selectedPatient.getId());
					int totalDebt = 0;
					int totalAmountPaid = 0;

					for (Financial financial : financialData) {
						totalAmountPaid += financial.getAmount();
					}
					if (Patients.patientDebt(selectedPatient.getId()) > totalAmountPaid) {
						totalDebt = Patients.patientDebt(selectedPatient.getId()) - totalAmountPaid;
					}
					Stage primaryStage = new Stage();
					AnchorPane root = new AnchorPane();
					root.setPrefSize(600, 400);

					Label patientLabel = new Label("Patient: " + selectedPatient.getName());
					patientLabel.setFont(new Font("System Bold", 16));
					patientLabel.setLayoutX(24);
					patientLabel.setLayoutY(23);

					Text debtText = new Text("Debt: " + totalDebt);
					debtText.setFont(new Font(14));
					debtText.setLayoutX(24);
					debtText.setLayoutY(73);
					debtText.setStrokeWidth(0);

					Text payText = new Text("Amount Paid: " + totalAmountPaid);
					payText.setFont(new Font(14));
					payText.setLayoutX(100);
					payText.setLayoutY(73);
					payText.setStrokeWidth(0);

					Pane treatmentPane = new Pane();
					treatmentPane.setLayoutX(14);
					treatmentPane.setLayoutY(99);
					treatmentPane.setPrefSize(409, 278);
					treatmentPane.setStyle("-fx-background-radius: 15; -fx-background-color: #FF8C00;");

					TableView<TreatmentHistory> treatmentTable = new TableView<>();
					treatmentTable.setLayoutX(16);
					treatmentTable.setLayoutY(54);
					treatmentTable.setPrefSize(377, 200);

					TableColumn<TreatmentHistory, String> dateColumn = new TableColumn<>("Date");
					dateColumn.setCellValueFactory(cellValue -> cellValue.getValue().date);
					dateColumn.setPrefWidth(75);
					TableColumn<TreatmentHistory, String> treatmentColumn = new TableColumn<>("Treatment");
					treatmentColumn.setCellValueFactory(cellValue -> cellValue.getValue().type);
					treatmentColumn.setPrefWidth(102.4);
					treatmentTable.getItems().addAll(FXCollections
							.observableArrayList(treatmentHistory.getTreatmentData(selectedPatient.getId())));
					treatmentTable.getColumns().addAll(dateColumn, treatmentColumn);

					Text treatmentHistoryText = new Text("Treatment History");
					treatmentHistoryText.setFont(new Font(16));
					treatmentHistoryText.setFill(javafx.scene.paint.Color.WHITE);
					treatmentHistoryText.setLayoutX(16);
					treatmentHistoryText.setLayoutY(29);
					treatmentHistoryText.setStrokeWidth(0);

					treatmentPane.getChildren().addAll(treatmentHistoryText, treatmentTable);
					root.getChildren().addAll(patientLabel, debtText, payText, treatmentPane);

					Scene scene = new Scene(root);
					primaryStage.setScene(scene);
					primaryStage.setTitle("Patient Information");
					primaryStage.show();
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		patientsController.getSearchButton().setOnAction(e -> {
			patientsController.getTableView().getItems().clear();
			patientsController.getTableView().getItems()
					.addAll(searchPatientList(patientsController.getSearchT().getText().trim()));
		});

		Stage needsStage = new Stage();
		Scene needsScene = new Scene(patientsController.getAnchorPane(), 800, 600);
		needsStage.setScene(needsScene);
		needsStage.show();
		needsStage.setResizable(false);
	}

	void openFinancialWindow(Patients patient) throws SQLException {
		FinantialController finantialController = new FinantialController();

		finantiallist.clear();
		try {
			getFinancialData();
		} catch (Exception e) {
			e.printStackTrace();
		}

		finantialController.financialTableView.setItems(FXCollections.observableArrayList(finantiallist));

		if (patient != null) {
			finantialController.pIdField.setText(String.valueOf(patient.getId()));
			finantialController.pIdField.setEditable(false);
		}

		finantialController.payButton.setOnAction(e1 -> {
			try {
				finantialController.handlePay();
				finantiallist.clear();
				getFinancialData();
				finantialController.financialTableView.setItems(FXCollections.observableArrayList(finantiallist));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		finantialController.loadFinancialData();

		Stage financialStage = new Stage();
		Scene financialScene = new Scene(finantialController.getRoot(), 800, 600);

		financialStage.setScene(financialScene);
		financialStage.show();
		financialStage.setResizable(false);
	}

	private void getFinancialData() throws SQLException {
		finantiallist = FXCollections.observableArrayList();

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
				finantiallist.add(new Financial(fId, amount, balance, fType,fdate, pId));
			}
		}
	}

	void insertAppointemnt(int pID, int rID, int dID, LocalDate date, Time time, String patientName, String doctorName,
			int amount) throws Exception {
		Date sqlDate = Date.valueOf(date);
		int id = getAppointmentID();
		String sql = "insert into appointments(Appointment_id, A_Date, A_time, P_id, R_id, D_id, price) values (" + id
				+ ", '" + sqlDate + "', '" + time + "', " + pID + ", " + rID + ", " + dID + ", " + amount + ")";
		Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
		Statement statement = conn.createStatement();
		statement.executeUpdate(sql);
		getAppointmentData();
	}

	void updateAppointmentList() {
		try {

			appointmentsList.clear(); // Clear the existing list to avoid duplicates
			Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
			Class.forName("com.mysql.cj.jdbc.Driver");
			String sql = "select * from appointments a, doctor d, room r, patients p where p.P_id = a.P_id and r.R_id = a.R_id and d.d_id = a.D_id  AND a.A_Date >= curdate() AND a.A_Date < current_date() + 6 Order by a.A_Date, a.A_time;";
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				int aid = resultSet.getInt("a.Appointment_id");
				int pid = resultSet.getInt("p.p_id");
				int rid = resultSet.getInt("r.R_id");
				int did = resultSet.getInt("d_id");
				LocalDate aDate = resultSet.getDate("a.A_Date").toLocalDate();
				Time aTime = resultSet.getTime("a.A_time");
				String pName = resultSet.getString("P_name");
				String dName = resultSet.getString("D_name");
				int price = resultSet.getInt("price");

				appointmentsList.add(new Appointments(aid, rid, did, pid, aDate, aTime, pName, dName, price));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void deleteAppointment() {
		try {
			String sql = "delete from appointments a where a.Appointment_id = " + appointment_ID;
			Connection conn;
			conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
			Statement statement = conn.createStatement();
			statement.executeUpdate(sql);
			updateAppointmentList();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	void updateAppointment(int pID, int rID, int dID, LocalDate date, String time, String patientName,
			String doctorName, int amount) throws Exception {
		appointmentsList.clear();
		Date sqlDate = Date.valueOf(date);
		String sql1 = "UPDATE appointments a SET a.A_Date = '" + sqlDate + "', a.A_time = '" + time + "', a.P_id = "
				+ pID + ", a.R_id = " + rID + ", a.D_id = " + dID + " , a.price = " + amount
				+ " WHERE a.Appointment_id = " + appointment_ID;

		Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
		Statement statement = conn.createStatement();
		statement.executeUpdate(sql1);
		updateAppointmentList();
		updateAppointmentsObservableList();
	}

	int calculateTotalPatientPrice(int id) {
		int total = 0;
		for (int index = 0; index < appointmentsList.size(); index++) {
			if (appointmentsList.get(index).getPatient_ID().intValue() == id) {
				total += appointmentsList.get(index).getPrice().intValue();
			}
		}
		return total;
	}

	void openAppointmentsWindow() {
		appointmentsController = new AppointmentsController();

		try {
			getAppointmentData();
		} catch (Exception e) {
			System.out.println("Error");
		}
		System.out.println(calculateTotalPatientPrice(1));
		updateTotalNumbers(dashboard);

		appointmentsController.getInsert().setOnAction(e -> {
			try {
				LocalDate date = appointmentsController.getDatePicker().getValue();
				String doctorName = appointmentsController.getDoctorComboBox().getValue();
				int roomNumber = Integer.parseInt(appointmentsController.getRoomComboBox().getValue());
				Time time = convertStringToSqlTime(appointmentsController.getTimeTextField().getText());
				String patientName = appointmentsController.getPatientNameField().getText();
				int pID = getPatientID(patientName);
				int dID = getDoctorID(doctorName);
				int amount;
				try {
					amount = Integer.parseInt(appointmentsController.getSpinner().getText());
				} catch (NumberFormatException e2) {
					amount = 0;
				}
				treatmentHistory.insertTreatment(pID, appointmentsController.comboBox.getValue(), date.toString());
				insertAppointemnt(pID, roomNumber, dID, date, time, patientName, doctorName, amount);
				updateAppointmentList();
				updateAppointmentsObservableList();
				updateTotalNumbers(dashboard);

			} catch (Exception appInsert) {
				appInsert.printStackTrace();
			}
		});

		appointmentsController.getDeleteButton().setOnAction(e -> {
			deleteAppointment();
			updateAppointmentList();
			updateAppointmentsObservableList();
			updateTotalNumbers(dashboard);
		});

		appointmentsController.getUpdateButton().setOnAction(e -> {
			appointmentsController.getAppointmentsTableView().getItems().clear();
			LocalDate date = appointmentsController.getDatePicker().getValue();
			String doctorName = appointmentsController.getDoctorComboBox().getValue();
			int roomNumber = Integer.parseInt(appointmentsController.getRoomComboBox().getValue());
			String time = appointmentsController.getTimeTextField().getText();
			String patientName = appointmentsController.getPatientNameField().getText();
			int pID = getPatientID(patientName);
			int dID = getDoctorID(doctorName);
			int amount = 0;
			try {
				amount = Integer.parseInt(appointmentsController.getSpinner().getText());
			} catch (NumberFormatException e2) {
				amount = 0;
			}
			try {
				updateAppointment(pID, roomNumber, dID, date, time, patientName, doctorName, amount);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			updateTotalNumbers(dashboard);

		});

		appointmentsController.getSearchButton().setOnAction(e1 -> {
			System.out.println(list.size());
			appointmentsController.getPatientsTableView().getItems().clear();
			appointmentsController.getPatientsTableView()
					.setItems(searchPatientList(appointmentsController.getPatientNameField().getText().trim()));
		});

		appointmentsController.getPatientsTableView().setOnMouseClicked(event -> {
			if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
				Patients selectedPatient = appointmentsController.getPatientsTableView().getSelectionModel()
						.getSelectedItem();
				if (selectedPatient != null) {
					appointmentsController.getPatientNameField().setText(selectedPatient.getName());
					appointment_ID = 0;
				}
			}
		});

		appointmentsController.getAppointmentsTableView().setOnMouseClicked(event -> {
			if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
				Appointments selectedAppointments = appointmentsController.getAppointmentsTableView()
						.getSelectionModel().getSelectedItem();
				if (selectedAppointments != null) {
					appointment_ID = selectedAppointments.getA_id();
					appointmentsController.getPatientNameField().setText(selectedAppointments.getPatientName());
					appointmentsController.getDoctorComboBox().setValue(selectedAppointments.getDoctorName());
					appointmentsController.getRoomComboBox()
							.setValue(selectedAppointments.getRoom_ID().intValue() + "");
					appointmentsController.getTimeTextField().setText(selectedAppointments.getTime() + "");
					appointmentsController.getDatePicker().setValue(selectedAppointments.getDate());
					appointmentsController.getSpinner().setText(selectedAppointments.getPrice().intValue() + "");
					id = 0;
				}
			}
		});
		appointmentsController.filterComboBox.setOnAction(e -> {
			try {
				getAppointmentData();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		});
		Parent root = appointmentsController.root;
		Stage appointmentsStage = new Stage();
		Scene appintmentScene = new Scene(root, 800, 600);
		appointmentsStage.setScene(appintmentScene);
		appointmentsStage.show();
		appointmentsStage.setResizable(false);
	}

	void openDoctorsWindow() {
		doctorController = new DoctorController();
		doctorsList.clear();
		try {
			getDoctorsData();
		} catch (Exception e) {
		}

		doctorController.tableView.setItems(FXCollections.observableArrayList(doctorsList));

		doctorController.insertButton.setOnAction(e1 -> {
			try {
				doctorController.insert();
				doctorsList.clear();
				getDoctorsData();
				doctorController.tableView.getItems().clear();
				doctorController.tableView.setItems(FXCollections.observableArrayList(doctorsList));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		doctorController.deleteButton.setOnAction(e2 -> {
			try {
				doctorController.delete();
				doctorsList.clear();
				getDoctorsData();
				doctorController.tableView.getItems().clear();
				doctorController.tableView.setItems(FXCollections.observableArrayList(doctorsList));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		doctorController.updateButton.setOnAction(e2 -> {
			try {
				doctorController.update();
				doctorsList.clear();
				getDoctorsData();
				doctorController.tableView.getItems().clear();
				doctorController.tableView.setItems(FXCollections.observableArrayList(doctorsList));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		doctorController.tableView.setOnMouseClicked(event -> {
			if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
				Doctor doctor = doctorController.tableView.getSelectionModel().getSelectedItem();
				if (doctor != null) {
					doctorController.doctorNameField.setText(doctor.getName().get());
					doctorController.phoneNumberField.setText(doctor.getPhoneNumber().get());
					doctorController.specialityField.setText(doctor.getSpeciality().get());
				}
			}
		});
		Parent root = doctorController.anchorPane;
		Stage needsStage = new Stage();
		Scene needsScene = new Scene(root, 600, 400);
		needsStage.setScene(needsScene);
		needsStage.show();
		needsStage.setResizable(false);
	}

	void openRoomWindow() {
		roomcontroller = new RoomsController();
		roomList.clear();
		try {
			getRoomsData();
		} catch (Exception e) {
			e.printStackTrace();
		}

		roomcontroller.tableView.setItems(FXCollections.observableArrayList(roomList));

		doctorController = new DoctorController();
		doctorsList.clear();
		try {
			getDoctorsData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		roomcontroller.doctortableview.setItems(FXCollections.observableArrayList(doctorsList));

		// Double-click event to set doctorIdField in RoomsController
		roomcontroller.doctortableview.setOnMouseClicked(event -> {
			if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
				Doctor doctor = roomcontroller.doctortableview.getSelectionModel().getSelectedItem();
				if (doctor != null) {
					roomcontroller.doctorIdField.setText(String.valueOf(doctor.getId().intValue()));
				}
			}
		});

		// Set event handlers for roomcontroller buttons
		roomcontroller.insertButton.setOnAction(e1 -> {
			try {
				roomcontroller.insert();
				roomList.clear();
				getRoomsData();
				roomcontroller.tableView.setItems(FXCollections.observableArrayList(roomList));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		roomcontroller.deleteButton.setOnAction(e2 -> {
			try {
				roomcontroller.delete();
				roomList.clear();
				getRoomsData();
				roomcontroller.tableView.setItems(FXCollections.observableArrayList(roomList));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		roomcontroller.updateButton.setOnAction(e2 -> {
			try {
				roomcontroller.update();
				roomList.clear();
				getRoomsData();
				roomcontroller.tableView.setItems(FXCollections.observableArrayList(roomList));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		// Double-click event to fill the text fields in RoomsController's TableView
		roomcontroller.tableView.setOnMouseClicked(event -> {
			if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
				Room room = roomcontroller.tableView.getSelectionModel().getSelectedItem();
				if (room != null) {
					roomcontroller.doctorIdField.setText(String.valueOf(room.getDID()));
				}
			}
		});

		Parent root = roomcontroller.anchorPane;

		Stage needsStage = new Stage();
		Scene needsScene = new Scene(root, 800, 600);

		needsStage.setScene(needsScene);
		needsStage.show();
		needsStage.setResizable(false);
	}

	private void refreshDiagnosisData() {
		diagnosislist.clear();
		try {
			getDiagonosisData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		diagnosisController.tableView.setItems(FXCollections.observableArrayList(diagnosislist));
	}

	private void showAlert(AlertType alertType, String title, String headerText, String contentText) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		alert.showAndWait();
	}

	void opendiagonisisWindow() {
		diagnosisController = new DiagnosisController();
		diagnosislist.clear();
		try {
			getDiagonosisData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		diagnosisController.tableView.setItems(FXCollections.observableArrayList(diagnosislist));

		diagnosisController.insertButton.setOnAction(e1 -> {
			try {
				diagnosisController.insert();
				refreshDiagnosisData();
			} catch (Exception ex) {
				ex.printStackTrace();
				showAlert(AlertType.ERROR, "Error", "Failed to insert diagnosis", ex.getMessage());
			}
		});

		diagnosisController.deleteButton.setOnAction(e2 -> {
			try {
				diagnosisController.delete();
				refreshDiagnosisData();
			} catch (Exception ex) {
				ex.printStackTrace();
				showAlert(AlertType.ERROR, "Error", "Failed to delete diagnosis", ex.getMessage());
			}
		});

		diagnosisController.updateButton.setOnAction(e3 -> {
			try {
				diagnosisController.update();
				refreshDiagnosisData();
			} catch (Exception ex) {
				ex.printStackTrace();
				showAlert(AlertType.ERROR, "Error", "Failed to update diagnosis", ex.getMessage());
			}
		});

		diagnosisController.tableView.setOnMouseClicked(event -> {
			if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
				Diagnosis diagnosis = diagnosisController.tableView.getSelectionModel().getSelectedItem();
				if (diagnosis != null) {
					diagnosisController.typeField.setText(diagnosis.getType().get());
				}
			}
		});

		Parent root = diagnosisController.anchorPane;
		Stage needsStage = new Stage();
		Scene needsScene = new Scene(root, 600, 400);
		needsStage.setScene(needsScene);
		needsStage.show();
		needsStage.setResizable(false);
	}
	
	void openNeedsWindow() {
		needs = new Needs();
		needs.totalValue.setText("" + needs.totalValue());
		needs.populateTableView(needs.tableView);
		needs.addButton.setOnAction(e -> {
			try {
				needs.insert();
				needs.populateTableView(needs.tableView);
				needs.totalValue.setText("" + needs.totalValue());
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});

		needs.removeButton.setOnAction(e -> {
			Labortary labortary = needs.tableView.getSelectionModel().getSelectedItem();
			labortary.flag = 'D';
			System.out.println(labortary.getCompanyName());
			try {
				String sql = "update laboratory set statusFlag = 'D' where Lname = " + "'"
						+ labortary.getCompanyName().get() + "'";
				Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.executeUpdate();
				needs.populateTableView(needs.tableView);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
		needs.buyButton.setOnAction(e -> {
			Labortary labortary = needs.tableView.getSelectionModel().getSelectedItem();
			System.out.println(labortary.getId());
			try {
				needs.buy(labortary.getId());
				String sql = "INSERT INTO payment VALUES (?,?,?,?)";
				Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, getNextPaymentId());
				pstmt.setInt(2, labortary.getId());
				pstmt.setDouble(3, labortary.getPrice());
				pstmt.setString(4, LocalDate.now() + "");
				pstmt.executeUpdate();
				needs.populateTableView(needs.tableView);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});

		Parent root = needs.root;
		Stage needsStage = new Stage();
		Scene needsScene = new Scene(root, 800, 600);
		needsStage.setScene(needsScene);
		needsStage.show();
		needsStage.setResizable(false);
	}

	void openReportWindow() {
		Report report = new Report();
		Parent root = report.getRoot();
		report.datePicker.setOnAction(e->{
			report.spendingTable.getItems().clear();
			report.profitsTable.getItems().clear();
			String date = report.datePicker.getValue().toString();
			ArrayList<Payment> payments = report.getPayments(date);
			ArrayList<Financial> profits = report.getProfit(date);
			report.spendingTable.getItems().addAll(FXCollections.observableArrayList(payments));
			report.profitsTable.getItems().addAll(FXCollections.observableArrayList(profits));

		});

		Stage needsStage = new Stage();
		Scene needsScene = new Scene(root, 800, 600);
		needsStage.setScene(needsScene);
		needsStage.show();
		needsStage.setResizable(false);
	}

	void updateTotalNumbers(Dashboard dashboard) {
		try {
			getDashBoardAppData();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		dashboard.patientsValue.setText("" + list.size());
		dashboard.appointmentsValue.setText("" + appointmentsList.size());
		try {
			dashboard.earningsValue.setText("" + earnings() * -1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		;

	}

	int totalDebt() throws SQLException {
		int p = 0;
		for (int i = 0; i < list.size(); i++) {
			p += Patients.patientBalance(list.get(i).getId()) - Patients.patientDebt(list.get(i).getId());
		}
		return p;
	}

	int earnings() throws SQLException {
		String sql = "SELECT SUM(amount) FROM payment";
		try (Connection conn = DriverManager.getConnection(App.JDBC_URL, App.USERNAME, App.PASSWORD);
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {

			if (rs.next()) {
				return rs.getInt("SUM(amount)") - totalDebt();
			} else {
				return totalDebt();
			}
		}
	}

	public static Time convertStringToSqlTime(String timeString) {
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
		LocalTime localTime = LocalTime.parse(timeString, timeFormatter);
		return Time.valueOf(localTime);
	}

	int getPatientID(String name) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getName().equals(name)) {
				return list.get(i).getId();
			}
		}
		return 0;
	}

	int getAppointmentID() throws SQLException {
		String sql = "SELECT MAX(Appointment_id) + 1 AS next_id FROM appointments";
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

	int getDoctorID(String name) {
		for (int i = 0; i < doctorsList.size(); i++) {
			if (doctorsList.get(i).getName().getValue().equals(name)) {
				return doctorsList.get(i).getId().intValue();
			}
		}
		return 0;
	}

	int getNextPaymentId() throws SQLException {
		String sql = "SELECT MAX(P_id) + 1 AS next_id FROM payment";
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
