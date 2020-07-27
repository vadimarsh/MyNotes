package com.example.mynotes;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynotes.activities.EditNoteActivity;
import com.example.mynotes.data.Note;
import com.example.mynotes.data.NotesRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class NotesRecyclerAdapter extends RecyclerView.Adapter<NotesRecyclerAdapter.ViewHolder> {
    private List<Note> notes;
    private Context context;

    public NotesRecyclerAdapter(Context context, List<Note> notes) {
        this.context = context;
        this.notes = notes;
    }

    @NonNull
    @Override
    public NotesRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_note_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotesRecyclerAdapter.ViewHolder holder, int position) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        String noteTitle = notes.get(position).getTitle();
        String noteContent = notes.get(position).getContent();
        Date dateDeadline = notes.get(position).getDateDeadline();

        if (!noteTitle.isEmpty()) {
            holder.tvTitle.setText(noteTitle);
            holder.tvTitle.setVisibility(View.VISIBLE);
        }
        if (!noteContent.isEmpty()) {
            holder.tvDescription.setText(noteContent);
            holder.tvDescription.setVisibility(View.VISIBLE);
        }
        if (notes.get(position).getIsDeadLine()) {
            holder.tvDate.setText(formatter.format(dateDeadline));
            holder.tvDate.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public Context getContext() {
        return context;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvDescription;
        TextView tvDate;

        ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDescription = itemView.findViewById(R.id.tv_content);
            tvDate = itemView.findViewById(R.id.tv_deadline);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editNote(getAdapterPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    deleteNote(getAdapterPosition());
                    return true;
                }
            });
        }


        void editNote(int position) {
            Intent intent = new Intent(context, EditNoteActivity.class);
            Note note = notes.get(position);
            intent.putExtra("edit_note_id", note.getId());
            context.startActivity(intent);
        }

        void deleteNote(final int position) {
            final NotesRepository notesRepository = App.getNotesRepository();
            final Note note = notes.get(position);

            androidx.appcompat.app.AlertDialog.Builder dialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(context);
            dialogBuilder.setMessage(R.string.message_dialog_note_delete);
            dialogBuilder.setTitle(R.string.title_dialog_note_delete);
            dialogBuilder.setIcon(R.drawable.ic_delete_forever_black_24dp);
            dialogBuilder.setPositiveButton(R.string.text_button_confirm, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    notesRepository.deleteById(note);
                    NotesRecyclerAdapter.this.notifyItemRemoved(position);
                }
            });
            dialogBuilder.setNegativeButton(R.string.text_button_cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            });
            AlertDialog dialog = dialogBuilder.create();
            dialog.show();
        }
    }
}
