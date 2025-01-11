package ru.zatsoft.dashboarduu

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.TextView

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import ru.zatsoft.dashboarduu.usersstore.User
import ru.zatsoft.dashboarduu.usersstore.UserViewModel

class LogOutFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val vm: UserViewModel by activityViewModels()
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("LogOut")
                .setMessage("Отмена авторизации ${vm.user?.name}")
                .setIcon(R.drawable.logout_48)
                .setPositiveButton("OK") { dialog, id ->
                    vm.user = User()
                    dialog.cancel()
                }
                .setNegativeButton("Отменить") { dialog, id ->
                    dialog.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Ошибка диалогового окна")

    }
}