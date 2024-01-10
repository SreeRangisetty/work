package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private EditText taskInput, titleInput, descriptionInput;
    private DatePicker dueDate;
    private Spinner prioritySpinner, statusSpinner, categorySpinner;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHelper dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();

        titleInput = findViewById(R.id.titleInput);
        descriptionInput = findViewById(R.id.descriptionInput);
        dueDate = findViewById(R.id.dueDate);
        taskInput = findViewById(R.id.taskInput);
        prioritySpinner = findViewById(R.id.priority);
        statusSpinner = findViewById(R.id.statusSpinner);
        categorySpinner = findViewById(R.id.categorySpinner);

        ArrayAdapter<CharSequence> priorityAdapter = ArrayAdapter.createFromResource(
                this, R.array.priority_levels, android.R.layout.simple_spinner_item);
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prioritySpinner.setAdapter(priorityAdapter);

        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(
                this, R.array.task_status, android.R.layout.simple_spinner_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(statusAdapter);

        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(
                this, R.array.task_categories, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);
    }

    public void addTask(View view) {
        String title = titleInput.getText().toString();
        String description = descriptionInput.getText().toString();
        String task = taskInput.getText().toString();
        int day = dueDate.getDayOfMonth();
        int month = dueDate.getMonth();
        int year = dueDate.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        String priority = prioritySpinner.getSelectedItem().toString();
        String status = statusSpinner.getSelectedItem().toString();
        String category = categorySpinner.getSelectedItem().toString();

        ContentValues values = new ContentValues();
        values.put(DBHelper.TaskEntry.COLUMN_TITLE, title);
        values.put(DBHelper.TaskEntry.COLUMN_DESCRIPTION, description);
        values.put(DBHelper.TaskEntry.COLUMN_TASK, task);
        values.put(DBHelper.TaskEntry.COLUMN_DUE_DATE, calendar.getTimeInMillis());
        values.put(DBHelper.TaskEntry.COLUMN_PRIORITY, priority);
        values.put(DBHelper.TaskEntry.COLUMN_STATUS, status);
        values.put(DBHelper.TaskEntry.COLUMN_CATEGORY, category);

        long newRowId = db.insert(DBHelper.TaskEntry.TABLE_NAME, null, values);
        TextView taskDetailsTextView = findViewById(R.id.taskDetailsTextView);

        String taskDetails = "Title: " + title +
                "\nDescription: " + description +
                "\nTask: " + task +
                "\nDue Date: " + calendar.getTime().toString() + // Convert the date to a readable format
                "\nPriority: " + priority +
                "\nStatus: " + status +
                "\nCategory: " + category + "\n\n";

        // Append the new task details to the existing text in the TextView
        taskDetailsTextView.append(taskDetails);
        if (newRowId != -1) {
            Toast.makeText(this, "Task added successfully", Toast.LENGTH_SHORT).show();
            titleInput.setText("");
            descriptionInput.setText("");
            taskInput.setText("");
        } else {
            Toast.makeText(this, "Failed to add task", Toast.LENGTH_SHORT).show();
        }
    }
}

