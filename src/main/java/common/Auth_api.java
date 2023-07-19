package common;

import api.HTMLTokenExtractor;
import config.ConfigReader;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.ApiUtils;

import utils.Loggers;

public class Auth_api {

    private final String BASE_URL = ConfigReader.getPropertyValue("baseUrl");
    private final String LOGIN_PATH = ConfigReader.getPropertyValue("login_path");
//    private static final Logger logger = LogManager.getLogger(Auth_api.class);

    private String csrfToken;
    private String sessionValue;
    private String redirectUrl;

    private String xCsrfToken;

    public void auth(String username, String password) {

        HTMLTokenExtractor tokenExtractor = new HTMLTokenExtractor();

        try {
            ApiUtils apiUtils = new ApiUtils(BASE_URL);

            // Отправляем GET-запрос на страницу авторизации
            Response loginResponse =apiUtils.get(LOGIN_PATH,new HashMap<>());

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
            csrfToken = tokenExtractor.extractCsrfTokenFromHTML(htmlBody);
//            System.out.println("----HTML Token on the login page " + csrfToken);

            if (!csrfToken.isEmpty()) {
                // Создаем параметры формы для POST-запроса
                Map<String, Object> formParams = new HashMap<>();
                formParams.put("LoginForm[username]", username);
                formParams.put("LoginForm[password]", password);
                formParams.put("LoginForm[rememberMe]", 1);
                formParams.put("_csrf", csrfToken);

                // Создаем заголовки запроса
                Map<String, String> headers = new HashMap<>();
                headers.put("Referer", BASE_URL + LOGIN_PATH);

                // Выполняем POST-запрос для аутентификации на сайте
                Response authResponse = apiUtils.post(LOGIN_PATH, formParams, headers, cookies);

                int statusCode = authResponse.getStatusCode();
                if (statusCode == 302) {
                    // Авторизация прошла успешно, получаем куки и/или токены безопасности из ответа
                    Headers authHeaders = authResponse.getHeaders();
                    List<String> authSetCookieHeaders = authHeaders.getValues("Set-Cookie");
                    String locationHeader = authHeaders.getValue("Location");
                    // Сохраняем URL-адрес перенаправления
                    redirectUrl = locationHeader;

                    Map<String, String> authCookies = new HashMap<>();
                    for (String setCookieHeader : authSetCookieHeaders) {
                        for (String cookie : setCookieHeader.split(";")) {
                            String[] parts = cookie.trim().split("=");
                            if (parts.length == 2) {
                                authCookies.put(parts[0], parts[1]);
                            }
                        }
                    }

                    sessionValue = authCookies.get("PHPSESSID");
                    csrfToken = authCookies.get("_csrf");
                    // Выполняем GET-запрос на URL после перенаправления, чтобы убедиться, что все ок и получить токен
                    Response redirectResponse = apiUtils.get(locationHeader,authCookies);
                    // Проверяем, что перенаправление выполнено успешно
                    int redirectStatusCode = redirectResponse.getStatusCode();
                    if (redirectStatusCode == 200) {
                        // Все гуд, нужен токен для использования в остальных POST запросах на product page
                        String responseBody = redirectResponse.getBody().asString();
                        xCsrfToken = tokenExtractor.extractCsrfTokenFromHTML(responseBody);
//                        System.out.println("----Токен из html после перенаправления на Product page: " + xCsrfToken);
                        Loggers.info("Authorization was successful");
                    } else {
                        Loggers.warn("Error: Something goes wrong after redirect. Redirect status code -> " + redirectStatusCode); // Что-то пошло не так при аутентификации
                    }
                } else {
                    Loggers.error("Authorization failed. Server response code -> {}" + statusCode);
                }
            } else {
                Loggers.error("CSRF-token was not found on authorization page");
            }
        } catch (Exception e) {
            Loggers.error("An error occurred while trying to authenticate. Error message -> " + e.getMessage());
        }
    }

    public String getCsrfToken() {
        return csrfToken;
    }

    public String getxCsrfToken() {
        return xCsrfToken;
    }

    public String getSessionValue() {
        return sessionValue;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }
}


