package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.ConfigReader;

import java.util.List;

import static io.restassured.RestAssured.given;

/**
 * MovieApiTest
 *
 * בודק את TMDB API — גרסת 2026 של ה-DataBase.java המקורי
 *
 * במקום לבדוק INSERT/SELECT ל-Derby DB,
 * עכשיו בודקים GET/POST ל-TMDB REST API
 *
 * TMDB API docs: https://developer.themoviedb.org
 */
public class MovieApiTest {

    private String apiKey;
    private static final String BASE_URL = "https://api.themoviedb.org/3";

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
        apiKey = ConfigReader.get("tmdb.api.key");
    }

    // ─────────────────────────────────────────────────────
    // TEST 1 — חיפוש סרט לפי שם (= loadAllMoviesFiltered)
    // בפרויקט המקורי: DataBase.loadAllMoviesFiltered(filter)
    // ─────────────────────────────────────────────────────
    @Test(groups = "api", description = "Search movie by title — like original loadAllMoviesFiltered()")
    public void testSearchMovieByTitle() {
        Response response = given()
                .param("api_key", apiKey)
                .param("query", "Jumanji")
                .when()
                .get("/search/movie")
                .then()
                .statusCode(200)
                .extract().response();

        List<String> titles = response.jsonPath().getList("results.title");
        Assert.assertFalse(titles.isEmpty(), "Search results should not be empty");

        boolean found = titles.stream()
                .anyMatch(title -> title.toLowerCase().contains("jumanji"));
        Assert.assertTrue(found, "Jumanji should appear in search results");

        System.out.println("✅ Movie search passed — Found: " + titles.get(0));
    }

    // ─────────────────────────────────────────────────────
    // TEST 2 — קבלת פרטי סרט לפי ID (= loadAllMovies)
    // בפרויקט המקורי: getMoviesFromResultSet(rs)
    // ─────────────────────────────────────────────────────
    @Test(groups = "api", description = "Get movie details by ID — like original getMoviesFromResultSet()")
    public void testGetMovieById() {
        int movieId = 353486; // Jumanji: The Next Level — כמו בפרויקט המקורי!

        Response response = given()
                .param("api_key", apiKey)
                .when()
                .get("/movie/" + movieId)
                .then()
                .statusCode(200)
                .extract().response();

        String title = response.jsonPath().getString("title");
        int year = Integer.parseInt(response.jsonPath().getString("release_date").substring(0, 4));
        double rating = response.jsonPath().getDouble("vote_average");

        Assert.assertEquals(title, "Jumanji: The Next Level");
        Assert.assertEquals(year, 2019);
        Assert.assertTrue(rating > 0, "Rating should be positive");

        System.out.println("✅ Movie details passed — " + title + " (" + year + ") Rating: " + rating);
    }

    // ─────────────────────────────────────────────────────
    // TEST 3 — קבלת ז'אנרים (= loadAllGenre)
    // בפרויקט המקורי: DataBase.loadAllGenre()
    // ─────────────────────────────────────────────────────
    @Test(groups = "api", description = "Get all genres — like original loadAllGenre()")
    public void testGetAllGenres() {
        Response response = given()
                .param("api_key", apiKey)
                .when()
                .get("/genre/movie/list")
                .then()
                .statusCode(200)
                .extract().response();

        List<String> genres = response.jsonPath().getList("genres.name");
        Assert.assertFalse(genres.isEmpty(), "Genres list should no…
