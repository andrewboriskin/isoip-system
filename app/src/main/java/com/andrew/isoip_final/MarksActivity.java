package com.andrew.isoip_final;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MarksActivity extends AppCompatActivity {


    private boolean success = false;
    private ConnectionClass connectionClass;
    String ZKnumber;

    private ArrayList<ClassMarks> itemArrayList;
    private MyAppAdapter myAppAdapter;
    private ListView MarksList;
    // Button BTNprepods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marks);

        Bundle arguments = getIntent().getExtras();
        ZKnumber = arguments.get("ZKnumber").toString();

        MarksList = (ListView) findViewById(R.id.MarksList);
        connectionClass = new ConnectionClass();
        itemArrayList = new ArrayList<ClassMarks>();

        Toast.makeText(MarksActivity.this, "Студент: " + ZKnumber, Toast.LENGTH_SHORT).show();


        SyncData orderData = new SyncData();
        orderData.execute("");

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
                    String query = "SELECT [Название_Дисциплины], [Оценка_по_КТ1], [Оценка_по_КТ2], [Оценка_по_КТ3], [Итог_Надпись], [Преподаватель], [Тип_Контроля], [Дата_Контроля] FROM [andrewbo_mnshz].[dbo].[Оценки]";
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);

                    if (rs != null) {

                        while (rs.next()) {
                            try {
                                itemArrayList.add(new ClassMarks(rs.getString("Название_Дисциплины"),rs.getString("Оценка_по_КТ1"),rs.getString("Оценка_по_КТ2"), rs.getString("Оценка_по_КТ3"),rs.getString("Итог_Надпись"), rs.getString("Преподаватель"), rs.getString("Тип_Контроля"), rs.getString("Дата_Контроля")));
                            }
                            catch (Exception ex) {
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
            Toast.makeText(MarksActivity.this, msg + "", Toast.LENGTH_SHORT).show();

            if (success == false) {

            } else {
                try {
                    myAppAdapter = new MyAppAdapter(itemArrayList, MarksActivity.this);
                    //ListViewKaf.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    MarksList.setAdapter(myAppAdapter);
                } catch (Exception ex) {

                }
            }
        }

    }

    public class MyAppAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return parkingList2.size();
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
                rowView = inflater.inflate(R.layout.marks_inflater, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.MarkDiscipl = (TextView) rowView.findViewById(R.id.MarkDiscipl);
                viewHolder.resultKT1 = (TextView) rowView.findViewById(R.id.resultKT1);
                viewHolder.resultKT2 = (TextView) rowView.findViewById(R.id.resultKT2);
                viewHolder.resultKT3 = (TextView) rowView.findViewById(R.id.resultKT3);
                viewHolder.resultMark = (TextView) rowView.findViewById(R.id.resultMark);
                viewHolder.MarkPrepod = (TextView) rowView.findViewById(R.id.MarkPrepod);
                viewHolder.tipControl = (TextView) rowView.findViewById(R.id.tipKontrol);
                viewHolder.DateControl = (TextView) rowView.findViewById(R.id.DateControl);

                rowView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.MarkDiscipl.setText(parkingList2.get(position).getMarkDiscipl()+ "");
            viewHolder.resultKT1.setText(parkingList2.get(position).getResultKT1()+ "");
            viewHolder.resultKT2.setText(parkingList2.get(position).getResultKT2()+ "");
            viewHolder.resultKT3.setText(parkingList2.get(position).getResultKT3()+ "");
            viewHolder.resultMark.setText(parkingList2.get(position).getResultMark()+ "");
            viewHolder.MarkPrepod.setText(parkingList2.get(position).getMarkPrepod()+ "");
            viewHolder.tipControl.setText(parkingList2.get(position).getTipKontrol()+ "");
            viewHolder.DateControl.setText(parkingList2.get(position).getDateControl()+ "");

            // GroupName = parkingList2.get(position).getStGroup()+ "";

            return rowView;
        }

        public class ViewHolder {
            TextView MarkDiscipl, resultKT1, resultKT2, resultKT3, resultMark, MarkPrepod, tipControl, DateControl;
        }

        public List<ClassMarks> parkingList2;

        public Context context;
        ArrayList<ClassMarks> arrayList3;

        private MyAppAdapter(List<ClassMarks> apps, Context context) {
            this.parkingList2 = apps;
            this.context = context;
            arrayList3 = new ArrayList<ClassMarks>();
            arrayList3.addAll(parkingList2);

        }

    }

}