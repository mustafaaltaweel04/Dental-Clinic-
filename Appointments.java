
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Time;
import java.time.LocalDate;

public class Appointments {

	private IntegerProperty appointment_ID = new SimpleIntegerProperty();
	private IntegerProperty room_ID = new SimpleIntegerProperty();
	private IntegerProperty doctor_ID = new SimpleIntegerProperty();
	private IntegerProperty patient_ID = new SimpleIntegerProperty();
	private ObjectProperty<LocalDate> date = new SimpleObjectProperty<>();
	private ObjectProperty<Time> time = new SimpleObjectProperty<>();
	private StringProperty pName = new SimpleStringProperty();
	private StringProperty dName = new SimpleStringProperty();
	private IntegerProperty price = new SimpleIntegerProperty();
	// private IntegerProperty rNum = new SimpleIntegerProperty();

	public Appointments(int aid, int rid, int did, int pid, LocalDate date, Time time, String pName, String dName, int price) {
		this.appointment_ID = new SimpleIntegerProperty(aid);
		this.room_ID = new SimpleIntegerProperty(rid);
		this.doctor_ID = new SimpleIntegerProperty(did);
		this.patient_ID = new SimpleIntegerProperty(pid);
		this.date = new SimpleObjectProperty<>(date);
		this.time = new SimpleObjectProperty<>(time);
		this.pName = new SimpleStringProperty(pName);
		this.dName = new SimpleStringProperty(dName);
		this.price = new SimpleIntegerProperty(price);
	}

	
	public void setTime(ObjectProperty<Time> time) {
		this.time = time;
	}


	public IntegerProperty getPrice() {
		return price;
	}


	public void setPrice(int price) {
		this.price = new SimpleIntegerProperty(price);
	}

	public StringProperty getpName() {
		return pName;
	}

	public void setpName(StringProperty pName) {
		this.pName = pName;
	}

	public StringProperty getdName() {
		return dName;
	}

	public void setdName(StringProperty dName) {
		this.dName = dName;
	}

	public IntegerProperty getAppointment_ID() {
		return appointment_ID;
	}

	public void setAppointment_ID(IntegerProperty appointment_ID) {
		this.appointment_ID = appointment_ID;
	}

	public IntegerProperty getRoom_ID() {
		return room_ID;
	}

	public void setRoom_ID(IntegerProperty room_ID) {
		this.room_ID = room_ID;
	}

	public IntegerProperty getDoctor_ID() {
		return doctor_ID;
	}

	public void setDoctor_ID(IntegerProperty doctor_ID) {
		this.doctor_ID = doctor_ID;
	}

	public IntegerProperty getPatient_ID() {
		return patient_ID;
	}

	public void setPatient_ID(IntegerProperty patient_ID) {
		this.patient_ID = patient_ID;
	}

	public ObjectProperty<LocalDate> getDateProperty() {
		return date;
	}

	public void setDate(ObjectProperty<LocalDate> date) {
		this.date = date;
	}

	public ObjectProperty<Time> getTimepProperty() {
		return time;
	}

	public void setTimeProperty(ObjectProperty<Time> time) {
		this.time = time;
	}

	public int getA_id() {
		return appointment_ID.get();
	}

	public void setA_id(int id) {
		this.appointment_ID.set(id);
	}

	public int getR_id() {
		return room_ID.get();
	}

	public void setR_id(int id) {
		this.room_ID.set(id);
	}

	public LocalDate getDate() {
		return date.get();
	}

	public void setDate(LocalDate date) {
		this.date.set(date);
	}

	public Time getTime() {
		return time.get();
	}

	public void setTime(Time time) {
		this.time.set(time);
	}

	public String getPatientName() {
		return pName.get();
	}

	public void setPatientName(String pName) {
		this.pName.set(pName);
	}

	public String getDoctorName() {
		return dName.get();
	}

	public void setDoctorName(String dName) {
		this.dName.set(dName);
	}

	@Override
	public String toString() {
		return "Appointments [appointment_ID=" + appointment_ID + ", room_ID=" + room_ID + ", doctor_ID=" + doctor_ID
				+ ", patient_ID=" + patient_ID + ", date=" + date + ", time=" + time + ", pName=" + pName + ", dName="
				+ dName + "]";
	}


}
	// ========================================================
