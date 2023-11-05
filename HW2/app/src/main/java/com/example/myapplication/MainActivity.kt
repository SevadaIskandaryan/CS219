package com.example.myapplication
import androidx.compose.runtime.remember
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.compose.material3.Card
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "welcomeScreen"
                ) {
                    composable("welcomeScreen") {
                        WelcomeScreen(navController = navController)
                    }
                    composable("secondScreen") {
                        SecondScreen(navController = navController)
                    }
                }
            }
        }
    }
}

@Composable
fun WelcomeScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Welcome to My App",
            color = Color.Black,
            fontSize = 24.sp,
            modifier = Modifier.padding(16.dp)
        )

        Button(
            onClick = { navController.navigate("secondScreen") },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Go to Second Screen")
        }
    }
}

@Composable
fun SecondScreen(navController: NavController) {
    val cities = listOf(
        City("Yerevan", "Capital city of Armenia", R.drawable.yerevan),
        City("Washington", "description of Washington", R.drawable.washington),
        City("Madrid", "City of Spain", R.drawable.madrid),
        City("Tokyo", "The Land of the Rising Sun", R.drawable.tokyo),
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "This is the Second Screen",
            color = Color.Black,
            fontSize = 24.sp,
            modifier = Modifier.padding(16.dp)
        )
        Button(
            onClick = { navController.navigate("welcomeScreen") },
            modifier = Modifier
                .padding(16.dp)

        ) {
            Text(text = "Go to welcome Screen")
        }
        LazyColumn {
            items(cities) { city ->
                CityCard(city)
            }
        }
    }
}

@Composable
fun CityCard(city: City) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            Image(
                painter = painterResource(id = city.imageId),
                contentDescription = city.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(shape = MaterialTheme.shapes.medium)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = city.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            Text(
                text = city.description,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}

data class City(
    val name: String,
    val description: String,
    val imageId: Int
)