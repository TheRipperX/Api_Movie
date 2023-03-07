package com.example.filmapp.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmapp.models.register.BodyRegister
import com.example.filmapp.models.register.ResponseRegister
import com.example.filmapp.repository.RegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModels @Inject constructor(private val repository: RegisterRepository): ViewModel() {

    val registerUserApi = MutableLiveData<ResponseRegister>()
    val loader = MutableLiveData<Boolean>()

    fun registerUserApp(bodyRegister: BodyRegister) = viewModelScope.launch {

        loader.postValue(true)
        //Log.d("TAG", "registerUserApp: ${bodyRegister}")
        val response = repository.registerUsers(bodyRegister)

        //Log.d("TAG", "registerUserApp: ${response.body()}")

        if (response.isSuccessful) {
            registerUserApi.postValue(response.body())
        }
        loader.postValue(false)
    }
}