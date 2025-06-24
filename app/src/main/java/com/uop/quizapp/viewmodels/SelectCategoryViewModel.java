package com.uop.quizapp.viewmodels;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;

import androidx.lifecycle.ViewModel;

import com.uop.quizapp.GameState;

import java.io.ByteArrayOutputStream;

public class SelectCategoryViewModel extends ViewModel {
    public GameState updateCategory(GameState gs, String selectedCategory, Bitmap team1bitmap, Bitmap team2bitmap) {
        if (gs == null) {
            gs = new GameState();
        }
        gs.selectedCategory = selectedCategory;
        if (team1bitmap != null) {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            team1bitmap.compress(CompressFormat.JPEG, 100, bytes);
            gs.team1byte = bytes.toByteArray();
        }
        if (team2bitmap != null) {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            team2bitmap.compress(CompressFormat.JPEG, 100, bytes);
            gs.team2byte = bytes.toByteArray();
        }
        return gs;
    }
}
