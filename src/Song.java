
final public class Song {
    private String title;
    private String author;

    public int releaseYear; // Because the class is immutable, public variables are also final
    public int length;

    public Song(String title, String author, int releaseYear, int length) { // Constructor initialises everything
        this.title = title;
        this.author = author;
        this.releaseYear = releaseYear;
        this.length = length;
    }
    // Getters for private variables
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
    // Setters for private variables
    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
