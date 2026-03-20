package com.dogapi.tests;

import com.dogapi.base.BaseTest;
import com.dogapi.utils.TestData;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.TmsLink;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ImagesTest extends BaseTest {

    @Test(dataProvider = "validBreeds", dataProviderClass = TestData.class, groups = {"regression"})
    @Description("GET /breed/{breed}/images com raça válida retorna 200 e lista de URLs .jpg/.png")
    @Severity(SeverityLevel.CRITICAL)
    @TmsLink("TC-002")
    public void imagensPorRacaValida(String breed) {
        given()
                .pathParam("breed", breed)
                .when()
                .get("/breed/{breed}/images")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("status", equalTo("success"))
                .body("message.size()", greaterThan(0))
                .body("message", everyItem(matchesPattern("(?i)^https?://.*\\.(jpg|png)$")))
                .time(lessThan(2000L));
    }

    @Test(groups = {"regression"})
    @Description("GET /breed/{breed}/images com raça inválida retorna 404")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("TC-003")
    public void imagensPorRacaInvalida() {
        given()
                .pathParam("breed", "xyz")
                .when()
                .get("/breed/{breed}/images")
                .then()
                .statusCode(404)
                .contentType("application/json")
                .time(lessThan(2000L));
    }

    @Test(groups = {"smoke"})
    @Description("GET /breeds/image/random retorna 200, status success e URL válida da Dog API")
    @Severity(SeverityLevel.CRITICAL)
    @TmsLink("TC-004")
    public void imagemAleatoria() {
        given()
                .when()
                .get("/breeds/image/random")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("status", equalTo("success"))
                .body("message", startsWith("https://images.dog.ceo/"))
                .time(lessThan(2000L));
    }

    @Test(dataProvider = "validBreeds", dataProviderClass = TestData.class, groups = {"regression"})
    @Description("GET /breed/{breed}/images/random retorna 200 e uma imagem válida")
    @Severity(SeverityLevel.CRITICAL)
    public void imagemAleatoriaPorRaca(String breed) {
        given()
                .pathParam("breed", breed)
                .when()
                .get("/breed/{breed}/images/random")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("status", equalTo("success"))
                .body("message", matchesPattern("(?i)^https?://.*\\.(jpg|png)$"))
                .time(lessThan(2000L));
    }

    @Test(groups = {"regression"})
    @Description("GET /breeds/image/random/3 retorna 3 imagens válidas")
    @Severity(SeverityLevel.NORMAL)
    public void multiplasImagensAleatorias() {
        given()
                .when()
                .get("/breeds/image/random/3")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("status", equalTo("success"))
                .body("message.size()", equalTo(3))
                .body("message", everyItem(matchesPattern("(?i)^https?://.*\\.(jpg|png)$")))
                .time(lessThan(2000L));
    }

    @Test(groups = {"regression"})
    @Description("GET /breed/hound/list retorna lista de sub-raças de hound")
    @Severity(SeverityLevel.MINOR)
    public void subRacasDeHound() {
        given()
                .when()
                .get("/breed/hound/list")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("status", equalTo("success"))
                .body("message.size()", greaterThan(0))
                .time(lessThan(2000L));
    }
}
