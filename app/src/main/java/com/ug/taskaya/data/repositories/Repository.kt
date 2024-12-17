package com.ug.taskaya.data.repositories

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class Repository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val db: FirebaseFirestore
) {


    private val labelsCollection = db.collection("labels")

    suspend fun fetchLabels(): Result<List<String>> {
        return try {
            val snapshot = labelsCollection.get().await()
            val labels = snapshot.documents.mapNotNull { it.getString("name") }
            Result.success(labels)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun addLabel(name: String): Result<Unit> {
        return try {
            val newLabel = hashMapOf("name" to name)
            labelsCollection.add(newLabel).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun removeLabel(name: String): Result<Unit> {
        return try {
            val snapshot = labelsCollection.whereEqualTo("name", name).get().await()
            for (document in snapshot.documents) {
                document.reference.delete().await()
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun signOut(onComplete: () -> Unit) {
        firebaseAuth.signOut()
        onComplete()
    }

    fun signUp(email: String, password: String, onComplete: (Boolean, String) -> Unit) {
        if (email.isEmpty() || password.isEmpty()) {
            onComplete(false, "Email or Password can't be empty")
            return
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(true, "Account Created Successfully")
                } else {
                    val exception = task.exception
                    val errorMessage = when (exception) {
                        is FirebaseAuthWeakPasswordException -> "Password is too weak"
                        is FirebaseAuthInvalidCredentialsException -> "Invalid email format"
                        is FirebaseAuthUserCollisionException -> "Email already in use"
                        is FirebaseNetworkException -> "Network error. Please check your connection"
                        else -> exception?.message ?: "Failed to create account"
                    }
                    onComplete(false, errorMessage)
                }
            }
    }

    fun signIn(email: String, password: String, onComplete: (Boolean, String) -> Unit) {
        if (email.isEmpty() || password.isEmpty()) {
            onComplete(false, "Email or Password can't be empty")
            return
        }

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(true, "Welcome")
                } else {
                    val exception = task.exception
                    val errorMessage = when (exception) {
                        is FirebaseAuthInvalidUserException -> "No account found with this email"
                        is FirebaseAuthInvalidCredentialsException -> "Email or password is incorrect"
                        is FirebaseNetworkException -> "Network error. Please check your connection"
                        else -> exception?.message ?: "Failed to sign in"
                    }
                    onComplete(false, errorMessage)
                }
            }
    }

    fun sendPasswordResetEmail(email: String, onResult: (Result<Unit>) -> Unit) {

        if (email.isEmpty()) {
            onResult(Result.failure(Exception("Email can't be empty")))
            return
        }

        firebaseAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(Result.success(Unit))
                } else {
                    onResult(Result.failure(task.exception ?: Exception("Failed to send reset email")))
                }
            }
    }
}
