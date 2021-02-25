package com.example.memo.model

import androidx.room.*

@Dao
interface NoteDao {
    @Query("SELECT * FROM noter")
    fun getAll(): List<NoteR>

    @Query("SELECT * FROM noter WHERE uid IN (:noteIds)")
    fun loadAllByIds(noteIds: IntArray): List<NoteR>

    @Query("SELECT * FROM noter WHERE note_name LIKE :first")
    fun findByName(first: String): NoteR

    @Insert
    fun insertNote(notes: NoteR)

    @Update
    fun updateNote(note: NoteR)

    @Delete
    fun delete(note: NoteR)
}
