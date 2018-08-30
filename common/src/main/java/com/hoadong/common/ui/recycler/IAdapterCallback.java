package com.hoadong.common.ui.recycler;

/**
 * Created by linghaoDo on 2018/8/29
 */
public interface IAdapterCallback<Data> {
   void update(Data data,RecyclerAdapter.ViewHolder<Data> holder);
}
