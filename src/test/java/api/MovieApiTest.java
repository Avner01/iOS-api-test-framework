package api;

import base.BaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ConfigReader;

public class MovieApiTest extends BaseTest {

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

        Assert.assertNotNull(response.jsonPath().getList("results"));
        System.out.println("Popular Movies: " + response.jsonPath().getList("results.title"));
    }

    @Test
    public void testSearchMovie() {
        Response response = RestAssured
                .given()
                .queryParam("api_key", ConfigReader.get("tmdb.api.key"))
                .queryParam("query", "Avatar")
                .when()
                .get("/search/movie")
                .then()
                .statusCode(200)
                .extract()
                .response();

        Assert.assertTrue(response.jsonPath().getList("results").size() > 0);
        System.out.println("Search Results: " + response.jsonPath().getList("results.title"));
    }

    @Test
    public void testGetMovieById() {
        Response response = RestAssured
                .given()
                .queryParam("api_key", ConfigReader.get("tmdb.api.key"))
                .when()
                .get("/movie/76341")
                .then()
                .statusCode(200)
                .extract()
                .response();

        Assert.assertEquals(response.jsonPath().getString("title"), "Mad Max: Fury Road");
        System.out.println("Movie Title: " + response.jsonPath().getString("title"));
    }

    @Test
    public void testGetGenres() {
        Response response = RestAssured
                .given()
                .queryParam("api_key", ConfigReader.get("tmdb.api.key"))
                .when()
                .get("/genre/movie/list")
                .then()
                .statusCode(200)
                .extract()
                .response();

        Assert.assertNotNull(response.jsonPath().getList("genres"));
        System.out.println("Genres: " + response.jsonPath().getList("genres.name"));
    }

    @Test
    public void testInvalidApiKey() {
        RestAssured
                .given()
                .queryParam("api_key", "invalid_key_123")
                .when()
                .get("/movie/popular")
                .then()
                .statusCode(401);

        System.out.println("Invalid API Key test passed!");
    }

    @Test
    public void testGetMovieWithHeaders() {
        Response response = RestAssured
                .given()
                .header("Authorization", "Bearer " + ConfigReader.get("tmdb.api.key"))
                .header("Content-Type", "application/json")
                .when()
                .get("/movie/popular")
                .then()
                .statusCode(200)
                .extract()
                .response();

        Assert.assertNotNull(response.jsonPath().getList("results"));
        System.out.println("Headers Test Passed!");
    }

    @Test
    public void testFilterMoviesByGenre() {
        Response response = RestAssured
                .given()
                .queryParam("api_key", ConfigReader.get("tmdb.api.key"))
                .queryParam("with_genres", "28")
                .when()
                .get("/discover/movie")
                .then()
                .statusCode(200)
                .extract()
                .response();

        Assert.assertTrue(response.jsonPath().getList("results").size() > 0);
        System.out.println("Action Movies: " + response.jsonPath().getList("results.title"));
    }

    @Test
    public void testGetTopRatedMovies() {
        Response response = RestAssured
                .given()
                .queryParam("api_key", ConfigReader.get("tmdb.api.key"))
                .when()
                .get("/movie/top_rated")
                .then()
                .statusCode(200)
                .extract()
                .response();

        Assert.assertNotNull(response.jsonPath().getList("results"));
        System.out.println("Top Rated: " + response.jsonPath().getList("results.title"));
    }
}