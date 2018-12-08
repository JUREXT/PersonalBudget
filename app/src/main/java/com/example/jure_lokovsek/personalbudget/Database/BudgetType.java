package com.example.jure_lokovsek.personalbudget.Database;

import java.util.ArrayList;
import java.util.List;

public enum BudgetType {
    UNKNOWN(0), FOOD(1), DRINKS(2), DIESEL(3), RENT(4), CAR(5);

    final int id;

    BudgetType(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public static List<BudgetType> getBudgetTypeList(){
        final List<BudgetType> budgetTypes = new ArrayList<>();
        budgetTypes.add(BudgetType.UNKNOWN);
        budgetTypes.add(BudgetType.FOOD);
        budgetTypes.add(BudgetType.DRINKS);
        budgetTypes.add(BudgetType.DIESEL);
        budgetTypes.add(BudgetType.RENT);
        budgetTypes.add(BudgetType.CAR);
        return budgetTypes;
    }


}
