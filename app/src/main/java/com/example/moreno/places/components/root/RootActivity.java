package com.example.moreno.places.components.root;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.moreno.places.R;
import com.example.moreno.places.components.details.PlaceDetailsActivity;
import com.example.moreno.places.components.details.PlaceDetailsFragment;
import com.example.moreno.places.components.root.list.PlacesListAdapter;
import com.example.moreno.places.components.root.list.PlacesListView;
import com.example.moreno.places.gson.holder.PlaceDataHolder;

import java.util.List;

/**
 * Created on 09.10.2015.
 */
public class RootActivity extends AppCompatActivity
        implements RootFragment.OnDataReceivedListener {

    private static final String LOG_TAG = "RootActivity";
    private RootFragment mFragment;
    private PlacesListAdapter mPlacesListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.root_layout);
        prepareRootFragment();
        preparePlaceListView();
    }

    private void preparePlaceListView() {
        PlacesListView placesListView = (PlacesListView) findViewById(R.id.places_list);
        placesListView.setLayoutManager(this);
        mPlacesListAdapter = new PlacesListAdapter(this);
        placesListView.setAdapter(mPlacesListAdapter);
    }

    private void prepareRootFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        mFragment = (RootFragment) fragmentManager.findFragmentByTag(RootFragment.TAG);
        if (mFragment == null) {
            mFragment = new RootFragment();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(mFragment, RootFragment.TAG);
            transaction.commit();
        }
    }

    public void openPlaceDetails(String placeId) {
        Intent detailsIntent = new Intent(this, PlaceDetailsActivity.class);
        detailsIntent.putExtra(PlaceDetailsFragment.PLACE_ID_KEY, placeId);
        startActivity(detailsIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.root_action_bar, menu);
        final SearchView searchView = (SearchView) menu.findItem(R.id.search_place).getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.current_location_places:
                mFragment.getNearLocations();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            mFragment.getRequestedLocations(query);
        }
    }

    @Override
    public void onDataReceived(List<PlaceDataHolder> places) {
        mPlacesListAdapter.setPlacesList(places);
        findViewById(R.id.search_tip_text).setVisibility(View.GONE);
    }

}
