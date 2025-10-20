// Purpose - Loads and validates the pack of 8n cards from an input file.
// Thread Safety - Should be immutable once loaded

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Pack {
    public static List<Card> loadPack(String filename, int n){
        List<Card> pack = new ArrayList<>();
        try {
            for (String line : Files.readAllLines(Path.of(filename))) {
                String trimmed = line.trim();
                if (trimmed.isEmpty()) continue;
                int v;
                try {
                    v = Integer.parseInt(trimmed);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid card value in pack: '" + line + "'");
                }
                pack.add(new Card(v));
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Unable to read pack file: " + filename, e);
        }

        if (!isValidPack(pack, n)) {
            throw new IllegalArgumentException("Pack is not valid for n=" + n + " (must contain exactly 8n non-negative integers)");
        }

        return List.copyOf(pack);
    }



    public static boolean isValidPack(List<Card> pack, int n) {
        if (pack == null) return false;
        if (n <= 0) return false;
        if (pack.size() != 8 * n) return false;
        for (Card c : pack) {
            if (c == null) return false;
            if (c.getValue() < 0) return false;
        }
        return true;
    }

}