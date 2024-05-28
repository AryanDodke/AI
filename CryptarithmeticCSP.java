
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class CSP {
    private String word1, word2, word3;
    private Set<Character> all;
    private Set<Character> letters;
    private Map<Character, Integer> assignedChar;

    CSP(String word1, String word2, String word3) {
        this.word1 = word1;
        this.word2 = word2;
        this.word3 = word3;
        all = new HashSet<>();
        assignedChar = new HashMap<>();
        for (char c : (word1 + word2 + word3).toCharArray()) {
            if (Character.isAlphabetic(c)) {
                all.add(c);
            }
        }
        letters = new HashSet<>(all);

    }

    public void solveCryptarithmetic() {

        if (backtrack(letters, assignedChar, word1, word2, word3, 0)) {
            System.out.println("Solution is:");
            for (char letter : all) {
                System.out.println(letter + " : " + assignedChar.get(letter));
            }
        } else {
            System.out.println("No solution found.");
        }
    }

    private boolean backtrack(Set<Character> letters, Map<Character, Integer> assignedChar,
            String word1, String word2, String word3, int currentIndex) {
        if (currentIndex == letters.size()) {
            int value1 = value(word1, assignedChar);
            int value2 = value(word2, assignedChar);
            int value3 = value(word3, assignedChar);
            return value1 + value2 == value3;
        }

        char currentLetter = (char) letters.toArray()[currentIndex];
        for (int i = 0; i <= 9; i++) {
            if (!assignedChar.containsValue(i)) {
                assignedChar.put(currentLetter, i);
                if (backtrack(letters, assignedChar, word1, word2, word3, currentIndex + 1)) {
                    return true;
                }
                assignedChar.remove(currentLetter);
            }
        }

        return false;
    }

    private static int value(String word, Map<Character, Integer> assignedChar) {
        int value = 1;
        char[] words = word.toCharArray();
        int result = assignedChar.get(words[words.length - 1]);
        for (int i = words.length - 2; i >= 0; i--) {
            result = result + (int) (Math.pow(10, value++) * assignedChar.get(words[i]));
        }
        return result;
    }

}

public class CryptarithmeticCSP {
    public static void main(String[] args) {

        String word1 = "SEND";
        String word2 = "MORE";
        String word3 = "MONEY";
        CSP problem1 = new CSP(word1, word2, word3);

        problem1.solveCryptarithmetic();
    }
}