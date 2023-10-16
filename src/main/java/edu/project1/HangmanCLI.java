package edu.project1;

import java.util.Scanner;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HangmanCLI {
    private static final char badInput = '*';

    private static final int attempts = 5;

    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) {
        String answer = Dictionary.randomWord();
        Session session = new Session(answer, attempts);
        Scanner scanner = new Scanner(System.in);

        boolean isGameOver = false;
        while (!isGameOver) {
            char userGuess = readUserGuess(scanner);
            if (userGuess == badInput) {
                LOGGER.info("Некорректный ввод! Введите символ кириллического алфавита...");
                continue;
            }

            GuessResult guessResult = session.guess(userGuess);
            int code = checkAndPrintState(guessResult);

            if (code != 0) {
                return;
            }
        }
    }

    private static char readUserGuess(Scanner scanner) {
        LOGGER.info("Угадайте букву:");
        String userInput = scanner.nextLine().strip().toLowerCase();
        if (userInput.length() != 1 || !Pattern.matches("[а-яА-Я]", userInput)) {
            return badInput;
        }
        return userInput.charAt(0);
    }

    private static int checkAndPrintState(GuessResult guessResult) {
        switch (guessResult) {
            case GuessResult.Defeat defeat -> {
                LOGGER.info(guessResult.message());
                LOGGER.info("Слово: " + String.valueOf(guessResult.state()));
                return 2;
            }
            case GuessResult.FailedGuess failedGuess -> {
                LOGGER.info(guessResult.message());
                LOGGER.info("Слово: " + String.valueOf(guessResult.state()));
            }
            case GuessResult.RepeatedGuess repeatedGuess -> {
                LOGGER.info(guessResult.message());
                LOGGER.info("Слово: " + String.valueOf(guessResult.state()));
            }
            case GuessResult.SuccessfulGuess successfulGuess -> {
                LOGGER.info(guessResult.message());
                LOGGER.info("Слово: " + String.valueOf(guessResult.state()));
            }
            case GuessResult.Win win -> {
                LOGGER.info(guessResult.message());
                return 1;
            }
            default -> {
                return -1;
            }
        }
        return 0;
    }
}
