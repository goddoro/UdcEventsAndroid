package com.goddoro.common

import io.reactivex.subjects.PublishSubject

object Broadcast {

    val eventUploadBroadcast : PublishSubject<Unit> = PublishSubject.create()


    val findAddressBroadcast : PublishSubject<Triple<String,Double,Double>> = PublishSubject.create()

    val bottomIndexChangeBroadcast : PublishSubject<Int> = PublishSubject.create()
}