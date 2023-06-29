package pages;

import elements.Label;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class BasketPage {
private Label errorMessage = new Label($(By.cssSelector(".site-error")));

    public Label getErrorMessage() {
        return errorMessage;
    }
}
