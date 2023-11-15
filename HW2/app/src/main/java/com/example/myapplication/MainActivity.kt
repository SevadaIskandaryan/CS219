package com.example.myapplication
import android.Manifest
import android.location.Location
import android.location.LocationListener
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.location.LocationManager
import android.location.Geocoder
import java.io.IOException
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import java.util.Locale

class MainActivity : ComponentActivity() {

    private lateinit var locationManager: LocationManager
    private lateinit var geocoder: Geocoder
    var currentCountry = "";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        geocoder = Geocoder(this, Locale.getDefault())

        // Check if location permissions are granted
        // (You need to request and handle location permissions separately)

        // Request location updates
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            0,
            0f,
            locationListener
        )
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "welcomeScreen"
                ) {
                    composable("welcomeScreen") {
                        WelcomeScreen(navController = navController, currentCountry = currentCountry)
                    }
                    composable("secondScreen") {
                        SecondScreen(navController = navController)
                    }
                }
            }
        }
    }

    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            // Called when the location has changed

            try {
                val addresses = geocoder.getFromLocation(
                    location.latitude,
                    location.longitude,
                    1
                )

                if (addresses != null) {
                    if (addresses.isNotEmpty()) {
                        val country = addresses[0]?.countryName
                        // Now 'country' contains the name of the current country
                        // You can use it as needed
                        if (country != null) {
                            currentCountry = country
                        }
                        println("Current country: $country")
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}
@Composable
fun WelcomeScreen(navController: NavController, currentCountry : String) {

    var currentTempInCountry = remember { mutableStateOf(0.0f) }
    LaunchedEffect(Unit) {
        // Perform an asynchronous operation
        val result = withContext(Dispatchers.IO) {
            currentTempInCountry.value = getTemp(currentCountry).toFloat()
        }
    }
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

        Text(
            text = "Current country temperature is " + currentTempInCountry.value,
            color = Color.Black,
            fontSize = 24.sp,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun SecondScreen(navController: NavController) {
    var cities = remember { mutableStateListOf<City>()}

    LaunchedEffect(Unit) {
        // Perform an asynchronous operation
        val result = withContext(Dispatchers.IO) {
            // This will be executed on a background thread
            cities.add(City("Yerevan", "Capital city of Armenia", R.drawable.yerevan, getTemp("Yerevan")))
            cities.add(City("Washington", "description of Washington", R.drawable.washington, getTemp("Washington")),)
            cities.add(City("Madrid", "City of Spain", R.drawable.madrid, getTemp("Madrid")),)
            cities.add(City("Tokyo", "The Land of the Rising Sun", R.drawable.tokyo, getTemp("Tokyo")))
        }
    }

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

            Text(
                text = String.format("%.2f", city.temp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}

suspend fun getTemp(city: String): Double {
    val weatherService = RetrofitHelper.retrofit.create(WeatherService::class.java)
    val temp = weatherService.fetchWeather(city)?.current?.temp_c
    if (temp != null) {
        return temp.toDouble()
    }
    return 0.0
}
data class City(
    val name: String,
    val description: String,
    val imageId: Int,
    val temp: Double
)
