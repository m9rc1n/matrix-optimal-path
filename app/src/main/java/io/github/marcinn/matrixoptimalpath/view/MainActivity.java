package io.github.marcinn.matrixoptimalpath.view;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

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
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_top, R.anim.slide_out_bottom)
                        .show(mPreferenceFragment)
                        .commit();
                item.setIcon(R.drawable.ic_grid_on_white_48dp);
                mToolbar.setTitle("Preferences");
            } else {
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top)
                        .hide(mPreferenceFragment)
                        .commit();
                item.setIcon(R.drawable.ic_settings_white_48dp);
                mToolbar.setTitle(R.string.app_name);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPreferenceChanged(String text, int columnNumber) {
        String[] words = text.toLowerCase().split("\\s+");
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
