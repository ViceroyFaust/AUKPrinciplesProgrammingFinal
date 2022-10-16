import java.io.IOException;
import java.util.Scanner;

public class Main {
    final private SongCollection songs;
    final private Scanner userInput;

    // Checks whether a string is in MM:SS format for song length
    private boolean isSongLength(String text) {
        String[] elements = text.split(":");
        if (elements.length != 2)
            return false;
        return isInt(elements[0]) && isInt(elements[1]);
    }

    // Checks if the input is an integer or not
    private boolean isInt(String text) {
        if (text.length() == 0 || (text.length() == 1 && text.charAt(0) == '-'))
            return false;
        int startIndex = 0;
        if (text.charAt(0) == '-')
            ++startIndex;
        for (int i = startIndex; i < text.length(); ++i) {
            char c = text.charAt(i);
            if (c < '0' || c > '9') // Check if char is a number, if not return false
                return false;
        }
        return true;
    }

    // Parses MM:SS into integer representing seconds length
    private int parseSongTime(String text) {
        String[] elements = text.split(":");
        int minutes = Integer.parseInt(elements[0]);
        int seconds = Integer.parseInt(elements[1]);
        return minutes * 60 + seconds;
    }

    // Returns a user string from the terminal
    private String getUserString(String prompt) {
        System.out.print(prompt);
        return userInput.nextLine();
    }

    private int getUserInt(String prompt) {
        String input;
        boolean validityCheck;
        do {
            input = getUserString(prompt);
            validityCheck = isInt(input);
            if (!validityCheck)
                System.out.println("ERROR: ENTER A VALID NUMBER");
        } while (!validityCheck);
        return Integer.parseInt(input);
    }

    // Prompt user for song title and return it
    private String getUserSongTitle() {
        return getUserString("Song title: ");
    }

    // Prompt user for song author and return it
    private String getUserSongAuthor() {
        return getUserString("Song author: ");
    }

    // Prompt user for song length in seconds and return it
    private int getUserSongLength() {
        String input;
        boolean valid;
        do {
            input = getUserString("Song length (MM:SS): ");
            valid = isSongLength(input);
            if (!valid)
                System.out.println("ERROR: BAD FORMATTING");
        } while (!valid);
        return parseSongTime(input);
    }

    // Prompt user for song release year and return it
    private int getUserSongYear() {
        return getUserInt("Song year: ");
    }

    // Prompts user to enter a song
    private Song getUserSong() {
        String title = getUserSongTitle();
        String author = getUserSongAuthor();
        int length = getUserSongLength();
        int year = getUserSongYear();
        return new Song(title, author, length, year);
    }

    private void printMenu() {
        System.out.println("""
                Choose an option to proceed:
                1 - Print summarized song list
                2 - Print detailed song list
                3 - Add new song
                4 - Remove song
                5 - Sort songs by year
                6 - Search songs by title
                7 - Search songs by year
                8 - Read songs from file
                9 - Save songs to file
                0 - exit program""");
    }

    // Prints a summarized, enumerated list
    private void printSummary() {
        songs.printSummarized();
    }

    // Prints a detailed, enumerated list
    private void printDetails() {
        songs.printDetailed();
    }

    // Adds a user provided song to the collection
    private void addSong() {
        songs.add(getUserSong());
    }

    // Removes song at user provided index
    private void removeSong() {
        int index = getUserInt("Index to remove: ");
        songs.remove(index);
    }

    // Sorts songs by year
    private void sortSongs() {
        songs.sort();
    }

    // Searches song collection by Title via user provided string
    private void searchSongTitle() {
        String title = getUserSongTitle();
        songs.searchTitle(title);
    }

    // Searches song collection by Year via user provided int
    private void searchSongYear() {
        int year = getUserSongYear();
        songs.searchYear(year);
    }

    // Saves collection to file
    private void saveFile() {
        try {
            songs.saveToFile();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    // Loads a collection from file
    private void readFile() {
        try {
            songs.readFromFile();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean menu() {
        printMenu();
        String choice = getUserString("Choice: ");
        switch (choice) {
            case "0" -> { return false; }
            case "1" -> printSummary();
            case "2" -> printDetails();
            case "3" -> addSong();
            case "4" -> removeSong();
            case "5" -> sortSongs();
            case "6" -> searchSongTitle();
            case "7" -> searchSongYear();
            case "8" -> readFile();
            case "9" -> saveFile();
            default -> System.out.println("ERROR: WRONG CHOICE");
        }
        return true;
    }

    public Main() {
        songs = new SongCollection();
        userInput = new Scanner(System.in); // Doesn't need to be closed, since it is a System stream
    }

    public void run() {
        boolean running;
        do {
            running = menu();
        } while(running);
    }

    public static void main(String[] args) {
        Main program = new Main();
        program.run();
    }
}