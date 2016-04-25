package com.eluss.gdansknumerek;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class QueuesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listView;
    ArrayList<Queue> queues;
    ZOM zom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queues);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        listView = (ListView) findViewById(R.id.listview);
        assert listView != null;
        listView.setOnItemClickListener(this);

        queues = getIntent().getParcelableArrayListExtra("queues");
        zom = (ZOM) getIntent().getSerializableExtra("zom");

        listView.setAdapter(new QueuesListRowAdapter(this, queues));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent singleQueueActivity = new Intent(this, SingleQueueActivity.class);
        Queue queue = queues.get(position);
        singleQueueActivity.putExtra("queue", queue);
        singleQueueActivity.putExtra("zom", zom);
        singleQueueActivity.putExtra("selectedPosition", position);
        startActivity(singleQueueActivity);
    }
}
