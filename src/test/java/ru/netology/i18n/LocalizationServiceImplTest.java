package ru.netology.i18n;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocalizationServiceImplTest {

    @ParameterizedTest
    @MethodSource("greetings_string_locale_params")
    public void greetings_string_locale_test(Country country, String expectedGreetings) {
        LocalizationServiceImpl localizationService = new LocalizationServiceImpl();
        String actualGreetings = localizationService.locale(country);

        assertEquals(expectedGreetings, actualGreetings);
    }

    private static Stream<Arguments> greetings_string_locale_params() {
        return Stream.of(
                Arguments.of(Country.RUSSIA, "Добро пожаловать"),
                Arguments.of(Country.USA, "Welcome"),
                Arguments.of(Country.BRAZIL, "Welcome"),
                Arguments.of(Country.GERMANY, "Welcome")
        );
    }
}