package com.example.quang.project;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.quang.project.fragment.FragmentChi;
import com.example.quang.project.fragment.FragmentNganSach;
import com.example.quang.project.fragment.main.FragmentMain;
import com.example.quang.project.fragment.FragmentTaiKhoan;
import com.example.quang.project.fragment.FragmentThuNhap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DatabaseSqliteProject mDbProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDbProject = new DatabaseSqliteProject(getApplicationContext());
        mDbProject.createDatabse();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        FragmentMain test = new FragmentMain();
        FragmentTransaction dd =getSupportFragmentManager().beginTransaction();
        dd.replace(R.id.content,test);
        dd.commit();

        if (mDbProject.checkBangTaiKhoan() == 0){
            FragmentTaiKhoan fragmentTaiKhoan = new FragmentTaiKhoan();
            Bundle bundle = new Bundle();
            bundle.putString("taikhoan","taikhoan");
            fragmentTaiKhoan.setArguments(bundle);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content,fragmentTaiKhoan);
            transaction.commit();

        }


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        checkId(id);


        return true;
    }



    private void checkId(int id){
        Fragment fragment = null;
        switch (id){
            case R.id.nav_soluoc:
                fragment = new FragmentMain();
                break;
            case R.id.nav_thu:
                fragment = new FragmentThuNhap();
                break;
            case R.id.nav_chi:
                fragment = new FragmentChi();
                break;
            case R.id.nav_vi:
                fragment = new FragmentTaiKhoan();
                break;
            case R.id.nav_ngansach:
                fragment = new FragmentNganSach();
                break;
            case R.id.nav_logout:
                finish();
                break;
        }
        if (fragment != null) {

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content, fragment);
            transaction.commit();

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
}
