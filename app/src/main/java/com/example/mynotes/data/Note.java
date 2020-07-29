package com.example.mynotes.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity(tableName = "note")
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "date_deadline")
    @TypeConverters(DateConverter.class)
    private Date dateDeadline;

    @ColumnInfo(name = "has_deadline")
    private boolean hasDeadLine;

    @ColumnInfo(name = "content")
    private String content;

    public Note(String title, String content, Date dateDeadline, boolean hasDeadLine) {

        this.title = title;
        this.content = content;
        this.dateDeadline = dateDeadline;
        this.hasDeadLine = hasDeadLine;

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

    public boolean getHasDeadLine() {
        return hasDeadLine;
    }

    public void setHasDeadLine(boolean isDeadLine) {
        this.hasDeadLine = isDeadLine;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
