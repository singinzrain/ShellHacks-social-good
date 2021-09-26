package com.shell.hacks.ui.task;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.shell.hacks.R;
import com.shell.hacks.ui.help.TaskDetail;

import java.util.LinkedList;
import java.util.List;

public class TaskFragment extends Fragment implements OnMapReadyCallback {

    private TaskViewModel dashboardViewModel;
    private RecyclerView.LayoutManager rLayoutManger;
    private RecyclerView recyclerView;
    private RviewAdapter rviewAdapter;
    private View rootView;
    private FusedLocationProviderClient fusedLocationProviderClient;

    private List<TaskDetail> itemList = new LinkedList<>();

    private GoogleMap mMap;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(TaskViewModel.class);
        rootView = inflater.inflate(R.layout.fragment_task, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        // get sender token
        databaseReference.child("task").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    // Check the existence of the receiver
                    String value = snapshot1.getValue(String.class);
                    TaskDetail taskDetail = new Gson().fromJson(value, TaskDetail.class);
                    itemList.add(taskDetail);
                }
                createRecycler();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        return rootView;
    }

    private void createRecycler() {
        rLayoutManger = new LinearLayoutManager(getContext());
        recyclerView = rootView.findViewById(R.id.task_recycler);
        recyclerView.setHasFixedSize(false);

        System.out.println("~~~~~~");
        System.out.println(itemList);
        rviewAdapter = new RviewAdapter(itemList);
        ItemClickListener itemClickListener = new ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // update task status
            }
        };
        rviewAdapter.setOnItemClickListener(itemClickListener);
        recyclerView.setAdapter(rviewAdapter);
        recyclerView.setLayoutManager(rLayoutManger);
        System.out.println("created recycler============");
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera

        LocationRequest locationRequest = LocationRequest.create();
        // location update interval: 0.3 second
        locationRequest.setInterval(300000);
        // fast location update interval: 0.01 second
        locationRequest.setFastestInterval(100);
        // request High accuracy location based on the need of this app
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                System.out.println(locationResult);
                Location lastLocation = locationResult.getLastLocation();
                LatLng myLocation = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
                mMap.addMarker(new MarkerOptions()
                        .position(myLocation)
                        .title("My location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15));
            }
        }, null);
    }
}