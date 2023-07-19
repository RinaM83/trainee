package pages;

import elements.Button;
import elements.Collection;
import org.openqa.selenium.By;
import pages.blocks.ProductCard;

import static com.codeborne.selenide.Selenide.$;

public class ProductsPage {
    private final Collection<ProductCard>allProducts = new Collection<>(ProductCard.class, By.xpath("//div[@class='note-list row']//div[@class='col-3 mb-5']"));
//    //*[@class='wrap-ribbon']/ancestor::div[@class='note-list row']//div[@class='col-3 mb-5']
    private final Collection<ProductCard> productsWithoutDiscount = new Collection<>(ProductCard.class, By.xpath("//div[@class='note-list row']//div[@class='note-item card h-100']"));
    private final Collection<ProductCard>productsWithDiscount = new Collection<>(ProductCard.class,By.xpath("//div[@class='note-list row']//div[contains(@class, 'hasDiscount')]"));
    private final Collection<Button> paginationBtn = new Collection<>(Button.class,By.xpath("//a[@class='page-link']"));



    public Collection<ProductCard> getAllProducts() {
        return allProducts;
    }

    public Collection<ProductCard> getProductsWithoutDiscount() {
        return productsWithoutDiscount;
    }

    public Collection<ProductCard> getProductsWithDiscount() {
        return productsWithDiscount;
    }

    public Collection<Button> getPaginationBtn() {
        return paginationBtn;
    }
}

