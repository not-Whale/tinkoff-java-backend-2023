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

    private static final String ALPHABET_REGEX = "[а-яА-Я]";

    private static final String BAD_INPUT_MESSAGE = "Некорректный ввод: введите символ кириллического алфавита!";

    private static final String WIN_MESSAGE = "Вы победили!";

    private static final String DEFEAT_MESSAGE = "Вы проиграли!";

    private static final String GIVE_UP_MESSAGE = "Вы сдались!";

    private static final String SOMETHING_WRONG_MESSAGE = "Упс... Что-то пошло не так!";

    private static final String GUESS_SYMBOL_MESSAGE = "Угадайте букву: ";

    private static final String END_GAME = "Спасибо за игру!";

    public HangmanCLI() {}

    public void run() {
        String answer = Dictionary.randomWord();
        Session session = new Session(answer, ATTEMPTS);
        Scanner scanner = new Scanner(System.in);

        boolean isGameOver = false;
        while (!isGameOver) {
            int status = 0;

            try {
                char userGuess = readUserGuess(scanner);
                if (userGuess == BAD_INPUT_CHAR) {
                    LOGGER.info(BAD_INPUT_MESSAGE);
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
        LOGGER.info(END_GAME);
    }

    private char readUserGuess(Scanner scanner) {
        LOGGER.info(GUESS_SYMBOL_MESSAGE);
        String userInput = scanner.nextLine().strip().toLowerCase();
        if (userInput.length() != 1 || !Pattern.matches(ALPHABET_REGEX, userInput)) {
            return BAD_INPUT_CHAR;
        }
        return userInput.charAt(0);
    }

    private int checkAndPrintState(GuessResult guessResult) {
        int returnCode = 0;
        switch (guessResult) {
            case GuessResult.Win win -> {
                LOGGER.info(WIN_MESSAGE);
                returnCode = 1;
            }
            case GuessResult.Defeat defeat -> {
                LOGGER.info(DEFEAT_MESSAGE);
                printAnswerState(defeat);
                returnCode = 2;
            }
            case GuessResult.GiveUp giveUp -> {
                LOGGER.info(GIVE_UP_MESSAGE);
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
                LOGGER.info(SOMETHING_WRONG_MESSAGE);
                returnCode = -1;
            }
        }
        return returnCode;
    }

    private String getRepeatedGuessMessage(GuessResult.RepeatedGuess repeatedGuess) {
        return "Вы уже открывали данный символ! Ваши попытки: " + Arrays.toString(repeatedGuess.attempts()) + ".";
    }

    private String getFailedGuessMessage(GuessResult.FailedGuess failedGuess) {
        int attemptsLeft = failedGuess.maxMistakes() - failedGuess.mistakes();
        return "Неверно! Доступное количество попыток: " + attemptsLeft + " из " + failedGuess.maxMistakes() + ".";
    }

    private String getSuccessfulGuessMessage(GuessResult.SuccessfulGuess successfulGuess) {
        return "Символ \"" + successfulGuess.symbol() + "\" открыт " + successfulGuess.symbolsOpened() + " раз(-а)!";
    }

    private void printAnswerState(GuessResult guessResult) {
        LOGGER.info("Слово: " + String.valueOf(guessResult.state()) + ".");
    }
}
