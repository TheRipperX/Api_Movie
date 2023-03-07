package com.example.filmapp.repository

import com.example.filmapp.api.ApiServices
import com.example.filmapp.models.register.BodyRegister
import javax.inject.Inject

class RegisterRepository @Inject constructor(private val api: ApiServices) {

    suspend fun registerUsers(bodyRegister: BodyRegister) = api.registerUser(bodyRegister)
}