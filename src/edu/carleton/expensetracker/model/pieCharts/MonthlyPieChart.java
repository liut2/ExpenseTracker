package edu.carleton.expensetracker.model.pieCharts;

import edu.carleton.expensetracker.model.Test;
import edu.carleton.expensetracker.model.Transaction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by taoliu on 5/24/16.
 * This pie chart represents monthly summary of user's expenses.
 */
public class MonthlyPieChart extends PieChart{
    public MonthlyPieChart(List<Transaction> transactions) {
        super(transactions);
        this.transactions = getMonthlyTransactions();
    }

    /**
     * filter out those transactions occurred in current month
     * @return
     */
    private List<Transaction> getMonthlyTransactions() {
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        cal.set(Calendar.DAY_OF_MONTH, 1);

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
        MonthlyPieChart month = new MonthlyPieChart(Test.test());
        System.out.println(month.getExpenseTransactions().size());
    }
}
