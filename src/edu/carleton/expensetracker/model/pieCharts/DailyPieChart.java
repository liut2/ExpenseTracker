package edu.carleton.expensetracker.model.pieCharts;

import edu.carleton.expensetracker.model.Transaction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static sun.misc.Version.print;

/**
 * This daily pie chart class represents daily summary of user's expenses.
 */
public class DailyPieChart extends PieChart {
    public DailyPieChart(List<Transaction> transactions) {
        super(transactions);
        this.transactions = getDailyTransactions();
    }

    /**
     * Get a list of transactions for the current day
     * @return tempList a list of transactions for the current day
     */
    public List<Transaction> getDailyTransactions() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);

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
