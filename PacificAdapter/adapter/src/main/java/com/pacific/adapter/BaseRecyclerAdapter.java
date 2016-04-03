package com.pacific.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

abstract class BaseRecyclerAdapter<T, H extends RecyclerAdapterHelper> extends RecyclerView.Adapter<ViewHolder> implements DataIO<T> {

    protected final Context context;
    protected final LayoutInflater layoutInflater;
    protected final int layoutResId;
    protected final ArrayList<T> data;

    public BaseRecyclerAdapter(Context context, int layoutResId) {
        this(context, layoutResId, null);
    }

    public BaseRecyclerAdapter(Context context, int layoutResId, List<T> data) {
        this.context = context;
        this.layoutResId = layoutResId;
        this.layoutInflater = LayoutInflater.from(context);
        this.data = data == null ? new ArrayList<T>() : new ArrayList<>(data);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(layoutInflater.inflate(layoutResId, parent, false)) {
        };
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        H helper = getAdapterHelper(holder);
        T item = get(position);
        convert(helper, item);
    }

    @Override
    public int getItemCount() {
        return getSize();
    }

    @Override
    public void add(T elem) {
        data.add(elem);
        notifyDataSetChanged();
    }

    @Override
    public void addAt(int location, T elem) {
        data.add(location, elem);
        notifyDataSetChanged();
    }

    @Override
    public void addAll(List<T> elements) {
        data.addAll(elements);
        notifyDataSetChanged();
    }

    @Override
    public void addAllAt(int location, List<T> elements) {
        data.addAll(location, elements);
        notifyDataSetChanged();
    }

    @Override
    public void remove(T elem) {
        data.remove(elem);
        notifyDataSetChanged();
    }

    @Override
    public void removeAt(int index) {
        data.remove(index);
        notifyDataSetChanged();
    }

    @Override
    public void removeAll(List<T> elements) {
        data.removeAll(elements);
        notifyDataSetChanged();
    }

    @Override
    public void clear() {
        if (data != null && data.size() > 0) {
            data.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public void replace(T oldElem, T newElem) {
        replaceAt(data.indexOf(oldElem), newElem);
    }

    @Override
    public void replaceAt(int index, T elem) {
        data.set(index, elem);
        notifyDataSetChanged();
    }

    @Override
    public void replaceAll(List<T> elements) {
        if (data.size() > 0) {
            data.clear();
        }
        data.addAll(elements);
        notifyDataSetChanged();
    }

    @Override
    public T get(int position) {
        if (position >= data.size())
            return null;
        return data.get(position);
    }

    @Override
    public ArrayList<T> getAll() {
        return data;
    }

    @Override
    public int getSize() {
        return data.size();
    }

    @Override
    public boolean contains(T elem) {
        return data.contains(elem);
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    protected abstract void convert(H helper, T item);


    /**
     * You can override this method to use a custom RecycleAdapterHelper in order to fit your needs
     *
     * @param viewHolder The viewHolder that this view will eventually be attached to
     * @return An instance of RecycleAdapterHelper
     */
    protected abstract H getAdapterHelper(ViewHolder viewHolder);
}
