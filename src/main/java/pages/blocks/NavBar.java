package pages.blocks;

import com.codeborne.selenide.SelenideElement;
import elements.Button;
import elements.Label;
import org.openqa.selenium.By;
import static com.codeborne.selenide.Selenide.$;

public class NavBar {
    private final Button basketIconButton = new Button($(By.xpath("//div[@id='navbarNav']//i[starts-with(@class, 'basket_icon')]")));
    private final Button basket = new Button($(By.xpath("//a[contains(text(),'Корзина')]")));
    private final Label basketCounter = new Label($(By.xpath("//span[contains(@class, 'basket-count-items')]")));
    private final Label logoIcon = new Label(($(By.xpath("//a[@class='navbar-brand']"))));

    public Button getBasketIconButton() {
        return basketIconButton;
    }

    public Button getBasket() {
        return basket;
    }

    public Label getBasketCounter() {
        return basketCounter;
    }

    public Label getLogoIcon() {
        return logoIcon;
    }

    public String getBasketCounterValue(){
        return basketCounter.getElementText();
    }
}

