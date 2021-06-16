package com.thoughtworks.androidtrain.uiflow;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.thoughtworks.androidtrain.R;

public class HandlerActivity extends AppCompatActivity {

    private static final String TAG = "HandlerActivity";

    private static final String HANDLER_THREAD_NAME = "handlerThread";

    private static final int MSG_1 = 1;

    private static final int MSG_2 = 2;

    private HandlerThread handlerThread;

    private Handler workerHandler;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.handler_layout);
        initialize();
    }

    private void initialize() {
        initUI();
        initHandler();
    }

    private void initUI() {
        Button btnMsg1 = findViewById(R.id.btn_msg1);
        Button btnMsg2 = findViewById(R.id.btn_msg2);

        btnMsg1.setOnClickListener(v -> {
            Message msg = Message.obtain();
            msg.what = MSG_1;
            msg.obj = "MSG_1";

            workerHandler.sendMessage(msg);
        });

        btnMsg2.setOnClickListener(v -> {
            Message msg = Message.obtain();
            msg.what = MSG_2;
            msg.obj = "MSG_2";

            workerHandler.sendMessage(msg);
        });
    }

    private void initHandler() {
        handlerThread = new HandlerThread(HANDLER_THREAD_NAME);
        handlerThread.start();

        workerHandler = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case MSG_1:
                    case MSG_2:
                        showMsgBody(msg);
                        break; }
            }
        };
    }

    private void showMsgBody(Message msg) {
        String content = (String) msg.obj;
        runOnUiThread(() -> Toast.makeText(this, content, Toast.LENGTH_SHORT).show());
    }
}
