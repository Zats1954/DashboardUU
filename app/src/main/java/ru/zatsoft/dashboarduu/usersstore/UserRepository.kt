package ru.zatsoft.dashboarduu.usersstore

import androidx.lifecycle.LiveData

class UserRepository(private val userDao: UserDao) {
    val users: LiveData<List<UserDB>> = userDao.getAllUsers()

    suspend fun insert(userDB: UserDB):Long {
        val id =  userDao.insert(userDB)
        return id
    }

    suspend fun delete(userDB: UserDB) {
        userDao.delete(userDB)
    }
}
