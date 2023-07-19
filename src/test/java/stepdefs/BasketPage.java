package stepdefs;

import io.cucumber.java.en.Then;
import org.assertj.core.api.SoftAssertions;
import services.BasketPageService;

public class BasketPage {
    private final BasketPageService basketPageService = new BasketPageService();
    @Then("user navigates to the basket page")
    public void userNavigatesToTheBasketPage() {
        SoftAssertions softAssertions = new SoftAssertions();
        basketPageService.isErrorMessageNotVisible(softAssertions);
    }
}
