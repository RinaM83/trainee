package elements;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.time.Duration;

public class Label extends BaseElement{
    public Label(By by) {
        super(by);
    }

    public Label(SelenideElement wrappedElement) {
        super(wrappedElement);
    }

    public boolean isDisplayed() {
        this.logAction("Is displayed");
        return this.getWrappedElement()
                .shouldBe(Condition.visible, Duration.ofMillis(5000))
                .isDisplayed();
    }

    public boolean isNotDisplayed(){
        this.logAction("Is not displayed");
        return this.getWrappedElement()
                .shouldNotBe(Condition.visible,Duration.ofMillis(5000))
                .isDisplayed();
    }
}
