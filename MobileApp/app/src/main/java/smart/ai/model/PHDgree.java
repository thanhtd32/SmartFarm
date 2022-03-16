package smart.ai.model;

import java.io.Serializable;

public class PHDgree implements Serializable {
    private String id;
    private double value;
    private String color;

    public PHDgree(String id, double value, String color) {
        this.id = id;
        this.value = value;
        this.color = color;
    }

    public PHDgree(String id, double value) {
        this.id = id;
        this.value = value;
    }

    public PHDgree() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return value + "";
    }
}
