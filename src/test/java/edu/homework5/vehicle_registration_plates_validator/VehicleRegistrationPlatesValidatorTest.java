package edu.homework5.vehicle_registration_plates_validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Валидация российских номерных знаков.")
public class VehicleRegistrationPlatesValidatorTest {
    @ParameterizedTest
    @ValueSource(strings = {
        "А012ВЕ34",
        "К555МН678",
        "О001РС197"
    })
    @DisplayName("Валидные номерные знаки.")
    void validate(String input) {
        // given
        String plateString = input;

        // when
        boolean valid = VehicleRegistrationPlatesValidator.validate(plateString);

        // then
        assertThat(valid).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "123АВЕ777",
        "АА007В340",
        "А123ВГ77",
        "А123ВЕ7777"
    })
    @DisplayName("Невалидные номерные знаки.")
    void validateIncorrectPlates(String input) {
        // given
        String plateString = input;

        // when
        boolean valid = VehicleRegistrationPlatesValidator.validate(plateString);

        // then
        assertThat(valid).isFalse();
    }
}
