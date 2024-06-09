import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Patients {
  private final IntegerProperty id;
  private final IntegerProperty age;
  private final StringProperty name;
  private final StringProperty phoneNumber;
  private final ObjectProperty<LocalDate> localDate;

  public Patients(int id, int age, String name, String phoneNumber, LocalDate localDate) {
    this.id = new SimpleIntegerProperty(id);
    this.age = new SimpleIntegerProperty(age);
    this.name = new SimpleStringProperty(name);
    this.phoneNumber = new SimpleStringProperty(phoneNumber);
    this.localDate = new SimpleObjectProperty<>(localDate);
  }
	

  static int patientDebt(int id) throws SQLException {
		String sql = "SELECT SUM(price) AS current_debt FROM appointments WHERE P_id = " + id;
		try (Connection conn = DriverManager.getConnection(App.JDBC_URL, App.USERNAME, App.PASSWORD);
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {

			if (rs.next()) {
				return rs.getInt("current_debt");
			} else {
				return 0;
			}
		}
	}

	static int patientBalance(int id) throws SQLException {
		String sql = "select SUM(amount) AS current_amount from financial where P_id = " + id;
		try (Connection conn = DriverManager.getConnection(App.JDBC_URL, App.USERNAME, App.PASSWORD);
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {

			if (rs.next()) {
				return rs.getInt("current_amount");
			} else {
				return 0;
			}
		}
	}

  int totalDebt() throws SQLException{
    return patientDebt(id.get()) > patientBalance(id.get()) ? patientDebt(id.get()) - patientBalance(id.get()) : 0;
  }

  IntegerProperty totalDebtProperty() throws SQLException{
    return new SimpleIntegerProperty(totalDebt());
  }
  // Getter methods for properties 
  public IntegerProperty idProperty() {
    return id;
  }

  public IntegerProperty ageProperty() {
    return age;
  }

  public StringProperty nameProperty() {
    return name;
  }

  public StringProperty phoneNumberProperty() {
    return phoneNumber;
  }

  public IntegerProperty getAgeProperty() {
    return age;
  }

  @SuppressWarnings("rawtypes")
  public ObjectProperty getLocalDateProperty() {
    return localDate;
  }

  // Other getter and setter methods
  public int getId() {
    return id.get();
  }

  public void setId(int id) {
    this.id.set(id);
  }

  public String getName() {
    return name.get();
  }

  public void setName(String name) {
    this.name.set(name);
  }

  public String getPhoneNumber() {
    return phoneNumber.get();
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber.set(phoneNumber);
  }

  public int getAge() {
    return age.get();
  }

  public void setAge(int age) {
    this.age.set(age);
  }

  public LocalDate getDate() {
    return localDate.get();
  }

  public void setDate(LocalDate localDate) {
    this.localDate.set(localDate);
  }

  // toString method for debugging
  @Override
  public String toString() {
    return ("ID: " +
        getId() +
        ", Name: " +
        getName() +
        ", Phone Number: " +
        getPhoneNumber());
  }
}
