package edu.project1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Виселица с интерфейсом командной строки")
public class HangmanTest {
    @Test
    @DisplayName("Give up case")
    void sessionGiveUp() {
        // given
        Session session = new Session("слово", 5);

        // when
        GuessResult guessResult = session.giveUp();

        // then
        assertThat(guessResult.getClass()).isSameAs(GuessResult.GiveUp.class);
        assertThat(((GuessResult.GiveUp) guessResult).state()).isEqualTo(new char[] {'с', 'л', 'о', 'в', 'о'});
    }

    @Test
    @DisplayName("Successful guess case")
    void sessionSuccessfulGuess() {
        // given
        Session session = new Session("слово", 5);

        // when
        GuessResult guessResult = session.guess('о');

        // then
        assertThat(guessResult.getClass()).isSameAs(GuessResult.SuccessfulGuess.class);
        assertThat(((GuessResult.SuccessfulGuess) guessResult).state()).isEqualTo(new char[] {'*', '*', 'о', '*', 'о'});
        assertThat(((GuessResult.SuccessfulGuess) guessResult).symbol()).isEqualTo('о');
        assertThat(((GuessResult.SuccessfulGuess) guessResult).symbolsOpened()).isEqualTo(2);
    }

    @Test
    @DisplayName("Failed guess case")
    void sessionFailedGuess() {
        // given
        Session session = new Session("слово", 5);

        // when
        GuessResult guessResult = session.guess('а');

        // then
        assertThat(guessResult.getClass()).isSameAs(GuessResult.FailedGuess.class);
        assertThat(((GuessResult.FailedGuess) guessResult).state()).isEqualTo(new char[] {'*', '*', '*', '*', '*'});
        assertThat(((GuessResult.FailedGuess) guessResult).mistakes()).isEqualTo(1);
        assertThat(((GuessResult.FailedGuess) guessResult).maxMistakes()).isEqualTo(5);
    }

    @Test
    @DisplayName("Repeated successful guess case")
    void sessionRepeatedSuccessfulGuess() {
        // given
        Session session = new Session("слово", 5);

        // when
        session.guess('л');
        GuessResult guessResult = session.guess('л');

        // then
        assertThat(guessResult.getClass()).isSameAs(GuessResult.RepeatedGuess.class);
        assertThat(((GuessResult.RepeatedGuess) guessResult).state()).isEqualTo(new char[] {'*', 'л', '*', '*', '*'});
        assertThat(((GuessResult.RepeatedGuess) guessResult).attempts()).isEqualTo(new char[] {'л'});
    }

    @Test
    @DisplayName("Repeated failed guess case")
    void sessionRepeatedFailedGuess() {
        // given
        Session session = new Session("слово", 5);

        // when
        session.guess('ы');
        GuessResult guessResult = session.guess('ы');

        // then
        assertThat(guessResult.getClass()).isSameAs(GuessResult.RepeatedGuess.class);
        assertThat(((GuessResult.RepeatedGuess) guessResult).state()).isEqualTo(new char[] {'*', '*', '*', '*', '*'});
        assertThat(((GuessResult.RepeatedGuess) guessResult).attempts()).isEqualTo(new char[] {'ы'});
    }

    @Test
    @DisplayName("Defeat case")
    void sessionDefeat() {
        // given
        Session session = new Session("слово", 1);

        // when
        GuessResult guessResult = session.guess('п');

        // then
        assertThat(guessResult.getClass()).isSameAs(GuessResult.Defeat.class);
        assertThat(((GuessResult.Defeat) guessResult).state()).isEqualTo(new char[] {'с', 'л', 'о', 'в', 'о'});
    }

    @Test
    @DisplayName("Win case")
    void sessionWin() {
        // given
        Session session = new Session("победа", 5);

        // when
        session.guess('п');
        session.guess('о');
        session.guess('б');
        session.guess('е');
        session.guess('д');
        GuessResult guessResult = session.guess('а');

        // then
        assertThat(guessResult.getClass()).isSameAs(GuessResult.Win.class);
        assertThat(((GuessResult.Win) guessResult).state()).isEqualTo(new char[] {'п', 'о', 'б', 'е', 'д', 'а'});
    }
}
