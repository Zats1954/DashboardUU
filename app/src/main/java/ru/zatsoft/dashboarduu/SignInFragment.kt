package ru.zatsoft.dashboarduu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import ru.zatsoft.dashboarduu.databinding.FragmentSignInBinding
import ru.zatsoft.dashboarduu.usersstore.User
import ru.zatsoft.dashboarduu.usersstore.UserDB
import ru.zatsoft.dashboarduu.usersstore.UserViewModel

class SignInFragment : Fragment() {
    private  var _binding : FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private   var userDB: UserDB? = null
    private val vm: UserViewModel  by activityViewModels()
    private var name = ""
    private var pass = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLoginSignIn.setOnClickListener {
            userDB = null
            name = binding.etUserNameSignIn.text.toString()
            pass = binding.etUserPassSignIn.text.toString()
//  Поиск user в database
            var foundUser = false
            for(listUser in vm.users.value!!) {
                if(listUser.nameDB == name){
                    if(listUser.passwordDB == pass){
                        userDB = listUser
                        vm.user = userDB!!.toUser()
                    } else {
                        Toast.makeText( requireContext(), "Неправильный пароль", Toast.LENGTH_LONG).show()
                    }
                    foundUser = true
                }
                if(foundUser) break
            }

            binding.title.text = vm.user?.let{name} ?: "Приглашение"
            if(!foundUser && userDB == null)  {
                Toast.makeText( requireContext(), "Зарегистрируйтесь", Toast.LENGTH_LONG).show()
            }
        }

//  Переход к регистрации
        binding.btnLoginSignUp.setOnClickListener {
            name = binding.etUserNameSignIn.text.toString()
            pass = binding.etUserPassSignIn.text.toString()
            val bundle = Bundle()
             val user = User(0,name," ", pass, "")
            bundle.putParcelable("user", user)
            val fragmentSignUp = SignUpFragment()
            fragmentSignUp.arguments = bundle
            fragmentManager?.beginTransaction()
                ?.addToBackStack(null)
                ?.replace(R.id.main, fragmentSignUp)?.commit()
        }
    }
}