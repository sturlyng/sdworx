package sdworx.dispenser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sdworx.dispenser.domain.entities.*;

import java.util.HashMap;
import java.util.Map;

public class DrinkMachineTest {

    static DrinkMachine defaultDrinkMachine;

    @BeforeEach
    void init() {
        defaultDrinkMachine = createDefaultDrinkMachine();
    }

    @Test
    void insertValidCoinTest() {
        Coin twentyCentCoin1 = new Coin(Currency.EURO, 20);
        defaultDrinkMachine.insertCoin(twentyCentCoin1);
        Assertions.assertTrue(1 == defaultDrinkMachine.getCoins().size());
    }

    @Test
    void insertInvalidAmountCoinTest() {
        Coin twentyCentCoin1 = new Coin(Currency.EURO, 21);
        defaultDrinkMachine.insertCoin(twentyCentCoin1);
        Assertions.assertTrue(0 == defaultDrinkMachine.getCoins().size());
    }

    @Test
    void insertInvalidCurrencyCoinTest() {
        Coin twentyCentCoin1 = new Coin(Currency.DOLAR, 20);
        defaultDrinkMachine.insertCoin(twentyCentCoin1);
        Assertions.assertTrue(0 == defaultDrinkMachine.getCoins().size());
    }

    @Test
    void requestItemWithExactAmountTest() {
        String productCode = "DRINK01";
        Coin fiftyCentCoin1 = new Coin(Currency.EURO, 50);
        Coin fiftyCentCoin2 = new Coin(Currency.EURO, 50);
        defaultDrinkMachine.insertCoin(fiftyCentCoin1);
        defaultDrinkMachine.insertCoin(fiftyCentCoin2);
        Integer productStockBeforeRequest = defaultDrinkMachine.getProducts().get(productCode).getStock();

        defaultDrinkMachine.requestItem(productCode);

        Assertions.assertEquals(1, productStockBeforeRequest
                - defaultDrinkMachine.getProducts().get(productCode).getStock());
    }

    @Test
    void requestItemWithExtraAmountTest() {
        String productCode = "DRINK01";
        Coin fiftyCentCoin1 = new Coin(Currency.EURO, 100);
        Coin fiftyCentCoin2 = new Coin(Currency.EURO, 50);
        defaultDrinkMachine.insertCoin(fiftyCentCoin1);
        defaultDrinkMachine.insertCoin(fiftyCentCoin2);
        Integer productStockBeforeRequest = defaultDrinkMachine.getProducts().get(productCode).getStock();

        defaultDrinkMachine.requestItem(productCode);

        Assertions.assertEquals(1, productStockBeforeRequest
                - defaultDrinkMachine.getProducts().get(productCode).getStock());
        Assertions.assertEquals(0, defaultDrinkMachine.getCurrentCredit());
    }

    @Test
    void requestItemWithInsufficientAmountTest() {
        String productCode = "DRINK01";
        Coin fiftyCentCoin1 = new Coin(Currency.EURO, 50);
        defaultDrinkMachine.insertCoin(fiftyCentCoin1);
        Integer productStockBeforeRequest = defaultDrinkMachine.getProducts().get(productCode).getStock();

        defaultDrinkMachine.requestItem(productCode);

        Assertions.assertEquals(productStockBeforeRequest,
                defaultDrinkMachine.getProducts().get(productCode).getStock());
    }

    @Test
    void refundMoneyTest() {
        Coin fiftyCentCoin1 = new Coin(Currency.EURO, 50);
        defaultDrinkMachine.insertCoin(fiftyCentCoin1);

        Integer refundedMoney = defaultDrinkMachine.refundMoney();

        Assertions.assertEquals(0, defaultDrinkMachine.getCurrentCredit());
        Assertions.assertEquals(50, refundedMoney);
    }

    static DrinkMachine createDefaultDrinkMachine() {
        Map<String, Drink> products = new HashMap<>();
        products.put("DRINK01", new Drink("Coca", "DRINK01", 100, 10));
        products.put("DRINK02", new Drink("Redbull", "DRINK02", 125, 10));
        products.put("DRINK03", new Drink("Water", "DRINK03", 50, 10));
        products.put("DRINK04", new Drink("Orange juice", "DRINK04", 195, 10));

        return new DrinkMachine(products);
    }

}
