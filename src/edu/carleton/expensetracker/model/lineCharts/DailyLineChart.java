package edu.carleton.expensetracker.model.lineCharts;

import edu.carleton.expensetracker.model.Record;
import edu.carleton.expensetracker.model.Transaction;
import edu.carleton.expensetracker.model.TransactionType;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static jdk.nashorn.internal.objects.Global.println;

/**
 * Created by chenx2 on 5/25/2016.
 */
public class DailyLineChart extends LineChart{

    public DailyLineChart(Record record) {
        this.record = record;
        this.expenseTransactionPerUnit = getHourlyTransaction().get(0);
        this.incomeTransactionPerUnit = getHourlyTransaction().get(1);
    }

    public List<int[]> getHourlyTransaction() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        int[] expenseList = new int[24];
        int[] incomeList = new int[24];
        List<int[]> tempList = new ArrayList<int[]>();
        int offset;
        for (Transaction tran: record.transactions) {
            Date tempDate = tran.getDate();
            if ((cal.getTime().before(tempDate) || cal.getTime().equals(tempDate))) {
                offset = tempDate.getHours();
                if(tran.getType() == TransactionType.EXPENSE) {
                    expenseList[offset] += tran.getValue();
                }else{
                    incomeList[offset] += tran.getValue();
                }
            }
        }
        tempList.add(expenseList);
        tempList.add(incomeList);
        return tempList;
    }
    public static void main(String[] args) {
        DailyLineChart day = new DailyLineChart(LineChartTest.test());
        System.out.println(day.getHourlyTransaction().size());
    }

}
