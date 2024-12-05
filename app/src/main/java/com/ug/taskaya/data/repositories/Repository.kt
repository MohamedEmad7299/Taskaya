package com.ug.taskaya.data.repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import javax.inject.Inject

class Repository @Inject constructor(
    private val auth: FirebaseAuth
) {


    fun signIn(email: String, password: String, onComplete: (Boolean, String) -> Unit) {
        if (email.isEmpty() || password.isEmpty()) {
            onComplete(false, "Email or Password can't be empty")
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(true, "Welcome")
                } else {
                    val errorMessage = (task.exception as? FirebaseAuthException)?.message
                        ?: "Email or password is incorrect"
                    onComplete(false, errorMessage)
                }
            }
    }

    fun sendPasswordResetEmail(email: String, onResult: (Result<Unit>) -> Unit) {

        if (email.isEmpty()) {
            onResult(Result.failure(Exception("Email can't be empty")))
            return
        }

        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(Result.success(Unit))
                } else {
                    onResult(Result.failure(task.exception ?: Exception("Failed to send reset email")))
                }
            }
    }
}
