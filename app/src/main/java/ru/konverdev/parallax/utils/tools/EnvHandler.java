package ru.konverdev.parallax.utils.tools;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import ru.konverdev.parallax.R;
import ru.konverdev.parallax.activity.DirectionActivity;
import ru.konverdev.parallax.activity.ScheduleActivity;
import ru.konverdev.parallax.activity.WagonActivity;
import ru.konverdev.parallax.helper.CustomToast;
import ru.konverdev.parallax.model.classes.Station;

public class EnvHandler {

    public static void Init(AppCompatActivity activity, String title) {
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle(title);

        NavigationView nav_view = (NavigationView) activity.findViewById(R.id.nav_view);
        final DrawerLayout drawer = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(activity, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(final MenuItem item) {
                if (item.getTitle() == activity.getResources().getString(R.string.direction)) {
                    Intent intent = new Intent(activity, DirectionActivity.class);
                    activity.startActivity(intent);
                } else if (item.getTitle() == activity.getResources().getString(R.string.schedule)) {
                    Intent intent = new Intent(activity, ScheduleActivity.class);
                    activity.startActivity(intent);
                } else if (item.getTitle() == activity.getResources().getString(R.string.wagon)) {
                    Intent intent = new Intent(activity, WagonActivity.class);
                    activity.startActivity(intent);
                } else {
                    Toast.makeText(activity.getApplicationContext(), item.getTitle() + " Selected", Toast.LENGTH_SHORT).show();
                    actionBar.setTitle(item.getTitle());
                    drawer.closeDrawers();
                }
                return true;
            }
        });
    }
}
