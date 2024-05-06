import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Controller {
    private AnchorPane anchorPane;
    private MenuBar menuBar;
    private Menu tableMenu;
    private MenuItem patient, appointment, diagnose;
    private SplitPane splitPane;
    private TableView<Patients> tableView;
    private VBox leftVBox, rightVBox, allBottom ,allTop;
    private HBox leftOne, leftTwo, rightOne, buttonsHbox, all;
    private Label name, date, phone;
    private TextField nameT, phoneT;
    private DatePicker datePicker;
    private Button insert, delete, update;

    Controller() {
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
        all = new HBox(100);

        // Labels
        name = new Label("Name");
        phone = new Label("Phone");
        date = new Label("Birth Date");

        // TextFields
        nameT = new TextField();
        phoneT = new TextField();
        datePicker = new DatePicker();

        // Buttons
        insert = new Button("Insert");
        delete = new Button("Delete");
        update = new Button("Update");

        // Adding to HBox
        leftOne.getChildren().addAll(name, nameT);
        leftTwo.getChildren().addAll(phone, phoneT);
        rightOne.getChildren().addAll(date, datePicker);
        buttonsHbox.getChildren().addAll(insert, update, delete);
        leftOne.setAlignment(Pos.CENTER);
        leftTwo.setAlignment(Pos.CENTER);
        rightOne.setAlignment(Pos.CENTER);
        buttonsHbox.setAlignment(Pos.CENTER);

        // Adding to VBox
        leftVBox.getChildren().addAll(leftOne, leftTwo);
        rightVBox.getChildren().addAll(rightOne);
        allTop.getChildren().addAll(menuBar,tableView);
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

        // Adding to AnchorPane
        anchorPane.getChildren().addAll(splitPane, allBottom);
        AnchorPane.setTopAnchor(splitPane, 0.0);
        AnchorPane.setLeftAnchor(splitPane, 0.0);
        AnchorPane.setRightAnchor(splitPane, 0.0);
        AnchorPane.setBottomAnchor(allBottom, 0.0);
        AnchorPane.setLeftAnchor(allBottom, 0.0);
        AnchorPane.setRightAnchor(allBottom, 0.0);
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

    public TableView<Patients> getTableView() {
        return tableView;
    }

    public void setTableView(TableView<Patients> tableView) {
        this.tableView = tableView;
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
    
}
