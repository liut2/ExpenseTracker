package edu.carleton.expensetracker.model;

/**
 * Created by chenx2 on 5/24/2016.
 * This is a enum object to represent the transaction type
 */

public enum TransactionType implements java.io.Serializable{
    INCOME("income"),
    EXPENSE("expense");

    private String mDisplayName;

    TransactionType(String displayName) {
        mDisplayName = displayName;
    }

    public String getDisplayName() {
        return mDisplayName;
    }
}