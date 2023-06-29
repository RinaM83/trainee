package elements;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

public class Button extends BaseElement{

    public Button(By by) {
        super(by);
    }

    public Button(SelenideElement wrappedElement) {
        super(wrappedElement);
    }

    public void click(){
        this.logAction("Click");
        this.getWrappedElement()
                .scrollIntoView(true)
                .hover()
                .click();
    }
}