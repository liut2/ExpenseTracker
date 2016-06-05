package edu.carleton.expensetracker.model.listView;

import edu.carleton.expensetracker.model.Transaction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * This class handles the display of MonthlyListView. It takes a list of transactions and return a list of transactions within a month
 */
public class MonthlyListView extends MyListView {

    public MonthlyListView(List<Transaction> transactions) {
        super(transactions);
        this.transactions = getMonthlyTransactions();
    }
    /*
     * This function filter the list only to get transactions happened this month
     * @return tempList
     */
    public List<Transaction> getMonthlyTransactions() {
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
