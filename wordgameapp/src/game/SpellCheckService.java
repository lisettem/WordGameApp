package game;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class SpellCheckService implements SpellChecker{

    @Override
    public boolean isSpellingCorrect(String word) {
        try {
            return getResponseFromURL(word).equals("true");

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public String getResponseFromURL(String word) throws Exception {
        URL spellCheckURL = new URL("http://agile.cs.uh.edu/spell?check=" + word);
        BufferedReader result = new BufferedReader(new InputStreamReader(spellCheckURL.openStream()));

        return result.readLine();
    }
}
