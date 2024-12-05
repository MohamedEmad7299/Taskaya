package com.ug.taskaya.ui.sign_in_screen

import android.content.Context
import androidx.credentials.ClearCredentialStateRequest
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


class GoogleSignInAuth(
    private val context: Context
){
    private val TAG = "YOGE7299"
    private val credentialManager = CredentialManager.create(context)
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun isSignedIn(): Boolean{
        if (firebaseAuth.currentUser != null){
            println(TAG + "already signed in")
            return true
        } else return false
    }

    suspend fun signIn(): Boolean{

        try {

            val request = buildCredentialRequest()
            return handelSignIn(request)

        } catch (e: Exception){
            e.printStackTrace()
            if (e is CancellationException) throw e

            println(TAG + "signIn error: ${e.message}")
            return false
        }
    }

    private suspend fun handelSignIn(request: GetCredentialResponse): Boolean{

        val credential = request.credential

        if (
            credential is CustomCredential &&
            credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
            ){

            try {

                val tokenCredential = GoogleIdTokenCredential.createFrom(credential.data)

                println(TAG +"name: ${tokenCredential.displayName}")
                println(TAG +"email: ${tokenCredential.id}")
                println(TAG +"image: ${tokenCredential.profilePictureUri}")

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

    suspend fun signOut(){
        credentialManager.clearCredentialState(
            ClearCredentialStateRequest()
        )
        firebaseAuth.signOut()
    }
}