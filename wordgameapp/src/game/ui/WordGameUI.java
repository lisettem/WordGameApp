package game.ui;

import game.SpellCheckService;
import game.WordGame;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.util.stream.Collectors.toList;

public class WordGameUI {

    public static void main(String[] args) {

        WordGameUI gameUI = new WordGameUI();
        List<String> wordsFromFile = new ArrayList<>();

        try {
            wordsFromFile = gameUI.readFromFile();
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            System.exit(1);
        }

        SpellCheckService spellChecker = new SpellCheckService();
        WordGame wordGame = new WordGame(spellChecker);

        String randomWord = wordGame.getARandomWordFromList(wordsFromFile, System.currentTimeMillis());
        String scrambledWord = wordGame.shuffleWord(randomWord, System.currentTimeMillis());
        String guess = "";

        Scanner scanner = new Scanner(System.in);

        System.out.println(scrambledWord + " is the scrambled word.");

        while (!guess.equals(randomWord)) {
            System.out.println("Enter guess: ");
            guess = scanner.nextLine();
            System.out.println("Your guess " + guess + " achieved " + wordGame.score(scrambledWord, guess) + " points.");
        }

        System.out.println("Correct! The original word is " + randomWord + ".");
    }

    public List<String> readFromFile() throws Exception{

        return Files.lines(Paths.get("wordgameapp","words.txt")).collect(toList());


    }
}




