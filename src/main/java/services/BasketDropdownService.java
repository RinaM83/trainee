package services;

import org.assertj.core.api.SoftAssertions;
import pages.blocks.BasketDropdown;
import pages.blocks.NavBar;
import utils.Loggers;

import static utils.Assertions.isVisible;

public class BasketDropdownService {
    private final BasketDropdown basketDropdown= new BasketDropdown();
    private final NavBar navBar = new NavBar();

    public void scrollToBasketIcon() {
        navBar.getBasketIconButton().scrollIntoView();
    }
    public void scrollToBasketCounter() {
        navBar.getBasketCounter().scrollIntoView();
        navBar.getBasketCounter().isDisplayed();
    }
    public void logoIsDisplayed(){
        navBar.getLogoIcon().isDisplayed();
    }

    public void openBasketDropdown(){
        Loggers.info("Click on Basket icon");
        navBar.getBasketIconButton().click();
    }
    public void openBasketDropdownByTextLabel(){
        Loggers.info("Click on \"/\"Корзина\"/\" button");
        navBar.getBasket().click();
    }

    public void isBasketIconVisible(SoftAssertions softAssertions){
        Loggers.info("Basket icon is visible on the Navbar");
        isVisible(softAssertions,navBar.getBasketIconButton(),"Basket icon");
    }

    public void isBasketCounterVisible(SoftAssertions softAssertions){
        Loggers.info("Basket counter is visible on the Navbar");
        isVisible(softAssertions, navBar.getBasketCounter(),"Basket Counter");
    }

    public void isBasketDropdownAppears(SoftAssertions softAssertions){
        Loggers.info("Basket dropdown appears");
        isVisible(softAssertions,basketDropdown.getBasketDropdown(),"Basket Dropdown");
    }

    public String getBasketCountValue(){
        Loggers.info("Getting basket counter value");
        final String basketCounterValue;
        return basketCounterValue = navBar.getBasketCounterValue();
    }

    public void goToTheBasket(){
        Loggers.info("Click on \"/\"Перейти в корзину\"/\" button");
        basketDropdown.getGoToBasket().click();
    }
    public void clearBasket(){
        Loggers.info("Click on \"/\"Очистить корзину\"/\" button");
        basketDropdown.getClearBasket();
    }

}
