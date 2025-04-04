package ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cau_1.R
import com.example.cau_1.TaskViewModel
import model.interBold
import model.robotoBold
import model.robotoRegular
import model.robotoSemiBold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(viewModel: TaskViewModel, navController: NavController) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Image(
                painter = painterResource(id = R.drawable.chevron_left),
                contentDescription = "Chevron_left",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(40.dp, 40.dp).align(Alignment.TopStart)
                    .clickable {
                        navController.navigate(BottomNavItem.Task.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
            )


            Text(
                text = "Add New",
                fontFamily = robotoSemiBold,
                fontSize = 30.sp,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 37.5.sp,
                color = Color(0xFF2196F3),
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }

        Spacer(modifier = Modifier.padding(12.dp))

        Text(
            text = "Task",
            fontFamily = interBold,
            fontSize = 16.sp,
            lineHeight = 13.sp,
            color = Color(0xFF333333),
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.padding(3.dp))

        TextField(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
            value = title,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent
            ),
            onValueChange = { title = it }
        )

        Spacer(modifier = Modifier.padding(8.dp))

        Text(
            text = "Description",
            fontFamily = interBold,
            fontSize = 16.sp,
            lineHeight = 13.sp,
            color = Color(0xFF333333),
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.padding(3.dp))

        TextField(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 8.dp)
                .height(56.dp),
            value = description,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent
            ),
            onValueChange = { description = it },
        )

        Spacer(modifier = Modifier.padding(8.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(
                onClick = {
                viewModel.addTask(title, description)
                navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth(0.4f)
                ) {
                Text(
                    text = "Add",
                    fontFamily = robotoBold,
                    fontSize = 20.sp,
                    lineHeight = 24.sp,
                    color = Color(0xFFFFFFFF),
                )
            }
        }
    }
}

