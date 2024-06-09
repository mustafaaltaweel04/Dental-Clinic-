import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Labortary {
    private final StringProperty productName;
    private final StringProperty companyName;
    private final IntegerProperty id;
    private final DoubleProperty price;
    private final IntegerProperty quantity;
    char flag = 'E';
    
    public Labortary(String companyName, String productName, int id, double price, int quantity, char flag) {
        this.companyName = new SimpleStringProperty(companyName);
        this.productName = new SimpleStringProperty(productName);
        this.id = new SimpleIntegerProperty(id);
        this.price = new SimpleDoubleProperty(price);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.flag = flag;
    }

    public StringProperty getCompanyName() {
        return companyName;
    }

    public StringProperty getProductNameP() {
        return productName;
    }

    public IntegerProperty getIdP() {
        return id;
    }

    public DoubleProperty getPriceP() {
        return price;
    }

    public IntegerProperty getQuantityP() {
        return quantity;
    }
    public String getProductName() {
        return productName.get();
    }

    public void setProductName(String productName) {
        this.productName.set(productName);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public double getPrice() {
        return price.get();
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public int getQuantity() {
        return quantity.get();
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }
    



}
