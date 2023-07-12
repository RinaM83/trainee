package stepdefs;

import config.ConfigReader;
import hooks.Hooks_new;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.hamcrest.Matchers;
import services.BasketDropdownService;
import utils.ApiUtils;

import java.util.HashMap;
import java.util.Map;

import static hooks.Hooks_new.csrfTokenFromHTML;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class BasketDropdownStepdefs {
    private final BasketDropdownService basketDropdownService = new BasketDropdownService();
    @Given("product basket is empty")
    public void product_basket_is_empty() {
        String BASE_URL = ConfigReader.getPropertyValue("url");
        String CLEAR_BASKET_PATH = "/basket/clear";
        String GET_BASKET_PATH = "/basket/get";

        ApiUtils apiUtils = new ApiUtils(BASE_URL);

//        Map<String, Object> formParams = new HashMap<>();
//        formParams.put("_csrf", csrfTokenFromHTML);

        Map<String, String> headers = new HashMap<>();
        headers.put("X-Csrf-Token", csrfTokenFromHTML);
        headers.put("X-Requested-With", "XMLHttpRequest");

        Map<String, String> cookies = new HashMap<>();
        cookies.put("Cookie", "PHPSESSID=" + Hooks_new.sessionValue + "; _csrf=" + Hooks_new.csrfToken);

        Response clearBasket = apiUtils.post(CLEAR_BASKET_PATH, new HashMap<>(), headers, cookies);

        Response getBasket = apiUtils.post(GET_BASKET_PATH, new HashMap<>(), headers, cookies);

        assertThat(clearBasket.getStatusCode(), equalTo(200));
        assertThat(clearBasket.getBody().asString(), Matchers.equalTo("{\"response\":true}"));
        assertThat(getBasket.getStatusCode(),equalTo(200));
        assertThat(getBasket.getBody().asString(), Matchers.equalTo("{\"response\":true,\"basket\":[],\"basketCount\":0,\"basketPrice\":0}"));

        System.out.println("Товары в корзине " + getBasket.getBody().asString());
    }

    @And("basket icon is visible on the navigation bar")
    public void basket_icon_is_visible_on_the_navigation_bar() {
        SoftAssertions softAssertions = new SoftAssertions();
        basketDropdownService.isBasketIconVisible(softAssertions);
        softAssertions.assertAll();
    }

    @When("user clicks on the basket icon")
    public void user_clicks_on_the_basket_icon() throws InterruptedException {
        basketDropdownService.openBasketDropdown();
        Thread.sleep(5000);
    }

    @And("a basket dropdown appears")
    public void a_basket_dropdown_appears() {
        SoftAssertions softAssertions = new SoftAssertions();
        basketDropdownService.isBasketDropdownAppears(softAssertions);
        softAssertions.assertAll();
    }
}
