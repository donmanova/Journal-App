package com.brainwaves.myjournal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.brainwaves.myjournal.data.AppDatabase;
import com.brainwaves.myjournal.data.JournalEntry;

public class JournalViewActivity extends AppCompatActivity {
    public static final String EXTRA_TASK_ID = "extraTaskId";
    // Extra for the task ID to be received after rotation

    private static final int DEFAULT_TASK_ID = -1;

    public static final String INSTANCE_TASK_ID = "instanceTaskId";
    private int mTaskId = DEFAULT_TASK_ID;
    Button mButton;
    private AppDatabase mDb;

    EditText mTitle, mContent;
    TextView mDate, mTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_journal);
        mButton = (Button) findViewById(R.id.btn_save);
        mDb = AppDatabase.getInstance(getApplicationContext());

        mTime = (TextView) findViewById(R.id.tv_Time);
        mDate = (TextView) findViewById(R.id.tv_Date);
        mTitle = (EditText) findViewById(R.id.edt_Title);
        mContent = (EditText) findViewById(R.id.edt_content);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_TASK_ID)) {
            mButton.setText("UPDATE");
            if (mTaskId == DEFAULT_TASK_ID) {

                mTaskId = intent.getIntExtra(EXTRA_TASK_ID, DEFAULT_TASK_ID);

                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {

                        final JournalEntry task = mDb.taskDao().loadTaskById(mTaskId);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (task == null) {
                                    return;
                                }
                                mTitle.setText(task.getTitle());
                                mContent.setText(task.getContent());
                                mDate.setText(task.getDateOfUpdate());
                                mTime.setText(task.getTimeOfUpdate());
                            }

                        });
                    }
                });
            }
        }

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onUpdateButtonClicked();
            }
        });

    }

    public void onUpdateButtonClicked() {
        String jTitle = mTitle.getText().toString();
        String jContent = mContent.getText().toString();

        String jDate = mDate.getText().toString();
        String jTime = mTime.getText().toString();






        final JournalEntry journalEntry = new JournalEntry(jTitle,jContent,jDate,jTime);

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                // COMPLETED (3) Move the remaining logic inside the run method
                if (mTaskId == DEFAULT_TASK_ID) {
                    // insert new task
                    mDb.taskDao().insertTask(journalEntry);
                } else {
                    //update task
                    journalEntry.setId(mTaskId);
                    mDb.taskDao().updateTask(journalEntry);
                }
                finish();
            }
        });
    }
}
