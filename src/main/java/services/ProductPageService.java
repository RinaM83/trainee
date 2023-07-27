package services;

import elements.Button;
import elements.Label;
import pages.ProductsPage;
import pages.blocks.BasketDropdown;
import pages.blocks.BasketItem;
import pages.blocks.ProductCard;

import elements.Collection;
import utils.Loggers;

public class ProductPageService {

    private final ProductsPage productsPage;
    private final BasketDropdown basketDropdown;
    private final BasketDropdownService basketDropdownService;

    public ProductPageService(ProductsPage productsPage, BasketDropdown basketDropdown, BasketDropdownService basketDropdownService) {
        this.productsPage = productsPage;
        this.basketDropdown = basketDropdown;
        this.basketDropdownService = basketDropdownService;
    }

    private int count;

    public void buyAllProducts(int numProducts, String counterValue){
        Loggers.info("Buy products with and without discount");
        buyProducts(productsPage.getAllProducts(), numProducts, counterValue);
    }

    public void buyProductsWithDiscount(int numProducts, String counterValue){
        Loggers.info("Buy products with discount");
        productsPage.getProductIdForProductsWithDiscount();
        buyProducts(productsPage.getProductsWithDiscount(), numProducts, counterValue);
    }

    public void buyProductsWithoutDiscount(int numProducts, String counterValue) {
        Loggers.info("Buy products without discount");
        buyProducts(productsPage.getProductsWithoutDiscount(), numProducts, counterValue);
    }

    private void buyProducts(Collection<ProductCard> products, int numProducts, String counterValue) {
        int totalPages = productsPage.getPaginationBtn().size();
        System.out.println("Всего страниц: " + totalPages);
        int currentPage = 1;
        count = 0;

        while (numProducts > 0 && currentPage <= totalPages) {
            paginateToThePage(currentPage);
            int remainingNumProducts = numProducts;
            if (currentPage == 1) {
                remainingNumProducts -= count;
            }
            buyProductsOnThePage(products, remainingNumProducts, counterValue, currentPage);
            numProducts -= count;
            currentPage++;
        }
    }

    private void buyProductsOnThePage(Collection<ProductCard> products, int numProducts, String counterValue, int page) {
        int size = products.size();
        Loggers.info("Нужно взять всего товаров на " + page + " странице (-х) : " + numProducts);
        Loggers.info("Всего в коллекции на стр " + page + ": " + size);

        count = 0;
        for (int i = 0; i < size && count < numProducts; i++) {
            ProductCard productCard = products.getModel(i);

            if (productCard.getProductStockBalanceValue() > 0 && !isProductInBasket(productCard.getProductTitleValue())) {
                productCard.setProductCounterValue(counterValue);
                productCard.buy();
                count++;
                Loggers.info("Продукт " + productCard.getProductTitleValue() + " есть на складе и добавлен в корзину");

            } else {
                Loggers.info("Продукт " + productCard.getProductTitleValue() + " отсутствует на складе");
            }
        }
        Loggers.info("В корзину успешно добавлено на странице \" + page + \": \" + count + \" товаров");
//        System.out.println("В корзину успешно добавлено на странице " + page + ": " + count + " товаров");
    }

    public void paginateToThePage(int pageNum){
        Loggers.info("Paginate to the next page");
        final int size = productsPage.getPaginationBtn().size();
        for (int i = 0; i < pageNum && i < size; i++){
            Button paginationButton = productsPage.getPaginationBtn().getModel(i);
            paginationButton.click();
            paginationButton.isChangeColor("background-color", "rgba(0, 123, 255, 1)");
        }
    }

    public boolean isProductInBasket(String title) {
        Loggers.info("Comparison of products in the basket dropdown with products to be added to the basket");
        basketDropdownService.openBasketDropdownByTextLabel();
        final int size = basketDropdown.getBasketItems().size();
        for (int i = 0; i < size; i++) {
            BasketItem basketItem = basketDropdown.getBasketItems().getModel(i);
            basketItem.basketProductTltValue();
            if (basketItem.compareTitles(title)) {
                return true;
            }
        }
        return false;
    }
}
