package ru.zatsoft.dashboarduu

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.AdapterView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import ru.zatsoft.dashboarduu.dailyplan.DailyPlanFragment
import ru.zatsoft.dashboarduu.databinding.ActivityMainBinding
import ru.zatsoft.dashboarduu.usersstore.UserViewModel

class MainActivity : AppCompatActivity() {
        private lateinit var binding: ActivityMainBinding
        lateinit var viewModel: UserViewModel
        val vm: UserViewModel by viewModels()
    private var list = mutableListOf(
        GridViewModal("Login","Login",R.drawable.login_48),
        GridViewModal("Chat","http://193.168.3.28/chat", R.drawable.chat_48),
        GridViewModal("Profil","Profile", R.drawable.profile_48),
        GridViewModal("Daily Plan","Daily Plan", R.drawable.daily_plan_48),
        GridViewModal("Setting","Settings", R.drawable.settings_48),
        GridViewModal("Logout","Logout", R.drawable.logout_48)
    )

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            viewModel = ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            )[UserViewModel::class.java]

            val adapter = GridViewAdapter(list,this)
            binding.gridView.adapter = adapter
            binding.gridView.onItemClickListener = AdapterView.OnItemClickListener{
                    _,_, position, _ ->
                println("----- ${list.get(position).uri}")
                when(list.get(position).name){
                    "Login"    -> {
                        supportFragmentManager.beginTransaction()
                            .add(R.id.main, SignInFragment())
                            .addToBackStack(null)
                            .commit()
                    }
                    "Chat"  ->{ startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(list.get(position).uri)))}
                    "Profil" ->{}
                    "Daily Plan" ->{
                        supportFragmentManager.beginTransaction()
                        .add(R.id.main, DailyPlanFragment())
                        .addToBackStack(null)
                        .commit()}
                    "Setting" ->{}
                    "Logout" ->{}
                    else  ->{}
                }
              }
    }
}