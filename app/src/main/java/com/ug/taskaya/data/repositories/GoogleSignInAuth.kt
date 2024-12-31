package com.ug.taskaya.data.repositories

import android.content.Context
import android.widget.Toast
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CancellationException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class GoogleSignInAuth @Inject constructor (
    private val context: Context
){
    private val TAG = "YOGE7299"
    private val credentialManager = CredentialManager.create(context)
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()


    suspend fun signIn( onSuccess: () -> Unit) {

        try {

            val request = buildCredentialRequest()

            val success = handleSignIn(request)

            val message = if (success) {
                onSuccess()
                "Sign-in successful! Welcome!"
            } else {
                "Sign-in failed. Please try again."
            }

            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

        } catch (e: Exception) {
            e.printStackTrace()

            val errorMessage =
                if (e is CancellationException) "Something went wrong"
                else "Login cancelled"

            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    private suspend fun handleSignIn(request: GetCredentialResponse): Boolean{

        val credential = request.credential

        if (
            credential is CustomCredential &&
            credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
            ){

            try {

                val tokenCredential = GoogleIdTokenCredential.createFrom(credential.data)

                val authCredential = GoogleAuthProvider.getCredential(
                    tokenCredential.idToken, null
                )
                val authResult = firebaseAuth.signInWithCredential(authCredential).await()

                return authResult.user != null

            } catch (e: GoogleIdTokenParsingException){
                println(TAG +"GoogleIdTokenParsingException: ${e.message}")
                return false
            }

        } else {
            println(TAG +"credential is not GoogleIdTokenCredential")
            return false
        }
    }

    private suspend fun buildCredentialRequest(): GetCredentialResponse {

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(
                GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(
                        "1077443442261-0mvhbtp186n09ci9qf1obgduqqr6jo88.apps.googleusercontent.com"
                    ).setAutoSelectEnabled(false)
                    .build()
            ).build()

        return credentialManager.getCredential(
            request = request,
            context = context
        )
    }
}