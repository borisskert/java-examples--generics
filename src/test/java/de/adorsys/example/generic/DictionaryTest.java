package de.adorsys.example.generic;

import org.junit.Before;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class DictionaryTest {

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

    @org.junit.Test
    public void shouldNotContainMapping() {
        carDictionary.remove("N-AD-123");
        carDictionary.remove("N-AD-1");
        carDictionary.remove("N-AD-5");
        carDictionary.remove("N-AD-666");

        assertThat(carDictionary.size(), is(equalTo(0)));
        assertThat(carDictionary.get("N-AD-123"), is(equalTo(null)));
        assertThat(carDictionary.get("N-AD-1"), is(equalTo(null)));
        assertThat(carDictionary.get("N-AD-5"), is(equalTo(null)));
        assertThat(carDictionary.get("N-AD-666"), is(equalTo(null)));
    }

    @org.junit.Test
    public void shouldBeEmptyAfterRemovingAllElements() {
        carDictionary.remove("N-AD-123");
        carDictionary.remove("N-AD-1");
        carDictionary.remove("N-AD-5");
        carDictionary.remove("N-AD-666");

        assertThat(carDictionary.size(), is(equalTo(0)));
        assertThat(carDictionary.isEmpty(), is(equalTo(true)));
    }

    @org.junit.Test
    public void shouldNotBeEmptyAfterRemovingOneElement() {
        carDictionary.remove("N-AD-123");


        assertThat(carDictionary.size(), is(equalTo(3)));
        assertThat(carDictionary.isEmpty(), is(equalTo(false)));
        assertThat(carDictionary.get("N-AD-1").getDriver(), is(equalTo("fpo")));
    }

    @org.junit.Test
    public void shouldNotContainsKey() {
        assertThat(carDictionary.containsKey("irgendwas was es einfach nicht gibt, ne?"), is(equalTo(false)));
    }

    @org.junit.Test
    public void shouldNotContainKeyAfterRemoving() {
        carDictionary.remove("N-AD-123");
        assertThat(carDictionary.containsKey("N-AD-123"), is(equalTo(false)));
    }

    @org.junit.Test
    public void shouldContainKeyAfterAdding() {
        carDictionary.put("N-AD-12", new Car("N-AD-12", "bsk", "Audi A6"));
        assertThat(carDictionary.containsKey("N-AD-12"), is(equalTo(true)));
    }

}
