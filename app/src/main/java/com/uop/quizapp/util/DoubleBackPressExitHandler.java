package com.uop.quizapp.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

/**
 * Utility to handle double back press to exit behaviour for Activities.
 */
public class DoubleBackPressExitHandler {
    private boolean doubleBackToExitPressedOnce;
    private final Context context;

    public DoubleBackPressExitHandler(Context context) {
        this.context = context;
    }

    /**
     * Call from {@link android.app.Activity#onBackPressed()}.
     *
     * @return true if the Activity should exit
     */
    public boolean onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            return true;
        }
        doubleBackToExitPressedOnce = true;
        Toast.makeText(context, "Please click BACK twice to exit", Toast.LENGTH_SHORT).show();
        new Handler(Looper.getMainLooper()).postDelayed(() -> doubleBackToExitPressedOnce = false, 1000);
        return false;
    }
}
