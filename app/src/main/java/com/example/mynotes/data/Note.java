package com.example.mynotes.data;

import java.util.Date;

public class Note {
    private int id;
    private String title;
    private Date dateDeadline;
    private boolean isDeadLine;
    private String content;

    public Note(int id, String title, String content, Date dateDeadline, boolean isDeadLine) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.dateDeadline = dateDeadline;
        this.isDeadLine = isDeadLine;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDateDeadline() {
        return dateDeadline;
    }

    public void setDateDeadline(Date dateDeadline) {
        this.dateDeadline = dateDeadline;
    }

    public boolean getIsDeadLine() {
        return isDeadLine;
    }

    public void setIsDeadLine(boolean isDeadLine) {
        this.isDeadLine = isDeadLine;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
