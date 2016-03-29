package com.apaza.moises.practices.notifications;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.apaza.moises.practices.R;
import com.apaza.moises.practices.Utils;

public class TestNotificationFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private NotificationManager notificationManager;


    private Button btnSendNotification;

    private OnFragmentInteractionListener mListener;

    public static TestNotificationFragment newInstance(String param1, String param2) {
        TestNotificationFragment fragment = new TestNotificationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public TestNotificationFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test_notification, container, false);
        btnSendNotification = (Button)view.findViewById(R.id.btnSendNotification);
        btnSendNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotification("You have a trip, we will send a car to you home");
            }
        });
        return view;
    }

    public void sendNotification(String message){
        notificationManager = (NotificationManager)getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intentYes = new Intent();
        intentYes.setAction(Utils.YES_ACTION);
        PendingIntent pendingIntentYes = PendingIntent.getBroadcast(getContext(), 12345, intentYes, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent intentNo = new Intent();
        intentNo.setAction(Utils.NO_ACTION);
        PendingIntent pendingIntentNo = PendingIntent.getBroadcast(getContext(), 12345, intentNo, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification builder = new NotificationCompat.Builder(getContext())
                .setSmallIcon(R.drawable.ic_river_plate)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_SOUND)
                .setAutoCancel(true)
                .setContentText(message)
                .setTicker(getResources().getString(R.string.app_name))
                .addAction(android.R.drawable.checkbox_on_background, "YES", pendingIntentYes)
                .addAction(android.R.drawable.ic_delete, "NO", pendingIntentNo)
                        .build();

        notificationManager.notify(Utils.NOTIFICATION_ID, builder);
        //getActivity().finish();
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

}
