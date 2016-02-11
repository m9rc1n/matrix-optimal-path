package io.github.marcinn.matrixoptimalpath.view;

import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import io.github.marcinn.matrixoptimalpath.R;
import io.github.marcinn.matrixoptimalpath.model.WordCell;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.ViewHolder> {

    private WordCell[] mDataset;
    private SparseBooleanArray mPath;

    public TableAdapter(WordCell[] myDataset) {
        mDataset = myDataset;
    }

    public void setDataset(WordCell[] mDataset, SparseBooleanArray mPath) {
        this.mDataset = mDataset;
        this.mPath = mPath;
        notifyDataSetChanged();
    }

    @Override
    public TableAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        WordCell wordCell = mDataset[position];
        holder.mTextView.setText(wordCell.getText());
        holder.cost.setText(String.format("%d", wordCell.getCost()));
        if (wordCell.getCost() == Integer.MAX_VALUE) holder.cost.setText("\u221E");
        holder.layout.setSelected(mPath.get(position, false));
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public TextView cost;
        public FrameLayout layout;

        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.text);
            cost = (TextView) v.findViewById(R.id.cost);
            layout = (FrameLayout) v.findViewById(R.id.cell);
        }
    }
}