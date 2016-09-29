package edu.uoc.monuments.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

import edu.uoc.library.LibraryManager;
import edu.uoc.library.model.Monument;
import edu.uoc.library.utils.LibraryConstants;
import edu.uoc.monuments.R;
import edu.uoc.monuments.ui.adapters.MonumentAdapter;
import edu.uoc.monuments.utils.ApplicationUtils;

/**
 * Created by UOC on 28/09/2016.
 */
public class ListMonumentActivity extends AppCompatActivity {

    // Views
    private ListView mListView;
    private ProgressBar mProgressBar;

    // List items
    private MonumentAdapter monumentAdapter;
    private ArrayList<Monument> monumentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_monuments);

        // Set views
        mListView = (ListView) findViewById(R.id.listView);
        mProgressBar = (ProgressBar) findViewById(R.id.load_progress);

        // UOC-BEGIN-CODE1
//        LibraryManager.getInstance(getApplicationContext()).getAllMonuments(new GetCallback<List<Monument>>() {
//            @Override
//            public void onSuccess(List<Monument> result) {
//                mProgressBar.setVisibility(View.GONE);
//                Log.d(LibraryConstants.TAG, "Set breakpoint 1");
//                monumentList.addAll(result);
//                monumentAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onFailure(Throwable e) {
//                mProgressBar.setVisibility(View.GONE);
//                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
        // UOC-END-CODE1

        // UOC-BEGIN-CODE2
        monumentList = LibraryManager.getInstance(getApplicationContext()).getAllMonuments();
        Log.d(LibraryConstants.TAG, "Set breakpoint 2");
        mProgressBar.setVisibility(View.GONE);
        // UOC-END-CODE2
        
        monumentAdapter = new MonumentAdapter(this, monumentList);
        mListView.setAdapter(monumentAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            ApplicationUtils.setUserLoginState(getApplicationContext(), false);
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
