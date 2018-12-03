package com.example.jure_lokovsek.personalbudget.Database;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Budget {

    @Transient
    private static final long serialVersionUID = 768899456908832L;

    @Id(autoincrement = true)
    private Long id;
    private Long timeStamp;
    private Double value;
    private String comment;

    @Convert(converter = BudgetTypeConverter.class, columnType = Integer.class)
    private BudgetType budgetType;

    @Generated(hash = 1214732453)
    public Budget(Long id, Long timeStamp, Double value, String comment, BudgetType budgetType) {
        this.id = id;
        this.timeStamp = timeStamp;
        this.value = value;
        this.comment = comment;
        this.budgetType = budgetType;
    }

    @Keep
    public Budget(Long timeStamp, BudgetType budgetType) {
        this.timeStamp = timeStamp;
        this.budgetType = budgetType;
    }

    @Keep
    public Budget(Long timeStamp, BudgetType budgetType, String comment) {
        this.timeStamp = timeStamp;
        this.budgetType = budgetType;
        this.comment = comment;
    }

    @Generated(hash = 1734026453)
    public Budget() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTimeStamp() {
        return this.timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Double getValue() {
        return this.value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public BudgetType getBudgetType() {
        return this.budgetType;
    }

    public void setBudgetType(BudgetType budgetType) {
        this.budgetType = budgetType;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }




}
