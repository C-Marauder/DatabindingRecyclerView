package com.xhh.android.rv.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.xhh.android.rv.model.Item
import com.xhh.android.rv.holder.ItemViewHolder
import java.util.concurrent.ConcurrentHashMap

class DataBindingAdapter private constructor(
    private val itemList: MutableList<Item>,
    private val onViewHolderCreated:(holder:ItemViewHolder,itemType:Int)->Unit,
    private val onCreateItemLayout: (position: Int) -> Int,
    private val onBinding: (holder:ItemViewHolder,dataBinding:ViewDataBinding,position: Int) -> Unit
) : RecyclerView.Adapter<ItemViewHolder>() {
    companion object {
        private const val TAG:String = "DataBindingAdapter"
        fun create(
            itemList: MutableList<Item>,
            onViewHolderCreated: (holder: ItemViewHolder,itemType:Int) -> Unit,
            onCreateItemLayout: (position: Int) -> Int,
            onBinding: (holder:ItemViewHolder,dataBinding:ViewDataBinding,position: Int) -> Unit
        ): DataBindingAdapter {
            return DataBindingAdapter(
                itemList,
                onViewHolderCreated,
                onCreateItemLayout,
                onBinding)
        }
    }
    private val mNotifyCallback:NotifyCallback = object :NotifyCallback{
        override fun insert(start: Int, itemCount: Int, items: MutableList<Item>) {

            itemList.addAll(start,items)
            notifyItemRangeInserted(start,itemCount)
        }

        override fun remove(start: Int, itemCount: Int, items: MutableList<Item>) {
            val removeItems = itemList.slice(start until start+itemCount)
            itemList.removeAll(removeItems)
            notifyItemRangeRemoved(start,itemCount)
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val dataBinding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater,viewType,parent,false)
        return ItemViewHolder(dataBinding.root,{
            onViewHolderCreated(it,viewType)
        },dataBinding,onBinding).apply {
            this.notifyCallback = mNotifyCallback
        }
    }

    override fun getItemViewType(position: Int): Int {
        return onCreateItemLayout(position)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.onBind(holder,holder.dataBinding,position)
    }


}