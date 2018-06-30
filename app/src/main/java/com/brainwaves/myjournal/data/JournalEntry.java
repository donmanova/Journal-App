package com.brainwaves.myjournal.data;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "task")
public class JournalEntry {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String content;
    private String title;
    private String dateOfUpdate;
    private String timeOfUpdate;


    @Ignore
    public JournalEntry(String title, String content, String dateOfUpdate, String timeOfUpdate) {
        this.title = title;
        this.content = content;
        this.dateOfUpdate = dateOfUpdate;
        this.timeOfUpdate = timeOfUpdate;
    }

    public JournalEntry(int id, String title, String content, String dateOfUpdate, String timeOfUpdate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.dateOfUpdate = dateOfUpdate;
        this.timeOfUpdate = timeOfUpdate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateOfUpdate() {
        return dateOfUpdate;
    }

    public void setDateOfUpdate(String dateOfUpdate) {
        this.dateOfUpdate = dateOfUpdate;
    }

    public String getTimeOfUpdate() {
        return timeOfUpdate;
    }

    public void setTimeOfUpdate(String timeOfUpdate) {
        this.timeOfUpdate = timeOfUpdate;
    }
}
