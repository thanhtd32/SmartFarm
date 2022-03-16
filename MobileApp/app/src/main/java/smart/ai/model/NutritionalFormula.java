package smart.ai.model;

import java.io.Serializable;

public class NutritionalFormula implements Serializable {

    private String Id;
    private String formulaName;
    private double percentWater;
    private double percentNutritionalOne;
    private double percentNutritionalTwo;
    private double percentNutritionalThree;
    private double percentNutritionalFour;

    public NutritionalFormula(String id, String formulaName, double percentWater, double percentNutritionalOne, double percentNutritionalTwo, double percentNutritionalThree, double percentNutritionalFour) {
        Id = id;
        this.formulaName = formulaName;
        this.percentWater = percentWater;
        this.percentNutritionalOne = percentNutritionalOne;
        this.percentNutritionalTwo = percentNutritionalTwo;
        this.percentNutritionalThree = percentNutritionalThree;
        this.percentNutritionalFour = percentNutritionalFour;
    }

    public NutritionalFormula() {
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getFormulaName() {
        return formulaName;
    }

    public void setFormulaName(String formulaName) {
        this.formulaName = formulaName;
    }

    public double getPercentWater() {
        return percentWater;
    }

    public void setPercentWater(double percentWater) {
        this.percentWater = percentWater;
    }

    public double getPercentNutritionalOne() {
        return percentNutritionalOne;
    }

    public void setPercentNutritionalOne(double percentNutritionalOne) {
        this.percentNutritionalOne = percentNutritionalOne;
    }

    public double getPercentNutritionalTwo() {
        return percentNutritionalTwo;
    }

    public void setPercentNutritionalTwo(double percentNutritionalTwo) {
        this.percentNutritionalTwo = percentNutritionalTwo;
    }

    public double getPercentNutritionalThree() {
        return percentNutritionalThree;
    }

    public void setPercentNutritionalThree(double percentNutritionalThree) {
        this.percentNutritionalThree = percentNutritionalThree;
    }

    public double getPercentNutritionalFour() {
        return percentNutritionalFour;
    }

    public void setPercentNutritionalFour(double percentNutritionalFour) {
        this.percentNutritionalFour = percentNutritionalFour;
    }

    @Override
    public String toString() {
        return formulaName;
    }
}
