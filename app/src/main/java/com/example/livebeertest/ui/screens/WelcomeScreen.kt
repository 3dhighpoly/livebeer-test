package com.example.livebeertest.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.livebeertest.R
import com.example.livebeertest.ui.theme.LiveBeerBlack
import com.example.livebeertest.ui.theme.LiveBeerGrayBorder
import com.example.livebeertest.ui.theme.LiveBeerWhite
import com.example.livebeertest.ui.theme.LiveBeerYellow

import androidx.compose.ui.tooling.preview.Preview

@Composable
fun WelcomeScreen(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit
) {

    val baseModifier = Modifier
        .fillMaxSize()
        .background(LiveBeerWhite)
        .statusBarsPadding()
        .navigationBarsPadding()

    Column(
        modifier = baseModifier.verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.hops_background),
                contentDescription = null,
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(60.dp))

                Image(
                    painter = painterResource(id = R.drawable.livebeer_logo),
                    contentDescription = null,
                    Modifier.size(width = 245.dp, height = 67.58.dp),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.height(80.dp))

                Image(
                    painter = painterResource(id = R.drawable.beer_illustration),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Fit
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

            Column(
                modifier = Modifier
                    .padding(start = 30.dp, end = 30.dp)
                    .padding(bottom = 13.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Программа\nлояльности для\nклиентов LiveBeer",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = 36.sp,
                    textAlign = TextAlign.Center,
                    color = LiveBeerBlack,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    Button(
                        onClick = onLoginClick,
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = LiveBeerYellow,
                            contentColor = LiveBeerBlack
                        )
                    ) {
                        Text(
                            text = "Вход",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = LiveBeerBlack
                        )
                    }

                    Button(
                        onClick = onRegisterClick,
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = LiveBeerYellow,
                            contentColor = LiveBeerBlack
                        )
                    ) {
                        Text(
                            text = "Регистрация",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = LiveBeerBlack
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedButton(
                    onClick = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LiveBeerWhite,
                        contentColor = LiveBeerBlack
                    ),
                    border = BorderStroke(1.dp, LiveBeerGrayBorder)
                ) {
                    Text(
                        text = "Войти без регистрации",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = LiveBeerBlack
                    )
                    }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(name = "Small phone", device = "spec:width=360dp,height=640dp,dpi=480")
@Composable
fun WelcomeScreenSmallPreview() {
    WelcomeScreen({}, {})
}

@Preview(name = "Normal phone", device = "spec:width=411dp,height=891dp,dpi=420")
@Composable
fun WelcomeScreenNormalPreview() {
    WelcomeScreen({}, {})
}

@Preview(name = "Big phone", device = "spec:width=480dp,height=960dp,dpi=440")
@Composable
fun WelcomeScreenBigPreview() {
    WelcomeScreen({}, {})
}