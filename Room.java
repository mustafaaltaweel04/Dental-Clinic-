import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Room {
  private final IntegerProperty id;
  private final IntegerProperty dID;

  public Room(int id, int dID) {
    this.id = new SimpleIntegerProperty(id);
    this.dID = new SimpleIntegerProperty(dID);
  }

  public IntegerProperty idProperty() {
    return id;
  }

  public IntegerProperty dIDProperty() {
    return dID;
  }

  public int getId() {
    return id.get();
  }

  public void setId(int id) {
    this.id.set(id);
  }

  public int getDID() {
    return dID.get();
  }

  public void setDID(int dID) {
    this.dID.set(dID);
  }
  public String getRoomNumber() {
	  return getId() + "";
	}
}
