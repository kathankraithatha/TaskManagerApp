package com.example.taskactivityfinal

//import androidx.compose.foundation.layout.ColumnScopeInstance.weight
//import androidx.compose.foundation.layout.RowScopeInstance.weight
import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
//import androidx.compose.foundation.gestures.ModifierLocalScrollableContainerProvider.value
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
//import kotlinx.coroutines.flow.internal.NoOpContinuation.context


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun DetailedTasks(
    state: TaskState,
    onEvent:(TaskEvent) -> Unit
) {

    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(237, 236, 237, 255))
            .padding(start = 20.dp),
    ) {
        val categoryText= remember {
            mutableStateOf("")
        }
        val titleText= remember {
            mutableStateOf("")
        }
        val taskPriority= remember {
            mutableStateOf("")
        }
        var taskContent by remember {
            mutableStateOf(TextFieldValue())
        }
        val hint= "Enter your task here"


        Column {
            Text(
                text = "Enter Task Category",
                modifier = Modifier.padding( top = 20.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = state.taskCategory,
                onValueChange ={
                               onEvent(TaskEvent.SaveByCategory(it))
                },
                modifier= Modifier
                    .fillMaxWidth(0.8f)
                    .size(55.dp),
                shape = CircleShape,
                placeholder = {
                    Text(text = "Enter Category")
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Edit, contentDescription ="" )
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Enter Required Task Fields",
                modifier = Modifier.padding( top = 20.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
            )

            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier=Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                OutlinedTextField(
                    value = state.taskTitle,
                    onValueChange ={
                        onEvent(TaskEvent.SaveByTitle(it))
                    },
                    modifier= Modifier
                        .fillMaxWidth(0.5f)
                        .weight(1f)
                        .size(50.dp)
                    ,
                    shape = CircleShape,
                    placeholder = {
                        Text(text = "Enter Title")
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Edit, contentDescription ="" )
                    }
                )
                Spacer(modifier = Modifier.width(5.dp))
                OutlinedTextField(
                    value = state.taskPriority,
                    onValueChange ={
                        onEvent(TaskEvent.SaveByPriority(it))
                    },
                    modifier= Modifier
                        .fillMaxWidth(0.5f)
                        .weight(1f)
                        .size(50.dp)
                    ,
                    shape = CircleShape,
                    placeholder = {
                        Text(text = "Enter Priority ")
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Filled.Check, contentDescription ="" )
                    }
                )
            }
            Text(
                text = "Enter Note Content",
                modifier = Modifier.padding( top = 20.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(modifier = Modifier.height(20.dp))
            Card(
                modifier = Modifier
                    .fillMaxHeight(0.75f)
                    .fillMaxWidth(0.95f),
                colors = CardDefaults.cardColors(Color.Magenta)
            ) {
                if (taskContent.text.isEmpty()){
                    Text(text = hint,modifier = Modifier.padding(horizontal = 8.dp))
                }
                BasicTextField(
                    modifier = Modifier.padding(start = 8.dp),
                    value = state.taskContent,
                    onValueChange = {
                        onEvent(TaskEvent.SaveByContent(it))
                    }
                )
                
            }
            FloatingActionButton(
                onClick = {
                    val intent=Intent(context,MainActivity::class.java)
                    context.startActivity(intent)
                    onEvent(TaskEvent.SaveTasks)

                },
                modifier= Modifier
                    .fillMaxWidth(0.9f)
                    .padding(start = 25.dp, bottom = 20.dp, top = 20.dp)

            ) {
                Text(text = "Save Tasks")
            }
        }
    }
}
