package edu.project1;

class Session {
    private final String answer;
    private final char[] userAnswer;
    private final int maxAttempts;
    private int attempts;

    public Session(String answer, int maxAttempts) {
        this.answer = answer;
        this.userAnswer = new char[this.answer.length()];
        for (int i = 0; i < answer.length(); i++) {
            userAnswer[i] = '*';
        }

        this.maxAttempts = maxAttempts;
        this.attempts = 0;
    }

    public GuessResult guess(char guess) {
        if (userAnswerContains(guess)) {
            return new GuessResult.RepeatedGuess(
                this.userAnswer,
                this.attempts,
                this.maxAttempts,
                "Вы уже открывали данный символ!"
            );
        }

        int guessIndex = this.answer.indexOf(guess);
        if (guessIndex != -1) {
            openNewSymbol(guess);

            if (isAnswerGuessed()) {
                return new GuessResult.Win(
                    this.userAnswer,
                    this.attempts,
                    this.maxAttempts,
                    "Вы победили!"
                );
            }

            return new GuessResult.SuccessfulGuess(
                this.userAnswer,
                this.attempts,
                this.maxAttempts,
                "Символ \"" + guess + "\" открыт!"
            );
        }

        this.attempts++;

        return isAttemptsGone()
            ?
            new GuessResult.Defeat(
                this.answer.toCharArray(),
                this.attempts,
                this.maxAttempts,
                "Вы проиграли!"
            )
            :
            new GuessResult.FailedGuess(
                this.userAnswer,
                this.attempts,
                this.maxAttempts,
                getFailedGuessString()
            );
    }

    public GuessResult giveUp() {
        return new GuessResult.Defeat(
            this.answer.toCharArray(),
            this.attempts,
            this.maxAttempts,
            "Вы сдались!"
        );
    }

    private String getFailedGuessString() {
        int attemptsLeft = this.maxAttempts - this.attempts;
        return "Ошибка! Осталось " + attemptsLeft + " попыток из " + this.maxAttempts;
    }

    private boolean isAttemptsGone() {
        return this.attempts == this.maxAttempts;
    }

    private boolean isAnswerGuessed() {
        for (char current : this.userAnswer) {
            if (current == '*') {
                return false;
            }
        }

        return true;
    }

    private void openNewSymbol(char symbol) {
        int symbolsOpened = 0;

        for (int i = 0; i < this.answer.length(); i++) {
            if (this.answer.charAt(i) == symbol) {
                this.userAnswer[i] = symbol;
                symbolsOpened++;
            }
        }

    }

    private boolean userAnswerContains(char symbol) {
        for (char current : this.userAnswer) {
            if (current == symbol) {
                return true;
            }
        }

        return false;
    }
}
