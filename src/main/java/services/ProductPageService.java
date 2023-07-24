package services;

import pages.ProductsPage;
import pages.blocks.ProductCard;

import elements.Collection;

public class ProductPageService {

    private final ProductsPage productsPage;

    public ProductPageService(ProductsPage productsPage) {
        this.productsPage = productsPage;
    }

    public void buyAllProducts(int startIndex, int numProducts, String counterValue){
        buyProducts(productsPage.getAllProducts(), startIndex, numProducts, counterValue);

        int remainingNumProducts = numProducts - (productsPage.getAllProducts().size() - startIndex);
        System.out.println("Осталось взять на стр 2: " + remainingNumProducts);

        if (remainingNumProducts > 0) {
            paginateToThePage(2);
            int availableOnNextPage = productsPage.getAllProducts().size();
            if (remainingNumProducts > availableOnNextPage){
                throw new IndexOutOfBoundsException("Отсутствует необходимое количество товаров!");
            }

            buyProducts(productsPage.getAllProducts(), 0, remainingNumProducts, counterValue);
        }
    }
    public void buyProductsWithDiscount(int startIndex, int numProducts, String counterValue){
        buyProducts(productsPage.getProductsWithDiscount(),startIndex, numProducts, counterValue);

        int remainingNumProducts = numProducts - (productsPage.getProductsWithDiscount().size() - startIndex);
        System.out.println("Осталось взять на стр 2: " + remainingNumProducts);

        if (remainingNumProducts > 0) {
            paginateToThePage(2);
            int availableOnNextPage = productsPage.getProductsWithDiscount().size();
            if (remainingNumProducts > availableOnNextPage){
                throw new IndexOutOfBoundsException("Отсутствует необходимое количество товаров со скидкой!");
            }

            buyProducts(productsPage.getProductsWithDiscount(),0, remainingNumProducts, counterValue);
        }
    }

    public void buyProductsWithoutDiscount(int startIndex, int numProducts, String counterValue) {
        buyProducts(productsPage.getProductsWithoutDiscount(), startIndex, numProducts, counterValue);

        int remainingNumProducts = numProducts - (productsPage.getProductsWithoutDiscount().size() - startIndex);
        System.out.println("Осталось взять на стр 2: " + remainingNumProducts);

        if (remainingNumProducts > 0) {
            paginateToThePage(2);
        int availableOnNextPage = productsPage.getProductsWithoutDiscount().size();
        if (remainingNumProducts > availableOnNextPage){
            throw new IndexOutOfBoundsException("Отсутствует необходимое количество товаров без скидки!");
        }
            buyProducts(productsPage.getProductsWithoutDiscount(),0, remainingNumProducts, counterValue);
        }
    }

    private void buyProducts(Collection<ProductCard> products, int startIndex, int numProducts, String counterValue){
        int size = products.size();
        System.out.println("Нужно взять всего товаров: " + numProducts);

        if (startIndex < 0 || startIndex >= size) {
            throw new IndexOutOfBoundsException("Неверный startIndex. Индекс должен быть в диапазоне [0, " + (size - 1) + "]");
        }

        System.out.println("Продуктов на стр 1: " + (size - startIndex));

        for (int i = startIndex; i < startIndex + numProducts && i < size; i++) {
            ProductCard productCard = products.getModel(i);

            if(productCard.getProductStockBalanceValue() > 0){
                productCard.setProductCounterValue(counterValue);
                productCard.buy();
            } else {
                System.out.println("Продукт " + productCard.getProductTitleValue() + " отсутствует на складе");
            }

        }
    }
    private void paginateToThePage(int pageNum){
        final int size = productsPage.getPaginationBtn().size();
        for (int i = 0; i < pageNum && i < size; i++){
            productsPage.getPaginationBtn().getModel(i).click();
        }
    }
}
