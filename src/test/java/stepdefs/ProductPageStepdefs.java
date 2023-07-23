package stepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import pages.blocks.NavBar;
import services.ProductPageService;

public class ProductPageStepdefs {
    private final ProductPageService productPageService;
    private final NavBar navBar;

    public ProductPageStepdefs(ProductPageService productPageService, NavBar navBar) {
        this.productPageService = productPageService;
        this.navBar = navBar;
    }

    private int productNum;

    public int getProductNum() {
        return productNum;
    }

    @And("if Test case number = {int}, initial_num={int} product with discount added to the basket")
    public void ifTestCaseNumber4ProductWithDiscountAddedToTheBasket(int num, int initial_num) {
        if (num == 4) {
            productNum = initial_num;
            productPageService.buyProductsWithDiscount(initial_num, "1");
        } else {
            System.out.println("Test case number is not 4. Skipping the step.");
        }
    }

    @When("user adds product\\(-s) with discount={word} with Number of products={int} and quantity of each of the products Product count={word} to the basket")
    public void userAddsOneProductToTheBasketWithoutADiscount(String hasDiscount, int product_num, String product_count) {
        productNum = product_num;
        try {
            if (hasDiscount.equals("no")) {
                productPageService.buyProductsWithoutDiscount(product_num, product_count);
            } else if (hasDiscount.equals("yes")) {
                productPageService.buyProductsWithDiscount(product_num, product_count);
            } else if (hasDiscount.equals("all")) {
                productPageService.buyAllProducts(product_num, product_count);
            } else {
                System.out.println("Please check has_discount value in the scenario");
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Exception: " + e.getMessage());
            throw new AssertionError("Шаг не пройден", e);
        }
        String basketCounter = navBar.getBasketCounterValue();
        System.out.println("Товары в корзине: " + basketCounter);
        System.out.println("int product_num на ProductPageStepdefs: " + product_num);
        System.out.println("String product_count на ProductPageStepdefs: " + product_count);
    }
}
