package com.gatchi.notebooks;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Adapter between visual elements and note data.
 *
 * An ArrayAdapter can't do this by itself because...?
 */
public class NoteAdapter extends ArrayAdapter<Note> {

    private Context context;
    private int layoutResourceId;  // layout info for notes on list view
    private List<Note> data = null;  // list of notes to display

    public NoteAdapter(Context context, int layoutResourceId, List<Note> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

	/**
	 * Gets view information from the adapter.
	 * I believe this is what parent views/manager calls when it wants to set
	 * layout information for a specified view.
	 * Before this can happen, the view must be fully prepared, with all its text
	 * and info in place and its own internal layout set (in essence, made pretty).
	 */
    @Override
    @NonNull  // is this necessary?
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View row = convertView;  // view to re-use (if possible)
        NoteHolder holder;  // view info to display

        if(row == null)  // if empty, initialize
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new NoteHolder();
            holder.noteTitle = (TextView)row.findViewById(R.id.noteTitle);
            holder.noteContent = (TextView)row.findViewById(R.id.noteContent);
            holder.noteDate = (TextView)row.findViewById(R.id.noteDate);


            row.setTag(holder);
        }
        else  // otherwise, get its note info
        {
            holder = (NoteHolder)row.getTag();
        }

		// Set its title on the preview
        Note note = data.get(position);
        String noteTitle = note.getTitle();
        if (noteTitle == null || noteTitle.length() == 0)
            noteTitle = String.format(context.getString(R.string.note_number), note.getId());
        holder.noteTitle.setText(noteTitle);

		// Set its text body on the preview (first line)
        String title = note.getRawText();
        if (title.length() != 0) {
            holder.noteContent.setText(note.getRawText());
        }
        else {
            //TODO Finding out if there is picture on note
            holder.noteContent.setText("INFO: Note has no text");
        }

		// Set the date and time on the preview
        holder.noteDate.setText(context.getString(R.string.last_updated) + ": " + note.getFormattedDateUpdatted());

        return row;  // pass the view back
    }

	/**
	 * Overwrites the internal list of notes for display.
	 */
    public void setData(List<Note> data) {
        this.data = data;
    }

	/**
	 * Returns the internal list of notes.
	 * Called by MainActivity::onCreateOptionsMenu().
	 */
    public List<Note> getData() {
        return data;
    }

	/**
	 * Representation of a note preview.
	 * This class represents info used by the note list view.
	 */
    private static class NoteHolder
    {
        private TextView noteTitle;
        private TextView noteContent;
        private TextView noteDate;
    }
}
