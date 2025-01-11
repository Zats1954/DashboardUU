package ru.zatsoft.dashboarduu.usersstore

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: UserRepository
    var users: LiveData<List<UserDB>>
    var res: UserDB = UserDB()
    var user:User? = res.toUser()

    init {
        val dao = UsersDatabase.getDatabase(application).getUserDao()
        repository = UserRepository(dao)
        users = repository.users
    }

    fun deleteUser(userDB: UserDB) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(userDB)
    }

    fun insertUser(userDB: UserDB): LiveData<Long> {
        val id = MutableLiveData<Long>()
        viewModelScope.launch {
            id.value = deferred(userDB).await()
        }
        return id
    }

    private fun deferred(userDB: UserDB) =
        viewModelScope.async(Dispatchers.IO) {
            repository.insert(userDB)
        }
}
