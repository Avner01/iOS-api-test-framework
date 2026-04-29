package api;

import base.BaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ConfigReader;

public class ApiTest extends BaseTest {

    @Test
    public void testGetPopularMovies() {
        Response response = RestAssured
                .given()
                .queryParam("api_key", ConfigReader.get("tmdb.api.key"))
                .when()
                .get("/movie/popular")
                .then()
                .statusCode(200)
                .extract()
                .response();

        System.out.println("Response: " + response.asPrettyString());
        Assert.assertNotNull(response.jsonPath().getList("results"));
    }
}