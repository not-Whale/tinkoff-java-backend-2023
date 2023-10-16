package edu.project1;

import java.util.Arrays;
import java.util.NoSuchElementException;
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
            int status = 0;

            try {
                char userGuess = readUserGuess(scanner);
                if (userGuess == BAD_INPUT_CHAR) {
                    LOGGER.info(badInputMessage);
                    continue;
                }

                GuessResult guessResult = session.guess(userGuess);
                status = checkAndPrintState(guessResult);

            } catch (NoSuchElementException e) {
                GuessResult guessResult = session.giveUp();
                status = checkAndPrintState(guessResult);
            }

            if (status != 0) {
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
        int returnCode = 0;
        switch (guessResult) {
            case GuessResult.Win win -> {
                LOGGER.info(winMessage);
                returnCode = 1;
            }
            case GuessResult.Defeat defeat -> {
                LOGGER.info(defeatMessage);
                printAnswerState(defeat);
                returnCode = 2;
            }
            case GuessResult.GiveUp giveUp -> {
                LOGGER.info(giveUpMessage);
                printAnswerState(giveUp);
                returnCode = 2;
            }
            case GuessResult.RepeatedGuess repeatedGuess -> {
                LOGGER.info(getRepeatedGuessMessage(repeatedGuess));
                printAnswerState(repeatedGuess);
            }
            case GuessResult.SuccessfulGuess successfulGuess -> {
                LOGGER.info(getSuccessfulGuessMessage(successfulGuess));
                printAnswerState(successfulGuess);
            }
            case GuessResult.FailedGuess failedGuess -> {
                LOGGER.info(getFailedGuessMessage(failedGuess));
                printAnswerState(failedGuess);
            }
            default -> {
                LOGGER.info(somethingWrongMessage);
                returnCode = -1;
            }
        }
        return returnCode;
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
