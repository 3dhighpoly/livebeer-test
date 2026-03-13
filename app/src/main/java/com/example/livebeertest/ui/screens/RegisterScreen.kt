package com.example.livebeertest.ui.screens

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import java.util.Calendar
import com.example.livebeertest.ui.theme.LiveBeerBlack
import com.example.livebeertest.ui.theme.LiveBeerButtonDisabled
import com.example.livebeertest.ui.theme.LiveBeerGrayBorder
import com.example.livebeertest.ui.theme.LiveBeerGrayText
import com.example.livebeertest.ui.theme.LiveBeerIosBlue
import com.example.livebeertest.ui.theme.LiveBeerWhite
import com.example.livebeertest.ui.theme.LiveBeerYellow
import androidx.compose.foundation.border

@Composable
fun RegisterScreen(
    onBackClick: () -> Unit,
    onRegisterClick: (String) -> Unit,
    onTermsClick: () -> Unit = {}
) {
    var phoneField by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(
            TextFieldValue(
                text = "+7",
                selection = TextRange(2)
            )
        )
    }
    var name by rememberSaveable { mutableStateOf("") }
    var birthDate by rememberSaveable { mutableStateOf("") }
    var isAgreementChecked by rememberSaveable { mutableStateOf(false) }

    val context = LocalContext.current
    val phoneDigits = extractDigits(phoneField.text)

    val isFormValid = phoneDigits.length == 10 &&
            name.isNotBlank() &&
            birthDate.isNotBlank() &&
            isAgreementChecked

    val calendar = remember { Calendar.getInstance() }

    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                birthDate = "%02d.%02d.%04d".format(dayOfMonth, month + 1, year)
            },
            calendar.get(Calendar.YEAR) - 18,
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LiveBeerWhite)
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(top = 16.dp)
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
                text = "Регистрация\nаккаунта",
                color = LiveBeerBlack,
                fontSize = 36.sp,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 43.2.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Заполните поля данных ниже",
                color = LiveBeerGrayText,
                fontSize = 15.sp,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Номер телефона",
                color = LiveBeerGrayText,
                fontSize = 14.sp,
                lineHeight = 16.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            RegisterPhoneField(
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

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Ваше имя",
                color = LiveBeerGrayText,
                fontSize = 14.sp,
                lineHeight = 16.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            RegisterTextField(
                value = name,
                placeholder = "Введите имя",
                onValueChange = { name = it }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Дата рождения",
                color = LiveBeerGrayText,
                fontSize = 14.sp,
                lineHeight = 16.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            RegisterDateField(
                value = birthDate,
                placeholder = "ДД.ММ.ГГ",
                onClick = { datePickerDialog.show() }
            )

            Spacer(modifier = Modifier.height(16.dp))

            AgreementRow(
                checked = isAgreementChecked,
                onCheckedChange = { isAgreementChecked = it },
                onTermsClick = onTermsClick
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { onRegisterClick(phoneDigits) },
                enabled = isFormValid,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(vertical = 18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LiveBeerYellow,
                    contentColor = LiveBeerBlack,
                    disabledContainerColor = LiveBeerButtonDisabled,
                    disabledContentColor = LiveBeerGrayText
                )
            ) {
                Text(
                    text = "Зарегистрироваться",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun RegisterPhoneField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(
                color = LiveBeerWhite,
                shape = RoundedCornerShape(8.dp)
            )
            .borderBox()
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            textStyle = TextStyle(
                color = LiveBeerBlack,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                lineHeight = 20.sp
            ),
            modifier = Modifier.fillMaxWidth(),
            decorationBox = {
                Text(
                    text = buildPhoneAnnotatedString(value.text),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 20.sp
                )
            }
        )
    }
}

@Composable
private fun RegisterTextField(
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(
                color = LiveBeerWhite,
                shape = RoundedCornerShape(8.dp)
            )
            .borderBox()
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = TextStyle(
                color = LiveBeerBlack,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                lineHeight = 20.sp
            ),
            modifier = Modifier.fillMaxWidth(),
            decorationBox = { innerTextField ->
                if (value.isBlank()) {
                    Text(
                        text = placeholder,
                        color = LiveBeerGrayText,
                        fontSize = 16.sp,
                        lineHeight = 20.sp
                    )
                }
                innerTextField()
            }
        )
    }
}

@Composable
private fun RegisterDateField(
    value: String,
    placeholder: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(
                color = LiveBeerWhite,
                shape = RoundedCornerShape(8.dp)
            )
            .borderBox()
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = if (value.isBlank()) placeholder else value,
            color = if (value.isBlank()) LiveBeerGrayText else LiveBeerBlack,
            fontSize = 16.sp,
            lineHeight = 20.sp
        )
    }
}

@Composable
private fun AgreementRow(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onTermsClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.Top
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier
                .size(24.dp)
                .padding(top = 2.dp),
            colors = CheckboxDefaults.colors(
                checkedColor = LiveBeerYellow,
                uncheckedColor = LiveBeerGrayBorder,
                checkmarkColor = LiveBeerBlack
            )
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = buildAnnotatedString {
                append("Я согласен с ")
                withStyle(style = SpanStyle(color = LiveBeerIosBlue)) {
                    append("условиями обработки")
                }
                append("\n")
                withStyle(style = SpanStyle(color = LiveBeerIosBlue)) {
                    append("персональных данных.")
                }
            },
            modifier = Modifier.clickable(onClick = onTermsClick),
            color = LiveBeerBlack,
            fontSize = 14.sp,
            lineHeight = 20.sp
        )
    }
}

private fun Modifier.borderBox(): Modifier {
    return this.border(
        width = 1.dp,
        color = LiveBeerGrayBorder,
        shape = RoundedCornerShape(8.dp)
    )
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

private fun buildPhoneAnnotatedString(text: String) = buildAnnotatedString {
    if (text.startsWith("+7")) {
        withStyle(style = SpanStyle(color = LiveBeerGrayText)) {
            append("+7")
        }
        withStyle(style = SpanStyle(color = LiveBeerBlack)) {
            append(text.drop(2))
        }
    } else {
        append(text)
    }
}

@Preview(name = "Small phone", device = "spec:width=360dp,height=640dp,dpi=480")
@Composable
private fun RegisterScreenSmallPreview() {
    RegisterScreen(
        onBackClick = {},
        onRegisterClick = {}
    )
}

@Preview(name = "Normal phone", device = "spec:width=411dp,height=891dp,dpi=420")
@Composable
private fun RegisterScreenNormalPreview() {
    RegisterScreen(
        onBackClick = {},
        onRegisterClick = {}
    )
}

@Preview(name = "Big phone", device = "spec:width=480dp,height=960dp,dpi=440")
@Composable
private fun RegisterScreenBigPreview() {
    RegisterScreen(
        onBackClick = {},
        onRegisterClick = {}
    )
}