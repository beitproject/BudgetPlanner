package planner.budget.budgetplanner;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.channels.FileChannel;
import com.ajts.androidmads.library.SQLiteToExcel;
import com.github.mikephil.charting.utils.Utils;

public class NavDrawer_SpendSummary extends AppCompatActivity {
    Button exportcsvbtn;
    SQLiteDatabase sqLiteDatabase;
    DatabaseHelper dbhelper;
    public Cursor c;
    FloatingActionButton exportexcelbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer__spend_summary);
        Toolbar toolbar= (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        exportcsvbtn = (Button) findViewById(R.id.export_csv_button);
        exportexcelbtn = (FloatingActionButton) findViewById(R.id.export_excel_btn);
        dbhelper = new DatabaseHelper(getApplicationContext());
        sqLiteDatabase = dbhelper.getReadableDatabase();
        c = dbhelper.csvexpense_getData();


        //**** To create csv file which will be used for algorithm in python*******

        exportcsvbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int rowcount = 0;
                    int colcount = 0;
                    File sdCardDir = Environment.getExternalStorageDirectory();
                    String filename = "MyBackup.csv";  //the name of the file to export with

                    File saveFile = new File(sdCardDir, filename);
                    FileWriter fw = new FileWriter(saveFile);

                    BufferedWriter bw = new BufferedWriter(fw);
                    rowcount = c.getCount();
                    colcount = c.getColumnCount();

                    if(rowcount > 0){
                            c.moveToFirst();

                            for (int i=0; i < colcount; i++){
                                if(i != colcount - 1){
                                    bw.write(c.getColumnName(i) + ",");
                                }   else{
                                    bw.write(c.getColumnName(i));
                                }
                            }
                    }
                    bw.newLine();

                    for (int i=0;i<rowcount;i++){
                        c.moveToPosition(i);

                        for (int j=0;j<colcount;j++){
                            if(j != colcount-1){
                                bw.write(c.getString(j)+",");
                            }   else{
                                bw.write(c.getString(j));
                            }
                        }
                        bw.newLine();
                    }
                    bw.flush();
                    Toast.makeText(NavDrawer_SpendSummary.this, "Exported Data Successfully", Toast.LENGTH_LONG).show();

                }   catch (Exception ex){
                    //if(sqLiteDatabase.isOpen()) {
                        //sqLiteDatabase.close();
                        Toast.makeText(NavDrawer_SpendSummary.this, ""+ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                    //}
                }   finally {

                }

            }
        });


        //******To create Excel file of Sqlite db *******

        exportexcelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String directory_path = Environment.getExternalStorageDirectory().getPath();

                //Export Sqlite db to Excel
                SQLiteToExcel sqLiteToExcel = new SQLiteToExcel(getApplicationContext(),"budgetplanner.db",directory_path);

                sqLiteToExcel.exportAllTables("budgetplanner.xls", new SQLiteToExcel.ExportListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onCompleted(String filePath) {
                        Toast.makeText(NavDrawer_SpendSummary.this,"Data Exported to Excel", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
            }
        });

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==android.R.id.home){
            NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
