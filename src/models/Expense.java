package models;

import models.enums.Currency;

public class Expense{
    private final Currency currency;
    private int amount;
    private User paidBy;
    private User paidFor;
    private final Group group;

    public Expense(Currency currency, int amount, User paidBy, User paidFor, Group group) {
        this.currency = currency;
        this.amount = amount;
        this.paidBy = paidBy;
        this.paidFor = paidFor;
        this.group = group;
        group.addExpense(this);
        paidBy.addExpenseToDebts(this);
        paidFor.addExpenseToDemands(this);
    }

    public User getPaidBy() {
        return paidBy;
    }
    public User getPaidFor() {
        return paidFor;
    }
    public void changePaidByWithPaidFor() {
        User temp = paidBy;
        paidBy = paidFor;
        paidFor = temp;
    }
    public Currency getCurrency() {
        return currency;
    }

    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        if(amount == 0) {
            group.removeExpense(paidBy, paidFor);
        } else {
            this.amount = amount;
        }
    }

    public void setPaidBy(User paidBy) {
        this.paidBy = paidBy;
    }

    public void setPaidFor(User paidFor) {
        this.paidFor = paidFor;
    }

    public Group getGroup() {
        return group;
    }
}
