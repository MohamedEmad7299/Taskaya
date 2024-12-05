package com.ug.taskaya.ui.sign_in_screen

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth

class FacebookSignInAuth(
    private val context: Context,
    private val callbackManager: CallbackManager = CallbackManager.Factory.create()
) {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()


    private val loginManager = LoginManager.getInstance()


    fun registerFacebookCallback(
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        loginManager.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult) {
                onSuccess(result.accessToken.token)
            }

            override fun onCancel() {
                onError("Login canceled.")
            }

            override fun onError(error: FacebookException) {
                onError(error.message ?: "Login failed.")
            }
        })
    }


    fun login(activity: Activity) {
        loginManager.logInWithReadPermissions(
            activity,
            listOf("email", "public_profile")
        )
    }


    fun handleFacebookAccessToken(token: String, onResult: (Boolean, String) -> Unit) {
        val credential = FacebookAuthProvider.getCredential(token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    onResult(true, "Welcome ${user?.displayName}")
                } else {
                    onResult(false, task.exception?.message ?: "Authentication failed.")
                }
            }
    }


    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }
}
