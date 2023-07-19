package services;

import pages.ProductsPage;
import pages.blocks.ProductCard;

import elements.Collection;

public class ProductPageService {

    private final ProductsPage productsPage = new ProductsPage();

    public void buyAllProducts(int numProducts, String counterValue){
        buyProducts(productsPage.getAllProducts(),numProducts,counterValue);
        int remainingNumProducts = numProducts - productsPage.getAllProducts().size();
        System.out.println("Осталось взять на стр 2: " + remainingNumProducts);

        if (remainingNumProducts > 0) {
            paginateToThePage(2);
            int availableOnNextPage = productsPage.getAllProducts().size();
            if (remainingNumProducts > availableOnNextPage){
                throw new IndexOutOfBoundsException("Отсутствует необходимое количество товаров!");
            }

            buyProducts(productsPage.getAllProducts(), remainingNumProducts, counterValue);
        }
    }
    public void buyProductsWithDiscount(int numProducts, String counterValue){
        buyProducts(productsPage.getProductsWithDiscount(),numProducts,counterValue);
        int remainingNumProducts = numProducts - productsPage.getProductsWithDiscount().size();
        System.out.println("Осталось взять на стр 2: " + remainingNumProducts);

        if (remainingNumProducts > 0) {
            paginateToThePage(2);
            int availableOnNextPage = productsPage.getProductsWithDiscount().size();
            if (remainingNumProducts > availableOnNextPage){
                throw new IndexOutOfBoundsException("Отсутствует необходимое количество товаров со скидкой!");
            }

            buyProducts(productsPage.getProductsWithDiscount(), remainingNumProducts, counterValue);
        }
    }

    public void buyProductsWithoutDiscount(int numProducts, String counterValue) {
        buyProducts(productsPage.getProductsWithoutDiscount(),numProducts,counterValue);
        int remainingNumProducts = numProducts - productsPage.getProductsWithoutDiscount().size();
        System.out.println("Осталось взять на стр 2: " + remainingNumProducts);

        if (remainingNumProducts > 0) {
            paginateToThePage(2);
        int availableOnNextPage = productsPage.getProductsWithoutDiscount().size();
        if (remainingNumProducts > availableOnNextPage){
            throw new IndexOutOfBoundsException("Отсутствует необходимое количество товаров без скидки!");
        }
            buyProducts(productsPage.getProductsWithoutDiscount(), remainingNumProducts, counterValue);
        }
    }

    private void buyProducts(Collection<ProductCard> products, int numProducts, String counterValue){
        int size = products.size();
        System.out.println("Нужно взять всего товаров: " + numProducts);
        System.out.println("Возьмем на стр 1: " + size);
        for (int i = 0; i < numProducts && i < size; i++) {
            products.getModel(i).setProductCounterValue(counterValue);
            products.getModel(i).buy();
        }
    }
    private void paginateToThePage(int pageNum){
        final int size = productsPage.getPaginationBtn().size();
        for (int i=0; i<pageNum && i<size;i++){
            productsPage.getPaginationBtn().getModel(i).click();
        }
    }
}
