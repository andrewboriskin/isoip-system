package com.andrew.isoip_final;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RaspisanieActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String nameGroup;

    String[] nedeli = {"Первая", "Вторая"};
    Spinner nedspinner;
    //int spinznach = 1;
    private ArrayList<ClassRaspisanie> ItemArrayList;
    ListView listraspisanie;
    //int spinid;

    String query = "";
    String query1 = "SELECT [День_Недели], [Время_Занятия], [Дисциплина], [Аудитория], [Преподаватель] FROM [andrewbo_mnshz].[dbo].[Расписание] WHERE [Тип_Недели] = 1";
    String query2 = "SELECT [День_Недели], [Время_Занятия], [Дисциплина], [Аудитория], [Преподаватель] FROM [andrewbo_mnshz].[dbo].[Расписание] WHERE [Тип_Недели] = 2";

    private boolean success = false;
    private ConnectionClass connectionClass;
    private My3AppAdapter my3AppAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raspisanie);

        listraspisanie = findViewById(R.id.listraspisanie);
        ItemArrayList = new ArrayList<ClassRaspisanie>();
        connectionClass = new ConnectionClass();


        Intent intent = getIntent();
        nameGroup = intent.getStringExtra("GroupName");
        //Toast.makeText(RaspisanieActivity.this, nameGroup + "", Toast.LENGTH_SHORT).show();
        //tvRaspGroup.setText("" + nameGroup);

        nedspinner = (Spinner) findViewById(R.id.nedspinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nedeli);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nedspinner.setAdapter(adapter);
        nedspinner.setOnItemSelectedListener(this);


       // spinid = (int) nedspinner.getSelectedItemId() + 1;
       // Toast.makeText(RaspisanieActivity.this, spinid + "", Toast.LENGTH_SHORT).show();

        //SyncData orderData = new SyncData();
        //orderData.execute("");
    }

    private class SyncData extends AsyncTask<String, String, String> {

        String msg = "Internet/DB_Credential/Windows_FireWall_TurnOn Error";

        @Override
        protected String doInBackground(String... strings) {
            try {


                Connection conn = connectionClass.CONN();

                if (conn == null) {
                    success = false;
                    msg = "false";
                } else {
                    Statement stmt;
                    ResultSet rs;

                    stmt = conn.createStatement();
                    rs = stmt.executeQuery(query);

                    if (rs != null) {

                        while (rs.next()) {
                            try {
                                ItemArrayList.add(new ClassRaspisanie(rs.getString("День_Недели"),rs.getString("Время_Занятия"),rs.getString("Дисциплина"),rs.getString("Преподаватель"),rs.getString("Аудитория")));

                            } catch (Exception ex) {
                                ex.printStackTrace();
                                Writer writer = new StringWriter();
                                ex.printStackTrace(new PrintWriter(writer));
                                msg = writer.toString();
                            }
                        }
                        msg = "Выполнено";
                        success = true;
                    } else {
                        msg = "Не найдено";
                        success = false;
                    }

                }
            } catch (SQLException e) {
                e.printStackTrace();
                Writer writer = new StringWriter();
                e.printStackTrace(new PrintWriter(writer));
                msg = writer.toString();
                success = false;
            }
            return msg;
        }

        @Override
        protected void onPostExecute(String msg) {
            Toast.makeText(RaspisanieActivity.this, msg + "", Toast.LENGTH_SHORT).show();

            if (success == false) {

            } else {
                try {
                    my3AppAdapter = new My3AppAdapter(ItemArrayList, RaspisanieActivity.this);

                    listraspisanie.setAdapter(my3AppAdapter);
                } catch (Exception ex) {

                }
            }
        }

    }


    public class My3AppAdapter extends BaseAdapter {


        public class ViewHolder {
            TextView raspDay, raspTime, raspDisciplina, raspAud, raspPrepod;
        }

        public List<ClassRaspisanie> parkingList3;

        public Context context;
        ArrayList<ClassRaspisanie> arrayList4;

        private My3AppAdapter(List<ClassRaspisanie> apps, Context context) {
            this.parkingList3 = apps;
            this.context = context;
            arrayList4 = new ArrayList<ClassRaspisanie>();
            arrayList4.addAll(parkingList3);

        }

        @Override
        public int getCount() {
            return parkingList3.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            ViewHolder viewHolder = null;
            if(rowView == null) {
                LayoutInflater inflater = getLayoutInflater();
                rowView = inflater.inflate(R.layout.raspisanie_inflater, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.raspDay = (TextView) rowView.findViewById(R.id.raspDay);
                viewHolder.raspTime = (TextView) rowView.findViewById(R.id.raspTime);
                viewHolder.raspDisciplina = (TextView) rowView.findViewById(R.id.raspDisciplina);
                viewHolder.raspAud = (TextView) rowView.findViewById(R.id.raspAud);
                viewHolder.raspPrepod = (TextView) rowView.findViewById(R.id.raspPrepod);

                rowView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.raspDay.setText(parkingList3.get(position).getRaspDay()+ "");
            viewHolder.raspTime.setText(parkingList3.get(position).getRaspTime()+ "");
            viewHolder.raspDisciplina.setText(parkingList3.get(position).getRaspDisciplina()+ "");
            viewHolder.raspAud.setText(parkingList3.get(position).getRaspAud()+ "");
            viewHolder.raspPrepod.setText(parkingList3.get(position).getRaspPrerod()+ "");

            return rowView;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {

        SyncData orderData = new SyncData();

        switch (i){
            case 0:
                ItemArrayList.clear();
                query = query1;

                orderData.execute("");
                break;
            case 1:
                ItemArrayList.clear();
                query = query2;

                orderData.execute("");
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
