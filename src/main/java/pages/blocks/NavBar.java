package pages.blocks;

import com.codeborne.selenide.SelenideElement;
import elements.Button;
import elements.Label;
import org.openqa.selenium.By;
import static com.codeborne.selenide.Selenide.$;

public class NavBar {
    private final Button basketIconButton = new Button($(By.xpath("//div[@id='navbarNav']//i[starts-with(@class, 'basket_icon')]")));
    private final Label basketCounter = new Label($(By.xpath("//span[contains(@class, 'basket-count-items')]")));

    public Button getBasketIconButton() {
        return basketIconButton;
    }

    public Label getBasketCounter() {
        return basketCounter;
    }
}
