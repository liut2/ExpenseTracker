package edu.carleton.expensetracker.model.listView;

import edu.carleton.expensetracker.model.Record;
import edu.carleton.expensetracker.model.Transaction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by chenx2 on 5/25/2016.
 */
public class WeeklyListView extends ListView{
    public WeeklyListView(Record record) {
        super(record);
        this.transactions = getWeeklyTransactions();
    }
    /*
    filter the list only to get transactions happened this week
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
}
