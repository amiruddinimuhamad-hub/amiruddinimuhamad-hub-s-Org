package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.ui.components.BottomNavBar
import com.example.ui.screens.AuthScreen
import com.example.ui.screens.HorizonScreen
import com.example.ui.screens.OrbitScreen
import com.example.ui.screens.SparkScreen
import com.example.ui.screens.TheDenScreen
import com.example.ui.theme.MYLYTheme
import com.example.ui.theme.WarmSurface
import com.example.ui.viewmodel.MylyViewModel
import com.example.ui.viewmodel.NavigationTab
import kotlinx.coroutines.flow.collectLatest

class MainActivity : ComponentActivity() {
    private val viewModel: MylyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MYLYTheme {
                val snackbarHostState = remember { SnackbarHostState() }

                LaunchedEffect(Unit) {
                    viewModel.toastMessage.collectLatest { message ->
                        snackbarHostState.showSnackbar(message)
                    }
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    contentWindowInsets = WindowInsets(0, 0, 0, 0),
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
                ) { innerPadding ->
                    MylyApp(
                        viewModel = viewModel,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .background(WarmSurface)
                    )
                }
            }
        }
    }
}

@Composable
fun MylyApp(
    viewModel: MylyViewModel,
    modifier: Modifier = Modifier
) {
    val currentTab by viewModel.currentTab.collectAsState()
    val authState by viewModel.authState.collectAsState()

    Box(modifier = modifier) {
        Crossfade(
            targetState = authState.showAuthScreen || !authState.isAuthenticated,
            animationSpec = tween(300),
            label = "auth_crossfade"
        ) { showingAuth ->
            if (showingAuth) {
                AuthScreen(
                    viewModel = viewModel,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Box(modifier = Modifier.fillMaxSize()) {
                    Crossfade(
                        targetState = currentTab,
                        animationSpec = tween(300),
                        label = "tab_crossfade"
                    ) { tab ->
                        when (tab) {
                            NavigationTab.ORBIT -> OrbitScreen(viewModel = viewModel)
                            NavigationTab.SPARK -> SparkScreen(viewModel = viewModel)
                            NavigationTab.HORIZON -> HorizonScreen(viewModel = viewModel)
                            NavigationTab.THE_DEN -> TheDenScreen(viewModel = viewModel)
                        }
                    }

                    // Floating pill bottom navigation bar
                    BottomNavBar(
                        currentTab = currentTab,
                        onTabSelected = { viewModel.setTab(it) },
                        modifier = Modifier.align(Alignment.BottomCenter)
                    )
                }
            }
        }
    }
}

