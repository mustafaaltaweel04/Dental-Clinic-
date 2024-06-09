import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Diagnosis {
	private final IntegerProperty dID;
	private final StringProperty type;

	public Diagnosis(int dID, String type) {
		this.dID = new SimpleIntegerProperty(dID);
		this.type = new SimpleStringProperty(type);

	}

	public IntegerProperty getdID() {
		return dID;
	}

	public StringProperty getType() {
		return type;
	}
	public SimpleIntegerProperty idProperty() {
		return (SimpleIntegerProperty) dID;
	}

}