import javafx.beans.property.*;

public class Payment {
    StringProperty product;
    IntegerProperty amount;
    
    public Payment(String product, int amount) {
        this.product = new SimpleStringProperty(product);
        this.amount = new SimpleIntegerProperty(amount);
    }

    

    
}
