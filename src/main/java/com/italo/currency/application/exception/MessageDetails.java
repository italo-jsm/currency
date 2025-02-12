package com.italo.currency.application.exception;

import java.util.Map;

public class MessageDetails {
    private static final Map<String, String> details = Map.of(
            "unsupported-code", "BaseCode or TargetCode not supported.",
            "invalid-key", "Your API key is invalid.",
            "malformed-request", "Invalid Request.",
            "inactive-account", "Verify the account related to the API key.",
            "quota-reached", "You reached your the account request limit."
    );

    public static String getMessageDetails(String message){
        return details.get(message);
    }
}
