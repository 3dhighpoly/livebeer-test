package com.example.livebeertest.navigation

object Routes {
    const val WELCOME = "welcome"
    const val PHONE_INPUT = "phone_input"
    const val REGISTER = "register"
    const val VERIFICATION_CODE = "verification_code/{phoneDigits}"

    fun verificationCode(phoneDigits: String): String {
        return "verification_code/$phoneDigits"
    }
}
