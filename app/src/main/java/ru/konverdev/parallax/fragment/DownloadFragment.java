package ru.konverdev.parallax.fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import ru.konverdev.parallax.R;
import ru.konverdev.parallax.utils.ViewAnimation;

public class DownloadFragment extends Fragment {

    public DownloadFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_download, container, false);
        loadingDisplay(root);
        return root;
    }

    private void loadingDisplay(View root) {
        final LinearLayout lyt_progress = (LinearLayout) root.findViewById(R.id.FrDownloadLoading);
        lyt_progress.setVisibility(View.VISIBLE);
        lyt_progress.setAlpha(1.0f);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ViewAnimation.fadeOut(lyt_progress);
            }
        }, 100000);
    }
}
