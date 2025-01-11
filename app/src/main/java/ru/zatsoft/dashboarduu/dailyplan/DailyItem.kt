package ru.zatsoft.dashboarduu.dailyplan

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DailyItem (val hour: String, var task: String, var isChecked: Boolean): Parcelable
