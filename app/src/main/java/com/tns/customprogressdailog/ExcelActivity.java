package com.tns.customprogressdailog;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ExcelActivity extends Activity implements View.OnClickListener{

    private String inputFile;
    String[] permissions = {
            "android.permission.ACCESS_FINE_LOCATION",
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_SMS",
            "android.permission.RECEIVE_SMS"
    };

    TextView tv_textview;

    public static final int MULTIPLE_PERMISSIONS = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excel);
        if(checkPermissions()){

        }

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);



        Button button_read = (Button) findViewById(R.id.button2);
        button_read.setOnClickListener(this);

        tv_textview =(TextView)findViewById(R.id.tv_textview);


    }

    @Override
    public void onClick(View arg0) {

        if(arg0.getId() == R.id.button) {

            String Fnamexls = "testfile" + ".xls";

            File sdCard = Environment.getExternalStorageDirectory();

            File directory = new File(sdCard.getAbsolutePath() + "/newfolder");
            directory.mkdirs();

            File file = new File(directory, Fnamexls);

            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("en", "EN"));

            WritableWorkbook workbook;
            try {
                int a = 1;
                workbook = Workbook.createWorkbook(file, wbSettings);
                WritableSheet sheet = workbook.createSheet("First Sheet", 0);
                Label label = new Label(0, 2, "SECOND");
                Label label1 = new Label(0, 1, "first");
                Label label0 = new Label(0, 0, "HEADING");
                Label label3 = new Label(1, 0, "Heading2");
                Label label4 = new Label(1, 1, String.valueOf(a));

                try {
                    sheet.addCell(label);
                    sheet.addCell(label1);
                    sheet.addCell(label0);
                    sheet.addCell(label4);
                    sheet.addCell(label3);
                } catch (RowsExceededException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }
                workbook.write();

                try {
                    workbook.close();
                } catch (WriteException e) {

                    e.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if(arg0.getId() == R.id.button2)
        {
            try {
            read(Environment.getExternalStorageDirectory().getAbsolutePath() + "/newfolder"+ "/testfile" + ".xls");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    private  boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p:permissions) {
            result = ContextCompat.checkSelfPermission(this,p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),MULTIPLE_PERMISSIONS );
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                } else {
                    // no permissions granted.
                    Toast.makeText(this, "permission not Granted", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }


    public void read(String inputFile ) throws IOException {
        File inputWorkbook = new File(inputFile);

        String add = "";

        Workbook w;
        try {
            w = Workbook.getWorkbook(inputWorkbook);
            // Get the first sheet
            Sheet sheet = w.getSheet(0);
            // Loop over first 10 column and lines

            for (int j = 0; j < sheet.getColumns(); j++) {
                for (int i = 0; i < sheet.getRows(); i++) {
                    Cell cell = sheet.getCell(j, i);
                    CellType type = cell.getType();
                    if (type == CellType.LABEL) {
                        System.out.println("I got a label " + cell.getContents());


                      add =  add.concat( cell.getContents()+"\n");

                        Toast.makeText(this, "I got a label " + cell.getContents(),Toast.LENGTH_LONG).show();
                    }

                    if (type == CellType.NUMBER) {
                        System.out.println("I got a number " + cell.getContents());
                        Toast.makeText(this, "I got a number " + cell.getContents(),Toast.LENGTH_LONG).show();
                    }

                    tv_textview.setText( add);

                }


            }
        } catch (BiffException e) {
            e.printStackTrace();
        }
    }


}