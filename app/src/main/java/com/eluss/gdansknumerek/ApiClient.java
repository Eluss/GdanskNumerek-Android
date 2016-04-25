package com.eluss.gdansknumerek;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Eluss on 31/03/16.
 */
public class ApiClient {

    OnQueuesReceivedCallback onQueuesReceivedCallback;
    String zom1URL = "http://www.gdansk.pl/urzad/download/dane-otwarty-gdansk/qmatic-zom0.xml";
    String zom2URL = "http://www.gdansk.pl/urzad/download/dane-otwarty-gdansk/qmatic-zom1.xml";
    String zom3URL = "http://www.gdansk.pl/urzad/download/dane-otwarty-gdansk/qmatic-zom2.xml";
    String zom4URL = "http://www.gdansk.pl/urzad/download/dane-otwarty-gdansk/qmatic-zom3.xml";

    QueuesResponseParser parser = new QueuesResponseParser();


    public void getZOMQueues(Context context, ZOM zom) {
        switch (zom) {
            case ZOM1:
                getZOM1Queues(context);
                break;
            case ZOM2:
                getZOM2Queues(context);
                break;
            case ZOM3:
                getZOM3Queues(context);
                break;
            case ZOM4:
                getZOM4Queues(context);
                break;
            default:
                break;
        }
    }

    public void getZOM1Queues(Context context) {
        getQueues(zom1URL, context, ZOM.ZOM1);
    }

    public void getZOM2Queues(Context context) {
        getQueues(zom2URL, context, ZOM.ZOM2);
    }

    public void getZOM3Queues(Context context) {
        getQueues(zom3URL, context, ZOM.ZOM3);
    }

    public void getZOM4Queues(Context context) {
        getQueues(zom4URL, context, ZOM.ZOM4);
    }

    private void getQueues(String url, Context context, final ZOM zom) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            String newStr = URLDecoder.decode(URLEncoder.encode(response, "iso8859-1"), "UTF-8");
                            ArrayList<Queue> queues = parser.parseResponse(newStr);
                            onQueuesReceivedCallback.didReceiveQueues(queues, zom);
                        } catch (XmlPullParserException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onQueuesReceivedCallback.errorWhileDownloadingQueues();
            }
        });


        Volley.newRequestQueue(context).add(stringRequest);

    }

    interface OnQueuesReceivedCallback {
        void didReceiveQueues(ArrayList<Queue> queues, ZOM zom);
        void errorWhileDownloadingQueues();
    }

}
