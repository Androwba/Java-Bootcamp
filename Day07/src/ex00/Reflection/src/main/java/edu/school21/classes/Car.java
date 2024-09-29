package edu.school21.classes;

import java.util.StringJoiner;

public class Car {
    private String model;
    private final String color;
    private int year;

    public Car() {
        this.model = "Default model";
        this.color = "Default color";
        this.year = 0;
    }

    public Car(String model, String color, int year) {
        this.model = model;
        this.color = color;
        this.year = year;
    }

    public int renew(int value) {
        this.year += value;
        return year;
    }

    public String updateModelAndYear(String newModel, int newYear) {
        this.model = newModel;
        this.year = newYear;
        return "Updated Car to model: " + newModel + ", year: " + newYear;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Car.class.getSimpleName() + "[", "]")
                .add("model='" + model + "'")
                .add("color='" + color + "'")
                .add("year=" + year)
                .toString();
    }
}
