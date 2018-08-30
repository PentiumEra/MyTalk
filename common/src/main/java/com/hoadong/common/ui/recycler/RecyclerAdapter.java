package com.hoadong.common.ui.recycler;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.util.AsyncListUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hoadong.common.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by linghaoDo on 2018/8/29
 */
public abstract class RecyclerAdapter<Data>
        extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder<Data>>
        implements View.OnClickListener,
        View.OnLongClickListener,
        IAdapterCallback<Data> {
    private final List<Data> mDataList  ;
    private AdapterListener mListener = null;

    /**
     * 构造函数模块
     */
    public RecyclerAdapter() {
        this(null);
    }
    public RecyclerAdapter( AdapterListener<Data> listener) {
      this(new ArrayList<Data>(),listener);
    }

    public RecyclerAdapter(List<Data> dataList, AdapterListener<Data> listener) {
        this.mListener = listener;
        this.mDataList = dataList;
    }


    /**
     * 复写默认布局类型返回
     *
     * @param position 坐标
     * @return ResId
     */
    @Override
    public int getItemViewType(int position) {
        return getItemViewType(position, mDataList.get(position));
    }

    /**
     * @param position
     * @param data
     * @return ResId, 用于创建ViewHolder
     */
    @LayoutRes
    protected abstract int getItemViewType(int position, Data data);

    /**
     * 创建viewholder
     *
     * @param parent   recyclerview
     * @param viewType 界面的类型 约定为xml的id
     * @return 返回viewholder
     */
    @NonNull
    @Override
    public ViewHolder<Data> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 把xml的布局变成view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // 把xml id 为viewtype的文件初始化为一个root view
        View root = inflater.inflate(viewType, parent, false);
        // 通过子类必须实现的方法得到viewholder
        ViewHolder<Data> holder = onCreatedViewHolder(root, viewType);
        // 使用设置id的方法，避免冲突
        root.setTag(R.id.tag_recycler_holder, holder);

        // 设置事件点击
        root.setOnClickListener(this);
        root.setOnLongClickListener(this);

        // 界面数据绑定
        holder.unbinder = ButterKnife.bind(holder, root);
        //绑定callback
        holder.mCallback = this;
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder<Data> holder, int position) {
        // 得到需要的数据
        Data data = mDataList.get(position);
        // 绑定数据
        holder.bind(data);
    }

    /**
     * 得到一个新的viewholder
     *
     * @param root     根布局
     * @param viewType 布局的类型
     * @return
     */
    protected abstract ViewHolder<Data> onCreatedViewHolder(View root, int viewType);

    /**
     * @return
     */
    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    /**
     * 插入一条数据
     *
     * @param data
     */
    public void add(Data data) {
        mDataList.add(data);
        // 只跟新当前
        notifyItemInserted(mDataList.size() - 1);
    }

    /**
     * 插入多条数据
     *
     * @param dataList
     */
    public void add(Data... dataList) {
        if (dataList != null && dataList.length > 0) {
            int startPos = mDataList.size();
            Collections.addAll(mDataList, dataList);
            notifyItemRangeChanged(startPos, dataList.length);
        }
    }

    /**
     * 插入数据
     *
     * @param dataList
     */
    public void add(Collection<Data> dataList) {
        if (dataList != null && dataList.size() > 0) {
            int startPos = mDataList.size();
            mDataList.addAll(dataList);
            notifyItemRangeChanged(startPos, dataList.size());
        }
    }

    /**
     * 删除 全部更新
     */
    public void clear() {
        mDataList.clear();
        notifyDataSetChanged();
    }

    /**
     * 将数据进行更替，其中包括清空
     *
     * @param dataList
     */
    public void replace(Collection<Data> dataList) {
        mDataList.clear();
        if (mDataList == null || mDataList.size() == 0) {
            mDataList.addAll(dataList);
            notifyDataSetChanged();
        }
    }
    @Override
    public void onClick(View v) {
        ViewHolder viewHolder = (ViewHolder) v.getTag(R.id.tag_recycler_holder);
        if (this.mListener != null) {
            int pos = viewHolder.getAdapterPosition();
            this.mListener.onItemClick(viewHolder, mDataList.get(pos));
        }
    }

    @Override
    public boolean onLongClick(View v) {
        ViewHolder viewHolder = (ViewHolder) v.getTag(R.id.tag_recycler_holder);
        if (this.mListener != null) {
            int pos = viewHolder.getAdapterPosition();
            this.mListener.onItemLongClick(viewHolder, mDataList.get(pos));
            return true;
        }
        return false;
    }
    /**
     * 设置适配器的监听
     *
     * @param adapterListener
     */
    public void setListener(AdapterListener<Data> adapterListener) {
        this.mListener = adapterListener;
    }

    /**
     * 自定义监听器
     *
     * @param <Data>
     */
    public interface AdapterListener<Data> {
        void onItemClick(RecyclerAdapter.ViewHolder holder, Data data);

        void onItemLongClick(RecyclerAdapter.ViewHolder holder, Data data);
    }


    /**
     * 自定义的viewholder
     *
     * @param <Data>泛型数据类型
     */
    public static abstract class ViewHolder<Data> extends RecyclerView.ViewHolder {
        protected Data mData;
        private IAdapterCallback<Data> mCallback = null;
        private Unbinder unbinder = null;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        /**
         * 用于绑定数据的触发
         *
         * @param data 绑定的数据
         */
        void bind(Data data) {
            this.mData = data;
            onBind(data);
        }

        /**
         * 当触发绑定数据的时候的回调；必须复写
         *
         * @param data 绑定的数据
         */
        protected abstract void onBind(Data data);

        /**
         * holder自己对自己对应的data进行更新
         *
         * @param data
         */
        public void updateData(Data data) {
            if (this.mCallback != null) {
                this.mCallback.update(data, this);
            }
        }
    }



}
