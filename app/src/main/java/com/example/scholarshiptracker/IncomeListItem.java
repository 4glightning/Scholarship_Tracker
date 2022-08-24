package com.example.scholarshiptracker;

public class IncomeListItem {

    String id, Amount, Category, Date, Type, Comment;

    public IncomeListItem() {
    }

    public IncomeListItem(String id, String amount, String category, String date, String type, String comment) {
        this.id = id;
        Amount = amount;
        Category = category;
        Date = date;
        Type = type;
        Comment = comment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        this.Amount = amount;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        this.Category = category;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        this.Date = date;
    }


}
