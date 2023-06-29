package elements;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

public class Selector extends BaseElement{
    public Selector(By by) {
        super(by);
    }

    public Selector(SelenideElement wrappedElement) {
        super(wrappedElement);
    }

    public void select(Keys option){
        this.logAction("Select " + option);
        this.getWrappedElement().sendKeys(option);
    }
}