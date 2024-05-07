import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

public class Driver extends Application {

  private static final String JDBC_URL = "jdbc:mysql://localhost:3306/Clinic";
  private static final String USERNAME = "root";
  private static final String PASSWORD = "Abd-Sh2021";
  public static int id = 0;
  ArrayList<Patients> list = new ArrayList<>();
  ObservableList<Patients> list_ObservableList;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    primaryStage.setTitle("Phase III");
    Controller controller = new Controller();
    getData(controller);

    controller.getInsert().setOnAction(e -> {
        try {
          String name = controller.getNameT().getText();
          LocalDate date = controller.getDatePicker().getValue();
          String phone = controller.getPhoneT().getText();
          insert(name, date, phone);
          updateObservableList(list_ObservableList, controller.getTableView());
        }
        catch (Exception insert) {
          insert.printStackTrace();
        }
      });
    controller.getUpdate().setOnAction(e -> {
        try {
          String name = controller.getNameT().getText();
          LocalDate date = controller.getDatePicker().getValue();
          String phone = controller.getPhoneT().getText();
          update(id, name, date, phone);
          updateObservableList(list_ObservableList, controller.getTableView());
        }
        catch (Exception update) {
          update.printStackTrace();
        }
      });

    controller.getDelete().setOnAction(e -> {
        try {
          delete(id);
          updateObservableList(list_ObservableList, controller.getTableView());
        } catch (Exception delete) {
          delete.printStackTrace();
        }
      });
    controller.getTableView().setOnMouseClicked(event -> {
        if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
          controller.getNameT().setText(controller.getTableView().getSelectionModel().getSelectedItem().getName());
          controller.getDatePicker().setValue(controller.getTableView().getSelectionModel().getSelectedItem().getDate());
          controller.getPhoneT().setText(((Patients) controller.getTableView().getSelectionModel().getSelectedItem()).getPhoneNumber());
          id = controller.getTableView().getSelectionModel().getSelectedItem().getId();
        }
      });
    primaryStage.setScene(new Scene(controller.getAnchorPane(), 800, 600));
    primaryStage.show();
  }

  @SuppressWarnings("unchecked")
  void getData(Controller controller) throws Exception {
    Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    Class.forName("com.mysql.cj.jdbc.Driver");
    String sql = "SELECT * FROM patients ";
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

    TableView<Patients> tableView = (TableView<Patients>) controller.getTableView();
    TableColumn<Patients, Integer> idColumn = new TableColumn<>("ID");
    TableColumn<Patients, Integer> ageColumn = new TableColumn<>("Age");
    TableColumn<Patients, String> nameColumn = new TableColumn<>("Name");
    TableColumn<Patients, String> phoneColumn = new TableColumn<>("Phone");
    TableColumn<Patients, LocalDate> localDateColumn = new TableColumn<>("Birth Date");

    phoneColumn.setEditable(true);
    idColumn.setCellValueFactory(cellData ->
      cellData.getValue().idProperty().asObject()
      );
    phoneColumn.setCellValueFactory(cellData ->
      cellData.getValue().phoneNumberProperty()
      );
    nameColumn.setCellValueFactory(cellData ->
      cellData.getValue().nameProperty()
    );
    ageColumn.setCellValueFactory(cellData ->
      cellData.getValue().ageProperty().asObject()
      );
    
    localDateColumn.setCellValueFactory(cellData ->
      cellData.getValue().getLocalDateProperty()
    );

    tableView.getColumns().addAll(idColumn, nameColumn, ageColumn, phoneColumn);
    list_ObservableList = FXCollections.observableArrayList(list);
    tableView.getItems().addAll(list_ObservableList);
  }

  void insert(String name, LocalDate date, String phone) throws Exception {
    int age = calculateAge(date);
    Date sqlDate = Date.valueOf(date);
    int id = list.getLast().getId() + 1 ;
    String sql =
      "insert into patients(p_id,p_age,p_name,p_phonenumber,p_bdate) values (" + id + ", " + age + ", '" + name + "', '" + phone + "', '" + sqlDate +"')";  // '1950-01-01'
    Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    Statement statement = conn.createStatement();
    statement.executeUpdate(sql);
    updateList(list); 
    System.out.println(list.getLast().toString() + " Has been added.");
  }

  void update(int id, String name, LocalDate date, String phone) throws Exception {
    int age = calculateAge(date);
    Date sqlDate = Date.valueOf(date);
    String sql1 = "update patients p set p.p_name = '" + name + "' , p.p_age = " + age + ", p.p_phonenumber = '" + phone + "', p.p_bdate = '" + sqlDate + "' where p.p_id =" + id;

    Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    Statement statement = conn.createStatement();
    statement.executeUpdate(sql1);
    updateList(list);
  }

  void delete(int id) throws Exception {
    String sql = "delete from patients p where p.p_id = " + id;
    Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    Statement statement = conn.createStatement();
    statement.executeUpdate(sql);
    updateList(list);
  }

  void updateObservableList(ObservableList<Patients> observableList, TableView<Patients> tableView) {
    list_ObservableList = FXCollections.observableArrayList(list);
    tableView.getItems().clear(); 
    tableView.getItems().addAll(list_ObservableList);
  }

  void updateList(ArrayList<Patients> list) {
    list.clear() ;
    try {
      Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
      Class.forName("com.mysql.cj.jdbc.Driver");
      String sql = "SELECT * FROM patients ";
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
    } catch (Exception e) {
      // TODO: handle exception
    }
  }

  public static int calculateAge(LocalDate birthDate) {

        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(birthDate, currentDate);
        int age = period.getYears();
        return age;
    }
}
