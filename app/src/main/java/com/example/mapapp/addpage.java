package com.example.mapapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.Place.Field;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class addpage extends AppCompatActivity {
    private Button saveBtn,showOnMap,getLocation;
    private EditText location,newplace;
    public List<Place.Field> list;
    public static ArrayList<LatLng> arrayList = new ArrayList<LatLng>();
    public static ArrayList<String> nameLs = new ArrayList<String>();
    public static LatLng latLng;
    public static String locName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpage);
        location = findViewById(R.id.location);
        newplace = findViewById(R.id.newPlace);
        showOnMap = findViewById(R.id.showOnMap);
        saveBtn = findViewById(R.id.saveBtn);
        getLocation = findViewById(R.id.getClocation);

        Places.initialize(getApplicationContext(),"AIzaSyC4cAXAxU6pk1DKCowwo_Yjk3QskSF1SlI");
        getLocation.setFocusable(false);
        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list = Arrays.asList(Place.Field.ADDRESS, Field.LAT_LNG, Field.NAME);
                Intent i = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,list).build(addpage.this);
                startActivityForResult(i,100);

            }
        });
        showOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(addpage.this,MapsActivity2.class);
                startActivity(i);
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                arrayList.add(latLng);

                nameLs.add(newplace.getText().toString());

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == RESULT_OK){
            Place place = Autocomplete.getPlaceFromIntent(data);

            location.setText(place.getAddress());
            newplace.setText(String.format(place.getName()));
            locName = place.getName();
            latLng = place.getLatLng();




        }else if(resultCode == AutocompleteActivity.RESULT_ERROR)
        {
            Status status = Autocomplete.getStatusFromIntent(data);
            Toast.makeText(getApplicationContext(),status.getStatusMessage(), Toast.LENGTH_LONG).show();
        }
    }



}