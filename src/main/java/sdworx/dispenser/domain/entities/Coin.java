package sdworx.dispenser.domain.entities;

public class Coin {

    private Currency currency;
    private Integer amount;

    public Coin(Currency currency, Integer amount) {
        this.currency = currency;
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Integer getAmount() {
        return amount;
    }

}
