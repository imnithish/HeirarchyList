/*
 * Created by Nitheesh AG on 2022/8/13
 */

package com.heirarchylist.ui.components

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.heirarchylist.R
import com.heirarchylist.ui.CoolFont

@Composable
fun ListItemLayout(
    modifier: Modifier,
    heading: String,
    description: String,
    onCallClick: () -> Unit,
    onMessageClick: () -> Unit
) {
    Column(
        modifier = modifier
            .background(Color.White)
            .fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = heading, color = Color.Black, fontSize = 18.sp, fontFamily = CoolFont
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description, color = Color.Gray, fontFamily = CoolFont
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onCallClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_call_24),
                        contentDescription = "Search",
                        tint = Color.Gray
                    )
                }

                IconButton(onClick = onMessageClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_message_24),
                        contentDescription = "Search",
                        tint = Color.Gray
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        Divider(
            modifier = Modifier.padding(horizontal = 4.dp),
            color = Color.LightGray.copy(alpha = .4f)
        )
    }
}

@Composable
@Preview
fun ListItemLayoutPreview() {
    ListItemLayout(modifier = Modifier, heading = "Mr Smit", description = "Branch Manager", {}) {
    }
}