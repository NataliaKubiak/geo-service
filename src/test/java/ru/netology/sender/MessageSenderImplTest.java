package ru.netology.sender;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

class MessageSenderImplTest {

    private static Stream<Arguments> send_test_params() {
        return Stream.of(
                Arguments.of("172.123.12.19", new Location("Moscow", Country.RUSSIA, null, 0), "Добро пожаловать"),
                Arguments.of("96.44.183.149", new Location("New York", Country.USA, " 10th Avenue", 32), "Welcome"));
    }

    @ParameterizedTest
    @MethodSource("send_test_params")
    void send_test(String ip, Location location, String greetingsMsg) {
        GeoService geoService = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(geoService.byIp(ip))
                .thenReturn(location);

        LocalizationService localizationService = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(localizationService.locale(location.getCountry()))
                .thenReturn(greetingsMsg);

        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);
        messageSender.send(headers);
    }

    //переписанный тест с параметрами в том же месте, что и тест :)
    @ParameterizedTest
    @CsvSource({
            "172.123.12.19, Moscow, RUSSIA, , 0, Добро пожаловать",
            "96.44.183.149, New York, USA, 10th Avenue, 32, Welcome"
    })
    void send_test_with_params(String ip, String city, Country country, String street, int building, String greetingsMsg) {
        Location location = new Location(city, country, street, building);

        GeoService geoService = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(geoService.byIp(ip))
                .thenReturn(location);

        LocalizationService localizationService = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(localizationService.locale(location.getCountry()))
                .thenReturn(greetingsMsg);

        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);
        messageSender.send(headers);
    }
}