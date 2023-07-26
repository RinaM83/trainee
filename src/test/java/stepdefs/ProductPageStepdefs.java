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
    private int initialNum;

    public int getProductNum() {
        return productNum;
    }

    public int getInitialNum() {
        return initialNum;
    }

    @And("if initial_num={int} > {int}, this number of products with discount = {word} and count = {word} added to the basket")
    public void ifTestCaseNumber4ProductWithDiscountAddedToTheBasket(int initial_num, int a, String initial_hasDiscount, String initial_count) {
        if (initial_num > a) {
            initialNum = initial_num;
            try {
                if (initial_hasDiscount.equals("no")) {
                    productPageService.buyProductsWithoutDiscount(initial_num, initial_count);
                } else if (initial_hasDiscount.equals("yes")) {
                    productPageService.buyProductsWithDiscount(initial_num, initial_count);
                } else if (initial_hasDiscount.equals("all")) {
                    productPageService.buyAllProducts(initial_num, initial_count);
                } else {
                    System.out.println("Please check initial_has_discount value in the scenario");
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("! Exception: " + e.getMessage());
                throw new AssertionError("Шаг не пройден: ", e);
            }
        } else {
            System.out.println("Initial product number is not > " + a + ". Current step skipped");
        }
    }

    @When("initial_num={int} product added to the basket and user adds product\\(-s) with discount={word} with Number of products={int} and quantity of each of the products Product count={word} to the basket")
    public void userAddsOneProductToTheBasketWithoutDiscount(int initial_num, String hasDiscount, int product_num, String product_count) throws InterruptedException {
        initialNum = initial_num;
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
            System.out.println("! Exception: " + e.getMessage());
            throw new AssertionError("Шаг не пройден", e);
        }
        String basketCounter = navBar.getBasketCounterValue();
        System.out.println("Товары в корзине: " + basketCounter);
        System.out.println("initial_num на ProductPageStepdefs: " + initial_num);
        System.out.println("product_num на ProductPageStepdefs: " + product_num);
        System.out.println("String product_count на ProductPageStepdefs: " + product_count);
    }
}
