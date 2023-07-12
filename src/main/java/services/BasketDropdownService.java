package services;

import org.assertj.core.api.SoftAssertions;
import pages.blocks.BasketDropdown;
import pages.blocks.NavBar;

import static utils.Assertions.isVisible;

public class BasketDropdownService {
    private final BasketDropdown basketDropdown= new BasketDropdown();
    private final NavBar navBar = new NavBar();

    public void openBasketDropdown(){
        navBar.getBasketIconButton().click();
    }

    public void isBasketIconVisible(SoftAssertions softAssertions){
        isVisible(softAssertions,navBar.getBasketIconButton(),"Basket icon");
    }

    public void isBasketCounterVisible(SoftAssertions softAssertions){
        isVisible(softAssertions, navBar.getBasketCounter(),"Basket Counter");
    }

    public void isBasketDropdownAppears(SoftAssertions softAssertions){
        isVisible(softAssertions,basketDropdown.getBasketDropdown(),"Basket Dropdown");
    }



    public void goToTheBasket(){
        basketDropdown.getGoToBasket().click();
    }
    public void clearBasket(){
        basketDropdown.getClearBasket();
    }

}
