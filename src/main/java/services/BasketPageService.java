package services;

import org.assertj.core.api.SoftAssertions;
import pages.BasketPage;

import static utils.Assertions.isNotVisible;

public class BasketPageService {
    private final BasketPage basketPage = new BasketPage();
    public void isErrorMessageNotVisible(SoftAssertions softAssertions){
        isNotVisible(softAssertions, basketPage.getErrorMessage(),"Error message");
    }
}
