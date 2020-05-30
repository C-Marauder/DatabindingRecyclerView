package com.xhh.android.rv.adapter

import com.xhh.android.rv.model.Item

 internal interface NotifyCallback {

    fun insert(start:Int,itemCount:Int,items:MutableList<Item>)
     fun remove(start: Int,itemCount: Int,items: MutableList<Item>)
}