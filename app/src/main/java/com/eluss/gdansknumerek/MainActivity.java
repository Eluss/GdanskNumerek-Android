package com.eluss.gdansknumerek;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ApiClient.OnQueuesReceivedCallback {

    ApiClient client;
    Button zom1Button;
    Button zom2Button;
    Button zom3Button;
    Button zom4Button;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        zom1Button = (Button) findViewById(R.id.zom1Button);
        zom2Button = (Button) findViewById(R.id.zom2Button);
        zom3Button = (Button) findViewById(R.id.zom3Button);
        zom4Button = (Button) findViewById(R.id.zom4Button);

        zom1Button.setOnClickListener(this);
        zom2Button.setOnClickListener(this);
        zom3Button.setOnClickListener(this);
        zom4Button.setOnClickListener(this);

        client = new ApiClient();
        client.onQueuesReceivedCallback = this;
    }

    @Override
    public void onClick(View v) {
        if (isNetworkAvailable()) {
            getQueuesData(v);
        } else {
            showNoInternetConnectionError();
        }
    }

    private void getQueuesData(View v) {
        progress = new ProgressDialog(this);
        progress.setTitle("Aktualizowanie numerków");
        progress.setMessage("prosimy o cierpliwość...");
        progress.show();
        if (v == zom1Button) {
            client.getZOM1Queues(this);
        } else if (v == zom2Button) {
            client.getZOM2Queues(this);
        } else if (v == zom3Button) {
            client.getZOM3Queues(this);
        } else if (v == zom4Button) {
            client.getZOM4Queues(this);
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

    @Override
    public void didReceiveQueues(ArrayList<Queue> queues, final ZOM zom) {
        progress.dismiss();
        Intent queuesActivity = new Intent(this, QueuesActivity.class);
        queuesActivity.putParcelableArrayListExtra("queues", queues);
        queuesActivity.putExtra("zom", zom);
        startActivity(queuesActivity);
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
