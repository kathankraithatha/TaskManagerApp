package com.example.taskactivityfinal

import android.annotation.SuppressLint
import android.content.Intent
import android.service.autofill.OnClickAction
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.BoxScopeInstance.align
//import androidx.compose.foundation.layout.BoxScopeInstance.align
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.ColumnScopeInstance.align
import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.RowScopeInstance.align
//import androidx.compose.foundation.layout.RowScopeInstance.
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.ViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskManagerUI(
    viewModel: TaskViewModel,
    onEvent:(TaskEvent) -> Unit
) {
    val state by viewModel.state.collectAsState()
    Scaffold(modifier=Modifier.fillMaxSize()) { _ ->


        val context = LocalContext.current
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(224, 224, 224, 255))
                .verticalScroll(rememberScrollState())
        ) {
            TaskManagerHeader( viewModel,onEvent = {
                onEvent(TaskEvent.SaveTasks)
            },
            )
            state.tasks.forEach {task ->
                Spacer(modifier = Modifier.height(15.dp))

            Card(
                modifier= Modifier
                    .fillMaxWidth(0.94f)
                    .wrapContentHeight()
                    .padding(start = 20.dp),
                colors = CardDefaults.cardColors(Color(223, 182, 178, 255))
            ) {

                var isChecked by remember {
                    mutableStateOf(false)
                }
                val textDecoration=if(isChecked) TextDecoration.LineThrough else TextDecoration.None

                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = CenterVertically,
                    horizontalArrangement = Arrangement.End){
                    Column {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        modifier = Modifier.clickable {
                            onEvent(TaskEvent.DeleteTask(task))
                        }.padding(start = 10.dp)
                    )
                        Checkbox(
                            checked = isChecked,
                            onCheckedChange = { newCheckChanged ->
                                isChecked = newCheckChanged
                            },
                            colors = CheckboxDefaults.colors(Color.Red),
                            enabled = true,
                            interactionSource = MutableInteractionSource()

                        )
                    }
                     Column(modifier = Modifier.fillMaxSize()) {


                         Text(
                             text = "Title : ${task.taskTitle}",
                             modifier = Modifier.padding(start = 10.dp, top = 10.dp),
                             color = Color.Black,
                             fontSize = 20.sp,
                             fontWeight = FontWeight.SemiBold,
                             textDecoration = textDecoration
                         )


                         Text(
                             text = "Category : ${task.taskCategory}",
                             modifier = Modifier.padding(start = 10.dp, top = 5.dp, bottom = 5.dp),
                             color = Color.Black,
                             fontSize = 20.sp,
                             fontWeight = FontWeight.Normal,
                             textDecoration = textDecoration
                         )
                         Text(
                             text = "Content : ${task.taskContent}",
                             modifier = Modifier.padding(start = 10.dp, top = 5.dp, bottom = 5.dp),
                             color = Color.Black,
                             fontSize = 20.sp,
                             fontWeight = FontWeight.Normal,
                             textDecoration = textDecoration
                         )
                         Text(
                             text = "Priority : ${task.taskPriority}",
                             modifier = Modifier.padding(start = 10.dp, top = 5.dp, bottom = 5.dp),
                             color = Color.Black,
                             fontSize = 20.sp,
                             fontWeight = FontWeight.Normal,
                             textDecoration = textDecoration
                         )
                     }
                }




            }

            }
        }
        


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Bottom
        ) {

            FloatingActionButton(
                onClick = {
                    val intent = Intent(context, SecondActivity::class.java)
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .fillMaxWidth(0.2f)
                    .padding(bottom = 10.dp, end = 20.dp),
                shape = RoundedCornerShape(20.dp)


            ) {
              Icon(imageVector = Icons.Default.Add, contentDescription ="Add Tasks" )
            }
        }
        
    }
}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskManagerHeader(
    viewModel: TaskViewModel,
    onEvent:(TaskEvent) -> Unit


    ) {
    val state by viewModel.state.collectAsState()
    Column(
        modifier= Modifier
            .height(240.dp)
            .fillMaxWidth()
    ) {

        Row(
            modifier= Modifier
                .fillMaxWidth()
                .height(170.dp)
                .background(color = Color(187, 134, 252, 255)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            CurrentDate()
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(224, 224, 224, 255))
                .padding(start = 20.dp)

        ) {

            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Recent Tasks",
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(10.dp))

        }

    }

}


@Composable
fun CurrentDate() {
    Column(
        modifier=Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val currentDateTime = remember {
            LocalDateTime.now()
        }

        val formatter = DateTimeFormatter.ofPattern("dd") // You can format the date as per your requirement
        val dayOfWeek=currentDateTime.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
        val dayOfMonth=currentDateTime.month.getDisplayName(TextStyle.FULL, Locale.getDefault())


        Text(
            text = "${formatter.format(currentDateTime)} ${dayOfMonth}",
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 25.sp

            )
        Text(
            text = "$dayOfWeek",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            fontStyle = FontStyle.Italic
        )
    }
}


