package com.dogapi.base;

import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.SkipException;

import java.time.Instant;

public class BaseTest {

    @BeforeClass
    public void setUp() {
        System.setProperty("allure.results.directory", "target/allure-results");
        System.setProperty("java.net.preferIPv4Stack", "true");
        RestAssured.baseURI = "https://dog.ceo/api";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        String inCI = System.getenv("GITHUB_ACTIONS");
        String allowHttp = System.getenv("ALLOW_EXTERNAL_HTTP");
        if ("true".equalsIgnoreCase(inCI) && !"true".equalsIgnoreCase(allowHttp)) {
            throw new SkipException("Execução na CI sem ALLOW_EXTERNAL_HTTP=true: pulando testes de integração externos");
        }
        boolean ok = false;
        int attempts = 0;
        while (attempts < 3 && !ok) {
            try {
                RestAssured.
                        given().
                        when().
                        get("/breeds/list/all").
                        then().
                        statusCode(200);
                ok = true;
            } catch (Exception e) {
                attempts++;
                try { Thread.sleep(700); } catch (InterruptedException ignored) { Thread.currentThread().interrupt(); }
            }
        }
        if (!ok) throw new SkipException("API indisponível na execução atual");
    }

    @AfterMethod(alwaysRun = true)
    public void attachOnFailure(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            Throwable t = result.getThrowable();
            String title = "Falha: " + result.getMethod().getMethodName();
            String details = t != null ? t.getMessage() : "Sem mensagem";
            String text = title + "\n" + details + "\n" + Instant.now();
            Allure.addAttachment("Failure Details", "text/plain", text, ".txt");
        }
    }
}
