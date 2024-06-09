import javafx.beans.property.StringProperty;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;

public class TreatmentHistory {
    StringProperty date;
    StringProperty type;
    int patientId;
    
    public TreatmentHistory(String date, String type, int patientId) {
        this.date = new SimpleStringProperty(date);
        this.type = new SimpleStringProperty(type);
        this.patientId = patientId;
    }
    public TreatmentHistory() {;
    }

    public void insertTreatment(int id,String type,String date) throws Exception{
        Connection conn = DriverManager.getConnection(App.JDBC_URL, App.USERNAME, App.PASSWORD);
		Class.forName("com.mysql.cj.jdbc.Driver");
		String sql = "Insert into treatmenthistory(P_id,Ttype,Tdate) VALUES (" + id +",'" +type +"','" + date+"') ";
		Statement statement = conn.createStatement();
		statement.executeUpdate(sql);

    }

    public ArrayList<TreatmentHistory> getTreatmentData(int id) throws Exception{
        ArrayList<TreatmentHistory> list = new ArrayList<>();
        Connection conn = DriverManager.getConnection(App.JDBC_URL, App.USERNAME, App.PASSWORD);
		Class.forName("com.mysql.cj.jdbc.Driver");
		String sql = "select * from treatmenthistory where P_id = " + id;
		Statement statement = conn.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            list.add(new TreatmentHistory(resultSet.getString("Tdate"),resultSet.getString("Ttype"),id));
        }
        System.out.println(list);
        return list;

    }
    


}
