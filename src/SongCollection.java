import java.io.*;


public class SongCollection {
    private int count; // Storing number of elements
    private Song[] songs; // Array to store objects
    private final String collectionFile;

    // Checks if the input is an integer or not
    public static boolean isNumber(String text) {
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

    // Swaps two array elements in a Movie array
    private void swap(Song[] arr, int index1, int index2) {
        Song buffer = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = buffer;
    }

    // Parses title from a formatted line. Returns an empty String in case of incorrect input
    private String parseTitle(String line) {
        String[] elements = line.split("^Song: ");
        if (elements.length != 2)
            return ""; // Returns empty string for improper input
        String songQuotes = elements[1]; // String for song should always be second
        return songQuotes.substring(1, songQuotes.length() - 1); // remove quotes from string
    }

    // Returns the author string from line of formatted text. If input improper, empty string is returned
    private String parseAuthor(String line) {
        String[] elements = line.split("^Author: ");
        if (elements.length != 2)
            return ""; // Returns empty string for improper input
        return elements[1]; // Author string should always be second
    }

    // Returns length in seconds from formatted text; returns -1 for improper input
    private int parseLength(String line) {
        String[] elements = line.split("^Length: ");
        if (elements.length != 2) // Not enough elements = improper input
            return -1;
        String[] numbers = elements[1].split(":");
        if (numbers.length != 2 || !isNumber(numbers[0]) || !isNumber(numbers[1])) // Same as above check
            return -1;
        int minutes = Integer.parseInt(numbers[0]);
        int seconds = Integer.parseInt(numbers[1]);
        return minutes * 60 + seconds;
    }

    // Returns the year from line of formatted text; returns -1 for improper input
    private int parseYear(String line) {
        String[] elements = line.split("^Year: ");
        // Check whether there are enough elements and whether the year block can be parsed
        if (elements.length != 2 || !isNumber(elements[1]))
            return -1; // Returns 0 for improper input
        return Integer.parseInt(elements[1]); // Author string should always be second
    }

    public SongCollection() {
        count = 0;
        songs = new Song[1]; // Creating the smallest array, so I can demonstrate the use of dynamic arrays
        collectionFile = "songs.txt";
    }

    // (1) Adds a Song to the collection (uses dynamic arrays; resizes the array to make sure everything fits)
    public void add(Song song) {
        if (count == songs.length) {
            Song [] newArray = new Song[songs.length * 2]; // Creates a new array twice the size of the old one
            System.arraycopy(songs, 0, newArray, 0, songs.length);
            songs = newArray; // Replaces the old array with the new array
        }
        songs[count] = song;
        ++count;
    }

    // (2) Removes an element from the array and makes it contiguous
    public void remove(int i) {
        if (count == 0) // prevent user from removing when array is empty; prevent crashes
            return;
        // Create a new array with removed i and shift all elements after to make them contiguous
        Song[] newArray = new Song[songs.length];
        --count; // Decrement count, as there is now one less element
        // Copies all elements before i to the new array
        System.arraycopy(songs, 0, newArray, 0, i);
        // Copies all elements after i into the new array, shifted to the "left"
        System.arraycopy(songs, i + 1, newArray, i, count - i);
        songs = newArray; // Assigns songs to reflect the new array
    }

    // Prints a single song at index i in single line format along with index
    public void printShortOne(int i) {
        System.out.printf("%d: %s\n", i, songs[i].toString());
    }

    // (3) Prints a single Song at index i using multiline format along with index
    public void printOne(int i) {
        System.out.printf("%d:\n%s\n", i, songs[i].toMultilineString());
    }

    // (4) Prints the collection's information in a detailed format
    public void printAll() {
        for (int i = 0; i < count; ++i)
            printOne(i);
    }

    // (5) Prints enumerated list with one-line information about each song
    public void printList() {
        for (int i = 0; i < count; ++i)
            printShortOne(i);
    }

    // (6) Sorts the collection via optimised bubble sort
    public void sort() {
        boolean sorted;
        int innerLoopCount = count - 1; // inner loop optimisation
        do {
            sorted = true;
            for (int i = 0; i < innerLoopCount; ++i) { // I believe this loop can't get any further optimisations
                Song s1 = songs[i];
                Song s2 = songs[i + 1];
                if (s1.releaseYear > s2.releaseYear) {
                    swap(songs, i, i + 1);
                    sorted = false;
                }
            }
            --innerLoopCount;
        } while(!sorted); // I believe this should be an optimized outer loop, as it stops after the array is sorted
    }

    // (7) Finds and prints all movies where the substring is present in the title
    public void findTitle(String substring) {
        for (int i = 0; i < count; ++i) {
            if (songs[i].getTitle().toLowerCase().contains(substring.toLowerCase())) // Case-insensitive substring search
                printShortOne(i);
        }
    }

    // (8) Finds and prints movies by year
    public void searchYear(int year) {
        for (int i = 0; i < count; ++i) {
            if (songs[i].releaseYear == year)
                printShortOne(i);
        }
    }

    // (9) Saves the contents of the collection to file
    public void saveToFile() throws IOException {
        FileWriter fileWrite = new FileWriter(collectionFile);
        // The try-with-resources block will close the buffer regardless of whether an exception is thrown
        // It is equivalent to a try-finally block. The exception is rethrown, because we don't want to handle
        // Logging and similar actions at low-level functions
        try (BufferedWriter writer = new BufferedWriter(fileWrite)) {
            for (int i = 0; i < count; ++i) {
                writer.write(songs[i].toMultilineString());
                writer.write("\n");
            }
        }
    }

    // (10) Reads the contents of a file and adds elements to the collection
    public void readFromFile() throws IOException {
        FileReader fileRead = new FileReader(collectionFile);
        // The try-with-resources block will close the buffer regardless of whether an exception is thrown
        // It is equivalent to a try-finally block. The exception is rethrown, because we don't want to handle
        // Logging and similar actions at low-level functions
        try (BufferedReader reader = new BufferedReader(fileRead)) {
            String line;
            String title = "";
            String author = "";
            int length = 0;
            int year = 0;
            int count = 0;
            while ((line = reader.readLine()) != null) {
                switch (count % 4) {
                    case 0 -> { title = parseTitle(line); }
                    case 1 -> { author = parseAuthor(line); }
                    case 2 -> { length = parseLength(line); }
                    case 3 -> {
                        year = parseYear(line);
                        add(new Song(title, author, length, year));
                    }
                }
                ++count;
            }
        }
    }
}
