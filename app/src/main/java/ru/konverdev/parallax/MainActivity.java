package ru.konverdev.parallax;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.konverdev.parallax.fragment.DownloadFragment;
import ru.konverdev.parallax.fragment.ErrorFragment;
import ru.konverdev.parallax.model.classes.Direction;
import ru.konverdev.parallax.utils.tools.FragmentHandler;
import ru.konverdev.parallax.utils.web.ApiConnector;

public class MainActivity extends AppCompatActivity {

    private static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm.init(this);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        if (Direction.GetDirections(0).isEmpty()){
            FragmentHandler.Download(fragmentManager, R.id.AcMainFrame);
            ApiConnector.getParallaxLinkApi().getValidDirections().enqueue(new Callback<List<Direction>>() {
                @Override
                public void onResponse(Call<List<Direction>> call, Response<List<Direction>> response) {
                    Direction.SaveDirections(response.body());
                    FragmentHandler.Empty(fragmentManager, R.id.AcMainFrame);
                }

                @Override
                public void onFailure(Call<List<Direction>> call, Throwable t) {
                    FragmentHandler.Error(fragmentManager, R.id.AcMainFrame);
                }
            });
        }
    }
}
