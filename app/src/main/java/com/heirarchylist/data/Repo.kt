/*
 * Created by Nitheesh AG on 2022/8/13
 */

package com.heirarchylist.data

import com.heirarchylist.data.model.ResponseModel
import com.heirarchylist.network.APIInterface
import com.heirarchylist.util.ResultWrapper
import com.heirarchylist.util.safeApiCall
import javax.inject.Inject

class Repo @Inject constructor(private val api: APIInterface) {

    suspend fun getList(): ResultWrapper<ResponseModel> {
        return safeApiCall {
            api.getList()
        }
    }
}