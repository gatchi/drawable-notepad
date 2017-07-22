package com.gatchi.notebooks;

import android.graphics.Bitmap;
import android.text.Spannable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Base unit of this app; a note.
 */
public class Note {

    // KEY_ID of Note
    private final int mId;

    // Spannable used to format text
    private final Spannable mSpannable;

    // Title of note
    private final String mTitle;

    // Raw text used to make titles
    private final String rawText;

    // Painting
    private final Bitmap mImage;

    // Dates (Default value)
    private Date dateUpdated = new Date();

    // Formatter
    private static final DateFormat dt = new SimpleDateFormat("dd.MM.yyyy, hh:mm:ss", Locale.getDefault());





    public Note(int id, String title, Spannable spannable, Bitmap image, Date dateUpdated) {
        mId = id;
        mTitle = title;
        mSpannable = spannable;
        mImage = image;
        rawText = mSpannable.toString();
        this.dateUpdated = dateUpdated;
    }

	/**
	 * Returns the unique ID number of the note.
	 */
    public int getId() {
        return mId;
    }

	/**
	 * Returns the note title.
	 */
    public String getTitle(){
        return mTitle;
    }

	/**
	 * Returns the formatted body of note text.
	 */
    public Spannable getSpannable() {
        return mSpannable;
    }

	/**
	 * Returns the note body in plain text.
	 */
    public String getRawText() {
        return rawText;
    }

	/**
	 * Returns sketches associated with note.
	 */
    public Bitmap getImage() {
        return mImage;
    }

	/**
	 * Returns the date and time last accessed as a java Date object.
	 * @todo delete this
	 */
    public Date getDateUpdated() {
        return dateUpdated;
    }

	/**
	 * Returns the date and time last accessed.
	 * @todo change this to return last time edited.
	 */
    public String getFormattedDateUpdatted() {
        return dt.format(dateUpdated);
    }
}
