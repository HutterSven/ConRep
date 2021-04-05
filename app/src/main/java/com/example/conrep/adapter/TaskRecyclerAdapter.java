package com.example.conrep.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.conrep.R;
import com.example.conrep.database.task.TaskEntity;
import com.example.conrep.ui.util.RecyclerViewItemClickListener;

import java.util.List;
import java.util.Objects;

public class TaskRecyclerAdapter<T> extends RecyclerView.Adapter<TaskRecyclerAdapter.ViewHolder> {

    private List<TaskEntity> mData;
    private RecyclerViewItemClickListener mListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView mTextView;
        ViewHolder(TextView textView) {
            super(textView);
            mTextView = textView;
        }
    }

    public TaskRecyclerAdapter(RecyclerViewItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public TaskRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view, parent, false);
        final ViewHolder viewHolder = new ViewHolder(v);
        v.setOnClickListener(view -> mListener.onItemClick(view, viewHolder.getAdapterPosition()));
        v.setOnLongClickListener(view -> {
            mListener.onItemLongClick(view, viewHolder.getAdapterPosition());
            return true;
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TaskRecyclerAdapter.ViewHolder holder, int position) {
        TaskEntity item = mData.get(position);
        String open;
        if (!item.isStatus()) {
            open = "closed";
        }
        else open = "open";
        holder.mTextView.setText(((TaskEntity) item).getTaskID() + "        " + ((TaskEntity) item).getSiteTask() + "        " + ((TaskEntity) item).getName()+ "        " + open);
    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        } else {
            return 0;
        }
    }

    public void setData(final List<TaskEntity> data) {
        if (mData == null) {
            mData = data;
            notifyItemRangeInserted(0, data.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mData.size();
                }

                @Override
                public int getNewListSize() {
                    return data.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                        return ((TaskEntity) mData.get(oldItemPosition)).getTaskID() == (((TaskEntity) data.get(newItemPosition)).getTaskID());
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {

                    if (newItemPosition < mData.size() && newItemPosition < data.size()) {
                        TaskEntity newTask = (TaskEntity) data.get(newItemPosition);
                        TaskEntity oldTask = (TaskEntity) mData.get(newItemPosition);
                        return newTask.getTaskID() == (oldTask.getTaskID())
                                && Objects.equals(newTask.getName(), oldTask.getName())
                                && Objects.equals(newTask.getTaskID(), oldTask.getTaskID())
                                && newTask.getSiteTask() == oldTask.getSiteTask();
                    }
                    return false;
                }

            });
            mData = data;
            result.dispatchUpdatesTo(this);
        }
    }
}
