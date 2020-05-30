package com.xhh.android.rv.holder

import android.util.Log
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.xhh.android.rv.adapter.NotifyCallback
import com.xhh.android.rv.model.Item

class ItemViewHolder(
    itemView: View,
    private val onViewHolderCreated: (holder: ItemViewHolder) -> Unit,
    internal val dataBinding: ViewDataBinding,
    internal val onBind: (holder: ItemViewHolder, dataBinding: ViewDataBinding, position: Int) -> Unit
) : RecyclerView.ViewHolder(itemView) {
    companion object {
        private const val TAG: String = "ItemViewHolder"
    }

    private var childItems: MutableList<Item> ?=null

    private var childItemCount:Int = 0
    private var expanded: Boolean? = null
    private var onItems:((position:Int)->MutableList<Item>)?=null
    internal lateinit var notifyCallback: NotifyCallback
    private fun expand(itemCount: Int,items:MutableList<Item>) {

        notifyCallback.insert(adapterPosition + 1, itemCount,items)
    }

    private fun collapse(itemCount: Int,items: MutableList<Item>) {
        notifyCallback.remove(adapterPosition + 1, itemCount,items)
    }

    init {
        onViewHolderCreated(this)

    }


    fun onExpandClick() {
        if (expanded == null || childItems == null) {
            Log.e(TAG, "please init expanded state")
        } else {

            onItems?.let {

                if (childItems!!.isEmpty()){
                    childItems!!.addAll(it.invoke(adapterPosition+1))
                    childItemCount = childItems!!.size

                }

                if (expanded!!) {
                    expanded = !expanded!!
                    collapse(childItemCount,childItems!!)
                }else{
                    expanded = !expanded!!
                    expand(childItemCount,childItems!!)
                }
            }

        }
    }

    fun setOnExpandListener(
        initValue: Boolean,
        onItems:(position:Int)->MutableList<Item>
    ) {
        if (childItems == null){
            childItems = mutableListOf<Item>()
        }
        this.expanded = initValue
        this.onItems = onItems

    }
}
