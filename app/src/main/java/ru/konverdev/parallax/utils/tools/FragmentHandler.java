package ru.konverdev.parallax.utils.tools;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import ru.konverdev.parallax.R;
import ru.konverdev.parallax.fragment.DownloadFragment;
import ru.konverdev.parallax.fragment.ErrorFragment;

public class FragmentHandler {
    private static void Error(FragmentManager fragmentManager) {
        if (fragmentManager == null) {
            return;
        }
        Empty(fragmentManager);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.Frame, new ErrorFragment());
        transaction.commit();
    }

    public static void Download(FragmentManager fragmentManager) {
        if (fragmentManager == null) {
            return;
        }
        Empty(fragmentManager);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.Frame, new DownloadFragment());
        transaction.commit();
    }

    public static void Empty(FragmentManager fragmentManager) {
        if (fragmentManager.findFragmentById(R.id.Frame) != null) {
            fragmentManager.beginTransaction().
                    remove(fragmentManager.findFragmentById(R.id.Frame)).commit();
        }
    }
}
