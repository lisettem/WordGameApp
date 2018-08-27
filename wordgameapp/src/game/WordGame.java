package game;

import java.util.*;
import java.util.stream.*;
import static java.util.stream.Collectors.*;

public class WordGame  {
	
    SpellChecker spellChecker;

    public WordGame(SpellChecker theSpellChecker) {
        spellChecker = theSpellChecker;
    }

    public int score(String scrambledWord, String guess) {

        if(!spellChecker.isSpellingCorrect(guess))
            return 0;

        Map<String, Long> frequencyOfLettersInGuess = Stream.of(guess.split(""))
                .collect(groupingBy(letter -> letter, counting()));

        Map<String, Long> frequencyOfLettersWord = Stream.of(scrambledWord.split(""))
                .collect(groupingBy(letter -> letter, counting()));

        if(frequencyOfLettersInGuess.keySet().stream()
                .filter(letter -> frequencyOfLettersInGuess.get(letter) > frequencyOfLettersWord.computeIfAbsent(letter, key -> 0L))
                .count() > 0) return 0;

        List<String> VOWELS = Arrays.asList("a", "e", "i", "o", "u");

        return Stream.of(guess.split(""))
                .mapToInt(letter -> VOWELS.contains(letter) ? 1 : 2)
                .sum();
    }

    public String getARandomWordFromList(List<String> wordList, long seed) {
        Random random = new Random(seed);

        return wordList.get(random.nextInt(wordList.size()));
    }

    public String shuffleWord(String word, long seed) {
        List<String> wordList = Stream.of(word.split("")).collect(toList());
        Collections.shuffle(wordList, new Random(seed));

        return String.join("", wordList);
    }
}