package elements;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

public class Confirm extends BaseElement{

    public Confirm(By by) {
        super(by);
    }

    public Confirm(SelenideElement wrappedElement) {
        super(wrappedElement);
    }

    public void click(){
        this.logAction("Click");
        this.getWrappedElement()
                .hover()
                .click();
    }
}
