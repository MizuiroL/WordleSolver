import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class WordleSolver {
    private List<String> dictionary;

    public WordleSolver() {
        this.dictionary = this.loadDictionary("C:\\Users\\mizui\\IdeaProjects\\WordleSolver\\src\\main\\resources\\wordle-La.txt");
    }

    public List<String> loadDictionary(String file) {
        List<String> dictionary = new ArrayList<>();
        Charset charset = Charset.forName("UTF-8");
        try (BufferedReader reader = Files.newBufferedReader(Path.of(file), charset)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                dictionary.add(line);
                System.out.println(dictionary.stream().count());
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
            return word.matches("^[a-z][a-z][a-z][a-z][a-z]$") && dictionary.contains(word);
        }
        return word.matches("^[a-z][a-z][a-z][a-z][a-z]$");
    }

    public boolean checkCodeValidity(String code) {
        /*int n;
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
        return true;*/
        return code.matches("^[0-2][0-2][0-2][0-2][0-2]$");
    }

    public int[] codify(String code) {
        int[] output = new int[]{0, 0, 0, 0, 0};
        for (int i = 0; i < 5; i++) {
            output[i] = (int) code.charAt(i) - '0';
        }
        return output;
    }

    public boolean applyRules(String word, int[] code) {
        int previousDictionarySize = this.dictionary.size();
        String f = "^*$";
        System.out.println("Starting now: " + f);
        for (int i = 0; i < 5; i++) {
            char character = word.charAt(i);
            switch (code[i]) {
                case 0:
                    f = String.format("^%s[^%c]%s$", nPoints(i), character, nPoints(4 - i));
                    this.dictionary = applyFilter(this.dictionary, f);
                    int numberOfLetters = howManyLetters(word, character, code);
                    if (numberOfLetters == 0) {
                        f = String.format("^[^%c][^%c][^%c][^%c][^%c]$", character, character, character, character, character);
                        this.dictionary = applyFilter(this.dictionary, f);
                    } else {
                        f = filterThisManyLetters(character, numberOfLetters);
                        this.dictionary = applyFilter(this.dictionary, f);
                    }
                    break;
                case 1:
                    f = String.format("^%s[^%c]%s$", nPoints(i), character, nPoints(4 - i));
                    this.dictionary = applyFilter(this.dictionary, f);
                    f = String.format("^.*[%c].*$", character);
                    this.dictionary = applyFilter(this.dictionary, f);
                    break;
                case 2:
                    f = String.format("^%s[%c]%s$", nPoints(i), character, nPoints(4 - i));
                    this.dictionary = applyFilter(this.dictionary, f);
                    break;
            }
        }
        return this.dictionary.size() < previousDictionarySize;
    }

    public int howManyLetters(String word, char character, int[] code) {
        int count = 0;
        for (int i = 0; i < 5; i++) {
            if (word.charAt(i) == character && code[i] > 0) {
                count += 1;
            }
        }
        return count;
    }

    public String filterThisManyLetters(char character, int n) {
        String repeat = "";
        for (int i = 0; i < n; i++) {
            repeat = repeat.concat(String.format("[^%c]*[%c]", character, character));
        }
        return String.format("^%s[^%c]*$", repeat, character);
    }

    public String nPoints(int n) {
        String s = "";
        for (int i = 0; i < n; i++) {
            s += ".";
        }
        return s;
    }

    public List<String> applyFilter(List<String> list, String filter) {
        System.out.println("Regex for the filter: " + filter);
        return list.stream().filter(w -> w.matches(filter)).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        System.out.println("Hello world!");

        WordleSolver solver = new WordleSolver();

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
                solver.applyRules(word, wordCode);
                System.out.println("applyRules function done, I'll be printing the updated dictionary that contains: " + solver.dictionary.stream().count());
                for (String w : solver.dictionary) {
                    System.out.println(w);
                }
            } else {
                System.out.println("Please input a valid word");
            }
        } while (solver.dictionary.stream().count() > 1);
    }
}