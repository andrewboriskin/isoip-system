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
import android.widget.Button;
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

public class KafedersInfoActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private boolean success = false;
    private ConnectionClass connectionClass;
    String numberKaf;

    private ArrayList<ClassKafedersInfo> itemArrayList;
    private MyAppAdapter myAppAdapter;
    private ListView infoKaf;
    Button BTNprepods;
    //String GroupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kafeders_info);
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
        numberKaf = arguments.get("numberKaf").toString();
        //idGroup.setText(numberGroup);


        infoKaf = (ListView) findViewById(R.id.infoKaf);
        connectionClass = new ConnectionClass();
        itemArrayList = new ArrayList<ClassKafedersInfo>();
        BTNprepods = (Button) findViewById(R.id.BTNprepods);
        BTNprepods.setOnClickListener(this);

        SyncData orderData = new SyncData();
        orderData.execute("");

    }

    @Override
    public void onClick(View v) {
        //Toast.makeText(KafedersInfoActivity.this, numberKaf + "", Toast.LENGTH_SHORT).show();
        Intent intent;
        intent = new Intent(KafedersInfoActivity.this, PrepodsActivity.class);
        intent.putExtra("numberKaf", numberKaf);
        startActivity(intent);
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
                    String query = "SELECT * FROM [andrewbo_mnshz].[dbo].[Кафедры] WHERE [Код_Кафедры] = " + numberKaf;
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);

                    if (rs != null) {

                        while (rs.next()) {
                            try {
                                itemArrayList.add(new ClassKafedersInfo(rs.getString("Название"), rs.getString("Сокращение"), rs.getString("Зав_каф"), rs.getString("Телефон"), rs.getString("Аудитория")));


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
            Toast.makeText(KafedersInfoActivity.this, msg + "", Toast.LENGTH_SHORT).show();

            if (success == false) {

            } else {
                try {
                    myAppAdapter = new MyAppAdapter(itemArrayList, KafedersInfoActivity.this);
                    //ListViewKaf.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    infoKaf.setAdapter(myAppAdapter);
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
                rowView = inflater.inflate(R.layout.kafeders_info_inflater, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.nameKaf = (TextView) rowView.findViewById(R.id.nameKaf);
                viewHolder.sokrKaf = (TextView) rowView.findViewById(R.id.sokrKaf);
                viewHolder.zavkaf = (TextView) rowView.findViewById(R.id.zavkaf);
                viewHolder.telkaf = (TextView) rowView.findViewById(R.id.telKaf);
                viewHolder.AudKaf = (TextView) rowView.findViewById(R.id.AudKaf);

                rowView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.nameKaf.setText(parkingList2.get(position).getNameKaf()+ "");
            viewHolder.sokrKaf.setText(parkingList2.get(position).getSokrKaf()+ "");
            viewHolder.zavkaf.setText(parkingList2.get(position).getZavkaf()+ "");
            viewHolder.telkaf.setText(parkingList2.get(position).getTelKaf()+ "");
            viewHolder.AudKaf.setText(parkingList2.get(position).getAudKaf()+ "");

           // GroupName = parkingList2.get(position).getStGroup()+ "";

            return rowView;
        }

        public class ViewHolder {
            TextView nameKaf, sokrKaf, zavkaf, telkaf, AudKaf;
        }

        public List<ClassKafedersInfo> parkingList2;

        public Context context;
        ArrayList<ClassKafedersInfo> arrayList3;

        private MyAppAdapter(List<ClassKafedersInfo> apps, Context context) {
            this.parkingList2 = apps;
            this.context = context;
            arrayList3 = new ArrayList<ClassKafedersInfo>();
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
        getMenuInflater().inflate(R.menu.kafeders_info, menu);
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
