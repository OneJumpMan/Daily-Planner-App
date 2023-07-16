package com.example.testapp

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Construction
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.EventRepeat
import androidx.compose.material.icons.filled.PunchClock
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch





@SuppressLint("MutableCollectionMutableState")
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListsScreen(userData : UserData) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    var selectedEndeavor by remember { mutableStateOf<Endeavor?>(null) }

    data class TabItem(
        val title: String,
        val icon: ImageVector,
        val screen: @Composable () -> Unit
    )

    val tabs = listOf(
        TabItem(
            title = "Appointments",
            icon = Icons.Default.PunchClock,
            screen = {
                ListTab(
                    endeavors = userData.appointmentList,
                    selectEndeavor = { newSelectedEndeavor ->
                        selectedEndeavor = newSelectedEndeavor
                    }
                )
            }
        ),
        TabItem(
            title = "Tasks",
            icon = Icons.Default.Construction,
            screen = {
                ListTab(
                    endeavors = userData.taskList,
                    selectEndeavor = { newSelectedEndeavor ->
                        selectedEndeavor = newSelectedEndeavor
                    }
                )
            }
        ),
        TabItem(
            title = "Habits",
            icon = Icons.Default.EventRepeat,
            screen = {
                ListTab(
                    endeavors = userData.habitList,
                    selectEndeavor = { newSelectedEndeavor ->
                        selectedEndeavor = newSelectedEndeavor
                    }
                )
            }
        )
    )


    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TabRow(
            selectedTabIndex= pagerState.currentPage
        ) {
            tabs.forEachIndexed {index, item ->
                Tab(
                    selected = index == pagerState.currentPage,
                    text = { Text(text = item.title) },
                    icon = { Icon(item.icon,  "") },
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                )
            }
        }

        HorizontalPager(
            pageCount = tabs.size,
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            tabs[pagerState.currentPage].screen()
        }
    }








    if (selectedEndeavor != null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0x80000000))
                .clickable { selectedEndeavor = null },
            contentAlignment = Alignment.Center
        ) {
            EndeavorView(
                endeavor = selectedEndeavor!!,
                exitEndeavorView = { selectedEndeavor = null },
                updateUserData = { userData.updateLists() }
            )
        }
    }
}