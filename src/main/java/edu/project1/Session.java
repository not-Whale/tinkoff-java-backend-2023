package edu.project1;

class Session {
    private final String answer;
    private final char[] userAnswer;
    private final int maxAttempts;
    private int attempts;

    private final char unknownSymbol = '*';

    Session(String answer, int maxAttempts) {
        this.answer = answer;
        this.userAnswer = new char[this.answer.length()];
        for (int i = 0; i < answer.length(); i++) {
            userAnswer[i] = unknownSymbol;
        }

        this.maxAttempts = maxAttempts;
        this.attempts = 0;
    }

    public GuessResult guess(char guess) {
        if (isUserAnswerContainsSymbol(guess)) {
            return new GuessResult.RepeatedGuess(
                this.userAnswer
            );
        }

        int guessIndex = this.answer.indexOf(guess);
        if (guessIndex != -1) {
            int symbolsOpened = openNewSymbol(guess);

            return isAnswerGuessed()
                ?
                new GuessResult.Win(
                    this.userAnswer
                )
                :
                new GuessResult.SuccessfulGuess(
                    this.userAnswer,
                    guess,
                    symbolsOpened
                );
        }

        this.attempts++;

        return isAttemptsGone()
            ?
            new GuessResult.Defeat(
                this.answer.toCharArray()
            )
            :
            new GuessResult.FailedGuess(
                this.userAnswer,
                this.attempts,
                this.maxAttempts
            );
    }

    public GuessResult giveUp() {
        return new GuessResult.Defeat(
            this.answer.toCharArray()
        );
    }

    private boolean isAttemptsGone() {
        return this.attempts == this.maxAttempts;
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

    private boolean isUserAnswerContainsSymbol(char symbol) {
        for (char current : this.userAnswer) {
            if (current == symbol) {
                return true;
            }
        }

        return false;
    }
}
