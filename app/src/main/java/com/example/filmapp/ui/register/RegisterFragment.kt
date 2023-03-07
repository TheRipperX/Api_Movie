package com.example.filmapp.ui.register

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import com.example.filmapp.R
import com.example.filmapp.databinding.FragmentRegisterBinding
import com.example.filmapp.models.register.BodyRegister
import com.example.filmapp.utils.ConnectivityStatus
import com.example.filmapp.utils.DataStoreUserSave
import com.example.filmapp.utils.initToastMe
import com.example.filmapp.viewmodels.RegisterViewModels
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

    @Inject
    lateinit var dataStoreUserSave: DataStoreUserSave

    @Inject
    lateinit var bodyRegister: BodyRegister

    private val toastText = "please check internet"

    private  val registerViewModels: RegisterViewModels by viewModels()

    private val connectivityStatus: ConnectivityStatus by lazy {
        ConnectivityStatus(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {

            connectivityStatus.observe(viewLifecycleOwner) {
                if (it) {
                    main()
                }else {
                    initToastMe(requireContext(), toastText)
                }
            }

        } catch (_: Exception) {
            initToastMe(requireContext(), toastText)
        }

    }

    private fun main() {

        binding.apply {

            btnLogin.setOnClickListener {

                val name = edtName.editText
                val email = edtEmail.editText
                val pass = edtPass.editText

                if (name?.text.toString().isEmpty()) {
                    //name?.error = "enter name..."
                    edtName.error = "enter name..."
                    edtEmail.error = null
                    edtPass.error = null
                    return@setOnClickListener
                }
                else if (email?.text.toString().isEmpty()){
                    //email?.error = "enter email..."
                    edtEmail.error = "enter email..."
                    edtName.error = null
                    edtPass.error = null
                    return@setOnClickListener
                }
                else if (pass?.text.toString().isEmpty()) {
                    //pass?.error = "enter password..."
                    edtPass.error = "enter password..."
                    edtName.error = null
                    edtEmail.error = null
                    return@setOnClickListener

                }else {

                    edtName.error = null
                    edtEmail.error = null
                    edtPass.error = null

                    bodyRegister.name = name?.text.toString()
                    bodyRegister.email = email?.text.toString()
                    bodyRegister.password = pass?.text.toString()

                }
                //Log.d("TAG", "onViewCreated: $bodyRegister")
                registerViewModels.registerUserApp(bodyRegister)

                registerViewModels.loader.observe(viewLifecycleOwner) { isLoad ->
                    if (isLoad) {
                        progressCircular.visibility = View.VISIBLE
                        btnLogin.visibility = View.GONE
                    }else {
                        progressCircular.visibility = View.GONE
                        btnLogin.visibility = View.VISIBLE
                    }
                }

                registerViewModels.registerUserApi.observe(viewLifecycleOwner) {

                    lifecycle.coroutineScope.launchWhenCreated {
                        //Log.d("TAG", "registerUserApp: $it")
                        dataStoreUserSave.saveTokenDataStore(it.id.toString())

                    }
                }
            }
        }

    }

}