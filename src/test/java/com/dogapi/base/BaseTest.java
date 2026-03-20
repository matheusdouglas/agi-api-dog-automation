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
        try {
            io.restassured.RestAssured.
                    given().
                    when().
                    get("/breeds/list/all").
                    then().
                    statusCode(200);
        } catch (Exception ex) {
            throw new SkipException("API indisponível na execução atual: " + ex.getMessage());
        }
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
