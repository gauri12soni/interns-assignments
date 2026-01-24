package Challenge1_ApproximateSearch;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class ApproximateSearch {

    // calculate difference
    static int calculateDifference(String a, String b) {
        int minLength = Math.min(a.length(), b.length());
        int diff = Math.abs(a.length() - b.length());

        for (int i = 0; i < minLength; i++) {
            if (a.charAt(i) != b.charAt(i)) {
                diff++;
            }
        }
        return diff;
    }

    public static void main(String[] args) throws Exception {

        // read words from file into memory
        List<String> words = new ArrayList<>();
        BufferedReader br = new BufferedReader(
                new FileReader("interns-assignments/Gauri_Soni/input.txt")
        );

        String line;
        while ((line = br.readLine()) != null) {
            words.add(line.trim());
        }
        br.close();

        // user input
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a word: ");
        String input = sc.nextLine();

        // sort words based on similarity
        words.sort((w1, w2) ->
                calculateDifference(input, w1) - calculateDifference(input, w2)
        );

        int k = 3; // top k suggestions

        System.out.print("Suggestions: ");
        for (int i = 0; i < Math.min(k, words.size()); i++) {
            System.out.print(words.get(i));
            if (i < k - 1) {
                System.out.print(", ");
            }
        }

        sc.close();
    }
}
