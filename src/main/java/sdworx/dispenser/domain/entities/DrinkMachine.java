package sdworx.dispenser.domain.entities;

import java.util.*;

public class DrinkMachine implements Machine {

    private static final Map<Currency, List<Integer>> allowedCoins = Map.of(
            Currency.EURO, List.of(5, 10, 20, 50, 100, 200));

    private Status status;
    private final Map<String, Drink> products;
    private final List<Coin> coins;

    public DrinkMachine(Map<String, Drink> products) {
        this.products = products;
        this.coins = new ArrayList<>();
        this.status = Status.AVAILABLE;
    }

    @Override
    public Integer getCurrentCredit() {
        return coins.stream().mapToInt(Coin::getAmount).sum();
    }

    @Override
    public void insertCoin(Coin coin) {
        if (isValidCoin(coin)) {
            this.coins.add(coin);
        } else {
            System.out.println("Not valid coin, please insert a valid coin");
        }
    }

    @Override
    public Boolean isValidCoin(Coin coin) {
        return allowedCoins.containsKey(coin.getCurrency())
                && allowedCoins.get(coin.getCurrency()).contains(coin.getAmount());
    }

    @Override
    public void requestItem(String productCode) {
        if (this.products.containsKey(productCode)
                && this.getCurrentCredit() >= this.products.get(productCode).getPrice()) {
            this.status = Status.PREPARING;
            System.out.println("Serving drink");
            this.serveItem(productCode);
        }
    }

    @Override
    public void serveItem(String productCode) {
        System.out.println("Please pick up your drink");
        this.products.get(productCode).setStock(this.products.get(productCode).getStock() - 1);
        this.refundChange(productCode);
        this.coins.clear();
        this.status = Status.AVAILABLE;
    }

    @Override
    public Integer refundChange(String productCode) {
        Integer refundAmount = this.getCurrentCredit() - this.products.get(productCode).getPrice();
        // Refund
        return refundAmount;
    }

    @Override
    public Integer refundMoney() {
        // Refund currentCredit
        Integer amount = this.getCurrentCredit();
        this.coins.clear();
        return amount;
    }

    public String getProductStock(String productCode) {
        if (this.products.containsKey(productCode)) {
            return this.products.get(productCode).getStock().toString();
        } else {
            return "Invalid product code";
        }
    }

    @Override
    public void addNewItem(ProductImpl product) {
        if (!this.products.containsKey(product.getCode())) {
            this.products.put(product.getCode(), (Drink) product);
            System.out.println(product.getName() + " added to the catalog");
        } else {
            System.out.println("Drink with code " + product.getCode() + " already exists");
        }
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Map<String, Drink> getProducts() {
        return products;
    }

    public List<Coin> getCoins() {
        return coins;
    }

}
