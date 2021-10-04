package com.getcapacitor.community.intercom;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.getcapacitor.Bridge;
import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.CapConfig;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;

import io.intercom.android.sdk.Intercom;
import io.intercom.android.sdk.IntercomPushManager;
import io.intercom.android.sdk.UserAttributes;
import io.intercom.android.sdk.identity.Registration;
import io.intercom.android.sdk.push.IntercomPushClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

@CapacitorPlugin(name = "Intercom", permissions = @Permission(strings = {}, alias = "receive"))
public class IntercomPlugin extends Plugin {
    private final IntercomPushClient intercomPushClient = new IntercomPushClient();

    @Override
    public void load() {
        // Set up Intercom
        setUpIntercom();

        // load parent
        super.load();
    }

    @Override
    public void handleOnStart() {
        super.handleOnStart();
        bridge.getActivity().runOnUiThread(new Runnable() {
            @Override public void run() {
                //We also initialize intercom here just in case it has died. If Intercom is already set up, this won't do anything.
                setUpIntercom();
                Intercom.client().handlePushMessage();
            }
        });
    }

    @PluginMethod
    public void registerIdentifiedUser(PluginCall call) {
        try {
            String email = call.getString("email");
            String userId = call.getString("userId");

            Registration registration = new Registration();

            if (email != null && email.length() > 0) {
                registration = registration.withEmail(email);
            }
            if (userId != null && userId.length() > 0) {
                registration = registration.withUserId(userId);
            }
            Intercom.client().registerIdentifiedUser(registration);
            call.resolve();
        } catch (Exception e) {
            call.reject("Registering identified user failed", "FAILED", e);
        }
    }

    @PluginMethod
    public void registerUnidentifiedUser(PluginCall call) {
        try {
            Intercom.client().registerUnidentifiedUser();
            call.resolve();
        } catch (Exception e) {
            call.reject("Registering unidentified user failed", "FAILED", e);
        }
    }

    @PluginMethod
    public void updateUser(PluginCall call) {
        try {
            UserAttributes.Builder builder = new UserAttributes.Builder();
            String userId = call.getString("userId");
            if (userId != null && userId.length() > 0) {
                builder.withUserId(userId);
            }
            String email = call.getString("email");
            if (email != null && email.length() > 0) {
                builder.withEmail(email);
            }
            String name = call.getString("name");
            if (name != null && name.length() > 0) {
                builder.withName(name);
            }
            String phone = call.getString("phone");
            if (phone != null && phone.length() > 0) {
                builder.withPhone(phone);
            }
            String languageOverride = call.getString("languageOverride");
            if (languageOverride != null && languageOverride.length() > 0) {
                builder.withLanguageOverride(languageOverride);
            }
            Map<String, Object> customAttributes = mapFromJSON(call.getObject("customAttributes"));
            builder.withCustomAttributes(customAttributes);
            Intercom.client().updateUser(builder.build());
            call.resolve();
        } catch (Exception e) {
            call.reject("Updating user failed", "FAILED", e);
        }
    }

    @PluginMethod
    public void logout(PluginCall call) {
        try {
            Intercom.client().logout();
            call.resolve();
        } catch (Exception e) {
            call.reject("Logout failed", "FAILED", e);
        }
    }

    @PluginMethod
    public void logEvent(PluginCall call) {
        try {
            String eventName = call.getString("name");
            Map<String, Object> metaData = mapFromJSON(call.getObject("data"));

            if (metaData == null) {
                Intercom.client().logEvent(eventName);
            } else {
                Intercom.client().logEvent(eventName, metaData);
            }

            call.resolve();
        } catch (Exception e) {
            call.reject("Logging event failed", "FAILED", e);
        }
    }

    @PluginMethod
    public void displayMessenger(PluginCall call) {
        try {
            Intercom.client().displayMessenger();
            call.resolve();
        } catch (Exception e) {
            call.reject("Displaying messenger failed", "FAILED", e);
        }
    }

    @PluginMethod
    public void displayMessageComposer(PluginCall call) {
        try {
            Intercom.client().displayMessageComposer();
            call.resolve();
        } catch (Exception e) {
            call.reject("Displaying message composer failed", "FAILED", e);
        }
    }

    @PluginMethod
    public void displayHelpCenter(PluginCall call) {
        try {
            Intercom.client().displayHelpCenter();
            call.resolve();
        } catch (Exception e) {
            call.reject("Displaying help center failed", "FAILED", e);
        }
    }

    @PluginMethod
    public void hideMessenger(PluginCall call) {
        call.unimplemented("Not implemented on android.");
    }

    @PluginMethod
    public void displayLauncher(PluginCall call) {
        try {
            Intercom.client().setLauncherVisibility(Intercom.VISIBLE);
            call.resolve();
        } catch (Exception e) {
            call.reject("Displaying launcher failed", "FAILED", e);
        }
    }

