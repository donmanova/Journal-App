package com.brainwaves.myjournal;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brainwaves.myjournal.data.JournalEntry;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class JournalAdapter extends RecyclerView.Adapter<JournalAdapter.TaskViewHolder>  {

    // Constant for date format
    private static final String DATE_FORMAT = "dd/MM/yyy";

    // Member variable to handle item clicks
    final private ItemClickListener mItemClickListener;
    // Class variables for the List that holds task data and the Context
    private List<JournalEntry> mJournalEntry;
    private Context mContext;
    // Date formatter
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());

    /**
     * Constructor for the TaskAdapter that initializes the Context.
     *
     * @param context  the current Context
     * @param listener the ItemClickListener
     */
    public JournalAdapter(Context context, ItemClickListener listener) {
        mContext = context;
        mItemClickListener = listener;
    }


    @Override
    public void onBindViewHolder(JournalAdapter.TaskViewHolder holder, int position) {
        JournalEntry journalEntry = mJournalEntry.get(position);
        //String description = journalEntry.getContent();
        String mTitle = journalEntry.getTitle();
        String updateDate = journalEntry.getDateOfUpdate();
        String updateTime = journalEntry.getTimeOfUpdate();

        //Set values


        holder.dateOfUpdate.setText(updateDate);
        holder.timeOfUpdate.setText(updateTime);
        holder.journalTitle.setText(mTitle);
        //holder.journalContent.setText(description);



    }

    @Override
    public int getItemCount() {
        if (mJournalEntry == null) {
            return 0;
        }
        return mJournalEntry.size();
    }


    public List<JournalEntry> getTasks() {
        return mJournalEntry;
    }

    /**
     * When data changes, this method updates the list of taskEntries
     * and notifies the adapter to use the new values on it
     */
    public void setTasks(List<JournalEntry> journalEntries) {
        mJournalEntry = journalEntries;
        notifyDataSetChanged();
    }




    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the task_layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.journal_layout, parent, false);

        return new TaskViewHolder(view);


    }public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }



    class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Class variables for the task description and priority TextViews
        TextView dateOfUpdate, timeOfUpdate, journalTitle, journalContent;

        //TextView priorityView;

        /**
         * Constructor for the TaskViewHolders.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public TaskViewHolder(View itemView) {
            super(itemView);

            dateOfUpdate = itemView.findViewById(R.id.datedUpdated);
            timeOfUpdate = itemView.findViewById(R.id.updatedTime);
            journalTitle = itemView.findViewById(R.id.title);
           // journalContent=itemView.findViewById(R.id.content);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int elementId = mJournalEntry.get(getAdapterPosition()).getId();
            mItemClickListener.onItemClickListener(elementId);
        }
    }
}
