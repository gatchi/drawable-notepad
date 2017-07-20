package com.gatchi.notebooks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * Converts to and from byte arrays and bitmaps.
 * Should not be instantiated.
 */
public final class BitmapConverter {

    private BitmapConverter() throws InstantiationException {
        throw new InstantiationException("This class is not for instantiation");
    }

    /** Converts from bitmap to a byte array */
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    /** Converts from a byte array to a bitmap */
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

}
