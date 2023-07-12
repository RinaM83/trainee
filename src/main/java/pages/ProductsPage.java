package pages;

import elements.Collection;
import org.openqa.selenium.By;
import pages.blocks.NavBar;
import pages.blocks.ProductCard;

public class ProductsPage {

    private final Collection<ProductCard>allProducts = new Collection<>(ProductCard.class, By.xpath("//div[@class='note-list row']//div[@class='col-3 mb-5']"));
//    //*[@class='wrap-ribbon']/ancestor::div[@class='note-list row']//div[@class='col-3 mb-5']
    private final Collection<ProductCard> productsWithoutDiscount = new Collection<>(ProductCard.class, By.xpath("//div[@class='note-list row']//div[@class='note-item card h-100']"));
    private final Collection<ProductCard>productsWithDiscount = new Collection<>(ProductCard.class,By.xpath("//div[@class='note-list row']//div[contains(@class, 'hasDiscount')]"));



    public Collection<ProductCard> getAllProducts() {
        return allProducts;
    }

    public Collection<ProductCard> getProductsWithoutDiscount() {
        return productsWithoutDiscount;
    }

    public Collection<ProductCard> getProductsWithDiscount() {
        return productsWithDiscount;
    }
}

