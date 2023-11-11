package com.example.motionlayoutmyapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import com.example.motionlayoutmyapplication.ui.theme.MotionLayoutMyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MotionLayoutMyApplicationTheme {
                Column {
                    var progress by remember {
                        mutableStateOf(0f)
                    }
                    ProfileHeader(progress = progress, onClick = { newProgress ->
                        progress = newProgress
                    })
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMotionApi::class)
@Composable
fun ProfileHeader(progress: Float, onClick: (Float) -> Unit) {
    val context = LocalContext.current
    val motionScene = remember {
        context.resources.openRawResource(R.raw.motion_scene).readBytes().decodeToString()
    }
    val animatedProgress by animateFloatAsState(targetValue = progress, label = "",
        animationSpec = tween(durationMillis = 1000))

    MotionLayout(
        motionScene = MotionScene(content = motionScene),
        progress = animatedProgress,
        modifier = Modifier.fillMaxWidth()
    ) {
        val properties = motionProperties(id = "profile_pic")
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.DarkGray)
                .layoutId("box")
        )
        Image(
            painter = painterResource(id = R.drawable.axali),
            contentDescription = null,
            modifier = Modifier
                .clip(CircleShape)
                .border(
                    width = 2.dp, color = properties.value.color("background"), shape = CircleShape
                )
                .layoutId("profile_pic")
                .clickable {
                    val newProgress = (progress + 1f) % 2f
                    onClick(newProgress)
                }
        )
        Text(
            text = "Merabaaaa",
            fontSize = 24.sp,
            modifier = Modifier.layoutId("username"),
            color = properties.value.color("background")
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun GreetingPreview() {
    MotionLayoutMyApplicationTheme {
        Column {
            var progress by remember {
                mutableStateOf(0f)
            }
            ProfileHeader(progress = progress, onClick = { newProgress ->
                progress = newProgress
            })
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}