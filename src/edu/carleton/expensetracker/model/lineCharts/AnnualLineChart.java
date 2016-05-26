package edu.carleton.expensetracker.model.lineCharts;

import edu.carleton.expensetracker.model.Record;
import edu.carleton.expensetracker.model.Transaction;
import edu.carleton.expensetracker.model.TransactionType;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by chenx2 on 5/25/2016.
 */
public class AnnualLineChart extends LineChart{
    public AnnualLineChart(Record record) {
        this.record = record;
        List<int[]> listOfTransaction = getMonthlyTransaction();
        this.expenseTransactionPerUnit = listOfTransaction.get(0);
        this.incomeTransactionPerUnit = listOfTransaction.get(1);
    }

    public List<int[]> getMonthlyTransaction() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);

        cal.set(Calendar.YEAR, 2016);
        cal.set(Calendar.DAY_OF_YEAR, 1);

        int[] expenseList = new int[12];
        int[] incomeList = new int[12];
        List<int[]> tempList = new ArrayList<int[]>();
        int offset;
        for (Transaction tran: record.transactions) {
            Date tempDate = tran.getDate();
            if ((cal.getTime().before(tempDate) || cal.getTime().equals(tempDate))) {
                offset = tempDate.getMonth();
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
        AnnualLineChart day = new AnnualLineChart(LineChartTest.test());
    }
}
