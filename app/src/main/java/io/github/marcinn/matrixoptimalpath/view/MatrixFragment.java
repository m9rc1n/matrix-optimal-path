package io.github.marcinn.matrixoptimalpath.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.github.marcinn.matrixoptimalpath.R;
import io.github.marcinn.matrixoptimalpath.model.CalculationResult;

public class MatrixFragment extends Fragment {
    private GridLayoutManager mLayoutManager;
    private MatrixAdapter mAdapter;
    private OnFragmentInteractionListener mListener;
    private TextView mTextView;

    public MatrixFragment() {
    }

    public static MatrixFragment newInstance() {
        return new MatrixFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        View view = inflater.inflate(R.layout.fragment_matrix, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_matrix);
        mTextView = (TextView) view.findViewById(R.id.textView_statistics);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(getContext(), 1);
        mLayoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        mAdapter = new MatrixAdapter();
        recyclerView.setAdapter(mAdapter);
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

    public void updateAdapter(CalculationResult result) {
        mAdapter.setCalculationResult(result);
        mLayoutManager.setSpanCount(result.getColumnNumber());
        mTextView.setText(String.format(getResources().getString(R.string.words_in_matrix_cost_of_optimal_path),
                result.size(),
                result.getCostOfOptimalPath()));
    }

    public void moveListToPosition(int action) {
        if (action == R.id.action_matrix_to_top) {
            mLayoutManager.scrollToPosition(0);
        } else if (action == R.id.action_matrix_to_bottom) {
            int offset = 100;
            int lastItemPosition = mAdapter.getItemCount() - 1;
            mLayoutManager.scrollToPositionWithOffset(lastItemPosition, offset);
        }
    }

    public void resetView() {
        mAdapter.setCalculationResult(null);
        mTextView.setText(getResources().getText(R.string.you_can_add_new_words_in_settings));
    }

    public interface OnFragmentInteractionListener {
    }
}
