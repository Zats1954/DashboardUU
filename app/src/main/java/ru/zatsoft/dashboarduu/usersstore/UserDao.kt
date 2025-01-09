package ru.zatsoft.dashboarduu.usersstore

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(userDB: UserDB):Long

    @Delete
    suspend fun delete(userDB: UserDB)

    @Query("SELECT * FROM myUsers ORDER BY id ASC")
    fun getAllUsers(): LiveData<List<UserDB>>

    @Query("DELETE FROM myUsers")
    fun deleteAll()

    @Query("SELECT * FROM myUsers WHERE name = :nameU LIMIT 1")
    fun findUser(nameU: String):Flow<UserDB>
}