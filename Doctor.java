import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Doctor {
  private final IntegerProperty id;
  private final IntegerProperty clinic_id;
  private final StringProperty name;
  private final StringProperty speciality;
  private final StringProperty phoneNumber;
  char active;

  public Doctor(int id, int clinic_id, String name, String phoneNumber, String speciality,char active) {
    this.id = new SimpleIntegerProperty(id);
    this.clinic_id = new SimpleIntegerProperty(clinic_id);
    this.name = new SimpleStringProperty(name);
    this.phoneNumber = new SimpleStringProperty(phoneNumber);
    this.speciality = new SimpleStringProperty(speciality);
    this.active = active;
  }

public IntegerProperty getId() {
    return id;
}

public IntegerProperty getClinic_id() {
    return clinic_id;
}

public StringProperty getName() {
    return name;
}

public StringProperty getSpeciality() {
    return speciality;
}

public StringProperty getPhoneNumber() {
    return phoneNumber;
}


}
