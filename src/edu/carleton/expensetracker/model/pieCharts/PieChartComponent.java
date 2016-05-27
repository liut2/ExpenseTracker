package edu.carleton.expensetracker.model.pieCharts;

/**
 * This class is the component that builds up the pie chart
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
