package com.azizet.app.data.local

import android.content.Context
import android.content.SharedPreferences
import com.azizet.app.data.model.ActionState
import com.azizet.app.data.model.AuthUser
import com.azizet.app.util.getObject
import com.azizet.app.util.putObject

class AuthPref(val context: Context) {
    private val sp: SharedPreferences by lazy {
        context.getSharedPreferences(
            AuthPref::class.java.name,
            Context.MODE_PRIVATE
        )
    }

    private companion object {
        const val AUTH_USER = "auth_user"
        const val IS_LOGIN = "is_login"
    }

    var authUser: AuthUser?
        get() = sp.getObject(AUTH_USER)
        set(value) = sp.edit().putObject(AUTH_USER, value).apply()

    var isLogin: Boolean
        get() = sp.getBoolean(IS_LOGIN, false)
        private set(value) = sp.edit().putBoolean(IS_LOGIN, value).apply()

    suspend fun login(email: String, password: String): ActionState<AuthUser> {
        val user = authUser
        if (user == null) {
            return ActionState(message = "Daftar dulu gannn, isSuccess = false")
        } else if (email.isBlank() || password.isBlank()) {
            return ActionState(
                message = "E-mail dan password yaaaa",
                isSuccess = false
            )
        } else if (user.email == email && user.password == password) {
            isLogin = true
            return ActionState(authUser, message = "Horayyyy Loginnn")
        } else {
            return ActionState(message = "E-mail atau password yang bener dong", isSuccess = false)
        }
    }

    suspend fun register(user: AuthUser): ActionState<AuthUser> {
        return if (user.email.isBlank() || user.password.isBlank()) {
            ActionState(message = "gak boleh kosong gannnn", isSuccess = false)
        } else {
            authUser = user
            ActionState(user, message = "pendaftaran berhasil horayyy")
        }
    }

    suspend fun logout():ActionState<Boolean> {
        isLogin = false
        return ActionState(true, message = " logout")
    }

}