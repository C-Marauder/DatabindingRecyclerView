# DatabindingRecyclerView
使用DataBinding封装的RecyclerView库
### 1、依赖
```
implementation 'com.xhh.android.rv:android-databinding-rv:1.0.0'

```
### 2、app的build.gradle

```
android{
  dataBinding {
        enabled = true
    }
}
```
### 3、使用

* 所有的数据实体类实现Item接口
* 代码示例
> 如需折叠列表功能，可在onViewHolderCreated方法中通过holder.setOnExpandListener()
> true表示初始状态为展开、false表示初始状态为收缩。
```
val typeList = mutableListOf<String>("科幻", "恐怖", "动作", "爱情", "古装")
        mDataList = mutableListOf<Item>()
        typeList.forEach {
            mDataList.add(MoveType(it))
            for (i in 1..10) {
                mDataList.add(Move("$it----$i"))
            }
        }
recyclerView.bindAdapter(
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
                //根据不同的实体类型返回不同Item布局
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
```
