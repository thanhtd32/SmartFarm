package smart.ai.model;

import java.io.Serializable;

public class StatusFormula implements Serializable {
    private int pump;
    private boolean status;
    private String formulaId;
    private String liquid;
    private double speed;
    private double quantity;
    private double valueWater;
    private double valueOne;
    private double valueTwo;
    private double valueThree;
    private double valueFour;

    public StatusFormula() {
    }

    public StatusFormula(int pump, boolean status, String formulaId, String liquid, double speed, double quantity, double valueWater, double valueOne, double valueTwo, double valueThree, double valueFour) {
        this.pump = pump;
        this.status = status;
        this.formulaId = formulaId;
        this.liquid = liquid;
        this.speed = speed;
        this.quantity = quantity;
        this.valueWater = valueWater;
        this.valueOne = valueOne;
        this.valueTwo = valueTwo;
        this.valueThree = valueThree;
        this.valueFour = valueFour;
    }

    public int getPump() {
        return pump;
    }

    public void setPump(int pump) {
        this.pump = pump;
    }

    public boolean isStatus() {
        return status;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getFormulaId() {
        return formulaId;
    }

    public void setFormulaId(String formulaId) {
        this.formulaId = formulaId;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getLiquid() {
        return liquid;
    }

    public void setLiquid(String liquid) {
        this.liquid = liquid;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getValueWater() {
        return valueWater;
    }

    public void setValueWater(double valueWater) {
        this.valueWater = valueWater;
    }

    public double getValueOne() {
        return valueOne;
    }

    public void setValueOne(double valueOne) {
        this.valueOne = valueOne;
    }

    public double getValueTwo() {
        return valueTwo;
    }

    public void setValueTwo(double valueTwo) {
        this.valueTwo = valueTwo;
    }

    public double getValueThree() {
        return valueThree;
    }

    public void setValueThree(double valueThree) {
        this.valueThree = valueThree;
    }

    public double getValueFour() {
        return valueFour;
    }

    public void setValueFour(double valueFour) {
        this.valueFour = valueFour;
    }
}
