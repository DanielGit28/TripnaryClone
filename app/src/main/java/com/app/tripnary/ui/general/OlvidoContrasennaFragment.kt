package com.app.tripnary.ui.general


import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.postDelayed
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.R
import com.app.tripnary.ui.main.viewmodels.MainViewModel
import com.app.tripnary.ui.main.viewmodels.NavigationScreen
import com.google.firebase.auth.FirebaseAuth


class OlvidoContrasennaFragment : Fragment
    () {
    private lateinit var mainViewModel: MainViewModel

    private lateinit var enviarBtn: Button
    private lateinit var emailInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_olvido_contrasenna, container, false)

        enviarBtn = root.findViewById(R.id.button_submit)
        emailInput = root.findViewById(R.id.edit_text_forgot_email)
        enviarBtn.setOnClickListener {
            firebaseResetEmail(emailInput.text.toString())
        }

        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }



    private fun firebaseResetEmail(email: String) {
        if(isValidEmail(email)) {
            FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener {task ->
                if(task.isSuccessful) {
                    Log.d("Correo enviado: ", email)
                    Toast.makeText(activity?.applicationContext, "Un enlace para reestablecer la contraseña ha sido enviado a tu correo", Toast.LENGTH_SHORT).show()
                    emailInput.setError(null)
                    Handler().postDelayed({
                        mainViewModel.navigateTo(NavigationScreen.Login)
                    }, 2000)


                } else {
                    Log.d("Error: ", email)
                    Toast.makeText(activity?.applicationContext, "Ha ocurrido un error, intenta de nuevo", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Log.d("Correo no válido: ", email)
            Toast.makeText(activity?.applicationContext, "Ingresa un correo válido", Toast.LENGTH_SHORT).show()
            emailInput.setError("Ingresa un correo válido")
        }
    }
    fun isValidEmail(target: String?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }


}