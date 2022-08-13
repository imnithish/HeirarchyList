/*
 * Created by Nitheesh AG on 2022/8/13
 */

package com.heirarchylist.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.heirarchylist.R
import com.heirarchylist.ui.CoolFont

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchLayout(
    modifier: Modifier = Modifier,
    listState: LazyListState,
    buffer: Int = 0,
    hint: String = "",
    onSearch: (String) -> Unit,
    onClear: () -> Unit,
) {

    var text by remember {
        mutableStateOf("")
    }
    var isHintDisplayed by remember {
        mutableStateOf(hint != "")
    }
    val focusRequester = remember { FocusRequester() }

    val keyboardController = LocalSoftwareKeyboardController.current

    if (listState.isScrollInProgress)
        keyboardController?.hide()

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        backgroundColor = Color.White,
        elevation = 0.dp
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(48.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(12.dp))
                Icon(
                    painter = painterResource(id = R.drawable.baseline_search_24),
                    contentDescription = "Search",
                    tint = Color.LightGray
                )
                Spacer(modifier = Modifier.width(12.dp))
                Divider(
                    color = Color.LightGray,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                        .padding(vertical = 8.dp)
                )
            }

            Box(
                Modifier
                    .weight(1f)
                    .padding(start = 12.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                BasicTextField(
                    value = text,
                    onValueChange = {
                        isHintDisplayed = it == ""
                        if (it.length <= 100) {
                            text = it
                            if (it.length > buffer)
                                onSearch(it)
                        }
                        if (it.length < buffer + 1) {
                            onClear()
                        }
                    },
                    maxLines = 1,
                    singleLine = true,
                    modifier = Modifier
                        .focusRequester(focusRequester)
                        .fillMaxWidth(),
                    cursorBrush = SolidColor(Color.LightGray),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        autoCorrect = false,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            text.let {
                                if (it.length > buffer) {
                                    onSearch(it)
                                    keyboardController?.hide()
                                }
                            }
                        }
                    ),
                    textStyle = TextStyle(fontFamily = CoolFont, fontSize = 16.sp)
                )

                Column {
                    AnimatedVisibility(visible = isHintDisplayed) {
                        Text(
                            text = hint,
                            color = Color.LightGray,
                            fontFamily= CoolFont
                        )
                    }
                }
            }
            IconButton(
                onClick = {
                    onClear()
                    text = ""
                    keyboardController?.show()
                    focusRequester.requestFocus()
                    isHintDisplayed = true
                },
            ) {
                AnimatedVisibility(
                    visible = text.isNotEmpty()
                ) {
                    Icon(
                        painterResource(id = R.drawable.ic_close),
                        contentDescription = "Clear",
                        modifier = Modifier.size(16.dp),
                        tint = Color.LightGray
                    )
                }
            }
        }
    }

}


@Composable
@Preview
fun SearchLayoutPreview() {
    SearchLayout(listState = rememberLazyListState(), onSearch = {}) {
    }
}
