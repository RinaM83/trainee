package unitTests;

import api.ApiManager;
import common.Auth_api;
import config.ConfigReader;
import org.junit.jupiter.api.Test;

public class Auth_APITest {
private static Auth_api authapi;

@Test
    public void setAuth_api(){
    System.out.println(System.getProperty("user.dir"));
    authapi = new Auth_api();
    authapi.auth(ConfigReader.getPropertyValue("username"), ConfigReader.getPropertyValue("password"));

}

}
