/*
 * Created by Nitheesh AG on 2022/8/13
 */

package com.heirarchylist.data.model

data class ResponseModel(
    val dataObject: List<DataObject>,
    val message: String,
    val status: Boolean,
    val statusCode: Int
)