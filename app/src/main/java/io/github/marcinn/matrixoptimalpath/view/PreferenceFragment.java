package io.github.marcinn.matrixoptimalpath.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import io.github.marcinn.matrixoptimalpath.R;

public class PreferenceFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public PreferenceFragment() {
    }

    public static PreferenceFragment newInstance() {
        return new PreferenceFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        final View v = inflater.inflate(R.layout.fragment_preference, container, false);
        final DiscreteSeekBar seekBar = ((DiscreteSeekBar) v.findViewById(R.id.seekBar_columnsNumber));
        final EditText editText = (EditText) v.findViewById(R.id.editText_inputTable);
        final ImageButton imageButton = (ImageButton) v.findViewById(R.id.imageButton_appendText);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.append(getResources().getString(R.string.pan_tadeusz));
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mListener.onPreferenceChanged(s.toString(), seekBar.getProgress());
            }
        });

        seekBar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                mListener.onPreferenceChanged(editText.getText().toString(), value);
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {
            }
        });
        return v;
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

    public interface OnFragmentInteractionListener {
        void onPreferenceChanged(String text, int columnNumber);
    }
}
