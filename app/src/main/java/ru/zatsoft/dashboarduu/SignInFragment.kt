package ru.zatsoft.dashboarduu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import ru.zatsoft.dashboarduu.databinding.FragmentSignInBinding
import ru.zatsoft.dashboarduu.usersstore.User
import ru.zatsoft.dashboarduu.usersstore.UserDB
import ru.zatsoft.dashboarduu.usersstore.UserViewModel

class SignInFragment : Fragment() {
    private  var _binding : FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private   var userDB: UserDB? = null
    private var vm: UserViewModel? = null
    private var user: User? = null
    private lateinit var listUsers: List<UserDB>
   private lateinit var userNameET: EditText
   private lateinit var userPassET: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
           vm = ViewModelProvider(
               this,
               ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
           )[UserViewModel::class.java]
//    чтение списка users из database
           vm!!.users.observe(viewLifecycleOwner, { list ->
            listUsers = list
           })

        userNameET = binding.etUserNameSignIn
        userPassET = binding.etUserPassSignIn
        val loginInBTN = binding.btnLoginSignIn

        loginInBTN.setOnClickListener {
            userDB = null
            user = null
            val name = userNameET.text.toString()
            val pass = userPassET.text.toString()

//            Поиск user в database
            var foundUser = false
            for(listUser in listUsers) {
                if(listUser.nameDB == name){
                    if(listUser.passwordDB == pass){
                        userDB = listUser
                        user = userDB!!.toUser()

                    } else {
                        Toast.makeText( requireContext(), "Неправильный пароль", Toast.LENGTH_LONG).show()
                    }
                    foundUser = true
                }
                if(foundUser) break
            }

            binding.title.text = user?.let{name} ?: "Приглашение"
            if(!foundUser && userDB == null)  {
                Toast.makeText( requireContext(), "Зарегистрируйтесь", Toast.LENGTH_LONG).show()
            }
        }

//  Переход к регистрации
        binding.btnLoginSignUp.setOnClickListener {
            val bundle = Bundle()
            user = userDB?.toUser() ?:
                 User(-1,userNameET.text.toString()," ", userPassET.text.toString(), "")
            bundle.putParcelable("user", user)
            val fragmentSignUp = SignUpFragment()
            fragmentSignUp.arguments = bundle
            fragmentManager?.beginTransaction()
                ?.addToBackStack(null)
                ?.replace(R.id.main, fragmentSignUp)?.commit()
        }
    }
}