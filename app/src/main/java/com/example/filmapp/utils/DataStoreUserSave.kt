package com.example.filmapp.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class DataStoreUserSave @Inject constructor(@ApplicationContext val context: Context) {

    companion object {

        private val Context.dataStoreUser : DataStore<Preferences> by preferencesDataStore(Constants.DATASTORE_USER_SAVE_SETTING)
        private val userToken = stringPreferencesKey(Constants.DATASTORE_USER_SAVE_TOKEN)
    }

    suspend fun saveTokenDataStore(token: String) {
        context.dataStoreUser.edit {
            it[userToken] = token
        }
    }

    fun getUserToken() = context.dataStoreUser.data.map {
        it[userToken] ?: ""
    }
}