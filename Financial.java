import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Financial {
    private final IntegerProperty fId;
    private final IntegerProperty amount;
    private final IntegerProperty balance;
    private final StringProperty fType;
    private final StringProperty fdate;
    private final IntegerProperty pId;

    public Financial(int fId, int amount, int balance, String fType,String fdate, int pId) {
        this.fId = new SimpleIntegerProperty(fId);
        this.amount = new SimpleIntegerProperty(amount);
        this.balance = new SimpleIntegerProperty(balance);
        this.fType = new SimpleStringProperty(fType);
        this.fdate = new SimpleStringProperty(fdate);
        this.pId = new SimpleIntegerProperty(pId);
    }
    public String getPatientName(int id){
        String sql = "SELECT P_name FROM patients WHERE P_id = " + id;

        try (Connection conn = DriverManager.getConnection(App.JDBC_URL, App.USERNAME, App.PASSWORD);
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return rs.getString("P_name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }
    public StringProperty PatientName(){
        return new SimpleStringProperty(getPatientName(pId.get()));
    }
    public IntegerProperty fIdProperty() {
        return fId;
    }

    public IntegerProperty amountProperty() {
        return amount;
    }

    public IntegerProperty balanceProperty() {
        return balance;
    }

    public StringProperty fTypeProperty() {
        return fType;
    }

    public StringProperty getFdate() {
        return fdate;
    }

    public IntegerProperty pIdProperty() {
        return pId;
    }

    public int getfId() {
        return fId.get();
    }

    public int getAmount() {
        return amount.get();
    }

    public int getBalance() {
        return balance.get();
    }

    public String getfType() {
        return fType.get();
    }

    public int getpId() {
        return pId.get();
    }
}
