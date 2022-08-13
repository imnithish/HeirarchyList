/*
 * Created by Nitheesh AG on 2022/8/13
 */

package com.heirarchylist.util

import android.content.Context
import android.content.Intent
import android.net.Uri


fun Context.openDialer(number: String) {
    val intent = Intent(Intent.ACTION_DIAL)
    intent.data = Uri.parse("tel:$number")
    startActivity(intent)
}

fun Context.openMessagingApp(number: String) {
    val sendIntent = Intent(Intent.ACTION_VIEW)
    sendIntent.data = Uri.parse("sms:$number")
    startActivity(sendIntent)
}