public class SongCollection {
    private int count; // Storing number of elements
    private Song[] songs; // Array to store objects

    // Copies elements from an old array to a new one. The new array must be >= old array.
    private void copyArray(Song[] from, Song[] to) {
        // IntelliJ notes that it would be better to use System.arraycopy(). However, I don't think we are allowed
        // to use that type of function yet
        // System.arraycopy(from, 0, to, 0, from.length);
        for (int i = 0; i < from.length; ++i) {
            to[i] = from[i];
        }
    }

    // Swaps two array elements in a Movie array
    private void swap(Song[] arr, int index1, int index2) {
        Song buffer = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = buffer;
    }


    public SongCollection() {
        count = 0;
        songs = new Song[1]; // Creating the smallest array, so I can demonstrate the use of dynamic arrays
    }

    // (1) Adds a Song to the collection (uses dynamic arrays; resizes the array to make sure everything fits)
    public void add(Song song) {
        if (count == songs.length) {
            Song [] newArray = new Song[songs.length * 2]; // Creates a new array twice the size of the old one
            copyArray(songs, newArray);
            songs = newArray; // Replaces the old array with the new array
        }
        songs[count] = song;
        ++count;
    }

    // (2) Removes an element from the array and makes it contiguous
    public void remove(int i) {
        // Create a new array with removed i and shift all elements after to make them contiguous
        Song[] newArray = new Song[songs.length];
        --count; // Decrement count, as there is now one less element
        for (int index = 0; index < i; ++index) // Copies all elements before i to the new array
            newArray[index] = songs[index];
        for (int index = i; index < count; ++i)
            newArray[index] = songs[index + 1]; // Shifts all songs after i to the "left" of the array
    }

    // (3) Prints a single Song at index i using multiline format
    public void printOne(int i) {
        Song song = songs[i];
        System.out.println(song.toMultilineString());
    }

    // (4) Prints the collection's information in a detailed format
    public void printAll() {
        for (int i = 0; i < count; ++i) {
            System.out.printf("Index %d:\n", i);
            printOne(i);
        }
    }

    // (5) Prints enumerated list with one-line information about each song
    public void printList() {
        for (int i = 0; i < count; ++i) {
            System.out.printf("1: %s\n", songs[i].toString());
        }
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
                System.out.printf("1: %s\n", songs[i].toString());
        }
    }

    // (8) Finds and prints movies by year
    public void searchYear(int year) {
        for (int i = 0; i < count; ++i) {
            if (songs[i].releaseYear == year)
                System.out.printf("1: %s\n", songs[i].toString());
        }
    }

}
