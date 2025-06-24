package com.uop.quizapp.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * Utility methods for converting between {@link Bitmap} and byte arrays.
 */
public final class BitmapUtils {
    private BitmapUtils() {}

    /**
     * Convert a bitmap to a JPEG encoded byte array.
     *
     * @param bitmap Bitmap to convert, may be null.
     * @return byte array or null if bitmap was null
     */
    public static byte[] toByteArray(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        return bytes.toByteArray();
    }

    /**
     * Decode a bitmap from the given byte array.
     *
     * @param data Image data, may be null.
     * @return Decoded bitmap or null if data was null
     */
    public static Bitmap fromByteArray(byte[] data) {
        if (data == null) {
            return null;
        }
        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }
}
