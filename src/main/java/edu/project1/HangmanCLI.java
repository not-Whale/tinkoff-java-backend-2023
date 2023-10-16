package edu.project1;

import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HangmanCLI {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final int ATTEMPTS = 5;

    private static final char BAD_INPUT_CHAR = '*';

    private static final String alphabetRegex = "[а-яА-Я]";

    private static final String badInputMessage = "Некорректный ввод: введите символ кириллического алфавита!";

    private static final String winMessage = "Вы победили!";

    private static final String defeatMessage = "Вы проиграли!";

    private static final String giveUpMessage = "Вы сдались!";

    private static final String somethingWrongMessage = "Упс... Что-то пошло не так!";

    private static final String guessSymbolMessage = "Угадайте букву: ";

    private static final String endGame = "Спасибо за игру!";

    private HangmanCLI() {}

    public static void main(String[] args) {
        String answer = Dictionary.randomWord();
        Session session = new Session(answer, ATTEMPTS);
        Scanner scanner = new Scanner(System.in);

        boolean isGameOver = false;
        while (!isGameOver) {
            char userGuess = readUserGuess(scanner);
            if (userGuess == BAD_INPUT_CHAR) {
                LOGGER.info(badInputMessage);
                continue;
            }

            GuessResult guessResult = session.guess(userGuess);
            int code = checkAndPrintState(guessResult);

            if (code != 0) {
                isGameOver = true;
            }
        }
        LOGGER.info(endGame);
    }

    private static char readUserGuess(Scanner scanner) {
        LOGGER.info(guessSymbolMessage);
        String userInput = scanner.nextLine().strip().toLowerCase();
        if (userInput.length() != 1 || !Pattern.matches(alphabetRegex, userInput)) {
            return BAD_INPUT_CHAR;
        }
        return userInput.charAt(0);
    }

    private static int checkAndPrintState(GuessResult guessResult) {
        switch (guessResult) {
            case GuessResult.Win win -> {
                LOGGER.info(winMessage);
                return 1;
            }
            case GuessResult.Defeat defeat -> {
                LOGGER.info(defeatMessage);
                printAnswerState(guessResult);
                return 2;
            }
            case GuessResult.RepeatedGuess repeatedGuess -> {
                LOGGER.info(getRepeatedGuessMessage(repeatedGuess));
                printAnswerState(guessResult);
            }
            case GuessResult.SuccessfulGuess successfulGuess -> {
                LOGGER.info(getSuccessfulGuessMessage(successfulGuess));
                printAnswerState(guessResult);
            }
            case GuessResult.FailedGuess failedGuess -> {
                LOGGER.info(getFailedGuessMessage(failedGuess));
                printAnswerState(guessResult);
            }
            default -> {
                LOGGER.info(somethingWrongMessage);
                return -1;
            }
        }
        return 0;
    }

    private static String getRepeatedGuessMessage(GuessResult.RepeatedGuess repeatedGuess) {
        return "Вы уже открывали данный символ! Ваши попытки: " + Arrays.toString(repeatedGuess.attempts()) + ".";
    }

    private static String getFailedGuessMessage(GuessResult.FailedGuess failedGuess) {
        int attemptsLeft = failedGuess.maxAttempts() - failedGuess.attempts();
        return "Неверно! Доступное количество попыток: " + attemptsLeft + " из " + failedGuess.maxAttempts() + ".";
    }

    private static String getSuccessfulGuessMessage(GuessResult.SuccessfulGuess successfulGuess) {
        return "Символ \"" + successfulGuess.symbol() + "\" открыт " + successfulGuess.symbolsOpened() + " раз(-а)!";
    }

    private static void printAnswerState(GuessResult guessResult) {
        LOGGER.info("Слово: " + String.valueOf(guessResult.state()) + ".");
    }
}
