package services;

import org.assertj.core.api.SoftAssertions;
import pages.BasketPage;

import static utils.Assertions.isNotVisible;

public class BasketPageService {
    private final BasketPage basketPage = new BasketPage();
    public boolean isErrorMessageNotShown(){
        return basketPage.getErrorMessage().isNotDisplayed();
    }
}
