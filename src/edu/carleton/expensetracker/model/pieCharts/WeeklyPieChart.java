package edu.carleton.expensetracker.model.pieCharts;

import edu.carleton.expensetracker.model.Test;
import edu.carleton.expensetracker.model.Transaction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * This weekly pie chart class represents weekly summary of user's expenses.
 */
public class WeeklyPieChart extends PieChart {
    public WeeklyPieChart(List<Transaction> transactions) {
        super(transactions);
        this.transactions = getWeeklyTransactions();
    }

    /**
     * Get a list of transactions for the current week
     * @return tempList a list of transactions for the current week
     */
    public List<Transaction> getWeeklyTransactions() {
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());

        List<Transaction> tempList = new ArrayList<Transaction>();
        for (Transaction tran: this.transactions) {
            Date tempDate = tran.getDate();
            if (cal.getTime().before(tempDate) || cal.getTime().equals(tempDate)) {
                tempList.add(tran);
            }
        }
        return tempList;
    }

    public static void main(String[] args) {
        WeeklyPieChart week = new WeeklyPieChart(Test.test());
        System.out.println(week.getExpenseTransactions().size());
    }
}
