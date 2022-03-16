package smart.ai.model;

import java.io.Serializable;

public class ECDgree implements Serializable {
    private String id;
    private double value;

    public ECDgree(String id, double value) {
        this.id = id;
        this.value = value;
    }

    public ECDgree() {
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

    @Override
    public String toString() {
        return  value + " M";
    }
}
