package com.expensetracker.model;

import java.sql.Date;

public class Expense{
    private int expenseId;
    private int userId;
    private int categoryId;
    private String categoryName;
    private String title;
    private double amount;
    private Date expenseDate;
    private String notes;
    
    public Expense(){}
    
    public int getExpenseId(){return expenseId;}
    public void setExpenseId(int expenseId){this.expenseId=expenseId;}
    
    public int getUserId(){return userId;}
    public void setUserId(int userId){this.userId=userId;}
    
    public int getCategoryId(){return categoryId;}
    public void setCategoryId(int categoryId){this.categoryId=categoryId;}
    
    public String getCategoryName(){return categoryName;}
    public void setCategoryName(String categoryName){this.categoryName=categoryName;}
    
    public String getTitle(){return title;}
    public void setTitle(String title){this.title=title;}
    
    public double getAmount(){return amount;}
    public void setAmount(double amount){this.amount=amount;}
    
    public Date getExpenseDate(){return expenseDate;}
    public void setExpenseDate(Date expenseDate){this.expenseDate=expenseDate;}
    
    public String getNotes(){return notes;}
    public void setNotes(String notes){this.notes=notes;}
}