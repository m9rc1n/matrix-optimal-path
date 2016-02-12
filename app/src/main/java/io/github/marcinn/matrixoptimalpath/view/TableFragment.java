package io.github.marcinn.matrixoptimalpath.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.github.marcinn.matrixoptimalpath.R;
import io.github.marcinn.matrixoptimalpath.model.WordCell;

public class TableFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;
    private TableAdapter mAdapter;
    private OnFragmentInteractionListener mListener;
    private TextView mTextView;

    public TableFragment() {
    }

    public static TableFragment newInstance() {
        return new TableFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        View view = inflater.inflate(R.layout.fragment_table, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        mTextView = (TextView) view.findViewById(R.id.textViewStatistics);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(getContext(), 1);
        mLayoutManager.setSmoothScrollbarEnabled(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new TableAdapter(new WordCell[0]);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void updateAdapter(WordCell[] data, SparseBooleanArray path, int columnNumber) {
        mAdapter.setDataset(data, path);
        mLayoutManager.setSpanCount(columnNumber);
        mTextView.setText(String.format("Words in matrix: %d \nCost of optimal path: %d",
                data.length,
                data[data.length - 1].getCost()));

    }

    public void moveListToPosition(int action) {
        if (action == R.id.action_up_list) {
            mLayoutManager.scrollToPosition(0);
        } else if (action == R.id.action_down_list) {
            mLayoutManager.scrollToPositionWithOffset(mAdapter.getItemCount() - 1, 100);
        }
    }

    public interface OnFragmentInteractionListener {
    }
}
