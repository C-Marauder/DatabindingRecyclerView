package com.xhh.android.rv.utils

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView


inline fun <reified T : RecyclerView> T.bindAdapter(
    layoutManager: RecyclerView.LayoutManager,
    itemAnimator: RecyclerView.ItemAnimator = DefaultItemAnimator(),
    itemDecoration:RecyclerView.ItemDecoration?=null,
    bind:T.()->Unit
) {
    setHasFixedSize(true)
    this.layoutManager = layoutManager
    this.itemAnimator = itemAnimator
    itemDecoration?.let {
        addItemDecoration(it)
    }
    bind(this)

}