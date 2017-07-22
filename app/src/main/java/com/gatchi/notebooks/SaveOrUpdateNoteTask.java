package com.gatchi.notebooks;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Task that updates note data when saved.
 *
 * Asynchronous so application doesn't hang.
 * Called by NoteActivity::saveOrUpdateNote.
 */
public class SaveOrUpdateNoteTask extends AsyncTask<Note, Void, Void> {
	private final Activity mCallingActivity;
	private final DatabaseHandler mDbHandler;
	private final boolean mIsUpdating;  // change this to "mUpdate" in next commit

	/**
	 * @param callingActivity used to make Toasts/Dialogs on it
	 * @param databaseHandler database handler with calling activity context
	 * @param isUpdating boolean that is used to see if note is new or updated
	 */
	public SaveOrUpdateNoteTask(Activity callingActivity, DatabaseHandler databaseHandler, boolean isUpdating) {
		mCallingActivity = callingActivity;
		mDbHandler = databaseHandler;
		mIsUpdating = isUpdating;  // change "isUpdating" to "update" in next commit
	}

	/**
	 * Does nothing.
	 *
	 * This method is required by the superclass.
	 * However, it need not be implemented.
	 */
	@Override
	protected void onPreExecute() {
	}

	/**
	 * Saves note (data side).
	 * @see onPostExecute
	 */
	@Override
	protected Void doInBackground(Note... params) {
		if (mIsUpdating) {  // change to "mUpdate" in next commit
			mDbHandler.updateNote(params[0]);
		}
		else {
			mDbHandler.createNote(params[0]);
		}
		return null;
	}

	/**
	 * Saves note (UI side).
	 *
	 * Tells the note list adapter that the data has been changed,
	 * and hands it the data.
	 * Also gives a visual confirmation to the user.
	 * @remark Keep all GUI stuff in this class here as this runs on the main activity
	 * (Android struggles to do GUI stuff outside main).
	 * @see doInBackground
	 */
	@Override
	protected void onPostExecute(Void result) {
		ArrayList<Note> allNotes = mDbHandler.getAllNotesAsArrayList();
		if (mIsUpdating) {  // change to "mUpdate" in next commit
			Toast.makeText(mCallingActivity, mCallingActivity.getString(R.string.toast_note_updated), Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(mCallingActivity, mCallingActivity.getString(R.string.toast_note_created), Toast.LENGTH_SHORT).show();
			MainActivity.noteAdapter.add(allNotes.get(mDbHandler.getNoteCount() - 1));
		}
		MainActivity.noteAdapter.setData(allNotes);
		MainActivity.noteAdapter.notifyDataSetChanged();
	}
}
