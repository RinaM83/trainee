package api;

import config.ConfigReader;

import io.restassured.response.Response;
import utils.ApiUtils;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ClearBasket_api {
    String BASE_URL = ConfigReader.getPropertyValue("baseUrl");
    String GET_BASKET_PATH = ConfigReader.getPropertyValue("get_basket_path");;
    String CLEAR_BASKET_PATH = ConfigReader.getPropertyValue("clear_basket_path");

    public void clearBasket(String xCsrfToken, String sessionValue, String csrfToken){
        ApiUtils apiUtils = new ApiUtils(BASE_URL);
        Map<String, String> headers = new HashMap<>();
        headers.put("X-Csrf-Token",xCsrfToken);
        headers.put("X-Requested-With", "XMLHttpRequest");

        Map<String, String> cookies = new HashMap<>();
        cookies.put("PHPSESSID", sessionValue);
        cookies.put("_csrf", csrfToken);

        Response getBasketBeforeClear = apiUtils.get(GET_BASKET_PATH, cookies);
        assertThat(getBasketBeforeClear.getStatusCode(), equalTo(200));
        System.out.println("Товары в корзине перед очисткой: " + getBasketBeforeClear.getBody().asString());

        Response clearBasket = apiUtils.post(CLEAR_BASKET_PATH, new HashMap<>(), headers, cookies);
        System.out.println("Очистка корзины: " + clearBasket.getBody().asString());

        Response getBasket = apiUtils.post(GET_BASKET_PATH, new HashMap<>(), headers, cookies);
        System.out.println("Товары в корзине после очистки" + getBasket.getBody().asString());

        assertThat(clearBasket.getStatusCode(), equalTo(200));
        assertThat(clearBasket.getBody().asString(), equalTo("{\"response\":true}"));
        assertThat(getBasket.getStatusCode(), equalTo(200));
        assertThat(getBasket.getBody().asString(), equalTo("{\"response\":true,\"basket\":[],\"basketCount\":0,\"basketPrice\":0}"));
    }
}
