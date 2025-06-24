package com.uop.quizapp.viewmodels;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;

import androidx.lifecycle.ViewModel;

import com.uop.quizapp.GameState;
import com.uop.quizapp.util.BitmapUtils;

/**
 * Helper ViewModel for preparing game state when a category is chosen.
 */
public class SelectCategoryViewModel extends ViewModel {
    /**
     * Update the {@link GameState} with the selected category and optional team images.
     */
    public GameState applySelectedCategory(GameState gs, String selectedCategory, Bitmap team1bitmap, Bitmap team2bitmap) {
        if (gs == null) {
            gs = new GameState();
        }
        gs.selectedCategory = selectedCategory;
        if (team1bitmap != null) {
            gs.team1byte = BitmapUtils.toByteArray(team1bitmap);
        }
        if (team2bitmap != null) {
            gs.team2byte = BitmapUtils.toByteArray(team2bitmap);
        }
        return gs;
    }
}
