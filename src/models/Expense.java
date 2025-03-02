package models;

import models.enums.Currency;

public class Expense {
    private final Currency currency;
    private final double amount;
    private final User paidBy;
    private final User paidFor;
    private final Group group;

    public Expense(Currency currency, double amount, User paidBy, User paidFor, Group group) {
        this.currency = currency;
        this.amount = amount;
        this.paidBy = paidBy;
        this.paidFor = paidFor;
        this.group = group;
    }

    public Currency getCurrency() {
        return currency;
    }
    public double getAmount() {
        return amount;
    }
    public User getPaidBy() {
        return paidBy;
    }
    public User getPaidFor() {
        return paidFor;
    }
    public Group getGroup() {
        return group;
    }
    public double getAmountInCurrency(Currency currency){
        return currency.convertTo(this.currency, this.amount);
    }


}
