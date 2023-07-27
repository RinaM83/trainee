package stepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import org.assertj.core.api.SoftAssertions;
import org.junit.Assert;
import services.BasketDropdownService;
import services.ProductPageService;

public class BasketDropdownStepdefs {
    private final BasketDropdownService basketDropdownService;
    private final ProductPageService productPageService;
    private final ProductPageStepdefs productPageStepdefs;

    public BasketDropdownStepdefs(BasketDropdownService basketDropdownService, ProductPageService productPageService, ProductPageStepdefs productPageStepdefs) {
        this.basketDropdownService = basketDropdownService;
        this.productPageService = productPageService;
        this.productPageStepdefs = productPageStepdefs;
    }

    @Given("basket icon is visible on the navigation bar")
    public void basket_icon_is_visible_on_the_navigation_bar() {
        SoftAssertions softAssertions = new SoftAssertions();
        basketDropdownService.isBasketIconVisible(softAssertions);
        softAssertions.assertAll();
    }

    @When("user clicks on the basket icon")
    public void user_clicks_on_the_basket_icon() {
        basketDropdownService.scrollToBasketCounter();
        basketDropdownService.scrollToBasketIcon();
        basketDropdownService.openBasketDropdown();
    }

    @And("a basket dropdown appears")
    public void a_basket_dropdown_appears() {
        SoftAssertions softAssertions = new SoftAssertions();
        basketDropdownService.isBasketDropdownAppears(softAssertions);
        softAssertions.assertAll();
    }

    @And("user clicks on \"Go to the basket\" button")
    public void userClicksOnGoToTheBasketButton() {
        basketDropdownService.goToTheBasket();
    }

    @And("on the nav bar, the Product count={word} is displayed next to the basket icon")
    public void onTheNavBarTheNumberIsDisplayedNextToTheBasketIcon(String product_count) {
        SoftAssertions softAssertions = new SoftAssertions();
        basketDropdownService.isBasketCounterVisible(softAssertions);
        softAssertions.assertAll();
        String actualCount =  basketDropdownService.getBasketCountValue();
        int product_num = productPageStepdefs.getProductNum();
        int initial_num = productPageStepdefs.getInitialNum();

        String totalActualCount = String.valueOf((initial_num + product_num) * Integer.parseInt(product_count));

        System.out.println("Количество отображающееся в навбаре: " + actualCount);
        System.out.println("Реальное количество товаров в корзине: " + totalActualCount);
        System.out.println("product_count из сценария: " + product_count);
        System.out.println("Начальное количество товаров из ProductPageStepdefs: " + initial_num);
        System.out.println("Добавленное количество товаров из ProductPageStepdefs: " + product_num);

        Assert.assertEquals(actualCount, totalActualCount);
    }
}
