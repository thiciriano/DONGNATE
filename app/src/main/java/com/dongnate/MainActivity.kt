package com.dongnate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.dongnate.data.local.JsonDatabase
import com.dongnate.ui.AppNavigation
import com.dongnate.ui.theme.DongNateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        JsonDatabase.init(this)
        setContent {
            DongNateTheme {
                AppNavigation()
            }
        }
    }
}
