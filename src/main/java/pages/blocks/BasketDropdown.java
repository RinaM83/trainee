package pages.blocks;

import elements.Button;
import elements.Collection;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class BasketDropdown {
    private final Collection<BasketItem> basketItems = new Collection<>(BasketItem.class, By.cssSelector("div[class$='dropdown-menu-right show']"));
    private final Button goToBasket = new Button($(By.xpath("//*[text()='Перейти в корзину']")));
    private final Button clearBasket = new Button($(By.xpath("//*[text()='Очистить корзину']")));

    public Collection<BasketItem> getBasketItems() {
        return basketItems;
    }

    public void goToBasket() {
        goToBasket.click();
    }

    public void clearBasket(){
        clearBasket.click();
    }
}

