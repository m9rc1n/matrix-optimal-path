package io.github.marcinn.matrixoptimalpath.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import io.github.marcinn.matrixoptimalpath.R;
import io.github.marcinn.matrixoptimalpath.model.CalculationResult;

public class MatrixAdapter extends RecyclerView.Adapter<MatrixAdapter.ViewHolder> {

    private CalculationResult mCalculationResult;

    public MatrixAdapter() {
    }

    public void setCalculationResult(CalculationResult result) {
        this.mCalculationResult = result;
        notifyDataSetChanged();
    }

    @Override
    public MatrixAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.text.setText(mCalculationResult.getWords()[position].toString());
        holder.layout.setSelected(mCalculationResult.getPath().get(position, false));
    }

    @Override
    public int getItemCount() {
        return mCalculationResult == null ? 0 : mCalculationResult.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView text;
        public FrameLayout layout;

        public ViewHolder(View v) {
            super(v);
            text = (TextView) v.findViewById(R.id.textView_cellText);
            layout = (FrameLayout) v.findViewById(R.id.cell);
        }
    }
}