package edu.carleton.expensetracker.model.pieCharts;

/**
 * Created by taoliu on 5/24/16.
 */
public class PieChartComponent {
    private String category;
    private int value;

    public PieChartComponent(String category, int value) {
        this.category = category;
        this.value = value;
    }
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
