package elements;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import utils.Loggers;

import static com.codeborne.selenide.Selenide.$;

public class BaseElement {

    private final SelenideElement wrappedElement;
    public BaseElement(By by) {
        this.wrappedElement = $(by);
    }
    public BaseElement(SelenideElement wrappedElement) {
        this.wrappedElement = wrappedElement;
    }
    protected SelenideElement getWrappedElement() {
        return wrappedElement;
    }


    public boolean isVisible() {
        this.logAction("Is visible");
        try {
            return getWrappedElement().isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    public boolean isNotVisible() {
        this.logAction("Is not visible");
        try {
            return !getWrappedElement().isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return true;
        }
    }

    public boolean isDisabled(){
        this.logAction("Is disabled");
        return getWrappedElement().is(Condition.disabled);
    }

    protected void logAction(String action){
        Loggers.trace(String.format("[%s] %s - %s",
                this.getClass().getSimpleName(),
                action,
                this.getWrappedElement().toString()));
    }
}

