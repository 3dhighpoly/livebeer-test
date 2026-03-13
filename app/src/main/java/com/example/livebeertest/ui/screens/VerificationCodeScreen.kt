package com.example.livebeertest.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import com.example.livebeertest.ui.theme.*
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import com.example.livebeertest.R

@Composable
fun VerificationCodeScreen(
    phoneDigits: String,
    onBackClick: () -> Unit,
    onConfirmClick: () -> Unit
) {

    var code by rememberSaveable { mutableStateOf("") }

    var timer by rememberSaveable { mutableStateOf(60) }

    val isCodeValid = code.length == 4

    LaunchedEffect(timer) {
        if (timer > 0) {
            delay(1000)
            timer--
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LiveBeerWhite)
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding()
            .padding(top = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column {

            BackButton(
                onClick = onBackClick,
                modifier = Modifier.padding(start = 16.dp)
            )

            Spacer(modifier = Modifier.height(56.dp))

            Column(
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {

                Text(
                    text = "Введите номер\nактивации",
                    color = LiveBeerBlack,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = 43.2.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = LiveBeerGrayText)) {
                            append("Мы выслали его на номер ")
                        }
                        withStyle(style = SpanStyle(color = LiveBeerBlack)) {
                            append(formatMaskedPhone(phoneDigits))
                        }
                    },
                    fontSize = 15.sp,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(32.dp))

                OtpField(
                    code = code,
                    onCodeChange = { new ->
                        if (new.length <= 4 && new.all { it.isDigit() }) {
                            code = new
                        }
                    }
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Button(
                onClick = { onConfirmClick() },
                enabled = isCodeValid,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LiveBeerYellow,
                    contentColor = LiveBeerBlack,
                    disabledContainerColor = LiveBeerButtonDisabled,
                    disabledContentColor = LiveBeerGrayText
                )
            ) {
                Text(
                    text = "Войти в систему",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (timer > 0) {

                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = LiveBeerGrayText)) {
                            append("Отправить код повторно можно через ")
                        }
                        withStyle(style = SpanStyle(color = LiveBeerBlack)) {
                            append(formatTime(timer))
                        }
                    },
                    fontSize = 14.sp
                )

            } else {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {
                        timer = 60
                    }
                ) {

                    Image(
                        painter = painterResource(R.drawable.ic_resend),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = "Отправить код повторно",
                        color = LiveBeerIosBlue,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun OtpField(
    code: String,
    onCodeChange: (String) -> Unit
) {
    Box {
        BasicTextField(
            value = code,
            onValueChange = onCodeChange,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            textStyle = TextStyle(color = Color.Transparent),
            cursorBrush = SolidColor(Color.Transparent),
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            repeat(4) { index ->
                val char = code.getOrNull(index)

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier.height(44.dp),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        if (char == null) {
                            Box(
                                modifier = Modifier
                                    .offset(y = (-16).dp)
                                    .size(8.dp)
                                    .background(
                                        LiveBeerGrayText,
                                        shape = RoundedCornerShape(50)
                                    )
                            )
                        } else {
                            Text(
                                text = char.toString(),
                                fontSize = 36.sp,
                                fontWeight = FontWeight.Light,
                                color = LiveBeerBlack
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Box(
                        modifier = Modifier
                            .width(70.dp)
                            .height(1.dp)
                            .background(
                                if (char == null) LiveBeerGrayText else LiveBeerBlack
                            )
                    )
                }
            }
        }
    }
}

private fun formatTime(seconds: Int): String {
    val m = seconds / 60
    val s = seconds % 60
    return "%02d:%02d".format(m, s)
}

private fun formatMaskedPhone(digits: String): String {
    val clean = digits.filter { it.isDigit() }.take(10)

    val code = clean.take(3)
    val part1 = clean.drop(3).take(3)

    return "+7\u00A0($code)\u00A0$part1\u00A0**\u00A0**"
}

@Preview(name = "Small phone", device = "spec:width=360dp,height=640dp,dpi=480")
@Composable
private fun VerificationCodeScreenSmallPreview() {
    VerificationCodeScreen(
        phoneDigits = "9132109582",
        onBackClick = {},
        onConfirmClick = {}
    )
}

@Preview(name = "Normal phone", device = "spec:width=411dp,height=891dp,dpi=420")
@Composable
private fun VerificationCodeScreenNormalPreview() {
    VerificationCodeScreen(
        phoneDigits = "9132109582",
        onBackClick = {},
        onConfirmClick = {}
    )
}

@Preview(name = "Big phone", device = "spec:width=480dp,height=960dp,dpi=440")
@Composable
private fun VerificationCodeScreenBigPreview() {
    VerificationCodeScreen(
        phoneDigits = "9132109582",
        onBackClick = {},
        onConfirmClick = {}
    )
}