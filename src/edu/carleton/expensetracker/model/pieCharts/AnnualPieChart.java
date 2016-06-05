package edu.carleton.expensetracker.model.pieCharts;

import edu.carleton.expensetracker.model.Transaction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * This annual pie chart class represents annual summary of user's expenses.
 */
public class AnnualPieChart extends MyPieChart {
    public AnnualPieChart(List<Transaction> transactions) {
        super(transactions);
        this.transactions = getAnnualTransactions();
    }

    /**
     * Get a list of transactions for the current year
     * @return tempList a list of transactions for the current year
     */
    private List<Transaction> getAnnualTransactions() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);

        cal.set(Calendar.YEAR, 2016);
        cal.set(Calendar.DAY_OF_YEAR, 1);

        //System.out.println("Start of the year:       " + cal.getTime());
        //System.out.println("... in milliseconds:      " + cal.getTimeInMillis());

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
