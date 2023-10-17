package edu.project1;

import java.util.Arrays;

class Session {
    private final String answer;

    private final char[] userAnswer;

    private final char[] userAttempts;

    private final int maxMistakes;

    private int mistakes;

    private int attempts;

    private final char unknownSymbol = '*';

    private final int alphabetLength = 33;

    Session(String answer, int maxMistakes) {
        this.answer = answer;
        this.userAnswer = new char[this.answer.length()];
        for (int i = 0; i < answer.length(); i++) {
            this.userAnswer[i] = unknownSymbol;
        }

        this.attempts = 0;
        this.userAttempts = new char[alphabetLength];
        this.maxMistakes = maxMistakes;
        this.mistakes = 0;
    }

    public GuessResult guess(char guess) {
        if (isUserRepeatGuess(guess)) {
            return new GuessResult.RepeatedGuess(
                this.userAnswer,
                Arrays.copyOfRange(this.userAttempts, 0, this.attempts)
            );
        }
        addNewAttempt(guess);

        int guessIndex = this.answer.indexOf(guess);
        if (guessIndex != -1) {
            int symbolsOpened = openNewSymbol(guess);

            return isAnswerGuessed()
                ?
                new GuessResult.Win(this.userAnswer)
                :
                new GuessResult.SuccessfulGuess(this.userAnswer, guess, symbolsOpened);
        }
        this.mistakes++;

        return isAttemptsGone()
            ?
            new GuessResult.Defeat(this.answer.toCharArray())
            :
            new GuessResult.FailedGuess(this.userAnswer, this.mistakes, this.maxMistakes);
    }

    public GuessResult giveUp() {
        return new GuessResult.GiveUp(this.answer.toCharArray());
    }

    private boolean isAttemptsGone() {
        return this.mistakes > this.maxMistakes;
    }

    private boolean isAnswerGuessed() {
        for (char current : this.userAnswer) {
            if (current == unknownSymbol) {
                return false;
            }
        }
        return true;
    }

    private int openNewSymbol(char symbol) {
        int symbolsOpened = 0;

        for (int i = 0; i < this.answer.length(); i++) {
            if (this.answer.charAt(i) == symbol) {
                this.userAnswer[i] = symbol;
                symbolsOpened++;
            }
        }

        return symbolsOpened;
    }

    private boolean isUserRepeatGuess(char symbol) {
        for (char current : this.userAttempts) {
            if (current == symbol) {
                return true;
            }
        }
        return false;
    }

    private void addNewAttempt(char symbol) {
        this.userAttempts[this.attempts] = symbol;
        this.attempts++;
    }
}
