package schedule.ship;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * The class implements the generation of the ship name from an existing file.
 */

public class NameGenerator {
    final private List<String> names;
    final private Random rand = new Random();

    public NameGenerator() {
        names = read("src/resources/Names");
        Collections.shuffle(names);
    }

    /**
     * The function implements the generation of a random name for the ship.
     *
     * @return random ship name
     */
    public String generateName() {
        return names.get(rand.nextInt(names.size()));
    }

    /**
     * The function reads a list of names from a file.
     *
     * @param path path to the file containing the names of cargo ships
     * @return list of names read
     */
    private static List<String> read(String path) {
        List<String> list = new ArrayList<>(255);
        try (FileReader fileReader = new FileReader(path);
             BufferedReader fb = new BufferedReader(fileReader);) {
            String name = fb.readLine();
            while (name != null) {
                list.add(name);
                name = fb.readLine();
            }
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return list;
    }
}
