/*
 * Created by Nitheesh AG on 2022/8/13
 */

@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package com.heirarchylist.ui

import androidx.compose.animation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.heirarchylist.ui.components.ListItemLayout
import com.heirarchylist.ui.components.SearchLayout
import com.heirarchylist.util.openDialer
import com.heirarchylist.util.openMessagingApp
import java.util.*
import com.heirarchylist.R

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ListingScreen(listingScreenVM: ListingScreenVM = hiltViewModel()) {

    val context = LocalContext.current
    val lazyState = rememberLazyListState()

    Scaffold(
        topBar = {
            AnimatedVisibility(
                visible = listingScreenVM.uiState.value.result != null,
                enter = slideInVertically() + fadeIn()
            ) {
                Column {
                    SearchLayout(
                        modifier = Modifier.padding(
                            top = 16.dp,
                            start = 16.dp,
                            end = 16.dp,
                        ),
                        listState = lazyState,
                        hint = "Search",
                        onClear = {
                            listingScreenVM.clear()
                        },
                        onSearch = {
                            listingScreenVM.search(it.lowercase(Locale.ROOT))
                        }
                    )
                }
            }
        }
    ) { it ->

        Box(
            Modifier
                .fillMaxSize()
                .padding(it),
            contentAlignment = Alignment.Center
        ) {

            AnimatedVisibility(
                visible = listingScreenVM.uiState.value.result != null,
                exit = fadeOut(),
                enter = fadeIn()
            ) {
                LazyColumn(
                    state = lazyState,
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize(),
                    content = {
                        listingScreenVM.uiState.value.resultCopy?.forEach {
                            item(key = it.hashCode().toString()) {
                                ListItemLayout(
                                    modifier = Modifier
                                        .animateEnterExit(enter = slideInVertically() + fadeIn()),
                                    heading = it.contactName,
                                    description = it.designationName,
                                    onCallClick = {
                                        context.openDialer(it.contactNumber)
                                    },
                                    onMessageClick = {
                                        context.openMessagingApp(it.contactNumber)
                                    }
                                )
                            }
                        }
                    }
                )
            }

            AnimatedVisibility(
                visible = listingScreenVM.uiState.value.error != null,
                exit = fadeOut(),
                enter = fadeIn()
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        modifier = Modifier.padding(horizontal = 32.dp),
                        text = listingScreenVM.uiState.value.error ?: "",
                        color = Color.Red,
                        fontSize = 24.sp,
                        fontFamily = CoolFont
                    )
                    TextButton(onClick = { listingScreenVM.attemptAPICall() }) {
                        Text(
                            text = "Refresh",
                            color = Color.Black,
                            fontFamily = CoolFont,
                            textDecoration = TextDecoration.Underline
                        )
                    }
                }
            }

            AnimatedVisibility(
                visible = listingScreenVM.uiState.value.loading,
                exit = fadeOut(),
                enter = fadeIn()
            ) {
                CircularProgressIndicator(color = Color.Black, strokeWidth = 3.dp)
            }
        }

    }
}

val CoolFont = FontFamily(
    Font(
        R.font
            .figtree_regular
    ),
)