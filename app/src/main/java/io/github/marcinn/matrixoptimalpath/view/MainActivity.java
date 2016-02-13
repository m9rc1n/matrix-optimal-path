package io.github.marcinn.matrixoptimalpath.view;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import io.github.marcinn.matrixoptimalpath.R;
import io.github.marcinn.matrixoptimalpath.lib.model.Cell;
import io.github.marcinn.matrixoptimalpath.lib.strategy.Dijkstra;
import io.github.marcinn.matrixoptimalpath.lib.strategy.MatrixOptimalPath;
import io.github.marcinn.matrixoptimalpath.lib.util.MatrixHelper;
import io.github.marcinn.matrixoptimalpath.model.CalculationResult;
import io.github.marcinn.matrixoptimalpath.model.CalculationResultError;
import io.github.marcinn.matrixoptimalpath.model.MatrixCell;
import io.github.marcinn.matrixoptimalpath.model.SettingsData;

public class MainActivity extends AppCompatActivity
        implements SettingsFragment.OnFragmentInteractionListener,
        MatrixFragment.OnFragmentInteractionListener {

    private static final String STRING_EMPTY = "";
    private MatrixFragment mMatrixFragment;
    private View mRootView;
    private SettingsFragment mSettingsFragment;
    private MenuItem mMenuActionSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(STRING_EMPTY);
        toolbar.setLogo(R.drawable.logo);
        mRootView = findViewById(R.id.rootView);
        mMatrixFragment = MatrixFragment.newInstance();
        mSettingsFragment = SettingsFragment.newInstance();

        getSupportFragmentManager().beginTransaction().add(R.id.content_main, mMatrixFragment)
                .commit();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.content_main, mSettingsFragment)
                .hide(mSettingsFragment)
                .commit();

        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mMenuActionSettings = menu.findItem(R.id.action_settings);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                if (!item.isChecked()) {
                    showSettingsFragment();
                } else {
                    hideSettingsFragment();
                }
                return true;
            case R.id.action_matrix_to_top:
            case R.id.action_matrix_to_bottom:
                mMatrixFragment.moveListToPosition(id);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mSettingsFragment.isVisible()) {
            hideSettingsFragment();
        } else {
            super.onBackPressed();
        }
    }

    private void hideSettingsFragment() {
        mMenuActionSettings.setChecked(!mMenuActionSettings.isChecked());
        mMenuActionSettings.setIcon(R.drawable.ic_settings_white_48dp);
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_top, R.anim.slide_out_bottom)
                .hide(mSettingsFragment)
                .commit();
        hideKeyboard();
    }

    private void showSettingsFragment() {
        mMenuActionSettings.setChecked(!mMenuActionSettings.isChecked());
        mMenuActionSettings.setIcon(R.drawable.ic_grid_on_white_48dp);
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top)
                .show(mSettingsFragment)
                .commit();
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onSettingsChanged(SettingsData data) {
        if (data.getText() == null || data.getText().equals(STRING_EMPTY)) {
            Snackbar.make(mRootView, R.string.no_words_to_display, Snackbar.LENGTH_SHORT).show();
            return;
        }
        new PathCalculationAsyncTask().execute(data);
    }

    private class PathCalculationAsyncTask
            extends AsyncTask<SettingsData, Void, CalculationResult> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mSettingsFragment.setInputEnabled(false);
        }

        protected CalculationResult doInBackground(SettingsData... params) {
            SettingsData data = params[0];
            // Split text by spaces and filter non-ASCII and non letters characters
            String[] words = data.getText()
                    .replaceAll("[^a-zA-Z ]", STRING_EMPTY)
                    .toLowerCase()
                    .trim()
                    .split("\\s+");
            if (isFilledWthEmptyStrings(words)) {
                return new CalculationResultError(R.string.please_enter_only_ascii_letters);
            }
            // Prepare data for searching algorithm and execute
            Cell[] cells = new MatrixHelper().generateMatrixFromString(words,
                    data.getColumnNumber());
            cells = new MatrixOptimalPath(new Dijkstra()).execute(cells);

            // Extract result data
            MatrixCell[] resultCells = new MatrixCell[cells.length];
            for (Cell c : cells) {
                resultCells[c.getIndex()] = new MatrixCell(words[c.getIndex()], c.getCost());
            }
            if (resultCells.length > 0) {
                SparseBooleanArray path = extractPathFromData(cells[cells.length - 1]);
                return new CalculationResult(data.getColumnNumber(), resultCells, path);
            } else {
                return new CalculationResultError(R.string.no_words_to_display);
            }
        }

        @NonNull
        private SparseBooleanArray extractPathFromData(Cell cell) {
            SparseBooleanArray path = new SparseBooleanArray();
            path.append(cell.getIndex(), true);
            while (cell.getIndex() != 0) {
                cell = cell.getPrevious();
                path.append(cell.getIndex(), true);
            }
            return path;
        }

        private boolean isFilledWthEmptyStrings(String[] words) {
            return words.length == 0 || (words.length == 1 && (words[0] == null || words[0].equals(
                    STRING_EMPTY)));
        }

        protected void onPostExecute(CalculationResult result) {
            if (result instanceof CalculationResultError) {
                int resId = ((CalculationResultError) result).getMessageResId();
                Snackbar.make(mRootView, resId, Snackbar.LENGTH_SHORT).show();
            } else {
                mMatrixFragment.updateAdapter(result);
            }
            mSettingsFragment.setInputEnabled(true);
        }
    }
}
