package com.example.jure_lokovsek.personalbudget.Database;

public enum BudgetType {
    UNKNOWN(0), FOOD(1), DRINKS(2), DIESEL(3), RENT(4), CAR(5);

    final int id;

    BudgetType(int id) {
        this.id = id;
    }


}
