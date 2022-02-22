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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GroupInfoActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {


    private boolean success = false;
    private ConnectionClass connectionClass;
    String numberGroup;

    private ArrayList<ClassGroupInfo> itemArrayList;
    private My2AppAdapter my2AppAdapter;
    private ListView LVgroupInfo;
    String GroupName;

    Button BTNrasp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        //idGroup = (TextView) findViewById(R.id.idGroup);

        Bundle arguments = getIntent().getExtras();
        numberGroup = arguments.get("number").toString();
        //idGroup.setText(numberGroup);


        LVgroupInfo = (ListView) findViewById(R.id.LVgroupInfo);
        connectionClass = new ConnectionClass();
        itemArrayList = new ArrayList<ClassGroupInfo>();

        BTNrasp = (Button) findViewById(R.id.BTNrasp);
        BTNrasp.setOnClickListener(this);

        SyncData orderData = new SyncData();
        orderData.execute("");


    }

    @Override
    public void onClick(View v) {

        //Toast.makeText(GroupInfoActivity.this, GroupName + "", Toast.LENGTH_SHORT).show();
        Intent intent;
        intent = new Intent(GroupInfoActivity.this, RaspisanieActivity.class);
        //intent.putExtra("GroupName", GroupName);
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
                    String query = "SELECT * FROM [andrewbo_mnshz].[dbo].[Группы] WHERE [Код] = " + numberGroup;
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);

                    if (rs != null) {

                        while (rs.next()) {
                            try {
                               itemArrayList.add(new ClassGroupInfo(rs.getString("Имя_Группы"),rs.getString("Факультет"),rs.getString("Специальность"),rs.getString("Курс"),rs.getString("Год_Обучения"),rs.getString("Количество_студентов")));


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
            //Toast.makeText(GroupInfoActivity.this, msg + "", Toast.LENGTH_SHORT).show();

            if (success == false) {

            } else {
                try {
                    my2AppAdapter = new My2AppAdapter(itemArrayList, GroupInfoActivity.this);
                    //ListViewKaf.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    LVgroupInfo.setAdapter(my2AppAdapter);
                } catch (Exception ex) {

                }
            }
        }

    }



    public class My2AppAdapter extends BaseAdapter {


        public class ViewHolder {
            TextView nameTV, nameFak, nameSpec, nameYear, nameKolvo, nameKurs;
        }

        public List<ClassGroupInfo> parkingList2;

        public Context context;
        ArrayList<ClassGroupInfo> arrayList3;

        private My2AppAdapter(List<ClassGroupInfo> apps, Context context) {
            this.parkingList2 = apps;
            this.context = context;
            arrayList3 = new ArrayList<ClassGroupInfo>();
            arrayList3.addAll(parkingList2);

        }

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
                rowView = inflater.inflate(R.layout.group_info_inflater, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.nameTV = (TextView) rowView.findViewById(R.id.nameTV);
                viewHolder.nameFak = (TextView) rowView.findViewById(R.id.nameFak);
                viewHolder.nameSpec = (TextView) rowView.findViewById(R.id.nameSpec);
                viewHolder.nameYear = (TextView) rowView.findViewById(R.id.nameYear);
                viewHolder.nameKurs = (TextView) rowView.findViewById(R.id.nameKurs);
                viewHolder.nameKolvo = (TextView) rowView.findViewById(R.id.nameKolvo);
                rowView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.nameTV.setText(parkingList2.get(position).getStGroup()+ "");
            viewHolder.nameFak.setText(parkingList2.get(position).getStFak()+ "");
            viewHolder.nameSpec.setText(parkingList2.get(position).getStSpec()+ "");
            viewHolder.nameYear.setText(parkingList2.get(position).getStYear()+ "");
            viewHolder.nameKurs.setText(parkingList2.get(position).getStKurs()+ "");
            viewHolder.nameKolvo.setText(parkingList2.get(position).getStKolvo()+ "");

            GroupName = parkingList2.get(position).getStGroup()+ "";

            return rowView;

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
        getMenuInflater().inflate(R.menu.group_info, menu);
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
