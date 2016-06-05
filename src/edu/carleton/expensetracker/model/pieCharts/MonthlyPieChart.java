package edu.carleton.expensetracker.model.pieCharts;

import edu.carleton.expensetracker.model.Transaction;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * This daily pie chart class represents daily summary of user's expenses.
 */
public class MonthlyPieChart extends MyPieChart {
    public MonthlyPieChart(List<Transaction> transactions) {
        super(transactions);
        this.transactions = getMonthlyTransactions();
    }

    /**
     * Get a list of transactions for the current month
     * @return tempList a list of transactions for the current month
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

}
