package com.song.sunset.services;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.song.sunset.utils.AppConfig;

/**
 * Created by Song on 2017/10/16 0016.
 * E-mail: z53520@qq.com
 */

public class MessengerService extends Service {

    public static final int SEND_A_MSG = 0;
    public static final String MESSAGE = "message";

    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SEND_A_MSG:
                    Bundle clientBundle = msg.getData();
                    String clientMessage = clientBundle.getString(MESSAGE);
                    Toast.makeText(AppConfig.getApp(), clientMessage, Toast.LENGTH_SHORT).show();

                    replyMessage(msg);
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }

        private void replyMessage(Message msg) {
            Messenger clientMessenger = msg.replyTo;
            Message message = Message.obtain();
            message.what = SEND_A_MSG;
            Bundle bundle = new Bundle();
            bundle.putString(MESSAGE, "message from service");
            message.setData(bundle);
            try {
                clientMessenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private final Messenger mMessenger= new Messenger(new MessengerHandler());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }
}
