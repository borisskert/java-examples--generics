package de.adorsys.example.generic;

import org.junit.Before;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class Test {

    private Dictionary<String, Car> carDictionary;


    @Before
    public void setup() {
        carDictionary = new Dictionary<>();

        carDictionary.put("N-AD-123", new Car("N-AD-123", "fst", "BMW 3er"));
        carDictionary.put("N-AD-1", new Car("N-AD-1", "fpo", "Porsche Cayenne"));
        carDictionary.put("N-AD-5", new Car("N-AD-5", "kts", "Mercedes E-Klasse"));
        carDictionary.put("N-AD-666", new Car("N-AD-666", "cch", "VW Golf 7"));
    }


    @org.junit.Test
    public void shouldFindCarByIdentifier() {
        Car foundCar = carDictionary.get("N-AD-123");

        assertThat(foundCar.getDriver(), is(equalTo("fst")));
    }

    @org.junit.Test
    public void shouldRemoveCarByIdentifier() {
        carDictionary.remove("N-AD-123");

        assertThat(carDictionary.size(), is(equalTo(3)));
        assertThat(carDictionary.get("N-AD-1").getDriver(), is(equalTo("fpo")));
        assertThat(carDictionary.get("N-AD-5").getDriver(), is(equalTo("kts")));
        assertThat(carDictionary.get("N-AD-666").getDriver(), is(equalTo("cch")));
    }

}
