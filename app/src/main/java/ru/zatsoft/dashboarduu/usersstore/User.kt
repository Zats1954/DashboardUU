package ru.zatsoft.dashboarduu.usersstore

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var id: Long,
    var name: String,
    var phone: String,
    var password: String,
    var time: String
): Parcelable
