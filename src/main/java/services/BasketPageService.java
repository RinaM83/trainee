package services;

import org.assertj.core.api.SoftAssertions;
import pages.BasketPage;
import utils.Loggers;

import java.util.logging.Logger;

import static utils.Assertions.isNotVisible;

public class BasketPageService {
    private final BasketPage basketPage = new BasketPage();
    public boolean isErrorMessageNotShown(){
        Loggers.info("Error on the Basket page should not be shown");
        return basketPage.getErrorMessage().isNotDisplayed();
    }
}
