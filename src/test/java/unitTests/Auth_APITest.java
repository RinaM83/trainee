package unitTests;

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
    System.out.println("CsrfToken= " + authapi.getCsrfToken());
    System.out.println("SessionValue= " + authapi.getSessionValue());
    System.out.println("RedirectURL= " + authapi.getRedirectUrl());
    System.out.println("X-Csrf-Token= " + authapi.getxCsrfToken());

}

}
