package com.droid.datingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.droid.datingapp.ui.navigation.DatingNavGraph
import com.droid.datingapp.ui.theme.BgWhite
import com.dating.app.ui.theme.DatingAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DatingAppTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = BgWhite) {
                    val navController = rememberNavController()
                    DatingNavGraph(navController = navController)
                }
            }
        }
    }
}
