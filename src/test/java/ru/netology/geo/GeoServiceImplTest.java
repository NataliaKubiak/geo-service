package ru.netology.geo;

import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GeoServiceImplTest {

    @Test
    public void exception_byCoordinates_test() {
        GeoServiceImpl geoService = new GeoServiceImpl();

        assertThrows(RuntimeException.class, () -> geoService.byCoordinates(48.8584, 2.2945));
    }

    private static Object[][] byIp_Params() {
        return new Object[][]{
                {new Location(null, null, null, 0), "127.0.0.1"},
                {new Location("Moscow", Country.RUSSIA, "Lenina", 15), "172.0.32.11"},
                {new Location("New York", Country.USA, " 10th Avenue", 32), "96.44.183.149"},
                {new Location("Moscow", Country.RUSSIA, null, 0), "172.0.2.0"},
                {new Location("New York", Country.USA, null, 0), "96.0.0.1"},
                {null, "0"}
        };
    }

    @ParameterizedTest
    @MethodSource("byIp_Params")
    public void byIp_test(Location expectedLocation, String passingIP) {
        GeoServiceImpl geoService = new GeoServiceImpl();
        Location actualLocation = geoService.byIp(passingIP);

        assertEquals(expectedLocation, actualLocation);
    }

    //переписанный тест с параметрами в том же месте, что и тест :)
    @ParameterizedTest
    @CsvSource({
            " , , , 0, 127.0.0.1",
            "Moscow, RUSSIA, Lenina, 15, 172.0.32.11",
            "New York, USA, ' 10th Avenue', 32, 96.44.183.149",
            "Moscow, RUSSIA, , 0, 172.0.2.0",
            "New York, USA, , 0, 96.0.0.1",
    })
    public void byIp_test(String city, Country country, String street, int building, String passingIP) {
        Location location = new Location(city, country, street, building);

        GeoServiceImpl geoService = new GeoServiceImpl();
        Location actualLocation = geoService.byIp(passingIP);

        assertEquals(location, actualLocation);
    }
}