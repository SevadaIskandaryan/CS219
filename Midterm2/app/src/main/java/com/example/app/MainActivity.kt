package com.example.app

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = ViewModelProvider(this)[UserViewModel::class.java]

        setContent {
            AppUI(viewModel = viewModel)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AppUI(viewModel: UserViewModel) {
        viewModel.users.observe(this) {
            setContent {
                Column {
                    TopAppBar(
                        title = { Text(text = "List of Users") }
                    )
                    if (it.isNullOrEmpty()) {
                        Text(text = "Loading...")
                    } else {
                        UserList(users = it)
                    }
                }
            }
        }
    }
}

class UserViewModel : ViewModel() {
    private val userService = RetrofitClient.getUsers

    private val _users = MutableLiveData<List<Post>>()
    val users: LiveData<List<Post>> get() = _users

    init {
        viewModelScope.launch {
            try {
                val response = userService.getUsers()
                _users.value = response
            } catch (e: Exception) {
                Log.e("Retrofit", "Error fetching user data", e)
            }
        }
    }
}

@Composable
fun UserList(users: List<Post>) {
    var temp = 0
    LazyColumn {
        items(users) { user ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                if(temp != user.userId){
                    Text(
                        text = "UserId: " + user.userId.toString(),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    temp = user.userId
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        if(temp != 0) {
                            Text(
                                text = user.title,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onBackground,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = user.body,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

object RetrofitClient {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val getUsers: UserService by lazy {
        retrofit.create(UserService::class.java)
    }
}

interface UserService {
    @GET("posts")
    suspend fun getUsers(): List<Post>
}