package pages.blocks;

import com.codeborne.selenide.SelenideElement;
import elements.Button;
import elements.Label;

import static com.codeborne.selenide.Selenide.$;

public class BasketItem {
    private final Label basketProductTlt;
    private final Label basketProductPrice;
    private final Label basketProductCount;
    private final Button deleteProductBtn;

    public BasketItem(SelenideElement root) {
        this.basketProductTlt = new Label(root.$("span.basket-item-title"));
        this.basketProductPrice = new Label(root.$("span.basket-item-price"));
        this.basketProductCount = new Label(root.$("span[class^='basket-item-count']"));
        this.deleteProductBtn = new Button($(root.$("i[class^='actionDeleteProduct']")));
    }

    public Label getBasketProductTlt() {
        return basketProductTlt;
    }

    public Label getBasketProductPrice() {
        return basketProductPrice;
    }

    public Label getBasketProductCount() {
        return basketProductCount;
    }

    public String basketProductTltValue(){
        return basketProductTlt.getElementText();
    }

    public boolean compareTitles(String title){
        return basketProductTltValue().equals(title);
    }

    public String basketProductPriceValue(){
        return basketProductPrice.getElementText();
    }

    public String basketProductCountValue(){
        return basketProductCount.getElementText();
    }

    public void delete(){
        deleteProductBtn.click();
    }
}