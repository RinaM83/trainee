package stepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.assertj.core.api.SoftAssertions;
import services.BasketPageService;

public class BasketPageStepdefs {
    private final BasketPageService basketPageService;

    public BasketPageStepdefs(BasketPageService basketPageService) {
        this.basketPageService = basketPageService;
    }

    @Then("user navigates to the basket page")
    public void userNavigatesToTheBasketPage() {
        basketPageService.isErrorMessageNotShown();
    }
}
