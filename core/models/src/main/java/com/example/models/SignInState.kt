package com.example.models

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)