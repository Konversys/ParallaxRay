package ru.konverdev.parallax;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.konverdev.parallax.activity.DirectionActivity;
import ru.konverdev.parallax.activity.ScheduleActivity;
import ru.konverdev.parallax.activity.WagonActivity;
import ru.konverdev.parallax.fragment.DownloadFragment;
import ru.konverdev.parallax.fragment.ErrorFragment;
import ru.konverdev.parallax.helper.CustomToast;
import ru.konverdev.parallax.model.Route;
import ru.konverdev.parallax.model.classes.Direction;
import ru.konverdev.parallax.model.classes.Wagon;
import ru.konverdev.parallax.utils.tools.FragmentHandler;
import ru.konverdev.parallax.utils.web.ApiConnector;

public class MainActivity extends AppCompatActivity {
    private static FragmentManager fragmentManager;
    private AppCompatActivity activity;

    private ActionBar actionBar;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm.init(this);
        activity = this;
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        if (Direction.GetDirections(0).isEmpty()) {
            FragmentHandler.Download(fragmentManager);
            ApiConnector.getParallaxLinkApi().getValidDirections().enqueue(new Callback<List<Direction>>() {
                @Override
                public void onResponse(Call<List<Direction>> call, Response<List<Direction>> response) {
                    Direction.SaveDirections(response.body());
                    FragmentHandler.Empty(fragmentManager);
                    Intent intent = new Intent(activity, DirectionActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onFailure(Call<List<Direction>> call, Throwable t) {
                    CustomToast.SnackBarIconError(activity, Route.ERROR_PARALLAX_API);
                }
            });
        } else {
            Intent intent = new Intent(activity, DirectionActivity.class);
            startActivity(intent);
        }
    }
}
