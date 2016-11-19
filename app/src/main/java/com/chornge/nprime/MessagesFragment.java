package com.chornge.nprime;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MessagesFragment extends Fragment implements View.OnClickListener {

    ImageButton sendMessage;
    EditText messageInputEntry;
    String capturedMessage;
    TextView messages_text_view;

    boolean isTabLoaded = false;

    //mandatory
    public MessagesFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_messages, container, false);

        Typeface robotoBold = Typeface.createFromAsset(getActivity().getAssets(), "font/Roboto-Bold.ttf");

        messages_text_view = (TextView) view.findViewById(R.id.messages_text_view);
        messages_text_view.setTypeface(robotoBold);
        messageInputEntry = (EditText) view.findViewById(R.id.message_input_area);

        capturedMessage = messageInputEntry.getText().toString();

        sendMessage = (ImageButton) view.findViewById(R.id.send_button);
        sendMessage.setOnClickListener(this);

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser && !isTabLoaded) {
            Log.e("Second Tab Fragment", "loaded");
            isTabLoaded = true;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onClick(View view) {
        if (view == sendMessage) {
            Toast.makeText(getContext(), capturedMessage + " sent...", Toast.LENGTH_SHORT).show();
        }
    }
}