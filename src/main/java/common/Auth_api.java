package common;

import config.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static io.restassured.RestAssured.given;

public class Auth_api {

    private final String BASE_URL = ConfigReader.getPropertyValue("url");
    private final String LOGIN_PATH = "/login";
    private static final Logger logger = LogManager.getLogger(Auth_api.class);

    private String csrfToken;
    private String sessionValue;

    public void auth(String username, String password) {
        try {
            RestAssured.config().getRedirectConfig().followRedirects(true);
            RestAssured.baseURI = BASE_URL;

            // Отправляем GET-запрос на страницу авторизации
            Response loginResponse = RestAssured.get(LOGIN_PATH);

            // Получаем значение всех заголовков Set-Cookie из ответа сервера
            Headers loginHeaders = loginResponse.getHeaders();
            List<String> setCookieHeaders = loginHeaders.getValues("Set-Cookie");

            // Разбиваем значения заголовков Set-Cookie на отдельные куки и сохраняем их в Map
            Map<String, String> cookies = new HashMap<>();
            for (String setCookieHeader : setCookieHeaders) {
                for (String cookie : setCookieHeader.split(";")) { // удаляем пробел после точки с запятой
                    String[] parts = cookie.trim().split("="); // обрезаем пробелы в начале и конце и затем разбиваем на части
                    if (parts.length == 2) { // проверка что cookie содержит имя и значение
                        cookies.put(parts[0], parts[1]);
                    }
                }
            }

            // Извлекаем CSRF токен из HTML-кода страницы авторизации
            String htmlBody = loginResponse.getBody().asString();
            Document doc = Jsoup.parse(htmlBody);
            csrfToken = doc.select("meta[name=csrf-token]").attr("content");

//            System.out.println("csrfToken from GET " + csrfToken);

            if (!csrfToken.isEmpty()) {
                // Выполняем POST-запрос для аутентификации на сайте
                Response authResponse = given()
                        .cookies(cookies)
                        .contentType(ContentType.URLENC)
                        .formParam("LoginForm[username]", username)
                        .formParam("LoginForm[password]", password)
                        .formParam("LoginForm[rememberMe]", 1)
                        .formParam("_csrf", csrfToken)
                        .header("Referer", BASE_URL + LOGIN_PATH) // Добавляем заголовок Referer
                        .post(LOGIN_PATH);

                int statusCode = authResponse.getStatusCode();
                if (statusCode == 302) {
                    // Авторизация прошла успешно, получаем куки и/или токены безопасности из ответа
                    Headers authHeaders = authResponse.getHeaders();
                    List<String> authSetCookieHeaders = authHeaders.getValues("Set-Cookie");
                    String locationHeader = authHeaders.getValue("Location");

                    Map<String, String> authCookies = new HashMap<>();
                    for (String setCookieHeader : authSetCookieHeaders) {
                        for (String cookie : setCookieHeader.split(";")) {
                            String[] parts = cookie.trim().split("=");
                            if (parts.length == 2) {
                                authCookies.put(parts[0], parts[1]);
                            }
                        }
                    }

                    // Выполняем GET-запрос на URL, на который был перенаправлен пользователь, чтобы получить дополнительные данные
                    Response redirectResponse = RestAssured.given()
                            .cookies(authCookies)
                            .get(locationHeader);

                    // Проверяем, что получили корректный ответ после перенаправления
                    int redirectStatusCode = redirectResponse.getStatusCode();
                    if (redirectStatusCode == 200) {
                        // Доступ к защищенным ресурсам теперь разрешен, можно продолжать работу с приложением
                        String responseBody = redirectResponse.getBody().asString();
                        logger.info("Authorization was successful");
                        logger.debug("Response body after redirect -> {}", redirectResponse.getBody().asString());
//                        System.out.println("Responce body after redirect -> " + responseBody);
                    } else {
//                        System.out.println("Error: Something goes wrong after redirect"); // Что-то пошло не так при аутентификации
                        logger.warn("Error: Something goes wrong after redirect. Redirect status code -> {}", redirectStatusCode); // Что-то пошло не так при аутентификации
                    }
                } else {
//                    System.err.println("Authorization failed. Server response code: " + statusCode);
                    logger.error("Authorization failed. Server response code -> {}", statusCode);
                }
            } else {
//                System.err.println("CSRF-token was not found on authorization page");
                logger.error("CSRF-token was not found on authorization page");
                return;
            }

            // Получаем значение сессии из кук
            Map<String, String> allCookies = loginResponse.getCookies();
            sessionValue = allCookies.get("session");

        } catch (Exception e) {
            logger.error("An error occurred while trying to authenticate. Error message -> {}", e.getMessage());
        }
    }
    public String getCsrfToken() {
        return csrfToken;
    }

    public String getSessionValue() {
        return sessionValue;
    }
}

