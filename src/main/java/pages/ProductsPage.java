package pages;

import com.codeborne.selenide.Condition;
import elements.Button;
import elements.Collection;
import org.openqa.selenium.By;
import pages.blocks.ProductCard;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;

public class ProductsPage {
    private final Collection<ProductCard>allProducts = new Collection<>(ProductCard.class, By.xpath("//div[@class='note-list row']//div[@class='col-3 mb-5']"));
//    //*[@class='wrap-ribbon']/ancestor::div[@class='note-list row']//div[@class='col-3 mb-5']
    private final Collection<ProductCard> productsWithoutDiscount = new Collection<>(ProductCard.class, By.xpath("//div[@class='note-list row']//div[@class='note-item card h-100']"));
    private final Collection<ProductCard> productsWithDiscount = new Collection<>(ProductCard.class,By.xpath("//div[@class='note-list row']//div[contains(@class, 'hasDiscount')]"));
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

    public List<String> getProductIdForProductsWithDiscount(){
        List<String> productsIds = new ArrayList<>();
        for (int i = 0; i < productsWithDiscount.size(); i++){
            ProductCard productCard = productsWithDiscount.getModel(i);
            String productId = productCard.getProductId();
            System.out.println("product Id: " + productId);
        }
        return productsIds;
    }

    public List<String> getProductIdForProductsWithoutDiscount(){
        List<String> productsIds = new ArrayList<>();
        for (int i = 0; i < productsWithoutDiscount.size(); i++){
            ProductCard productCard = productsWithoutDiscount.getModel(i);
            String productId = productCard.getProductId();
            System.out.println("product Id: " + productId);
        }
        return productsIds;
    }
}

