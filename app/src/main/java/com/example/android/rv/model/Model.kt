package com.example.android.rv.model

import com.xhh.android.rv.model.Item


data class MoveType(val name:String,var index:Int?=null):Item

data class Move(val name: String,val time:String="2020-5-28"):Item