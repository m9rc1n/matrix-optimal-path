package io.github.marcinn.matrixoptimalpath.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import io.github.marcinn.matrixoptimalpath.R;

public class MainActivity extends AppCompatActivity
        implements TableFragment.OnFragmentInteractionListener {

    private TableFragment mTableFragment;
    private Toolbar mToolbar;
    private View mRootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mRootView = findViewById(R.id.rootView);
        mTableFragment = TableFragment.newInstance();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.content_main, mTableFragment)
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
