package com.example.livebeertest.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavType
import androidx.navigation.navArgument


import com.example.livebeertest.ui.screens.PhoneInputScreen
import com.example.livebeertest.ui.screens.RegisterScreen
import com.example.livebeertest.ui.screens.VerificationCodeScreen
import com.example.livebeertest.ui.screens.WelcomeScreen

@Composable
fun AppNavHost() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.WELCOME
    ) {

        composable(Routes.WELCOME) {
            WelcomeScreen(
                onLoginClick = { navController.navigate(Routes.PHONE_INPUT) },
                onRegisterClick = { navController.navigate(Routes.REGISTER) }
            )
        }

        composable(Routes.PHONE_INPUT) {
            PhoneInputScreen(
                onBackClick = { navController.popBackStack() },
                onContinueClick = { phoneDigits ->
                    navController.navigate(Routes.verificationCode(phoneDigits))
                }
            )
        }

        composable(Routes.REGISTER) {
            RegisterScreen(
                onBackClick = { navController.popBackStack() },
                onRegisterClick = { phoneDigits ->
                    navController.navigate(Routes.verificationCode(phoneDigits))
                }
            )
        }

        composable(
            route = Routes.VERIFICATION_CODE,
            arguments = listOf(
                navArgument("phoneDigits") { type = NavType.StringType }
            )
        ) { backStackEntry ->

            val phoneDigits = backStackEntry.arguments?.getString("phoneDigits").orEmpty()

            VerificationCodeScreen(
                phoneDigits = phoneDigits,
                onBackClick = { navController.popBackStack() },
                onConfirmClick = {
                    navController.navigate(Routes.WELCOME)
                }
            )
        }
    }
}