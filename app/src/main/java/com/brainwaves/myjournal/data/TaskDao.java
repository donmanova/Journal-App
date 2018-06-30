package com.brainwaves.myjournal.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;
@Dao
public interface TaskDao {
    @Query("SELECT id, title, dateOfUpdate, timeOfUpdate FROM task ORDER BY id")
    List<JournalEntry> loadAllTasks();

    @Insert
    void insertTask(JournalEntry journalEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTask(JournalEntry journalEntry);

    @Delete
    void deleteTask(JournalEntry journalEntry);

    @Query("SELECT * FROM task WHERE id = :id")
    JournalEntry loadTaskById(int id);
}
