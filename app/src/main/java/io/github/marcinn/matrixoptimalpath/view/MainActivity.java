package io.github.marcinn.matrixoptimalpath.view;

import android.content.Context;
import android.os.Bundle;
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
import io.github.marcinn.matrixoptimalpath.model.WordCell;

public class MainActivity extends AppCompatActivity
        implements PreferenceFragment.OnFragmentInteractionListener,
        TableFragment.OnFragmentInteractionListener {

    private TableFragment mTableFragment;
    private Toolbar mToolbar;
    private View mRootView;
    private PreferenceFragment mPreferenceFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mRootView = findViewById(R.id.rootView);
        mTableFragment = TableFragment.newInstance();
        mPreferenceFragment = PreferenceFragment.newInstance();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.content_main, mTableFragment)
                .commit();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.content_main, mPreferenceFragment)
                .hide(mPreferenceFragment)
                .commit();

        setSupportActionBar(mToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_preference) {
            item.setChecked(!item.isChecked());
            if (item.isChecked()) {
                hidePreferenceFragment();
                item.setIcon(R.drawable.ic_grid_on_white_48dp);
                mToolbar.setTitle("Preferences");
            } else {
                showPreferenceFragment();
                item.setIcon(R.drawable.ic_settings_white_48dp);
                mToolbar.setTitle(R.string.app_name);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void hidePreferenceFragment() {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_top, R.anim.slide_out_bottom)
                .show(mPreferenceFragment)
                .commit();
        hideKeyboard();
    }

    private void showPreferenceFragment() {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top)
                .hide(mPreferenceFragment)
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
    public void onPreferenceChanged(String text, int columnNumber) {
        if (text.equals("")) {
            Snackbar.make(mRootView, "No words to display", Snackbar.LENGTH_SHORT).show();
            return;
        }
        String[] words = text.toLowerCase().split("\\s+");
        if (columnNumber > words.length) {
            Snackbar.make(mRootView,
                    "Nr of columns should not be bigger than words number",
                    Snackbar.LENGTH_SHORT).show();
            return;
        }
        Cell[] cells = new MatrixHelper().generateMatrixFromString(words, columnNumber);
        cells = new MatrixOptimalPath(new Dijkstra()).execute(cells);
        WordCell[] resultCells = new WordCell[cells.length];
        for (Cell c : cells) {
            resultCells[c.getIndex()] = new WordCell(words[c.getIndex()], c.getCost());
        }
        if (resultCells.length > 0) {
            SparseBooleanArray path = new SparseBooleanArray();
            Cell cell = cells[cells.length - 1];
            path.append(cell.getIndex(), true);
            while (cell.getIndex() != 0) {
                cell = cell.getPrevious();
                path.append(cell.getIndex(), true);
            }
            mTableFragment.updateAdapter(resultCells, path, columnNumber);
        } else {
            Snackbar.make(mRootView, "No words to display", Snackbar.LENGTH_SHORT).show();
        }
    }
}
