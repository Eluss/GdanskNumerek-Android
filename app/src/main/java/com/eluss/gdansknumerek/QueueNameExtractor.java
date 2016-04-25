package com.eluss.gdansknumerek;

/**
 * Created by Eluss on 05/04/16.
 */
public class QueueNameExtractor {

    public String extractQueueName(String name) {
        if (name.length() == 1) {
            return "";
        }
        int dashPosition = name.indexOf("-");
        String substring = name.substring(dashPosition + 1);
        return substring.trim();
    }

}
