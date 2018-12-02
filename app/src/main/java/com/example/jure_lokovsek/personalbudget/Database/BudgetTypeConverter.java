package com.example.jure_lokovsek.personalbudget.Database;

import org.greenrobot.greendao.converter.PropertyConverter;

public class BudgetTypeConverter implements PropertyConverter<BudgetType, Integer> {

    @Override
    public synchronized BudgetType convertToEntityProperty(Integer databaseValue) {
        if(databaseValue == null){
            return  null;
        }
        for (BudgetType budgetType : BudgetType.values()) {
            if(budgetType.id == databaseValue){
                return budgetType;
            }
        }
        return BudgetType.UNKNOWN;
    }

    @Override
    public synchronized Integer convertToDatabaseValue(BudgetType entityProperty) {
        return entityProperty == null ? null : entityProperty.id;
    }

}