    @PluginMethod
    public void hideLauncher(PluginCall call) {
        try {
            Intercom.client().setLauncherVisibility(Intercom.GONE);
            call.resolve();
        } catch (Exception e) {
            call.reject("Hiding launcher failed", "FAILED", e);
        }
    }

    @PluginMethod
    public void hideIntercom(PluginCall call) {
        try {
            Intercom.client().hideIntercom();
            call.resolve();
        } catch (Exception e) {
            call.reject("Hiding Intercom failed", "FAILED", e);
        }
    }

    @PluginMethod
    public void displayInAppMessages(PluginCall call) {
        try {
            Intercom.client().setInAppMessageVisibility(Intercom.VISIBLE);
            call.resolve();
        } catch (Exception e) {
            call.reject("Displaying in app messages failed", "FAILED", e);
        }
    }

    @PluginMethod
    public void hideInAppMessages(PluginCall call) {
        try {
            Intercom.client().setLauncherVisibility(Intercom.GONE);
            call.resolve();
        } catch (Exception e) {
            call.reject("Hiding in app messages failed", "FAILED", e);
        }
    }

    @PluginMethod
    public void setUserHash(PluginCall call) {
        String hmac = call.getString("hmac");
        if (hmac.isEmpty()) {
            call.reject("User hash not found");
        } else {
            try {
                Intercom.client().setUserHash(hmac);
                call.resolve();
            } catch (Exception e) {
                call.reject("Failed to set user hash " + hmac, "FAILED", e);
            }
        }
    }

    @PluginMethod
    public void setBottomPadding(PluginCall call) {
        String stringValue = call.getString("value");
        if (stringValue.isEmpty()) {
            call.reject("Bottom padding value not found", "NOT_FOUND");
        } else {
            try {
                int value = Integer.parseInt(stringValue);
                Intercom.client().setBottomPadding(value);
                call.resolve();
            } catch (Exception e) {
                call.reject("Set bottom padding failed with value " + stringValue, "FAILED", e);
            }
        }
    }

    @PluginMethod
    public void receivePush(PluginCall call) {
        try {
            JSObject data = call.getData();
            Map message = mapFromJSON(data);
            if (intercomPushClient.isIntercomPush(message)) {
                intercomPushClient.handlePush(this.bridge.getActivity().getApplication(), message);
            }
            call.resolve();
        } catch (Exception e) {
            call.reject("Failed to handle received push", "FAILED", e);
        }
    }

    @PluginMethod
    public void sendPushTokenToIntercom(PluginCall call) {
        String token = call.getString("value");
        if (token.isEmpty()) {
            call.reject("Token not found", "NOT_FOUND");
        } else {
            try {
                intercomPushClient.sendTokenToIntercom(this.bridge.getActivity().getApplication(), token);
                JSObject ret = new JSObject();
                ret.put("token", token);
                call.resolve();
            } catch (Exception e) {
                call.reject("Failed to send push token to Intercom " + token, "FAILED", e);
            }
        }
    }

    private void setUpIntercom() {
        try {
            // get config
            CapConfig config = this.bridge.getConfig();
            String apiKey = config.getPluginConfiguration("Intercom").getString("android-apiKey");
            String appId = config.getPluginConfiguration("Intercom").getString("android-appId");
            String senderId = config.getPluginConfiguration("Intercom").getString("android-senderId");

            // init intercom sdk
            IntercomPushManager.cacheSenderId(this.bridge.getContext(), senderId);
            Intercom.initialize(this.getActivity().getApplication(), apiKey, appId);
        } catch (Exception e) {
            Logger.error("Intercom", "ERROR: Something went wrong when initializing Intercom. Check your configurations", e);
        }
    }

    private static Map<String, Object> mapFromJSON(JSONObject jsonObject) {
        if (jsonObject == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        Iterator<String> keysIter = jsonObject.keys();
        while (keysIter.hasNext()) {
            String key = keysIter.next();
            Object value = getObject(jsonObject.opt(key));
            if (value != null) {
                map.put(key, value);
            }
        }
        return map;
    }

    private static Object getObject(Object value) {
        if (value instanceof JSONObject) {
            value = mapFromJSON((JSONObject) value);
        } else if (value instanceof JSONArray) {
            value = listFromJSON((JSONArray) value);
        }
        return value;
    }

    private static List<Object> listFromJSON(JSONArray jsonArray) {
        List<Object> list = new ArrayList<>();
        for (int i = 0, count = jsonArray.length(); i < count; i++) {
            Object value = getObject(jsonArray.opt(i));
            if (value != null) {
                list.add(value);
            }
        }
        return list;
    }
}
