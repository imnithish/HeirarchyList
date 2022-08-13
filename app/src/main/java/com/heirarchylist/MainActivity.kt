/*
 * Created by Nitheesh AG on 2022/8/13
 */

package com.heirarchylist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.heirarchylist.ui.ListingScreen
import com.heirarchylist.ui.ListingScreenVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ListingScreen()
        }
    }
}