package services;

import elements.Button;
import pages.ProductsPage;
import pages.blocks.ProductCard;

import elements.Collection;

public class ProductPageService {

    private final ProductsPage productsPage;

    public ProductPageService(ProductsPage productsPage) {
        this.productsPage = productsPage;
    }

    private int count;

    public void buyAllProducts(int numProducts, String counterValue){
        buyProducts(productsPage.getAllProducts(), numProducts, counterValue);
    }

    public void buyProductsWithDiscount(int numProducts, String counterValue){
        productsPage.getProductIdForProductsWithDiscount();
        buyProducts(productsPage.getProductsWithDiscount(), numProducts, counterValue);
    }

    public void buyProductsWithoutDiscount(int numProducts, String counterValue) {
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
        System.out.println("Нужно взять всего товаров на " + page + "-х страницах : " + numProducts);
        System.out.println("Всего в коллекции на стр " + page + ": " + size);

        count = 0;
        for (int i = 0; i < size && count < numProducts; i++) {
            ProductCard productCard = products.getModel(i);

            if (productCard.getProductStockBalanceValue() > 0) {
                productCard.setProductCounterValue(counterValue);
                productCard.buy();
                count++;
                System.out.println("Продукт " + productCard.getProductTitleValue() + " есть на складе в количестве 30 и добавлен в корзину");
            } else {
                System.out.println("Продукт " + productCard.getProductTitleValue() + " отсутствует на складе в количестве 30");
            }
        }

        System.out.println("В корзину успешно добавлено на странице " + page + ": " + count + " товаров.");
    }


    public void paginateToThePage(int pageNum){
        final int size = productsPage.getPaginationBtn().size();
        for (int i = 0; i < pageNum && i < size; i++){
            Button paginationButton = productsPage.getPaginationBtn().getModel(i);
            paginationButton.click();
            paginationButton.isChangeColor("background-color", "rgba(0, 123, 255, 1)");
        }
    }
}
