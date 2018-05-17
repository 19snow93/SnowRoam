package com.rhythm7.snowroam.data.category

import android.arch.persistence.room.*
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * Created by Jaminchanks on 2018-05-08.
 */

@Dao
interface CategoryDao {
    @Query("SELECT * FROM category")
    fun getAllCategory(): Flowable<List<Category>>

    @Query("SELECT * FROM category")
    fun getAllCategoryBlock(): List<Category>

    @Query("SELECT max(id) FROM category")
    fun getMaxId(): Long?

    @Insert
    fun insertCategory(category: Category)

    @Update
    fun updateCategory(category: Category)


    @Delete
    fun deleteCategory(category: Category)

    @Query("SELECT * FROM category WHERE name = :name")
    fun getCategoryByName(name: String): Flowable<Category?>

    @Query("SELECT * FROM category WHERE name = :name")
    fun getCategoryByNameBlock(name: String): Category?


    @Query("SELECT id FROM category WHERE name = :name")
    fun getCategoryIdByNameBlock(name: String): Long?
}