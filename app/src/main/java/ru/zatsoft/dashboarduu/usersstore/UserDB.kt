package ru.zatsoft.dashboarduu.usersstore

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "myUsers", indices = [Index(value = ["name"], unique = true)])
data class UserDB(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    @ColumnInfo(name = "name" )
    var nameDB: String,
    @ColumnInfo(name = "phone")
    var phoneDB: String,
    @ColumnInfo(name = "password")
    var passwordDB: String,
    @ColumnInfo(name = "time_created")
    var timeDB: String

) {
    fun toUser() = User(id, nameDB, phoneDB, passwordDB, timeDB)

    companion object{
        fun fromUser(user: User) = UserDB(user.id, user.name, user.phone, user.password, user.time)
    }
}