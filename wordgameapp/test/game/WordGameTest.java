package game;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.util.stream.*;
import static java.util.stream.Collectors.*;

import static org.mockito.Matchers.anyString;

public class WordGameTest {

    WordGame wordGame;

    @BeforeEach
    void init() {
        SpellChecker mockSpellChecker = Mockito.mock(SpellChecker.class);

        Mockito.when(mockSpellChecker.isSpellingCorrect(anyString())).thenReturn(true);

        wordGame = new WordGame(mockSpellChecker);
    }

    @Test
    void canary() { assertTrue(true); }

    @Test
    void checkIfGenerateRandomWordIsRandomWithDifferentSeeds() {
        List<String> words = Arrays.asList("some", "sample", "words");

        String randomWord1 = wordGame.getARandomWordFromList(words, 123);
        String randomWord2 = wordGame.getARandomWordFromList(words, 555);

        assertNotEquals(randomWord1, randomWord2);
    }

    @Test
    void checkIfGenerateRandomWordIsRandomWithSameSeeds() {
        List<String> words = Arrays.asList("some", "sample", "words");

        String randomWord1 = wordGame.getARandomWordFromList(words, 555);
        String randomWord2 = wordGame.getARandomWordFromList(words, 555);

        assertEquals(randomWord1, randomWord2);
    }

    @Test
    void checkIfRandomWordGeneratedIsSelectedFromAList() {
        List<String> words = Arrays.asList("some", "sample", "words");

        String randomWord = wordGame.getARandomWordFromList(words, 123);

        assertTrue(words.contains(randomWord));
    }

    @Test
    void checkIfWordIsShuffled(){
        String word = "cosmopolitan";

        String shuffledWord = wordGame.shuffleWord(word, 123);

        assertNotEquals(word, shuffledWord);
    }

    @Test
    void checkIfWordIsShuffledDifferentlyGivenDifferentSeeds(){
        String word = "cosmopolitan";

        String shuffledWord1 = wordGame.shuffleWord(word, 123);
        String shuffledWord2 = wordGame.shuffleWord(word, 222);

        assertNotEquals(shuffledWord1, shuffledWord2);
    }

    @Test
    void checkIfShuffledWordHasSameLengthAsOriginalWord(){
        String word = "cosmopolitan";

        String shuffledWord = wordGame.shuffleWord(word, 123);

        assertEquals(word.length(), shuffledWord.length());
    }

    @Test
    void checkIfShuffledWordHasSameLettersAsOriginal(){

        String shuffledWord = wordGame.shuffleWord("banana", 123);
        String shuffleWordSorted = Stream.of(shuffledWord.split("")).sorted().collect(joining(""));

        assertEquals("aaabnn", shuffleWordSorted);
    }

    @Test
    void checkScoreForScrambledWordAndGuessMonkey(){
        String scrambledWord = "oekmny";
        String guess = "monkey";

        assertEquals(10, wordGame.score(scrambledWord, guess));
    }

    @Test
    void checkScoreForScrambledWordAndGuessMonk(){
        String scrambledWord = "oekmny";
        String guess = "monk";

        assertEquals(7, wordGame.score(scrambledWord, guess));
    }

    @Test
    void checkScoreForScrambledWordAndGuessKey(){
        String scrambledWord = "oekmny";
        String guess = "key";

        assertEquals(5, wordGame.score(scrambledWord, guess));
    }

    @Test
    void checkScoreForScrambledWordAndGuessMe(){
        String scrambledWord = "oekmny";
        String guess = "me";

        assertEquals(3, wordGame.score(scrambledWord, guess));
    }

    @Test
    void checkScoreForScrambledWordAndGuessMat(){
        String scrambledWord = "oekmny";
        String guess = "mat";

        assertEquals(0, wordGame.score(scrambledWord, guess));
    }

    @Test
    void checkScoreForScrambledWordAndGuessMe2(){
        String scrambledWord = "oekmny";
        String guess = "Me";

        assertEquals(0, wordGame.score(scrambledWord, guess));
    }

    @Test
    void checkScoreForScrambledWordAndGuessMoon(){
        String scrambledWord = "oekmny";
        String guess = "moon";

        assertEquals(0, wordGame.score(scrambledWord, guess));
    }

    @Test
    void scoreForCorrectSpelling(){
        String scrambledWord = "oekmny";
        String guess = "money";

        assertEquals(8, wordGame.score(scrambledWord, guess));
    }

    @Test
    void scoreForIncorrectSpelling(){
        String scrambledWord = "oekmny";
        String guess = "money";

        SpellChecker mockSpellChecker = Mockito.mock(SpellChecker.class);
        Mockito.when(mockSpellChecker.isSpellingCorrect(guess)).thenReturn(false);
        wordGame = new WordGame(mockSpellChecker);

        assertEquals(0, wordGame.score(scrambledWord, guess));
    }

    @Test
    void scoreWhenThereIsANetworkError () throws Exception {

        SpellCheckService mockSpellChecker = Mockito.mock(SpellCheckService.class);
        String message = "Network Error";
        Mockito.when(mockSpellChecker.isSpellingCorrect(anyString())).thenThrow(new RuntimeException(message));

        wordGame = new WordGame(mockSpellChecker);
        assertThrows(RuntimeException.class, () -> wordGame.score("whatever", "something"), message);

    }
}