package ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.InsertDriveFile
import androidx.compose.material.icons.automirrored.outlined.InsertDriveFile
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cau_1.TaskViewModel
import model.TaskRepository
import model.TaskStorage

sealed class BottomNavItem(val route: String, val iconFilled: ImageVector, val iconOutlined: ImageVector) {
    object Task : BottomNavItem("task", Icons.Filled.Home, Icons.Outlined.Home)
    object Calendar : BottomNavItem("calendar", Icons.Filled.CalendarToday, Icons.Outlined.CalendarToday)
    object Document : BottomNavItem("document", Icons.AutoMirrored.Filled.InsertDriveFile, Icons.AutoMirrored.Outlined.InsertDriveFile)
    object Settings : BottomNavItem("settings", Icons.Filled.Settings, Icons.Outlined.Settings)
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.Task,
        BottomNavItem.Calendar,
        BottomNavItem.Document,
        BottomNavItem.Settings
    )

    BottomAppBar(
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        items.forEach { item ->
            val isSelected = currentRoute == item.route
            IconButton(
                onClick = { navController.navigate(item.route) },
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = if (isSelected) item.iconFilled else item.iconOutlined,
                    contentDescription = null,
                    tint = if (isSelected) Color.Blue else Color.Gray
                )
            }
        }
    }
}

@Composable
fun FloatingActionButton(navController: NavController) {
    FloatingActionButton(
        onClick = { navController.navigate("add_task") },
        shape = CircleShape,
        modifier = Modifier
            .offset(y = 30.dp),
        containerColor = Color.Blue
    ) {
        Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.White)
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val hideBottomNavScreens = listOf("add_task")
    val context = LocalContext.current
    val repository = TaskRepository(TaskStorage(context))
    val viewModel = remember { TaskViewModel(repository) }
    var currentRoute by remember { mutableStateOf<String?>(null) }
    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collect { backStackEntry ->
            currentRoute = backStackEntry.destination.route
        }
    }

        Scaffold(
            bottomBar = {
                if (currentRoute !in hideBottomNavScreens) {
                    BottomNavigationBar(navController)
                }
            },
            floatingActionButton = {
                if (currentRoute !in hideBottomNavScreens) {
                    FloatingActionButton(navController)
                }
            },
            floatingActionButtonPosition = FabPosition.Center
        ) { paddingValues ->
            val modifier = if (currentRoute in hideBottomNavScreens) {
                Modifier.fillMaxSize()
            } else {
                Modifier.padding(paddingValues)
            }

            NavHost(
                navController = navController,
                startDestination = BottomNavItem.Task.route,
                modifier = modifier
            ) {
                composable(BottomNavItem.Task.route) { TaskScreen(viewModel) }
                composable(BottomNavItem.Calendar.route) { CalendarScreen() }
                composable(BottomNavItem.Document.route) { DocumentScreen() }
                composable(BottomNavItem.Settings.route) { SettingsScreen() }
                composable("add_task") {
                    AddTaskScreen(viewModel, navController)
                }
            }
        }
    }