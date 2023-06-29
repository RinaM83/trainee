package services;

import pages.ProductsPage;

import java.util.Random;

public class ProductPageService {

    private final ProductsPage productsPage = new ProductsPage();

    private void goToTheBasketDropdown(){
        productsPage.getNavBar().openBasket();
    }

    private void basketDropdownIsVisible(){

    }

    private void buyDifferentProductsWithDiscount(int numProducts, String counterValue){
        final int size = productsPage.getProductsWithDiscount().size();
        for (int i = 0; i < numProducts && i < size; i++){
            productsPage.getProductsWithDiscount().getModel(i).setProductCounterValue(counterValue);
            productsPage.getProductsWithDiscount().getModel(i).buy();
        }
    }

    private void buyDifferentProductsWithoutDiscount(int numProducts, String counterValue){
        final int size = productsPage.getProductsWithDiscount().size();
        for (int i = 0; i < numProducts && i < size; i++){
            productsPage.getProductsWithoutDiscount().getModel(i).setProductCounterValue(counterValue);
            productsPage.getProductsWithoutDiscount().getModel(i).buy();
        }
    }

    private void buyRandomProduct(){
        Random random = new Random();
        final int size = productsPage.getAllProducts().size();
        productsPage.getAllProducts().getModel(random.nextInt(size)).buy();
    }

}
