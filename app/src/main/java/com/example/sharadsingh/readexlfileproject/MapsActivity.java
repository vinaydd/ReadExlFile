package com.example.sharadsingh.readexlfileproject;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;



    AssetManager assetManager;
    ArrayList<Double> latt  = new ArrayList<>();
    ArrayList<Double> logg  = new ArrayList<>();

    ArrayList<LatLng> latLngs  = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        assetManager = getAssets();


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }



    public void readExcelFileFromAssets() {

        try {
            InputStream myInput;
            myInput = assetManager.open("1001226_03.xls");
            POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);
            HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);
            HSSFSheet mySheet = myWorkBook.getSheetAt(0);
            Iterator<Row> rowIter = mySheet.rowIterator();
            while (rowIter.hasNext()) {
                HSSFRow myRow = (HSSFRow) rowIter.next();
                Iterator<Cell> cellIter = myRow.cellIterator();
                while (cellIter.hasNext()) {
                    HSSFCell myCell = (HSSFCell) cellIter.next();
                    if(myCell.getColumnIndex()==0){
                        String lat  = myCell.toString();
                        latt.add(Double.parseDouble(lat));
                    }else {
                        String laongi  = myCell.toString();
                        logg.add(Double.parseDouble(laongi));
                    }
                    Log.e("FileUtils", "Cell Value: " + myCell.toString()+ " Index :" +myCell.getColumnIndex());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }


    public void readXLSXFileFromAssets() {
        Log.e("latListSize", String.valueOf(latt.size()));
        Log.e("longListSize", String.valueOf(logg.size()));
        for(int i=0;i<latt.size();i++){
            LatLng latLng  = new LatLng(latt.get(i),logg.get(i));
            latLngs.add(latLng);

        }




     PolylineOptions options = new PolylineOptions().width(5).color(Color.RED).geodesic(true);
        for (int z = 0; z < latLngs.size(); z++) {
            LatLng point = latLngs.get(z);
            options.add(point);
        }
        mMap.addPolyline(options);


        return;
    }




    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
       // LatLng sydney = new LatLng(-34, 151);
     //   mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
       // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        readExcelFileFromAssets();

        readXLSXFileFromAssets();

    }
}
