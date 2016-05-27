package edu.carleton.expensetracker.model;

/**
 * This is an enum that represents the transaction type
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