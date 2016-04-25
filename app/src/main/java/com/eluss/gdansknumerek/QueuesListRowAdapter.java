package com.eluss.gdansknumerek;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by Eluss on 03/04/16.
 */
public class QueuesListRowAdapter extends BaseAdapter {


    Context context;
    ArrayList<Queue> data;

    private static LayoutInflater inflater = null;

    public QueuesListRowAdapter(Context context, ArrayList<Queue> data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View vi = convertView;
        vi = inflater.inflate(R.layout.queues_list_row, parent, false);

        Drawable background = getBackground(position);
        int textColor = getTextColor(position);

        FrameLayout layout = (FrameLayout) vi.findViewById(R.id.frame);
        layout.setBackground(background);

        Queue queue = data.get(position);

        TextView letter = (TextView) vi.findViewById(R.id.letter);
        letter.setText(queue.letter.toUpperCase());
        letter.setTextColor(textColor);

        QueueNameExtractor extractor = new QueueNameExtractor();
        String queueName = extractor.extractQueueName(queue.getName());

        TextView text = (TextView) vi.findViewById(R.id.name);
        text.setText(queueName);
        text.setTextColor(textColor);
        return vi;
    }

    private Drawable getBackground(int position) {
        if (position % 2 == 0) {
            return ContextCompat.getDrawable(context, R.layout.rounded_button_gray);
        } else {
            return ContextCompat.getDrawable(context, R.layout.rounded_button_red);
        }
    }

    private int getTextColor(int position) {
        if (position % 2 == 0) {
            return ContextCompat.getColor(context, R.color.blackColor);
        } else {
            return ContextCompat.getColor(context, R.color.lightTextColor);
        }
    }


}
