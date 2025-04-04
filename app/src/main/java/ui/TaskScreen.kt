package ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cau_1.R
import com.example.cau_1.TaskViewModel
import model.Task
import model.interBold
import model.robotoBold
import model.robotoRegular
import model.robotoSemiBold

@Composable
fun TaskScreen(viewModel: TaskViewModel) {
    val tasks by viewModel.tasks.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = R.drawable.chevron_left),
                contentDescription = "Chevron_left",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(40.dp, 40.dp)
            )


            Text(
                text = "List",
                fontFamily = robotoSemiBold,
                fontSize = 30.sp,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 37.5.sp,
                color = Color(0xFF2196F3),
            )

            Image(
                painter = painterResource(id = R.drawable.plus),
                contentDescription = "bin",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(40.dp, 40.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))


        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(tasks) { task ->
                TaskItem(task, onDelete = { viewModel.deleteTask(task) })
            }

        }
    }
}

@Composable
fun TaskItem(task: Task, onDelete: () -> Unit) {
    Card(modifier = Modifier.padding(8.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = task.title,
                    fontFamily = robotoBold,
                    fontSize = 18.sp,
                    lineHeight = 22.sp,
                    color = Color(0xFF333333),
                )
                Text(text = task.description,
                    fontFamily = robotoRegular,
                    fontSize = 18.sp,
                    lineHeight = 22.sp,
                    color = Color(0xFF333333),
                    )
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}
