package com.dogapi.tests;

import com.dogapi.base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.TmsLink;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class BreedsTest extends BaseTest {

    @Test(groups = {"smoke", "regression"})
    @Description("GET /breeds/list/all retorna 200, status success, mensagem não vazia e contém labrador ou hound")
    @Severity(SeverityLevel.CRITICAL)
    @TmsLink("TC-001")
    public void listarTodasAsRacas() {
        given()
                .when()
                .get("/breeds/list/all")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("status", equalTo("success"))
                .body("message.size()", greaterThan(0))
                .body("message", anyOf(hasKey("labrador"), hasKey("hound")))
                .time(lessThan(2000L));
    }
}
