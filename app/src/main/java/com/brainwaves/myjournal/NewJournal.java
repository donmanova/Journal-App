package com.brainwaves.myjournal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.brainwaves.myjournal.data.AppDatabase;
import com.brainwaves.myjournal.data.JournalEntry;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NewJournal extends AppCompatActivity {

    // Extra for the task ID to be received in the intent
    public static final String EXTRA_TASK_ID = "extraTaskId";
    // Extra for the task ID to be received after rotation
    public static final String INSTANCE_TASK_ID = "instanceTaskId";
    // Constants for priority

    private static final int DEFAULT_TASK_ID = -1;
    // Constant for logging
    private static final String TAG = NewJournal.class.getSimpleName();
    // Fields for views
    EditText mTitle, mContent;
    TextView mDate, mTime;
    RadioGroup mRadioGroup;
    Button btnSave;

    private int mTaskId = DEFAULT_TASK_ID;

    // COMPLETED (3) Create AppDatabase member variable for the Database
    // Member variable for the Database
    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_journal);
        initViews();

        mDb = AppDatabase.getInstance(getApplicationContext());

        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_TASK_ID)) {
            mTaskId = savedInstanceState.getInt(INSTANCE_TASK_ID, DEFAULT_TASK_ID);
        }

        Intent intent = getIntent();



    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(INSTANCE_TASK_ID, mTaskId);
        super.onSaveInstanceState(outState);
    }

    /**
     * initViews is called from onCreate to init the member variable views
     */
    private void initViews() {
        mTime = (TextView)findViewById(R.id.tv_Time);
        mDate = (TextView)findViewById(R.id.tv_Date);
        mTitle =(EditText)findViewById(R.id.edt_Title) ;
        mContent=(EditText)findViewById(R.id.edt_content);

        Date date = new Date();
        SimpleDateFormat oSimpleDateFormat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, MMM d, ''yy");
        String myString= oSimpleDateFormat.format(date.getTime());
        String myDate= simpleDateFormat.format(date);
        mTime.setText(myString);
        mDate.setText(myDate);


        btnSave = findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveButtonClicked();
            }
        });
    }

    public void onSaveButtonClicked() {
        String jTitle = mTitle.getText().toString();
        String jContent = mContent.getText().toString();

        String jDate = mDate.getText().toString();
        String jTime = mTime.getText().toString();

        final JournalEntry journalEntry = new JournalEntry(jTitle,jContent,jDate,jTime);

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                // COMPLETED (3) Move the remaining logic inside the run method
                mDb.taskDao().insertTask(journalEntry);
                finish();
            }
        });




        // COMPLETED (8) Create journalEntry variable using the variables defined above

    }


}
