package com.ug.taskaya.ui.splash_screen

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.ug.taskaya.data.repositories.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val repository: Repository,
    private val auth: FirebaseAuth,
) : ViewModel() {

    fun isSigned(): Boolean{

        return auth.currentUser != null
    }


}