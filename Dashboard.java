import java.sql.SQLException;
import java.sql.Time;

import javafx.geometry.Insets;

import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Dashboard {
	AnchorPane root;
	Button patientsButton;
	Button doctorButton;
	Button appointmentsButton;
	Button needsButton;
	Button reportButton;
	Button Rooms;
	Button diagnosiss;
	
	Text patientsValue;
	Text appointmentsValue;
	Text earningsValue;
    Text totalValue;
    TableView<Appointments> tableView;
	TableView<Patients> debtTable;
	TableView<Labortary> barChart;

    Dashboard() {
		root = new AnchorPane();
		root.setPrefSize(800, 600);
		root.setStyle("-fx-background-color: #FFFFFF;");

		SplitPane splitPane = new SplitPane();
		splitPane.setDividerPositions(0.1834590517241379);
		splitPane.setPrefSize(799, 598);

		AnchorPane leftPane = new AnchorPane();
		leftPane.setPrefSize(100, 160);

		VBox vBox = new VBox();
		vBox.setAlignment(javafx.geometry.Pos.CENTER);
		vBox.setPrefSize(148, 597);
		vBox.setSpacing(20);
		vBox.setStyle("-fx-background-color: #FFFFFF;");

		patientsButton = new Button("Patients");
		patientsButton.setPrefSize(136, 25);
		patientsButton.setStyle("-fx-background-color: #FFFFFF; -fx-font-family: Helvetica;");
		patientsButton.setFont(new Font(16));

		doctorButton = new Button("Doctor");
		doctorButton.setPrefSize(136, 25);
		doctorButton.setStyle("-fx-background-color: #FFFFFF; -fx-font-family: Helvetica;");
		doctorButton.setFont(new Font(16));

		appointmentsButton = new Button("Appointments");
		appointmentsButton.setPrefSize(136, 25);
		appointmentsButton.setStyle("-fx-background-color: #FFFFFF; -fx-font-family: Helvetica;");
		appointmentsButton.setFont(new Font(16));

		needsButton = new Button("Needs");
		needsButton.setPrefSize(136, 25);
		needsButton.setStyle("-fx-background-color: #FFFFFF; -fx-font-family: Helvetica;");
		needsButton.setFont(new Font(16));

		reportButton = new Button("Report");
		reportButton.setPrefSize(136, 25);
		reportButton.setStyle("-fx-background-color: #FFFFFF; -fx-font-family: Helvetica;");
		reportButton.setFont(new Font(16));
		
		
		
		Rooms = new Button("Rooms");
		Rooms.setPrefSize(136, 25);
		Rooms.setStyle("-fx-background-color: #FFFFFF; -fx-font-family: Helvetica;");
		Rooms.setFont(new Font(16));
		
		diagnosiss= new Button("Diagnosiss");
		diagnosiss.setPrefSize(136, 25);
		diagnosiss.setStyle("-fx-background-color: #FFFFFF; -fx-font-family: Helvetica;");
		diagnosiss.setFont(new Font(16));

		vBox.getChildren().addAll(createPaneWithButton(patientsButton), createPaneWithButton(doctorButton),
				createPaneWithButton(appointmentsButton), createPaneWithButton(needsButton),
				createPaneWithButton(reportButton), createPaneWithButton(Rooms), createPaneWithButton(diagnosiss));

		leftPane.getChildren().add(vBox);
		splitPane.getItems().add(leftPane);

		AnchorPane rightPane = new AnchorPane();
		rightPane.setPrefSize(707, 494);

		HBox topHBox = new HBox();
		topHBox.setAlignment(javafx.geometry.Pos.TOP_CENTER);
		topHBox.setLayoutX(24);
		topHBox.setLayoutY(38);
		topHBox.setPrefSize(600, 81);
		topHBox.setSpacing(15);
        topHBox.setPadding(new Insets(10));

		topHBox.getChildren().addAll(createStatsPane("PATIENTS", "#191970", patientsValue = new Text("")),
				createStatsPane("APPOINTMENTS", "E0B0FF", appointmentsValue = new Text("10")),
				createStatsPane("EARNINGS", "#FF8C00", earningsValue = new Text("100k")));

		rightPane.getChildren().addAll(topHBox, createScheduleSection(), createDebtSection(),
				createTitleText("Dashboard", 14, 33), createTitleText("Schedule", 14, 172),
				createTitleText("Needs", 298, 173), createTitleText("Debt", 24, 427));

		splitPane.getItems().add(rightPane);
		root.getChildren().add(splitPane);

	}

	private Pane createPaneWithButton(Button button) {
		Pane pane = new Pane();
		pane.setPrefSize(136, 26);
		pane.getChildren().add(button);
		return pane;
	}

	private Pane createStatsPane(String labelText, String color, Text value) {
		Pane pane = new Pane();
		pane.setPrefSize(200, 200);
		pane.setStyle(String.format("-fx-background-color: %s; -fx-background-radius: 15;", color));

		Text label = new Text(labelText);
		label.setFill(javafx.scene.paint.Color.WHITE);
		label.setLayoutX(14);
		label.setLayoutY(31);
		label.setFont(new Font("Helvetica", 16));
		label.setStyle("-fx-font-weight: 600;");

		value.setFill(javafx.scene.paint.Color.WHITE);
		value.setLayoutX(130);
		value.setLayoutY(55);
		value.setFont(new Font("Helvetica", 24));
		value.setStyle("-fx-font-weight: 700;");

		pane.getChildren().addAll(label, value);
		return pane;
	}

	@SuppressWarnings("unchecked")
	private HBox createScheduleSection() {
		HBox scheduleHBox = new HBox();
		scheduleHBox.setAlignment(javafx.geometry.Pos.CENTER);
		scheduleHBox.setLayoutX(24);
		scheduleHBox.setLayoutY(178);
		scheduleHBox.setPrefSize(600, 218);
		scheduleHBox.setSpacing(15);
		scheduleHBox.setPadding(new Insets(10));

		Pane schedulePane = new Pane();
		schedulePane.setPrefSize(307, 218);
		schedulePane.setStyle("-fx-background-color: #191970; -fx-background-radius: 15;");

		// TableView<Object> tableView = new TableView<>();
		// tableView.setLayoutY(-1);
		// tableView.setPrefSize(282, 200);
		// tableView.getColumns().addAll(new TableColumn<>("Patient"), new TableColumn<>("Time"),
		// 		new TableColumn<>("Room"));

		        tableView = new TableView<>();
        tableView.setLayoutY(6);
		tableView.setLayoutX(6);
        tableView.setPrefSize(270, 185);

        TableColumn<Appointments, String> patientNameColumn = new TableColumn<>("Patient");
        // patientNameColumn.setPrefWidth(75);

        TableColumn<Appointments, Time> timeColumn = new TableColumn<>("Time");
        // phoneNumberColumn.setPrefWidth(98.4);

        TableColumn<Appointments, Integer> roomColumn = new TableColumn<>("Room");
        // debtColumn.setPrefWidth(98.4);

        TableColumn<Appointments, String> doctorColumn = new TableColumn<>("Doctor");

        patientNameColumn.setCellValueFactory(cellData -> cellData.getValue().getpName());
        timeColumn.setCellValueFactory(cellData -> cellData.getValue().getTimepProperty());
        roomColumn.setCellValueFactory(cellData -> cellData.getValue().getRoom_ID().asObject());
        doctorColumn.setCellValueFactory(cellData -> cellData.getValue().getdName());

		tableView.getColumns().addAll(patientNameColumn, timeColumn, roomColumn, doctorColumn);
		schedulePane.getChildren().add(tableView);

		Pane chartPane = new Pane();
		chartPane.setPrefSize(307, 218);
		chartPane.setStyle("-fx-background-color: E0B0FF; -fx-background-radius: 15;");

		barChart = new TableView<>();
		barChart.setLayoutX(6);
		barChart.setLayoutY(6);
		barChart.setPrefSize(270, 185);
		TableColumn<Labortary,String> need = new TableColumn<>("Product");
		need.setCellValueFactory(cellData -> cellData.getValue().getProductNameP());
		barChart.getColumns().add(need);
		chartPane.getChildren().add(barChart);


		scheduleHBox.getChildren().addAll(schedulePane, chartPane);
		return scheduleHBox;
	}

	@SuppressWarnings("unchecked")
	private Pane createDebtSection() {
		Pane debtPane = new Pane();
		debtPane.setLayoutX(36);
		debtPane.setLayoutY(434);
		debtPane.setPrefSize(578, 124);
		debtPane.setStyle("-fx-background-color: #FF8C00; -fx-background-radius: 15;");
		debtPane.setPadding(new Insets(10));

		debtTable = new TableView<>();
		debtTable.setLayoutX(-4);
		debtTable.setLayoutY(-1);
		debtTable.setPrefSize(378, 124);
		TableColumn<Patients,String> patientColumn= new TableColumn<>("Patient");
		TableColumn<Patients,Integer> debtColumn= new TableColumn<>("Debt");
		patientColumn.setCellValueFactory(cellValue -> cellValue.getValue().nameProperty());
		debtColumn.setCellValueFactory(cellValue -> {
			try {
				return cellValue.getValue().totalDebtProperty().asObject();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		});

		debtTable.getColumns().addAll(patientColumn,debtColumn);

		

		debtPane.getChildren().addAll(debtTable);
		return debtPane;
	}

	private Text createTitleText(String text, double x, double y) {
		Text title = new Text(text);
		title.setLayoutX(x);
		title.setLayoutY(y);
		title.setFont(new Font("System Bold", 18));
		return title;
	}

	public Text getPatientsValue() {
		return patientsValue;
	}

	public Text getAppointmentsValue() {
		return appointmentsValue;
	}

	public Text getEarningsValue() {
		return earningsValue;
	}

    public Text getTotalValue() {
        return totalValue;
    }
    
    public TableView<Appointments> getTableView() {
        return tableView;
    }

    public void setTableView(TableView<Appointments> tableView) {
        this.tableView = tableView;
    }
}
