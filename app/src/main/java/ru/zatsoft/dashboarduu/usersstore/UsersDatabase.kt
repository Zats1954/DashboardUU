package ru.zatsoft.dashboarduu.usersstore

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserDB::class], version = 1, exportSchema = false)
abstract class UsersDatabase: RoomDatabase() {
    abstract fun getUserDao(): UserDao
    companion object{
        private var INSTANCE: UsersDatabase? = null
        fun getDatabase(context: Context) : UsersDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UsersDatabase::class.java,
                    "myUsers"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}