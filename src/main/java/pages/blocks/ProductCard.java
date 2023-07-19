package pages.blocks;

import com.codeborne.selenide.SelenideElement;
import elements.Button;
import elements.Input;
import elements.Label;

public class ProductCard {
    private final Label productTitle;
    private final Button buyBtn;
    private final Input productCounter;

    public ProductCard(SelenideElement root){
        this.productTitle = new Label(root.$("div.note-list.row div[class^=product_name]"));
        this.buyBtn = new Button(root.$("div.note-list.row button.actionBuyProduct"));
        this.productCounter = new Input(root.$("div.note-list.row input[name='product-enter-count']"));
    }

    public Label getProductTitle() {
        return productTitle;
    }

    public Input getProductCounter() {
        return productCounter;
    }

    public void setProductCounterValue(String value) {
        productCounter.clearAndType(value);
    }

    public void getProductTitleValue(){
        productTitle.getElementText();
    }

    public void getProductCounterValue(){
        productCounter.getElementText();
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
