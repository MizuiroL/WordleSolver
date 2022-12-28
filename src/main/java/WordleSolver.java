import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WordleSolver {
    private List<String> dictionary;

    public List<String> loadDictionary(String file) {
        List<String> dictionary = new ArrayList<>();
        Charset charset = Charset.forName("UTF-8");
        try (BufferedReader reader = Files.newBufferedReader(Path.of(file), charset)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                dictionary.add(line);
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
        return dictionary;
    }

    public boolean checkWordValidity(String word, boolean enhanced) {
        if (word.length() != 5) {
            return false;
        } else if (enhanced) {
            return dictionary.contains(word);
        }
        return word.matches("^[a-z][a-z][a-z][a-z][a-z]$");
    }

    public boolean checkCodeValidity(String code) {
        int n;
        if (code.length() != 5) {
            return false;
        } else {
            for (int i = 0; i < 5; i++) {
                n = (int) code.charAt(i) - '0';
                if (n < 0 || n > 2) {
                    return false;
                }
            }
        }
        return true;
    }

    public int[] codify(String code) {
        int[] output = new int[]{0, 0, 0, 0, 0};
        for (int i = 0; i < 5; i++) {
            output[i] = (int) code.charAt(i) - '0';
        }
        return output;
    }

    public boolean applyRules(String word, int[] code) {
        List<String> d = this.dictionary;
        String f = "^";
        System.out.println("Starting now: " + f);
        for (int i = 0; i < 5; i++) {
            f = "^".concat(nPoints(i));
            char character = word.charAt(i);
            switch (code[i]) {
                case 0:
                    f = f.concat("[").concat(String.valueOf(character).concat("]"));
                    break;
                case 1:
                    f = f.concat("[").concat(String.valueOf(character).concat("]"));
                    break;
                case 2:
                    f = f.concat("[^").concat(String.valueOf(character).concat("]"));
                    break;
            }
            f = f.concat(nPoints(4-i)).concat("$");
            applyFilter(d, f);
        }
        return this.dictionary.stream().count() == 1;
    }

    public String nPoints(int n) {
        String s = "";
        for (int i = 0; i < n; i++) {
            s += ".";
        }
        return s;
    }

    public void applyFilter(List<String> list, String filter) {
        list.stream().filter(w -> w.matches(filter));
    }

    public static void main(String[] args) {
        System.out.println("Hello world!");
        WordleSolver solver = new WordleSolver();
        solver.dictionary = solver.loadDictionary("C:\\Users\\mizui\\IdeaProjects\\WordleSolver\\src\\wordle-La.txt");

        Scanner scan = new Scanner(System.in);
        String word;
        String gameOutput;
        int[] wordCode = new int[]{0, 0, 0, 0, 0};
        do {
            word = scan.nextLine();
            gameOutput = scan.nextLine();
            if (solver.checkWordValidity(word, false) && solver.checkCodeValidity(gameOutput)) {    // If the word is valid
                System.out.println("Let me check");
                wordCode = solver.codify(gameOutput);
                System.out.println(wordCode.toString());
                solver.applyRules(word, wordCode);
                System.out.println("applyRules function done, I'll be printing the updated dictionary");
                for(String w : solver.dictionary) {
                    System.out.println(w);
                }
            } else {
                System.out.println("Please input a valid word");
            }
        } while (solver.dictionary.stream().count() > 1);
    }
}