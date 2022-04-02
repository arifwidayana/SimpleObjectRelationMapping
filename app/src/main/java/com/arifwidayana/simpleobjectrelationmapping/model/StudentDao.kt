package com.arifwidayana.simpleobjectrelationmapping.model

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface StudentDao {
    @Query("SELECT * FROM Student")
    fun getDataStudent(): List<Student>

    @Insert(onConflict = REPLACE)
    fun insertDataStudent(student: Student): Long

    @Update
    fun updateDataStudent(student: Student): Int

    @Delete
    fun deleteDataStudent(student: Student): Int
}