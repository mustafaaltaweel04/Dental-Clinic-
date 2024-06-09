import java.time.LocalDate;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PatientsController {
    private AnchorPane anchorPane;
    private MenuBar menuBar;
    private Menu tableMenu;
    private MenuItem patient, appointment, diagnose;
    private SplitPane splitPane, splitPaneVertical;
    private TableView<Patients> tableView;
    private VBox leftVBox, rightVBox, allBottom, allTop;
    private HBox leftOne, leftTwo, rightOne, buttonsHbox, searchBox, all;
    private Label name, date, phone, search;
    private TextField nameT, phoneT;
    private DatePicker datePicker;
    private Button insert, delete, update, details, searchButton,finantial;
    private TextField searchT;

    


    @SuppressWarnings("unchecked")
    PatientsController() {
        anchorPane = new AnchorPane();

        // MenuBar
        menuBar = new MenuBar();
        tableMenu = new Menu("Table");
        patient = new MenuItem("Patient");
        appointment = new MenuItem("Appointment");
        diagnose = new MenuItem("Diagnose");
        tableMenu.getItems().addAll(patient, appointment, diagnose);
        menuBar.getMenus().addAll(tableMenu);

        // TableView
        tableView = new TableView<>();
        tableView = new TableView<>();

        TableColumn<Patients, Integer> idColumn = new TableColumn<>("ID");
        TableColumn<Patients, Integer> ageColumn = new TableColumn<>("Age");
        TableColumn<Patients, String> nameColumn = new TableColumn<>("Name");
        TableColumn<Patients, String> phoneColumn = new TableColumn<>("Phone");
        TableColumn<Patients, LocalDate> localDateColumn = new TableColumn<>("Birth Date");

        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        ageColumn.setCellValueFactory(cellData -> cellData.getValue().ageProperty().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        phoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneNumberProperty());
        localDateColumn.setCellValueFactory(cellData -> cellData.getValue().getLocalDateProperty());

        tableView.getColumns().addAll(idColumn, nameColumn, ageColumn, phoneColumn, localDateColumn);

        // VBoxes
        leftVBox = new VBox(10);
        rightVBox = new VBox(10);
        allBottom = new VBox(50);
        allTop = new VBox();

        // HBoxes
        leftOne = new HBox(10);
        leftTwo = new HBox(10);
        rightOne = new HBox(10);
        buttonsHbox = new HBox(20);
        searchBox = new HBox(10);
        all = new HBox(100);

        // Labels
        name = new Label("Name");
        phone = new Label("Phone");
        date = new Label("Birth Date");
        search = new Label("Enter name:");

        // TextFields
        nameT = new TextField();
        phoneT = new TextField();
        datePicker = new DatePicker();
        searchT = new TextField();

        // Buttons
        insert = new Button("Insert/اضافة مريض  ");
        delete = new Button("Delete/حذف مريض ");
        update = new Button("Update/تحديث ");
        searchButton = new Button("Search/البحث عن مريض");
        details = new Button("Details/عرض تفاصيل عن مريض ");
        finantial=new Button("Finantial/الدفع");

        // Adding to HBox
        leftOne.getChildren().addAll(name, nameT);
        leftTwo.getChildren().addAll(phone, phoneT);
        rightOne.getChildren().addAll(date, datePicker);
        buttonsHbox.getChildren().addAll(insert, update, delete, details, finantial);
        searchBox.getChildren().addAll(search, searchT, searchButton);
        leftOne.setAlignment(Pos.CENTER);
        leftTwo.setAlignment(Pos.CENTER);
        rightOne.setAlignment(Pos.CENTER);
        buttonsHbox.setAlignment(Pos.CENTER);
        searchBox.setAlignment(Pos.CENTER);

        // Adding to VBox
        leftVBox.getChildren().addAll(leftOne, leftTwo);
        rightVBox.getChildren().addAll(rightOne);
        leftVBox.setAlignment(Pos.CENTER);
        allTop.setAlignment(Pos.CENTER);

        // Adding to top HBox
        all.getChildren().addAll(leftVBox, rightVBox);
        allBottom.getChildren().addAll(all, buttonsHbox);
        allBottom.setAlignment(Pos.CENTER);
        all.setAlignment(Pos.CENTER);

        // SplitPane
        splitPane = new SplitPane();
        splitPane.setOrientation(Orientation.VERTICAL);
        splitPane.getItems().addAll(allTop, allBottom); // MenuBar and TableView at the top
        splitPaneVertical = new SplitPane();
        splitPaneVertical.setOrientation(Orientation.HORIZONTAL);
        splitPaneVertical.getItems().addAll(tableView, searchBox);

        // adding to top VBox
        allTop.getChildren().addAll( splitPaneVertical);

        // Adjust sizes
        tableView.setPrefSize(500, 400);
        leftVBox.setPrefWidth(300);
        rightVBox.setPrefWidth(300);

        // Adding to AnchorPane
        anchorPane.getChildren().addAll(splitPane, allBottom);
        AnchorPane.setTopAnchor(splitPane, 0.0);
        AnchorPane.setLeftAnchor(splitPane, 0.0);
        AnchorPane.setRightAnchor(splitPane, 0.0);
        AnchorPane.setBottomAnchor(allBottom, 0.0);
        AnchorPane.setLeftAnchor(allBottom, 0.0);
        AnchorPane.setRightAnchor(allBottom, 0.0);
    }

    public Button getDetails() {
        return details;
    }

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    public void setAnchorPane(AnchorPane anchorPane) {
        this.anchorPane = anchorPane;
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }

    public void setMenuBar(MenuBar menuBar) {
        this.menuBar = menuBar;
    }

    public Menu getTableMenu() {
        return tableMenu;
    }

    public void setTableMenu(Menu tableMenu) {
        this.tableMenu = tableMenu;
    }

    public MenuItem getPatient() {
        return patient;
    }

    public void setPatient(MenuItem patient) {
        this.patient = patient;
    }

    public MenuItem getAppointment() {
        return appointment;
    }

    public void setAppointment(MenuItem appointment) {
        this.appointment = appointment;
    }

    public MenuItem getDiagnose() {
        return diagnose;
    }

    public void setDiagnose(MenuItem diagnose) {
        this.diagnose = diagnose;
    }

    public SplitPane getSplitPane() {
        return splitPane;
    }

    public void setSplitPane(SplitPane splitPane) {
        this.splitPane = splitPane; 
    }

    public SplitPane getSplitPaneVertical() {
        return splitPaneVertical;
    }

    public void setSplitPaneVertical(SplitPane splitPaneVertical) {
        this.splitPaneVertical = splitPaneVertical;
    }

    public TableView<Patients> getTableView() {
        return tableView;
    }

    public void setTableView(TableView<Patients> tableView) {
        this.tableView = tableView;
    }

    public VBox getLeftVBox() {
        return leftVBox;
    }

    public void setLeftVBox(VBox leftVBox) {
        this.leftVBox = leftVBox;
    }

    public VBox getRightVBox() {
        return rightVBox;
    }

    public void setRightVBox(VBox rightVBox) {
        this.rightVBox = rightVBox;
    }

    public VBox getAllBottom() {
        return allBottom;
    }

    public void setAllBottom(VBox allBottom) {
        this.allBottom = allBottom;
    }

    public VBox getAllTop() {
        return allTop;
    }

    public void setAllTop(VBox allTop) {
        this.allTop = allTop;
    }

    public HBox getLeftOne() {
        return leftOne;
    }

    public void setLeftOne(HBox leftOne) {
        this.leftOne = leftOne;
    }

    public HBox getLeftTwo() {
        return leftTwo;
    }

    public void setLeftTwo(HBox leftTwo) {
        this.leftTwo = leftTwo;
    }

    public HBox getRightOne() {
        return rightOne;
    }

    public void setRightOne(HBox rightOne) {
        this.rightOne = rightOne;
    }

    public HBox getButtonsHbox() {
        return buttonsHbox;
    }

    public void setButtonsHbox(HBox buttonsHbox) {
        this.buttonsHbox = buttonsHbox;
    }

    public HBox getSearchBox() {
        return searchBox;
    }

    public void setSearchBox(HBox searchBox) {
        this.searchBox = searchBox;
    }

    public HBox getAll() {
        return all;
    }

    public void setAll(HBox all) {
        this.all = all;
    }

    public Label getName() {
        return name;
    }

    public void setName(Label name) {
        this.name = name;
    }

    public Label getDate() {
        return date;
    }

    public void setDate(Label date) {
        this.date = date;
    }

    public Label getPhone() {
        return phone;
    }

    public void setPhone(Label phone) {
        this.phone = phone;
    }

    public Label getSearch() {
        return search;
    }

    public void setSearch(Label search) {
        this.search = search;
    }

    public TextField getNameT() {
        return nameT;
    }

    public void setNameT(TextField nameT) {
        this.nameT = nameT;
    }

    public TextField getPhoneT() {
        return phoneT;
    }

    public void setPhoneT(TextField phoneT) {
        this.phoneT = phoneT;
    }

    public DatePicker getDatePicker() {
        return datePicker;
    }

    public void setDatePicker(DatePicker datePicker) {
        this.datePicker = datePicker;
    }

    public Button getInsert() {
        return insert;
    }

    public void setInsert(Button insert) {
        this.insert = insert;
    }

    public Button getDelete() {
        return delete;
    }

    public void setDelete(Button delete) {
        this.delete = delete;
    }

    public Button getUpdate() {
        return update;
    }

    public void setUpdate(Button update) { 
        this.update = update;
    }

    public Button getSearchButton() {
        return searchButton;
    }

    public void setSearchButton(Button searchButton) {
        this.searchButton = searchButton;
    }

    public TextField getSearchT() {
        return searchT;
    }

    public void setSearchT(TextField searchT) {
        this.searchT = searchT;
    }

	public Button getFinantial() {
		return finantial;
	}

	public void setFinantial(Button finantial) {
		this.finantial = finantial;
	}

	public void setDetails(Button details) {
		this.details = details;
	}
    

}
