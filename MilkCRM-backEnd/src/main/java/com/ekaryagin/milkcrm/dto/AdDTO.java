package com.ekaryagin.milkcrm.dto;

import com.ekaryagin.milkcrm.entity.employee.Manager;

import java.sql.Timestamp;

public class AdDTO {

    private long id;
    private Manager author;
    private String title;
    private String text;
    private Timestamp creationDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Manager getAuthor() {
        return author;
    }

    public void setAuthor(Manager author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }
}
