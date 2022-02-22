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

public class RaspisaniePrepodovActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String PrepodID;

    String[] nedeli = {"Первая", "Вторая"};
    Spinner NedSpinnerPrep;

    private ArrayList<ClassRaspisaniePrepodov> ItemArrayList5;
    ListView ListRaspPrep;

    String query = "";
    String query1 = "SELECT [День_Недели], [Время_Занятия], [Дисциплина], [Имя_группы], [Аудитория] FROM [andrewbo_mnshz].[dbo].[Расписание] WHERE [Тип_Недели] = 1 AND [Код_Преподавателя] = " + PrepodID;
    String query2 = "SELECT [День_Недели], [Время_Занятия], [Дисциплина], [Имя_группы], [Аудитория] FROM [andrewbo_mnshz].[dbo].[Расписание] WHERE [Тип_Недели] = 2 AND [Код_Преподавателя] = " + PrepodID;

    private boolean success = false;
    private ConnectionClass connectionClass;
    private MyAppAdapter myAppAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raspisanie_prepodov);

        Bundle arguments = getIntent().getExtras();
        PrepodID = arguments.get("numberPrep").toString();

        NedSpinnerPrep = (Spinner) findViewById(R.id.NedSpinnerPrep);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nedeli);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        NedSpinnerPrep.setAdapter(adapter);
        NedSpinnerPrep.setOnItemSelectedListener(this);

        ListRaspPrep = (ListView) findViewById(R.id.ListRaspPrep);
        connectionClass = new ConnectionClass();
        ItemArrayList5 = new ArrayList<ClassRaspisaniePrepodov>();

    }

    private class SyncData extends AsyncTask<String, String, String> {

        String msg = "Internet/DB_Credential/Windows_FireWall_TurnOn Error";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                Connection conn = connectionClass.CONN();

                if (conn == null) {
                    success = false;
                    msg = "false";
                } else {
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);

                    if (rs != null) {

                        while (rs.next()) {
                            try {
                                ItemArrayList5.add(new ClassRaspisaniePrepodov(rs.getString("День_Недели"), rs.getString("Время_занятия"), rs.getString("Дисциплина"), rs.getString("Имя_группы"), rs.getString("Аудитория")));


                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                        msg = "Выполнено";
                        success = true;
                    } else {
                        msg = "Не найдено";
                        success = false;
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                Writer writer = new StringWriter();
                ex.printStackTrace(new PrintWriter(writer));
                msg = writer.toString();
                success = false;

            }
            return msg;
        }

        @Override
        protected void onPostExecute(String msg) {
            Toast.makeText(RaspisaniePrepodovActivity.this, msg + "", Toast.LENGTH_SHORT).show();

            if (success == false) {

            } else {
                try {
                    myAppAdapter = new MyAppAdapter(ItemArrayList5, RaspisaniePrepodovActivity.this);
                    ListRaspPrep.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    ListRaspPrep.setAdapter(myAppAdapter);
                } catch (Exception ex) {

                }
            }
        }

    }

    public class MyAppAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return parkingList.size();
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
            if (rowView == null) {
                LayoutInflater inflater = getLayoutInflater();
                rowView = inflater.inflate(R.layout.rasp_prepodov_inflater, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.raspDayPrep = (TextView) rowView.findViewById(R.id.raspDayPrep);
                viewHolder.raspTimePrep = (TextView) rowView.findViewById(R.id.raspTimePrep);
                viewHolder.raspDiscipPrep = (TextView) rowView.findViewById(R.id.raspDiscipPrep);
                viewHolder.raspGroupPrep = (TextView) rowView.findViewById(R.id.raspGroupPrep);
                viewHolder.raspAudPrep = (TextView) rowView.findViewById(R.id.raspAudPrep);
                rowView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.raspDayPrep.setText(parkingList.get(position).getRaspDayPrep() + "");
            viewHolder.raspTimePrep.setText(parkingList.get(position).getRaspTimePrep() + "");
            viewHolder.raspDiscipPrep.setText(parkingList.get(position).getRaspDiscpPrep() + "");
            viewHolder.raspGroupPrep.setText(parkingList.get(position).getRaspGroupPrep() + "");
            viewHolder.raspAudPrep.setText(parkingList.get(position).getRaspAudPrep() + "");

            return rowView;
        }

        public class ViewHolder {
            TextView raspDayPrep, raspTimePrep, raspDiscipPrep, raspGroupPrep, raspAudPrep;
        }

        public List<ClassRaspisaniePrepodov> parkingList;

        public Context context;
        ArrayList<ClassRaspisaniePrepodov> arrayList2;

        private MyAppAdapter(List<ClassRaspisaniePrepodov> apps, Context context) {
            this.parkingList = apps;
            this.context = context;
            arrayList2 = new ArrayList<ClassRaspisaniePrepodov>();
            arrayList2.addAll(parkingList);
        }

    }
        @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {

            SyncData orderData = new SyncData();

            switch (i) {
                case 0:
                    ItemArrayList5.clear();
                    query = query1;

                    orderData.execute("");
                    break;
                case 1:
                    ItemArrayList5.clear();
                    query = query2;

                    orderData.execute("");
                    break;

            }

        }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}

