package com.example.android.rv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.rv.databinding.ItemMoveBinding
import com.example.android.rv.databinding.ItemMoveTypeBinding
import com.example.android.rv.model.Move
import com.example.android.rv.model.MoveType
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.xhh.android.rv.adapter.DataBindingAdapter
import com.xhh.android.rv.holder.ItemViewHolder
import com.xhh.android.rv.model.Item
import com.xhh.android.rv.utils.bindAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var mDataBindingAdapter: DataBindingAdapter
    private lateinit var mDataList:MutableList<Item>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val typeList = mutableListOf<String>("科幻", "恐怖", "动作", "爱情", "古装")
        mDataList = mutableListOf<Item>()
        typeList.forEach {
            mDataList.add(MoveType(it))
            for (i in 1..10) {
                mDataList.add(Move("$it----$i"))
            }
        }
        listView.bindAdapter(
            LinearLayoutManager(this),
            itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        ) {

            mDataBindingAdapter = DataBindingAdapter.create(mDataList,onViewHolderCreated = {
                holder, itemType -> if (itemType == R.layout.item_move_type){
                holder.setOnExpandListener(true){
                    mDataList.subList(it,it+10)
                }
            }

            },onCreateItemLayout =  { position ->
                when(mDataList[position]){
                    is Move-> R.layout.item_move
                    is MoveType->R.layout.item_move_type
                    else -> 0
                }

            },onBinding= { holder,dataBinding, position ->
                when (dataBinding) {
                    is ItemMoveTypeBinding -> {
                        dataBinding.holder = holder
                        dataBinding.item = (mDataList[position] as MoveType).apply {
                            index = position
                        }
                    }
                    is ItemMoveBinding -> dataBinding.item = mDataList[position] as Move
                }

            })
            adapter = mDataBindingAdapter
        }
    }



}

interface OnItemClickListener{

    fun onItemClick(holder:ItemViewHolder,position:Int)
}