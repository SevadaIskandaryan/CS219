package com.example.exam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.exam.ui.theme.EXAMTheme

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable

import androidx.compose.material3.Slider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import kotlin.random.Random
import kotlin.math.abs

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EXAMTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                val randomInitialValue = generateRandomTargetValue()

                NavHost(
                    navController = navController,
                    startDestination = "gameScreen"
                ) {
                    composable("gameScreen") {
                        GameScreen(navController = navController, targetValue = randomInitialValue)
                    }
                }
            }
        }
    }
}

@Composable
fun GameScreen(navController: NavController, targetValue: Float) {
    var sliderValue by remember { mutableStateOf(50.0f) }
    var score by remember { mutableStateOf(0) }
    var feedback by remember { mutableStateOf("") }
    
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Bull's Eye Game",
            color = Color.Black,
            fontSize = 24.sp,
            modifier = Modifier.padding(16.dp)
        )
        Slider(
            value = sliderValue,
            onValueChange = { sliderValue = it },
            valueRange = 0.0f..100.0f, // Set the value range of the slider
            steps = 100, 
        )
        Text("Slider Value: ${sliderValue}")
        

        Button(
            onClick = {
                      val proximity = abs(targetValue - sliderValue)
                      if(proximity <= 3.0 ){
                          score+= 5
                          feedback = "exelent"
                      } else if (proximity  <=8){
                          score+= 1
                          feedback = "good"
                      }else {
                          score = 0
                          feedback = "Keep trying! You're not quite there."
                      }
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Hit me!")
        }

        Text("Actual value: $targetValue")       // for debugging
        Text("Your value: $sliderValue")
        Text("Score: $score")
        Text(feedback)
    }
}


fun generateRandomTargetValue(): Float {
    val random = Random.Default
    return random.nextFloat() * 100.0f
}