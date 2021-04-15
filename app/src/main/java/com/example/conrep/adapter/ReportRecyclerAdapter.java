package com.example.conrep.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.conrep.R;
import com.example.conrep.database.entity.ReportEntity;
import com.example.conrep.ui.util.RecyclerViewItemClickListener;

import java.util.List;
import java.util.Objects;

public class ReportRecyclerAdapter<T> extends RecyclerView.Adapter<ReportRecyclerAdapter.ViewHolder> {

    private List<ReportEntity> mData;
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

    public ReportRecyclerAdapter(RecyclerViewItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public ReportRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
    public void onBindViewHolder(ReportRecyclerAdapter.ViewHolder holder, int position) {
        ReportEntity item = mData.get(position);
        holder.mTextView.setText(((ReportEntity) item).getReportID() + "               " + ((ReportEntity) item).getSiteReport() + "               " + ((ReportEntity) item).getWorkerName());
    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        } else {
            return 0;
        }
    }

    public void setData(final List<ReportEntity> data) {
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
                        return ((ReportEntity) mData.get(oldItemPosition)).getReportID() == (((ReportEntity) data.get(newItemPosition)).getReportID());
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {

                    if (newItemPosition < mData.size() && newItemPosition < data.size()) {
                        ReportEntity newReport = (ReportEntity) data.get(newItemPosition);
                        ReportEntity oldReport = (ReportEntity) mData.get(newItemPosition);
                        return newReport.getReportID() == (oldReport.getReportID())
                                && Objects.equals(newReport.getWorkerName(), oldReport.getWorkerName())
                                && Objects.equals(newReport.getReportID(), oldReport.getReportID())
                                && newReport.getSiteReport() == oldReport.getSiteReport();
                    }
                    return false;
                }

            });
            mData = data;
            result.dispatchUpdatesTo(this);
        }
    }
}
