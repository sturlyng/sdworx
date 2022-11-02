package sdworx.dispenser.domain.entities;

public interface Machine {

    Integer getCurrentCredit();
    void insertCoin(Coin coin);
    Boolean isValidCoin(Coin coin);
    void requestItem(String productCode);
    void serveItem(String productCode);
    Integer refundChange(String productCode);
    Integer refundMoney();
    String getProductStock(String productCode);
    void addNewItem(ProductImpl product);

}
