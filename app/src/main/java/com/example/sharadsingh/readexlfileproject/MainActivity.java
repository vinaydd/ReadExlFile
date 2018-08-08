package com.example.sharadsingh.readexlfileproject;

import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    AssetManager assetManager;
    ArrayList<Double> latt  = new ArrayList<>();
    ArrayList<Double> logg  = new ArrayList<>();

    ArrayList<LatLng> latLngs  = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        assetManager = getAssets();

        readExcelFileFromAssets();

        readXLSXFileFromAssets();

        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(MainActivity.this,MapsActivity.class);
                intent.putExtra("data",latLngs);
                startActivity(intent);




            }
        });
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



       /* try {
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
                    Log.e("FileUtils", "Cell Value: " + myCell.toString()+ " Index :" +myCell.getColumnIndex());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        return;
    }


}
