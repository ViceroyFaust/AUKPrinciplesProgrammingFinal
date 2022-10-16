
public class Song {
    private String title; // (1) Private instance variables
    private String author;

    public int secondsLength; // (1) Public instance variables
    public int releaseYear; // (6) Numeric property

    public Song(String title, String author, int secondsLength, int releaseYear) { // Constructor initialises everything
        this.title = title;
        this.author = author;
        this.secondsLength = secondsLength;
        this.releaseYear = releaseYear;
    }
    // (2) Getters for private variables
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    // (5) Returns a double representing minute length of the song. Length is stored in seconds
    public double getMinutes() {
        return secondsLength / 60.0; // 60 seconds in a minute
    }

    // (5) Returns a String representation of song length
    public String getMinutesString() {
        int minutes = secondsLength / 60;
        int seconds = secondsLength % 60;
        return String.format("%02d:%d", minutes, seconds);
    }

    // (2) Setters for private variables
    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    /* (3) Returns a multiline formatted string describing the class in the format:
        Song: "<TITLE>"
        Author: <AUTHOR>
        Length: <LENGTH> minutes
        Year: <YEAR>
     */
    public String toMultilineString() {
        return String.format("""
                Song: "%s"
                Author: %s
                Length: %s
                Year: %d""", getTitle(), getAuthor(), getMinutesString(), releaseYear);
    }

    /* (4) Returns a one line formatted description of the class in the format:
        "<TITLE>", by <AUTHOR>, <LENGTH> min, y. <RELEASE YEAR>     */
    public String toString() {
        return String.format("\"%s\", by %s, %s, y. %d", getTitle(), getAuthor(), getMinutesString(), releaseYear);
    }
}
