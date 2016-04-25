package com.eluss.gdansknumerek;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SingleQueueActivity extends AppCompatActivity implements View.OnClickListener, ApiClient.OnQueuesReceivedCallback {

    Button refreshButton;
    Queue queue;
    TextView ticketNameTextView;
    TextView currentNumberTextView;
    TextView peopleInQueueTextView;
    TextView numberOfHandlersTextView;
    TextView averageTimeTextView;
    TextView updateTimeTextView;
    ZOM zom;
    int selectedPosition;
    ApiClient client;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_queue);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        queue = getIntent().getExtras().getParcelable("queue");
        zom = (ZOM) getIntent().getSerializableExtra("zom");
        selectedPosition = getIntent().getIntExtra("selectedPosition", 0);

        Log.d("this", queue.getName());

        refreshButton = (Button) findViewById(R.id.refresh_button);
        if (refreshButton != null) {
            refreshButton.setOnClickListener(this);
        }

        updateTimeTextView = (TextView) findViewById(R.id.update_time);
        ticketNameTextView = (TextView) findViewById(R.id.ticket_name);
        currentNumberTextView = (TextView) findViewById(R.id.current_number);
        peopleInQueueTextView = (TextView) findViewById(R.id.people_in_queue);
        numberOfHandlersTextView = (TextView) findViewById(R.id.number_of_handlers);
        averageTimeTextView = (TextView) findViewById(R.id.average_time);



        client = new ApiClient();
        client.onQueuesReceivedCallback = this;
        applyModelToView(queue);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (isNetworkAvailable()) {
            progress = new ProgressDialog(this);
            progress.setTitle("Aktualizowanie numerków");
            progress.setMessage("prosimy o cierpliwość...");
            progress.show();
            client.getZOMQueues(this, zom);
        } else {
            showNoInternetConnectionError();
        }

    }

    private void showNoInternetConnectionError() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Brak połączenia z internetem.");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void applyModelToView(Queue queue) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String currentTime = sdf.format(new Date());



        String currentNumber = queue.getLetter() + queue.getCurrentNumber();
        String minutes = queue.getHandlingTime() + " min";


        String updateTime = getString(R.string.update_time_template) + " " + currentTime;
        updateTimeTextView.setText(updateTime);
        ticketNameTextView.setText(queue.getName());
        currentNumberTextView.setText(currentNumber);
        peopleInQueueTextView.setText(queue.getPeopleInQueue());
        numberOfHandlersTextView.setText(queue.getActiveHandlers());
        averageTimeTextView.setText(minutes);
    }

    @Override
    public void didReceiveQueues(ArrayList<Queue> queues, ZOM zom) {
        progress.dismiss();
        queue = queues.get(selectedPosition);
        applyModelToView(queue);
    }

    @Override
    public void errorWhileDownloadingQueues() {
        progress.dismiss();
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Przepraszamy, wystąpił problem podczas pobierania numerków.");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
