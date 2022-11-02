package sdworx.dispenser.domain.entities;

public class Drink implements Product {

    private String name;
    private String code;
    private Integer price;
    private Integer stock;

    public Drink(String name, String code, Integer price, Integer stock) {
        this.name = name;
        this.code = code;
        this.price = price;
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
