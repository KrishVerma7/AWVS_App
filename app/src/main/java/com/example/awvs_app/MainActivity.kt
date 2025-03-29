package com.example.awvs_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.awvs_app.presentation.feature.navigation.NavGraph
import com.example.awvs_app.ui.theme.AWVS_AppTheme
import com.example.data.repository.sign_in.GoogleAuthUiClient
import com.google.android.gms.auth.api.identity.Identity


class MainActivity : ComponentActivity() {

    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }
    private val scanResultsViewModel by viewModels<ScanResultsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AWVS_AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
//                    LaunchedEffect(Unit) {
//                        if (googleAuthUiClient.getSignedInUser() != null) {
//                            navController.navigate("home") {
//                                popUpTo("sign_in") { inclusive = true }
//                            }
//                        }
//                    }

                    NavGraph(
                        navController = navController,
                        scanResultsViewModel = scanResultsViewModel
//                        googleAuthUiClient = googleAuthUiClient,
//                        coroutineScope = lifecycleScope
                    )
                }
            }
        }
    }
}