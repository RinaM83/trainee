package utils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

public class ApiUtils {

    private String baseUrl;

    public ApiUtils(String baseUrl) {
        this.baseUrl = baseUrl;
        RestAssured.baseURI = baseUrl;
    }

    public Response get(String endpoint, Map<String, String> cookies) {
        return RestAssured.given()
                .cookies(cookies)
                .get(endpoint);
    }

    public Response post(String endpoint, Map<String, ?> formParams, Map<String, String> headers, Map<String, String> cookies) {
        return RestAssured.given()
                .headers(headers)
                .cookies(cookies)
                .contentType(ContentType.URLENC)
                .formParams(formParams)
                .post(endpoint);
    }

    public Response put(String endpoint, Object body) {
        return RestAssured.given()
                .body(body)
                .put(endpoint);
    }

    public Response delete(String endpoint, Map<String, String> cookies) {
        return RestAssured.given()
                .cookies(cookies)
                .delete(endpoint);
    }
}
