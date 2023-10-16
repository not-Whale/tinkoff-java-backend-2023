package edu.project1;

sealed interface GuessResult {
    char[] state();

    record Defeat(char[] state)
        implements GuessResult {}

    record Win(char[] state)
        implements GuessResult {}

    record SuccessfulGuess(char[] state, char symbol, int symbolsOpened)
        implements GuessResult {}

    record FailedGuess(char[] state, int attempts, int maxAttempts)
        implements GuessResult {}

    record RepeatedGuess(char[] state)
        implements GuessResult {}
}
