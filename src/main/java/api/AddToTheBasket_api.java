package api;

import config.ConfigReader;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.DataTableType;
import io.restassured.response.Response;
import pages.ProductsPage;
import utils.ApiUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class AddToTheBasket_api {
    String BASE_URL = ConfigReader.getPropertyValue("baseUrl");
    String GET_BASKET_PATH = ConfigReader.getPropertyValue("get_basket_path");
    ;
    String CREATE_BASKET_PATH = ConfigReader.getPropertyValue("create_basket_path");

    ProductsPage productsPage = new ProductsPage();

    public void addToTheBasket(String xCsrfToken, String sessionValue, String csrfToken) {
        ApiUtils apiUtils = new ApiUtils(BASE_URL);
        Map<String, String> headers = new HashMap<>();
        headers.put("X-Csrf-Token", xCsrfToken);
        headers.put("X-Requested-With", "XMLHttpRequest");

        Map<String, String> cookies = new HashMap<>();
        cookies.put("PHPSESSID", sessionValue);
        cookies.put("_csrf", csrfToken);

                    Map<String, Object> formParams = new HashMap<>();
                    formParams.put("product", 1);
                    formParams.put("count", 1);

                    Response getBasketBeforeAdd = apiUtils.get(GET_BASKET_PATH, cookies);
                    assertThat(getBasketBeforeAdd.getStatusCode(), equalTo(200));
                    System.out.println("Товары в корзине перед ДОБАВЛЕНИЕМ в корзину: " + getBasketBeforeAdd.getBody().asString());

                    Response addToTheBasket = apiUtils.post(CREATE_BASKET_PATH, formParams, headers, cookies);
                    System.out.println("Добавление в корзину : " + addToTheBasket.getBody().asString());

                    Response getBasket = apiUtils.post(GET_BASKET_PATH, new HashMap<>(), headers, cookies);
                    System.out.println("Товары в корзине после добавления: " + getBasket.getBody().asString());

                    assertThat(addToTheBasket.getStatusCode(), equalTo(200));
                    assertThat(addToTheBasket.getBody().asString(), equalTo("{\"response\":true}"));
                    assertThat(getBasket.getStatusCode(), equalTo(200));
                }
            }
