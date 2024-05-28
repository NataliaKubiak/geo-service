package ru.netology.geo;

import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
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

    @ParameterizedTest
    @MethodSource("location_buIp_Params")
    public void location_byIp_test(Location expectedLocation, String passingIP) {
        GeoServiceImpl geoService = new GeoServiceImpl();
        Location actualLocation = geoService.byIp(passingIP);

        assertEquals(expectedLocation, actualLocation);
    }

    //нормально ли написать через массив объектов? если нет - подскажите как лучше было бы написать и почему? :)
    //спасибо *^_^*
    private static Object[][] location_buIp_Params() {
        return new Object[][]{
                {new Location(null, null, null, 0), "127.0.0.1"},
                {new Location("Moscow", Country.RUSSIA, "Lenina", 15), "172.0.32.11"},
                {new Location("New York", Country.USA, " 10th Avenue", 32), "96.44.183.149"},
                {new Location("Moscow", Country.RUSSIA, null, 0), "172.0.2.0"},
                {new Location("New York", Country.USA, null, 0), "96.0.0.1"},
                {null, "0"}
        };
    }
}