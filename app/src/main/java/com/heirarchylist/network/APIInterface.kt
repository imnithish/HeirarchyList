/*
 * Created by Nitheesh AG on 2022/8/13
 */

package com.heirarchylist.network

import com.heirarchylist.data.model.ResponseModel
import retrofit2.http.GET

interface APIInterface {

    @GET("getMyList")
    suspend fun getList(): ResponseModel
}