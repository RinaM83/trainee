package services;

import pages.ProductsPage;

import java.util.Random;

public class ProductPageService {

    private final ProductsPage productsPage = new ProductsPage();

    public void buyDifferentProductsWithDiscount(int numProducts, String counterValue){
        final int size = productsPage.getProductsWithDiscount().size();
        for (int i = 0; i < numProducts && i < size; i++){
            productsPage.getProductsWithDiscount().getModel(i).setProductCounterValue(counterValue);
            productsPage.getProductsWithDiscount().getModel(i).buy();
        }
    }

    public void buyDifferentProductsWithoutDiscount(int numProducts, String counterValue){
        final int size = productsPage.getProductsWithoutDiscount().size();
        for (int i = 0; i < numProducts && i < size; i++){
            productsPage.getProductsWithoutDiscount().getModel(i).setProductCounterValue(counterValue);
            productsPage.getProductsWithoutDiscount().getModel(i).buy();
        }
    }

    public int buyRandomProduct(){
        Random random = new Random();
        final int size = productsPage.getAllProducts().size();
        final int randomValue = random.nextInt(size);
        productsPage.getAllProducts().getModel(randomValue).buy();
        return randomValue;
    }
}
