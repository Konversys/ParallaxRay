package ru.konverdev.parallax.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.konverdev.parallax.R;

public class ErrorFragment extends Fragment {
    public ErrorFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_error, container, false);
        return root;
    }
}
