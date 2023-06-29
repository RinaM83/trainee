package pages.blocks;

import com.codeborne.selenide.SelenideElement;
import elements.Button;
import org.openqa.selenium.By;
import static com.codeborne.selenide.Selenide.$;

public class NavBar {
    private final SelenideElement root = $(By.xpath("//nav[contains(@class, 'navbar')]/div"));
    private final Button basketIconButton = new Button(root.$(By.xpath("//i[contains(@class, 'basket')]")));

    public void openBasket(){
        basketIconButton.click();
    }

    public void isBasketIconIsVisible(){

    }
}
