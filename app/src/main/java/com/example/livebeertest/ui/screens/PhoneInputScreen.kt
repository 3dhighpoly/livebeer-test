package com.example.livebeertest.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.res.painterResource
import com.example.livebeertest.R
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.livebeertest.ui.theme.LiveBeerBlack
import com.example.livebeertest.ui.theme.LiveBeerButtonDisabled
import com.example.livebeertest.ui.theme.LiveBeerButtonDisabledText
import com.example.livebeertest.ui.theme.LiveBeerGrayText
import com.example.livebeertest.ui.theme.LiveBeerIosBlue
import com.example.livebeertest.ui.theme.LiveBeerWhite
import com.example.livebeertest.ui.theme.LiveBeerYellow

@Composable
fun PhoneInputScreen(
    onBackClick: () -> Unit,
    onContinueClick: (String) -> Unit,
    onRegisterClick: () -> Unit = {}
) {
    var phoneField by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(
            TextFieldValue(
                text = "+7",
                selection = TextRange(2)
            )
        )
    }

    var isLoading by rememberSaveable { mutableStateOf(false) }

    val digits = extractDigits(phoneField.text)
    val isPhoneValid = digits.length == 10

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
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            BackButton(
                onClick = onBackClick,
                modifier = Modifier.padding(start = 16.dp)
            )

            Spacer(modifier = Modifier.height(56.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                Text(
                    text = "Введите ваш\nномер телефона",
                    color = LiveBeerBlack,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = 43.2.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Мы вышлем вам проверочный код",
                    color = LiveBeerGrayText,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                PhoneNumberField(
                    value = phoneField,
                    onValueChange = { newValue ->
                        val digitsOnly = extractDigits(newValue.text)
                        val formatted = formatPhone(digitsOnly)

                        phoneField = TextFieldValue(
                            text = formatted,
                            selection = TextRange(formatted.length)
                        )
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
                onClick = {
                    if (!isPhoneValid || isLoading) return@Button
                    isLoading = true
                    onContinueClick(digits)
                },
                enabled = isPhoneValid && !isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(vertical = 18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LiveBeerYellow,
                    contentColor = LiveBeerBlack,
                    disabledContainerColor = LiveBeerButtonDisabled,
                    disabledContentColor = LiveBeerButtonDisabledText
                )
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = LiveBeerBlack,
                        strokeWidth = 2.dp,
                        modifier = Modifier.height(18.dp)
                    )
                } else {
                    Text(
                        text = "Далее",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        lineHeight = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "У вас нет аккаунта? ",
                    color = LiveBeerBlack,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 16.sp,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Регистрация",
                    modifier = Modifier.clickable(onClick = onRegisterClick),
                    color = LiveBeerIosBlue,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 16.sp,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun BackButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.chevron_left),
            contentDescription = "Назад",
            tint = LiveBeerIosBlue,
            modifier = Modifier.height(24.dp)
        )

        Spacer(modifier = Modifier.width(5.dp))

        Text(
            text = "Назад",
            color = LiveBeerIosBlue,
            fontSize = 17.sp,
            fontWeight = FontWeight.Normal,
            lineHeight = 22.sp
        )
    }
}

@Composable
private fun PhoneNumberField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        textStyle = TextStyle(
            color = LiveBeerBlack,
            fontSize = 32.sp,
            fontWeight = FontWeight.Normal,
            lineHeight = 38.4.sp
        ),
        modifier = Modifier.fillMaxWidth(),
        decorationBox = {
            Text(
                text = buildPhoneAnnotatedString(value.text),
                fontSize = 32.sp,
                fontWeight = FontWeight.Normal,
                lineHeight = 38.4.sp
            )
        }
    )
}

private fun buildPhoneAnnotatedString(text: String): AnnotatedString {
    return buildAnnotatedString {
        if (text.startsWith("+7")) {
            pushStyle(SpanStyle(color = LiveBeerGrayText))
            append("+7")
            pop()

            if (text.length > 2) {
                pushStyle(SpanStyle(color = LiveBeerBlack))
                append(text.drop(2))
                pop()
            }
        } else {
            append(text)
        }
    }
}

private fun extractDigits(text: String): String {
    return text.filter { it.isDigit() }.drop(1).take(10)
}

private fun formatPhone(digits: String): String {
    val clean = digits.take(10)

    return buildString {
        append("+7")

        if (clean.isNotEmpty()) {
            append(" (")
            append(clean.take(3))
        }

        if (clean.length >= 4) {
            append(") ")
            append(clean.substring(3, minOf(6, clean.length)))
        }

        if (clean.length >= 7) {
            append(" ")
            append(clean.substring(6, minOf(8, clean.length)))
        }

        if (clean.length >= 9) {
            append(" ")
            append(clean.substring(8, minOf(10, clean.length)))
        }
    }
}

@Preview(name = "Small phone", device = "spec:width=360dp,height=640dp,dpi=480")
@Composable
private fun PhoneInputScreenSmallPreview() {
    PhoneInputScreen(
        onBackClick = {},
        onContinueClick = {}
    )
}

@Preview(name = "Normal phone", device = "spec:width=411dp,height=891dp,dpi=420")
@Composable
private fun PhoneInputScreenNormalPreview() {
    PhoneInputScreen(
        onBackClick = {},
        onContinueClick = {}
    )
}

@Preview(name = "Big phone", device = "spec:width=480dp,height=960dp,dpi=440")
@Composable
private fun PhoneInputScreenBigPreview() {
    PhoneInputScreen(
        onBackClick = {},
        onContinueClick = {}
    )
}