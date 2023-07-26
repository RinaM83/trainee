package elements;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

public class Button extends BaseElement {

    public Button(By by) {
        super(by);
    }

    public Button(SelenideElement wrappedElement) {
        super(wrappedElement);
    }

    public void click() {
        this.logAction("Click");
        this.getWrappedElement()
                .hover()
                .click();
    }

    public void isChangeColor(String cssProperty, String value) {
        this.logAction("Is change color");
        this.getWrappedElement()
                .shouldBe(Condition.cssValue(cssProperty, value));
    }
}
