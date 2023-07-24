package pages.blocks;

import com.codeborne.selenide.SelenideElement;
import elements.Button;
import elements.Input;
import elements.Label;

public class ProductCard {
    private final Label productTitle;
    private final Button buyBtn;
    private final Input productCounter;
    private final Label stockBalance;

    public ProductCard(SelenideElement root){
        this.productTitle = new Label(root.$("div.note-list.row div[class^=product_name]"));
        this.buyBtn = new Button(root.$("div.note-list.row button.actionBuyProduct"));
        this.productCounter = new Input(root.$("div.note-list.row input[name='product-enter-count']"));
        this.stockBalance = new Label(root.$("div.note-list.row span[class^=product_count]"));
    }

    public Label getProductTitle() {
        return productTitle;
    }

    public Input getProductCounter() {
        return productCounter;
    }

    public Label getStockBalance() {
        return stockBalance;
    }

    public void setProductCounterValue(String value) {
        productCounter.clearAndType(value);
    }

    public String getProductTitleValue(){
        return productTitle.getElementText();
    }

    public void getProductCounterValue(){
        productCounter.getElementText();
    }

    public int getProductStockBalanceValue(){
        return Integer.parseInt(stockBalance.getElementText());
    }

    public boolean getProductTitleValue(String expectedValue) {
        String actualValue = productTitle.getElementText();
        return actualValue.equals(expectedValue);
    }

    public boolean getProductCountValue(String expectedValue){
        String actualValue = productCounter.getElementText();
        return actualValue.equals(expectedValue);
    }

    public void buy(){
        buyBtn.click();
    }

}
