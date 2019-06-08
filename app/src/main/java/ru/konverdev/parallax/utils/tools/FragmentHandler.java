package ru.konverdev.parallax.utils.tools;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import ru.konverdev.parallax.R;
import ru.konverdev.parallax.fragment.DownloadFragment;
import ru.konverdev.parallax.fragment.ErrorFragment;

public class FragmentHandler {
    public static void Error(FragmentManager fragmentManager, int frame) {
        if (fragmentManager == null) {
            return;
        }
        Empty(fragmentManager, frame);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frame, new ErrorFragment());
        transaction.commit();
    }

    public static void Download(FragmentManager fragmentManager, int frame) {
        if (fragmentManager == null) {
            return;
        }
        Empty(fragmentManager, frame);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frame, new DownloadFragment());
        transaction.commit();
    }

    public static void Empty(FragmentManager fragmentManager, int frame) {
        if (fragmentManager.findFragmentById(frame) != null) {
            fragmentManager.beginTransaction().
                    remove(fragmentManager.findFragmentById(frame)).commit();
        }
    }
}
