package com.example.awvs_app.presentation.feature.navigation

//import com.example.awvs_app.presentation.feature.auth.SignInViewModel
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.awvs_app.ScanResultsViewModel
import com.example.awvs_app.presentation.feature.auth.SignInScreen
import com.example.awvs_app.presentation.feature.home.HomeScreen


@Composable
fun NavGraph(
    navController: NavHostController,
//    googleAuthUiClient: GoogleAuthUiClient,
//    coroutineScope: CoroutineScope,
    scanResultsViewModel: ScanResultsViewModel,
    startDestination: String = "sign_in"

) {
    NavHost(navController = navController, startDestination = startDestination) {

        composable("sign_in") {
            SignInScreen {
                navController.navigate("home")
            }
        }

//        composable("sign_in") {
//            val viewModel = viewModel<SignInViewModel>()
//            val state = viewModel.state.collectAsStateWithLifecycle().value
//            val context = LocalContext.current
//            LaunchedEffect(key1 = state.isSignInSuccessful) {
//                if (state.isSignInSuccessful) {
//                    Toast.makeText(
//                        context,
//                        "Sign in successful",
//                        Toast.LENGTH_LONG
//                    ).show()
//                    navController.navigate("home") {
//                        popUpTo("sign_in") { inclusive = true }
//                    }
//                    viewModel.resetState()
//                }
//            }
//
//
//            LaunchedEffect(key1 = state.isSignInSuccessful) {
//                if (state.isSignInSuccessful) {
//                    Toast.makeText(
//                        context,
//                        "Sign in successful",
//                        Toast.LENGTH_LONG
//                    ).show()
//                    navController.navigate("home") {
//                        popUpTo("sign_in") { inclusive = true }
//                    }
//                    viewModel.resetState()
//                }
//            }
//
//            SignInScreen(
//                state = state,
//                onSignInClick = {
//                    coroutineScope.launch {
//                        val signInResult = googleAuthUiClient.signIn()
//                        viewModel.onSignInResult(signInResult)
//                    }
//                }
//            )
//        }

        composable("home") {
            HomeScreen(navController, scanResultsViewModel)
        }
//        composable("profile") {
//            ProfileScreen(
//                userData = googleAuthUiClient.getSignedInUser(),
//                onSignOut = {
//                    coroutineScope.launch {
//                        googleAuthUiClient.signOut()
//                        navController.popBackStack()
//                        navController.navigate("sign_in")
//                    }
//                },
//                navController = navController
//            )
//        }
    }
}
