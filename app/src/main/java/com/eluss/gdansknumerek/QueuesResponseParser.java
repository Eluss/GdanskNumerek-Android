package com.eluss.gdansknumerek;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by Eluss on 31/03/16.
 */
public class QueuesResponseParser {

    ArrayList<Queue> parseResponse(String response) throws XmlPullParserException, IOException {

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();
        xpp.setInput(new StringReader(response));
        int eventType = xpp.getEventType();
        ArrayList<Queue> queues = new ArrayList<Queue>();
        Queue currentQueue = null;
        String value = "";
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_DOCUMENT) {
            } else if (eventType == XmlPullParser.START_TAG) {
                if (xpp.getName().equals("GRUPY")) {
                    currentQueue = new Queue();
                }
            } else if (eventType == XmlPullParser.END_TAG) {
                String name = xpp.getName();
                if (name.equals("LP")) {
                    currentQueue.id = value;
                }
                if (name.equals("LITERAGRUPY")) {
                    currentQueue.letter = value;
                }
                if (name.equals("NAZWAGRUPY")) {
                    currentQueue.name = value;
                    System.out.print(value);
                }
                if (name.equals("AKTUALNYNUMER")) {
                    currentQueue.currentNumber = value;
                }
                if (name.equals("LICZBAKLWKOLEJCE")) {
                    currentQueue.peopleInQueue = value;
                }
                if (name.equals("LICZBACZYNNYCHSTAN")) {
                    currentQueue.activeHandlers = value;
                }
                if (name.equals("CZASOBSLUGI")) {
                    currentQueue.handlingTime = value;
                }
                if (name.equals("GRUPY")) {
                    if (!currentQueue.getName().equals("\n")) { // // TODO: 01/04/16 fix it
                        queues.add(currentQueue);
                    }
                }
            } else if (eventType == XmlPullParser.TEXT) {
                value = xpp.getText();
            }
            eventType = xpp.next();
        }
        return queues;
    }
}
