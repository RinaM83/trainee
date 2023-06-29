package elements;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

public class Input extends BaseElement{
    public Input(By by) {
        super(by);
    }

    public Input(SelenideElement wrappedElement) {
        super(wrappedElement);
    }

    public void clearAndType(String text){
        this.logAction("Clear and type" + text);
        this.getWrappedElement().scrollIntoView(true).clear();
        this.getWrappedElement().sendKeys(text);
    }

    public void clear(){
        this.getWrappedElement().scrollIntoView(true).clear();
    }
}