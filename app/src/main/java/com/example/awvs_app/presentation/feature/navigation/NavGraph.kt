package com.example.awvs_app.presentation.feature.navigation

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.awvs_app.presentation.feature.auth.SignInScreen
import com.example.awvs_app.presentation.feature.auth.SignInViewModel
import com.example.awvs_app.presentation.feature.home.HomeScreen
import com.example.awvs_app.presentation.feature.profile.ProfileScreen
import com.example.data.repository.sign_in.GoogleAuthUiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext


@Composable
fun NavGraph(
    navController: NavHostController,
    googleAuthUiClient: GoogleAuthUiClient,
    coroutineScope: CoroutineScope,
    startDestination: String = "sign_in"
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable("sign_in") {
            val viewModel = viewModel<SignInViewModel>()
            val state = viewModel.state.collectAsStateWithLifecycle().value
            val context = LocalContext.current
            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartIntentSenderForResult(),
                onResult = { result ->
                    if (result.resultCode == Activity.RESULT_OK) {
                        coroutineScope.launch {
                            val signInResult = googleAuthUiClient.signInWithIntent(
                                intent = result.data ?: return@launch
                            )
                            viewModel.onSignInResult(signInResult)
                        }
                    }
                }
            )

            LaunchedEffect(key1 = state.isSignInSuccessful) {
                if (state.isSignInSuccessful) {
                    Toast.makeText(
                        context,
                        "Sign in successful",
                        Toast.LENGTH_LONG
                    ).show()
                    navController.navigate("home") {
                        popUpTo("sign_in") { inclusive = true }
                    }
                    viewModel.resetState()
                }
            }

            SignInScreen(
                state = state,
                onSignInClick = {
                    coroutineScope.launch {
                        val signInIntentSender = googleAuthUiClient.signIn()
                        if (signInIntentSender != null) {
                            launcher.launch(
                                IntentSenderRequest.Builder(signInIntentSender).build()
                            )
                        } else {
                            Toast.makeText(
                                context,
                                "Google Sign-In failed.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            )
        }

        composable("home") {
            HomeScreen(navController)
        }
        composable("profile") {
            ProfileScreen(
                userData = googleAuthUiClient.getSignedInUser(),
                onSignOut = {
                    coroutineScope.launch {
                        googleAuthUiClient.signOut()
                        navController.popBackStack()
                        navController.navigate("sign_in")
                    }
                },
                navController = navController
            )
        }
    }
}
