package models;

/**
 * Movie Model
 * גרסה מודרנית של ה-Movie class מהפרויקט המקורי (2020)
 * הוסרה תלות ב-Derby DB — המידע מגיע עכשיו מ-TMDB API
 */
public class Movie {

    private int id;
    private String title;
    private String director;
    private int year;
    private String genre;
    private String description;
    private String imagePath;
    private double rating;

    // Constructor ריק
    public Movie() {}

    // Constructor מלא
    public Movie(int id, String title, String director, int year,
                 String genre, String description, double rating) {
        this.id = id;
        this.title = title;
        this.director = director;
        this.year = year;
        this.genre = genre;
        this.description = description;
        this.rating = rating;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", year=" + year +
                ", genre='" + genre + '\'' +
                ", rating=" + rating +
                '}';
    }
}
