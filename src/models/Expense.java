package models;

import models.enums.Currency;

public record Expense(Currency currency, int amount, User paidBy, User paidFor, Group group) {
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

    public int getAmountInCurrency(Currency currency) {
        return (int) currency.convertTo(this.currency, this.amount);
    }


}
