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

        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        user = arguments?.getParcelable("user")
        val vm = UserViewModel(requireActivity().application)

        binding.etUserNameSignUp.setText(user?.name)
        binding.etUserPassSignUp.setText(user?.password)

        binding.btnLoginSignUp.setOnClickListener {
            val userNameSignUp = binding.etUserNameSignUp.text.toString()
            val userPassSignUp = binding.etUserPassSignUp.text.toString()
            if (userNameSignUp.isEmpty() || userPassSignUp.isEmpty()) {
                Toast.makeText(context, "Данные не введены", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            } else {
                user?.name = binding.etUserNameSignUp.text.toString()
                user?.password = binding.etUserPassSignUp.text.toString()
                user?.phone = binding.etUserPhoneSignUp.text.toString()
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

    fun formatMilliseconds(time: Long): String {
        val formatTime = SimpleDateFormat("dd.MM.yy HH:mm")
        formatTime.timeZone = TimeZone.getTimeZone("GMT+03")
        return formatTime.format(Date(time))
    }
}