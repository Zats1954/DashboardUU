package ru.zatsoft.dashboarduu


import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import ru.zatsoft.dashboarduu.databinding.FragmentSignUpBinding
import ru.zatsoft.dashboarduu.usersstore.User
import ru.zatsoft.dashboarduu.usersstore.UserDB
import ru.zatsoft.dashboarduu.usersstore.UserViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private var user: User? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        user = arguments?.getParcelable("user")
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var vm = UserViewModel(requireActivity().application)
        val userNameET = binding.etUserNameSignUp
        val userPassET = binding.etUserPassSignUp

        userNameET.setText(user?.name)
        userPassET.setText(user?.password)

        binding.btnLoginSignUp.setOnClickListener {
            val userNameSignUp = userNameET.text.toString()
            val userPassSignUp = userPassET.text.toString()
            if (userNameSignUp.isEmpty() || userPassSignUp.isEmpty()) {
                Toast.makeText(context, "Данные не введены", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            } else {
                user?.name = userNameET.text.toString()
                user?.password = userPassET.text.toString()
                user?.phone = "77777"
                user?.time = formatMilliseconds(Date().time)
                vm.insertUser(UserDB.fromUser(user!!)).observe(viewLifecycleOwner, Observer {id ->
                    if(id < 0L) {
                        Toast.makeText(
                            requireContext(),
                            "Имя '${user?.name}' уже используется",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
                    fragmentManager?.popBackStack()
                }
            }
        }


//    private fun deferred(
//        vm: UserViewModel,
//        it: User
//    ) = CoroutineScope(Dispatchers.IO).async { vm.findUser("yyy").await()
//        vm.findUser(it.name).await() }

    fun formatMilliseconds(time: Long): String {
        val formatTime = SimpleDateFormat("dd.MM.yy HH:mm")
        formatTime.timeZone = TimeZone.getTimeZone("GMT+03")
        return formatTime.format(Date(time))
    }
}