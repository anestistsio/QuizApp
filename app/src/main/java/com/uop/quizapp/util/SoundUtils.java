package com.uop.quizapp.util;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Helper for playing short sound effects respecting mute state.
 */
public final class SoundUtils {
    private SoundUtils() {}

    /**
     * Play the given sound resource if not muted.
     * The created {@link MediaPlayer} is released automatically.
     */
    public static void play(Context context, int soundResId, boolean isMute) {
        if (isMute) {
            return;
        }
        MediaPlayer mp = MediaPlayer.create(context, soundResId);
        if (mp == null) return;
        mp.setOnCompletionListener(MediaPlayer::release);
        mp.start();
    }
}
