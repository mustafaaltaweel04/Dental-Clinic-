import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class AppointmentsController {

    // Declare all the UI components as attributes
    AnchorPane root;
    private TextField patientNameField;
    private Button searchButton;
    private DatePicker datePicker;
    private Spinner<Integer> hourSpinner;
    private Spinner<Integer> minuteSpinner;
    private TextField timeTextField;
    private Button insertButton;
    private Button deleteButton;
    private Button updateButton;
    private ComboBox<String> doctorComboBox;
    private ComboBox<String> roomComboBox;
    private TableView<Appointments> appointmentsTableView;
    private TableView<Patients> patientsTableView;
    private TextField spinner;
    ComboBox<String> comboBox;
    ComboBox<String> filterComboBox;

    @SuppressWarnings("unchecked")
    AppointmentsController() {
        // Create and configure the main AnchorPane
        root = new AnchorPane();
        root.setPrefSize(800, 600);

        // Title text
        Text titleText = new Text("Appointments");
        titleText.setFont(new Font(20));
        titleText.setLayoutX(14);
        titleText.setLayoutY(29);

        // Add Appointment Pane
        Pane addAppointmentPane = new Pane();
        addAppointmentPane.setLayoutX(14);
        addAppointmentPane.setLayoutY(49);
        addAppointmentPane.setPrefSize(350, 438);
        addAppointmentPane.setStyle("-fx-background-color: #FF8C00; -fx-background-radius: 15;");

        // Add Appointment Text
        Text addAppointmentText = new Text("Add Appointment");
        addAppointmentText.setFont(new Font(18));
        addAppointmentText.setLayoutX(14);
        addAppointmentText.setLayoutY(34);

        // Patient name HBox
        HBox patientNameHBox = new HBox(5);
        patientNameHBox.setAlignment(javafx.geometry.Pos.CENTER);
        patientNameHBox.setLayoutX(14);
        patientNameHBox.setLayoutY(41);
        patientNameHBox.setPrefSize(296, 61);

        Label patientNameLabel = new Label("Patient name");
        patientNameLabel.setFont(new Font(14));

        patientNameField = new TextField();

        searchButton = new Button("Search");

        patientNameHBox.getChildren().addAll(patientNameLabel, patientNameField, searchButton);

        // Select Date HBox
        HBox dateHBox = new HBox(15);
        dateHBox.setAlignment(javafx.geometry.Pos.CENTER);
        dateHBox.setLayoutX(13);
        dateHBox.setLayoutY(93);
        dateHBox.setPrefSize(296, 61);

        Label dateLabel = new Label("Select Date");
        dateLabel.setFont(new Font(14));

        datePicker = new DatePicker();
        datePicker.setPrefSize(207, 26);

        dateHBox.getChildren().addAll(dateLabel, datePicker);

        // Select Time HBox
        HBox timeHBox = new HBox(2);
        timeHBox.setAlignment(javafx.geometry.Pos.CENTER);
        timeHBox.setLayoutX(14);
        timeHBox.setLayoutY(154);
        timeHBox.setPrefSize(296, 61);

        Label timeLabel = new Label("Select Time");
        timeLabel.setFont(new Font(14));

        hourSpinner = new Spinner<>(0, 23, 0);
        hourSpinner.setPrefSize(75, 26);

        minuteSpinner = new Spinner<>(0, 59, 0);
        minuteSpinner.setPrefSize(70, 26);

        timeTextField = new TextField();
        timeTextField.setPrefSize(66, 26);

        ChangeListener<Number> updateListener = (observable, oldValue, newValue) -> {
            int hours = hourSpinner.getValue();
            int minutes = minuteSpinner.getValue();
            timeTextField.setText(String.format("%02d:%02d", hours, minutes));
        };

        hourSpinner.valueProperty().addListener(updateListener);
        minuteSpinner.valueProperty().addListener(updateListener);

        timeHBox.getChildren().addAll(timeLabel, hourSpinner, minuteSpinner, timeTextField);

        // Dark Pane
        Pane darkPane = new Pane();
        darkPane.setLayoutX(21.0);
        darkPane.setLayoutY(264.0);
        darkPane.setPrefHeight(121.0);
        darkPane.setPrefWidth(296.0);
        darkPane.setStyle("-fx-background-radius: 15; -fx-background-color: #cc7000;");

        Text priceText = new Text();
        priceText.setLayoutX(14.0);
        priceText.setLayoutY(38.0);
        priceText.setStrokeWidth(0.0);
        priceText.setText("Price");
        priceText.setFont(new Font(14.0));

        Text diagnoseText = new Text();
        diagnoseText.setLayoutX(14.0);
        diagnoseText.setLayoutY(82.0);
        diagnoseText.setStrokeWidth(0.0);
        diagnoseText.setText("Diagnose");
        diagnoseText.setFont(new Font(14.0));

        spinner = new TextField("0");
        spinner.setLayoutX(79.0);
        spinner.setLayoutY(20.0);
        spinner.setPrefHeight(26.0);
        spinner.setPrefWidth(193.0);

        comboBox = new ComboBox<>();
        comboBox.setLayoutX(79.0);
        comboBox.setLayoutY(62.0);
        comboBox.setPrefHeight(26.0);
        comboBox.setPrefWidth(193.0);
        try {
            comboBox.getItems().addAll(readAllDiagnoses());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        darkPane.getChildren().addAll(priceText, diagnoseText, spinner, comboBox);

        // Buttons HBox
        HBox buttonsHBox = new HBox(10);
        buttonsHBox.setAlignment(javafx.geometry.Pos.CENTER);
        buttonsHBox.setLayoutX(37);
        buttonsHBox.setLayoutY(360);
        buttonsHBox.setPrefSize(266, 100);

        insertButton = new Button("insert/ادخال");
        deleteButton = new Button("delete/حذف");
        updateButton = new Button("update/تعديل");

        buttonsHBox.getChildren().addAll(insertButton, deleteButton, updateButton);

        // Doctor and Room HBox
        HBox doctorRoomHBox = new HBox(5);
        doctorRoomHBox.setAlignment(javafx.geometry.Pos.CENTER);
        doctorRoomHBox.setLayoutX(14);
        doctorRoomHBox.setLayoutY(215);
        doctorRoomHBox.setPrefSize(296, 48);

        Text doctorText = new Text("Doctor");
        doctorText.setFont(new Font(14));

        doctorComboBox = new ComboBox<>();
        doctorComboBox.setPrefSize(95, 26);

        Text roomText = new Text("Room");
        roomText.setFont(new Font(14));

        roomComboBox = new ComboBox<>();
        roomComboBox.setPrefSize(95, 26);

        doctorRoomHBox.getChildren().addAll(doctorText, doctorComboBox, roomText, roomComboBox);

        addAppointmentPane.getChildren().addAll(addAppointmentText, patientNameHBox, dateHBox, timeHBox, darkPane, buttonsHBox, doctorRoomHBox);

        // Appointments Table Pane
        Pane appointmentsPane = new Pane();
        appointmentsPane.setLayoutX(399);
        appointmentsPane.setLayoutY(21);
        appointmentsPane.setPrefSize(386, 290);
        appointmentsPane.setStyle("-fx-background-color: #191970; -fx-background-radius: 15;");

        // ComboBox for filtering appointments
        filterComboBox = new ComboBox<>();
        filterComboBox.setLayoutX(13);
        filterComboBox.setLayoutY(8);
        filterComboBox.setPrefWidth(100);
        filterComboBox.getItems().addAll("all", "week", "month");
        filterComboBox.setValue("all");

        appointmentsTableView = new TableView<>();
        appointmentsTableView.setLayoutX(13);
        appointmentsTableView.setLayoutY(38);
        appointmentsTableView.setPrefSize(361, 239);

        TableColumn<Appointments, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setPrefWidth(105.6);

        TableColumn<Appointments, LocalDate> dateColumn = new TableColumn<>("Date");
        dateColumn.setPrefWidth(74.4);

        TableColumn<Appointments, Time> timeColumn = new TableColumn<>("Time");
        timeColumn.setPrefWidth(48);

        TableColumn<Appointments, String> doctorColumn = new TableColumn<>("Doctor");
        doctorColumn.setPrefWidth(86.4);

        TableColumn<Appointments, Integer> roomColumn = new TableColumn<>("Room");
        roomColumn.setPrefWidth(48);

        doctorColumn.setCellValueFactory(cellData -> cellData.getValue().getdName());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getpName());
        roomColumn.setCellValueFactory(cellData -> cellData.getValue().getRoom_ID().asObject());
        timeColumn.setCellValueFactory(cellData -> cellData.getValue().getTimepProperty());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().getDateProperty());

        appointmentsTableView.getColumns().addAll(nameColumn, dateColumn, timeColumn, doctorColumn, roomColumn);

        appointmentsPane.getChildren().addAll(filterComboBox, appointmentsTableView);

        appointmentsTableView.getSortOrder().add(dateColumn);
        dateColumn.setSortType(TableColumn.SortType.ASCENDING);
        appointmentsTableView.sort();

        // Patients Table Pane
        Pane patientsPane = new Pane();
        patientsPane.setLayoutX(399);
        patientsPane.setLayoutY(317);
        patientsPane.setPrefSize(386, 275);
        patientsPane.setStyle("-fx-background-color: #E0B0FF; -fx-background-radius: 15;");

        patientsTableView = new TableView<>();
        patientsTableView.setLayoutX(12);
        patientsTableView.setLayoutY(8);
        patientsTableView.setPrefSize(361, 259);

        TableColumn<Patients, String> patientNameColumn = new TableColumn<>("Name");
        patientNameColumn.setPrefWidth(75);

        TableColumn<Patients, String> phoneNumberColumn = new TableColumn<>("Phone Number");
        phoneNumberColumn.setPrefWidth(98.4);

        TableColumn<Patients, Integer> debtColumn = new TableColumn<>("Debt/ديون");
        debtColumn.setPrefWidth(98.4);

        patientNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        phoneNumberColumn.setCellValueFactory(cellData -> cellData.getValue().phoneNumberProperty());
        debtColumn.setCellValueFactory(cellData -> {
            try {
                return cellData.getValue().totalDebtProperty().asObject();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });

        patientsTableView.getColumns().addAll(patientNameColumn, phoneNumberColumn, debtColumn);

        patientsPane.getChildren().add(patientsTableView);

        // Add all components to the root
        root.getChildren().addAll(titleText, addAppointmentPane, appointmentsPane, patientsPane);

    }

    public TextField getSpinner() {
        return spinner;
    }

    public void setSpinner(TextField spinner) {
        this.spinner = spinner;
    }

    public TextField getPatientNameField() {
        return patientNameField;
    }

    public void setPatientNameField(TextField patientNameField) {
        this.patientNameField = patientNameField;
    }

    public Button getSearchButton() {
        return searchButton;
    }

    public void setSearchButton(Button searchButton) {
        this.searchButton = searchButton;
    }

    public DatePicker getDatePicker() {
        return datePicker;
    }

    public void setDatePicker(DatePicker datePicker) {
        this.datePicker = datePicker;
    }

    public Spinner<Integer> getHourSpinner() {
        return hourSpinner;
    }

    public void setHourSpinner(Spinner<Integer> hourSpinner) {
        this.hourSpinner = hourSpinner;
    }

    public Spinner<Integer> getMinuteSpinner() {
        return minuteSpinner;
    }

    public void setMinuteSpinner(Spinner<Integer> minuteSpinner) {
        this.minuteSpinner = minuteSpinner;
    }

    public TextField getTimeTextField() {
        return timeTextField;
    }

    public void setTimeTextField(TextField timeTextField) {
        this.timeTextField = timeTextField;
    }

    public Button getInsert() {
        return insertButton;
    }

    public void setInsertButton(Button insertButton) {
        this.insertButton = insertButton;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(Button deleteButton) {
        this.deleteButton = deleteButton;
    }

    public Button getUpdateButton() {
        return updateButton;
    }

    public void setUpdateButton(Button updateButton) {
        this.updateButton = updateButton;
    }

    public ComboBox<String> getDoctorComboBox() {
        return doctorComboBox;
    }

    public void setDoctorComboBox(ComboBox<String> doctorComboBox) {
        this.doctorComboBox = doctorComboBox;
    }

    public ComboBox<String> getRoomComboBox() {
        return roomComboBox;
    }

    public void setRoomComboBox(ComboBox<String> roomComboBox) {
        this.roomComboBox = roomComboBox;
    }

    public TableView<Appointments> getAppointmentsTableView() {
        return appointmentsTableView;
    }

    public void setAppointmentsTableView(TableView<Appointments> appointmentsTableView) {
        this.appointmentsTableView = appointmentsTableView;
    }

    public TableView<Patients> getPatientsTableView() {
        return patientsTableView;
    }

    public void setPatientsTableView(TableView<Patients> patientsTableView) {
        this.patientsTableView = patientsTableView;
    }

    // Sql methods
    public ObservableList<String> readAllDiagnoses() throws SQLException{
        ArrayList<String> list = new ArrayList<>();
        String sql = "SELECT Dtype FROM diagnosis";

        try (Connection conn = DriverManager.getConnection(App.JDBC_URL, App.USERNAME, App.PASSWORD);
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String type = rs.getString("Dtype");
                list.add(type);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ObservableList<String> newList = FXCollections.observableArrayList(list);
        return newList;

    }
}
