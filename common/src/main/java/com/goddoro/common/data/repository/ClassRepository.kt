package com.goddoro.common.data.repository

import com.goddoro.common.data.model.DanceClass


/**
 * created By DORO 12/27/20
 */

interface ClassRepository {

    suspend fun listClasses (
        isMainClass : Boolean? = false,
        type : String? = null
    ) : List<DanceClass>

    suspend fun getClass (
         id : Int
    ) : DanceClass
}