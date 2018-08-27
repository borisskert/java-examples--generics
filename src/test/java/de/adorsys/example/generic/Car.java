package de.adorsys.example.generic;

public class Car {
    private final String identifier;
    private final String driver;
    private final String model;

    public Car(String identifier, String driver, String model) {
        this.identifier = identifier;
        this.driver = driver;
        this.model = model;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getDriver() {
        return driver;
    }

    public String getModel() {
        return model;
    }
}
