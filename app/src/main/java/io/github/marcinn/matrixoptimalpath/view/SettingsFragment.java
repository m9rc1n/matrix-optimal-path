package io.github.marcinn.matrixoptimalpath.view;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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
import io.github.marcinn.matrixoptimalpath.model.SettingsData;

public class SettingsFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private DiscreteSeekBar mSeekBar;
    private EditText mEditText;
    private ImageButton mGenerateTextButton;
    private ImageButton mPasteButton;
    private ImageButton mClearButton;

    public SettingsFragment() {
    }

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        final View v = inflater.inflate(R.layout.fragment_settings, container, false);
        mSeekBar = ((DiscreteSeekBar) v.findViewById(R.id.seekBar_columnsNumber));
        mEditText = (EditText) v.findViewById(R.id.editText_inputTable);
        mGenerateTextButton = (ImageButton) v.findViewById(R.id.imageButton_generateText);
        mPasteButton = (ImageButton) v.findViewById(R.id.imageButton_pasteText);
        mClearButton = (ImageButton) v.findViewById(R.id.imageButton_clearText);

        mGenerateTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditText.append(getResources().getString(R.string.lorem_ipsum_1000));
            }
        });

        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditText.setText("");
                mListener.onResetMatrix();
            }
        });

        mPasteButton.setOnClickListener(new View.OnClickListener() {
            public static final String MIME_TYPE_TEXT_PLAIN = "text/plain";

            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(
                        Context.CLIPBOARD_SERVICE);
                if (!(clipboard.hasPrimaryClip())) {
                    if (getView() != null) {
                        Snackbar.make(getView(), R.string.clipboard_is_empty, Snackbar.LENGTH_SHORT)
                                .show();
                    }
                } else if (!(clipboard.getPrimaryClipDescription()
                        .hasMimeType(MIME_TYPE_TEXT_PLAIN))) {
                    if (getView() != null) {
                        Snackbar.make(getView(),
                                R.string.make_sure_that_you_are_pasting_text,
                                Snackbar.LENGTH_SHORT).show();
                    }
                } else {
                    ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
                    mEditText.append(" " + item.getText());
                }
            }
        });

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mListener.onSettingsChanged(new SettingsData(s.toString(), mSeekBar.getProgress()));
            }
        });

        mSeekBar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                mListener.onSettingsChanged(new SettingsData(mEditText.getText().toString(),
                        value));
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

    public void setInputEnabled(boolean inputEnabled) {
        mGenerateTextButton.setEnabled(inputEnabled);
        mPasteButton.setEnabled(inputEnabled);
        mClearButton.setEnabled(inputEnabled);
    }

    public interface OnFragmentInteractionListener {
        void onSettingsChanged(SettingsData data);

        void onResetMatrix();
    }
}
