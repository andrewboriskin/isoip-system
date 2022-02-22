package com.andrew.isoip_final;

import android.app.ProgressDialog;
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
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PrepodsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayList<ClassPrepods> arrayList;
    private MyAppAdapter myAppAdapter;
    private ListView Prepods_List;
    private boolean success = false;
    private ConnectionClass connectionClass;
    String numberKaf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepods);
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


        Prepods_List = (ListView) findViewById(R.id.Prepods_List);
        connectionClass = new ConnectionClass();
        arrayList = new ArrayList<ClassPrepods>();

        SyncData orderData = new SyncData();
        orderData.execute();
    }

    private class SyncData extends AsyncTask<String, String, String> {

        String msg = "Internet/DB_Credential/Windows_FireWall_TurnOn Error";
        ProgressDialog progress;

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(PrepodsActivity.this, "Выполняется", "Подождите!", true);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                Connection conn = connectionClass.CONN();

                if (conn == null) {
                    success = false;
                    msg = "false";
                } else {
                    String query = "SELECT [ФИО],[Ссылка_фото],[Должность] FROM [andrewbo_mnshz].[dbo].[Преподаватели] WHERE [Код_Кафедры] = "+ numberKaf;
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);

                    if (rs != null) {

                        while (rs.next()) {
                            try {
                                arrayList.add(new ClassPrepods(rs.getString("ФИО"),rs.getString("Ссылка_фото"),rs.getString("Должность")));

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
            progress.dismiss();
            Toast.makeText(PrepodsActivity.this, msg + "", Toast.LENGTH_SHORT).show();

            if (success == false) {

            } else {
                try {
                    myAppAdapter = new MyAppAdapter(arrayList, PrepodsActivity.this);
                    Prepods_List.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    Prepods_List.setAdapter(myAppAdapter);
                    Prepods_List.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            int temp = position+1;
                            Intent intent = new Intent(PrepodsActivity.this, RaspisaniePrepodovActivity.class);
                            intent.putExtra("numberPrep", temp);
                            startActivity(intent);
                        }
                    });
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
            if(rowView == null) {
                LayoutInflater inflater = getLayoutInflater();
                rowView = inflater.inflate(R.layout.prepods_inflater, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.tvPrepod = (TextView) rowView.findViewById(R.id.tvPrepod);
                viewHolder.ivPrepod = (ImageView) rowView.findViewById(R.id.ivPrepod);
                viewHolder.tvDolzhnost = (TextView) rowView.findViewById(R.id.tvDolzhnost);
                rowView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.tvPrepod.setText(parkingList.get(position).getNamePrepod()+ "");
            Picasso.with(context).load("" + parkingList.get(position).getImg()).into(viewHolder.ivPrepod);
            viewHolder.tvDolzhnost.setText(parkingList.get(position).getDolzhnost()+ "");

            return rowView;
        }

        public class ViewHolder {
            TextView tvPrepod;
            ImageView ivPrepod;
            TextView tvDolzhnost;
        }

        public List<ClassPrepods> parkingList;

        public Context context;
        ArrayList<ClassPrepods> arrayList2;

        private MyAppAdapter(List<ClassPrepods> apps, Context context) {
            this.parkingList = apps;
            this.context = context;
            arrayList2 = new ArrayList<ClassPrepods>();
            arrayList2.addAll(parkingList);
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
        getMenuInflater().inflate(R.menu.prepods, menu);
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
