package com.example.conrep.ui.site;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.example.conrep.R;
import com.example.conrep.database.repository.ConstructionSiteRepository;
import com.example.conrep.database.site.ConstructionSiteEntity;
import com.example.conrep.ui.BaseActivity;
import com.example.conrep.ui.viewmodel.constructionSite.ConstructionSiteListViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class Map extends BaseActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<ConstructionSiteEntity> sites;
    private Bundle savedInstanceState;

    ConstructionSiteRepository repo;
    ConstructionSiteListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        this.savedInstanceState = savedInstanceState;
        getLayoutInflater().inflate(R.layout.activity_construction_site_list, frameLayout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ConstructionSiteListViewModel.Factory factory = new ConstructionSiteListViewModel.Factory(getApplication(), 0);
        viewModel = ViewModelProviders.of(this, factory).get(ConstructionSiteListViewModel.class);
        viewModel.getSites().observe(this, ConstructionSiteEntities -> {
            if (ConstructionSiteEntities != null) {
                sites = ConstructionSiteEntities;
            }
        });

        System.out.println(sites.get(0).getAddress());


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        ArrayList<LatLng> address = new ArrayList<LatLng>();

        for (ConstructionSiteEntity site : sites) {
            address.add(getLocationFromAddress(this, site.getAddress() + " " + site.getCity()));
            mMap.addMarker(new MarkerOptions().position(address.get(address.size() - 1)).title(site.getSiteName()));
            System.out.println(site.getAddress() + site.getCity());
        }

        if (address.size() > 0)
            mMap.moveCamera(CameraUpdateFactory.newLatLng(address.get(address.size() - 1)));
    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {
        Geocoder coder = new Geocoder(context);

        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p1;

    }
}