package com.example.jure_lokovsek.personalbudget.Room;

import android.arch.persistence.room.TypeConverter;

import com.example.jure_lokovsek.personalbudget.Database.BudgetType;

public class BudgetRTypeConverter {

    @TypeConverter
    public static BudgetType toType(int value) {
        if (value == BudgetType.UNKNOWN.ordinal()) {
            return BudgetType.UNKNOWN;
        } else if (value == BudgetType.FOOD.ordinal()) {
            return BudgetType.FOOD;
        } else if (value == BudgetType.DRINKS.ordinal()) {
            return BudgetType.DRINKS;
        } else if (value == BudgetType.DIESEL.ordinal()) {
            return BudgetType.DIESEL;
        } else if (value == BudgetType.RENT.ordinal()) {
            return BudgetType.RENT;
        } else if (value == BudgetType.CAR.ordinal()) {
            return BudgetType.CAR;
        } else {
            throw new IllegalArgumentException("Could not recognize status");
        }
    }

    @TypeConverter
    public static Integer toInteger(BudgetType budgetType) {
        return budgetType.getId();
    }
}
