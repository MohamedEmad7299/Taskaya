package com.ug.taskaya.data.repositories

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class FacebookSignInAuth @Inject constructor(
    private val context: Context
){

    private val firebaseAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val loginManager: LoginManager by lazy { LoginManager.getInstance() }
    private val callbackManager: CallbackManager by lazy { CallbackManager.Factory.create() }


    fun signInWithFacebook(
        activity: Activity,
        onSuccess: (() -> Unit)? = null
    ){
        loginManager.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult) {
                val token = result.accessToken.token
                handleFacebookAccessToken(token, onSuccess)
            }

            override fun onCancel() {
                showToast("Facebook login canceled.")
            }

            override fun onError(error: FacebookException) {
                val errorMessage = error.localizedMessage ?: "Facebook login failed."
                showToast(errorMessage)
            }
        })

        login(activity)
    }


    private fun handleFacebookAccessToken(
        token: String,
        onSuccess: (() -> Unit)?
    ){
        val credential = FacebookAuthProvider.getCredential(token)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = firebaseAuth.currentUser
                val message = "Welcome ${user?.displayName ?: "User"}"
                showToast(message)
                onSuccess?.invoke()
            } else {
                val errorMessage = task.exception?.localizedMessage ?: "Authentication failed."
                showToast(errorMessage)
            }
        }
    }


    private fun login(activity: Activity) {
        loginManager.logInWithReadPermissions(activity, listOf("email", "public_profile"))
    }


    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }
}