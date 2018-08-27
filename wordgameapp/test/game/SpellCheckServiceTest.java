package game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.spy;

public class SpellCheckServiceTest {

    SpellCheckService spellChecker;

    @BeforeEach
    void init(){
        spellChecker = new SpellCheckService();
    }

    @Test
    void returnTrueWhenSpellCheckVerifiesCorrectWord(){
        String guess = "monkey";

        assertTrue(spellChecker.isSpellingCorrect(guess));
    }

    @Test
    void returnFalseWhenSpellCheckVerifiesIncorrectWord() {
        String guess = "ney";

        assertFalse(spellChecker.isSpellingCorrect(guess));
    }

    @Test
    void spellCheckThrowsExceptionForANetworkError() throws Exception{

        SpellCheckService spellCheckService = spy(SpellCheckService.class);
        String message = "Network error";
        Mockito.when(spellCheckService.getResponseFromURL(anyString())).thenThrow(new IOException(message));
        assertThrows(RuntimeException.class, () -> spellCheckService.isSpellingCorrect("whatever"), message);

    }
}

