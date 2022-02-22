package com.andrew.isoip_final;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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

public class FackultInfoActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private boolean success = false;
    private ConnectionClass connectionClass;
    String numberFack;

    private ArrayList<ClassFackultInfo> itemArrayList;
    private MyAppAdapter myAppAdapter;
    private ListView FackInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fackult_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        Bundle arguments = getIntent().getExtras();
        numberFack = arguments.get("numberFack").toString();
        //idGroup.setText(numberGroup);


        FackInfoList = (ListView) findViewById(R.id.FackInfoList);
        connectionClass = new ConnectionClass();
        itemArrayList = new ArrayList<ClassFackultInfo>();

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
                    String query = "SELECT * FROM [andrewbo_mnshz].[dbo].[Факультеты] WHERE [Код_Факультета] = " + numberFack;
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);

                    if (rs != null) {

                        while (rs.next()) {
                            try {
                                itemArrayList.add(new ClassFackultInfo(rs.getString("Факультет"), rs.getString("Сокращение"), rs.getString("Декан"), rs.getString("Телефон"), rs.getString("Аудитория")));


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
            Toast.makeText(FackultInfoActivity.this, msg + "", Toast.LENGTH_SHORT).show();

            if (success == false) {

            } else {
                try {
                    myAppAdapter = new MyAppAdapter(itemArrayList, FackultInfoActivity.this);
                    //ListViewKaf.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    FackInfoList.setAdapter(myAppAdapter);
                } catch (Exception ex) {

                }
            }
        }
    }


    public class MyAppAdapter extends BaseAdapter {

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
                rowView = inflater.inflate(R.layout.fackult_info_inflater, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.nameFack = (TextView) rowView.findViewById(R.id.nameFackult);
                viewHolder.sokrFack = (TextView) rowView.findViewById(R.id.sokrFack);
                viewHolder.dekanFack = (TextView) rowView.findViewById(R.id.dekanFack);
                viewHolder.telFack = (TextView) rowView.findViewById(R.id.telFack);
                viewHolder.audFack = (TextView) rowView.findViewById(R.id.AudFack);

                rowView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.nameFack.setText(parkingList2.get(position).getNameFack()+ "");
            viewHolder.sokrFack.setText(parkingList2.get(position).getSokrFack()+ "");
            viewHolder.dekanFack.setText(parkingList2.get(position).getDekanFack()+ "");
            viewHolder.telFack.setText(parkingList2.get(position).getTelFack()+ "");
            viewHolder.audFack.setText(parkingList2.get(position).getAudFack()+ "");

            // GroupName = parkingList2.get(position).getStGroup()+ "";

            return rowView;
        }

        public class ViewHolder {
            TextView nameFack, sokrFack, dekanFack, telFack, audFack;
        }

        public List<ClassFackultInfo> parkingList2;

        public Context context;
        ArrayList<ClassFackultInfo> arrayList3;

        private MyAppAdapter(List<ClassFackultInfo> apps, Context context) {
            this.parkingList2 = apps;
            this.context = context;
            arrayList3 = new ArrayList<ClassFackultInfo>();
            arrayList3.addAll(parkingList2);

        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.fackult_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Intent intent;
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_main) {
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_groups) {
            intent = new Intent(this, GroupsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_kafeders) {
            intent = new Intent(this, KafedersActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_fackultets) {
            intent = new Intent(this, FackultActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_ocenki) {
            intent = new Intent(this, FindMarksActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
