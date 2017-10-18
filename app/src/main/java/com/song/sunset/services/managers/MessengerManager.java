package com.song.sunset.services.managers;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.widget.Toast;

import com.song.sunset.services.MessengerService;
import com.song.sunset.utils.AppConfig;

/**
 * Created by Song on 2017/10/16 0016.
 * E-mail: z53520@qq.com
 */

public class MessengerManager {

    private static MessengerManager mInstance = new MessengerManager();

    private MessengerManager() {
    }

    public static MessengerManager getInstance() {
        return mInstance;
    }

    private Messenger mMessenger;

    private Messenger mGetReplyMessenger = new Messenger(new MessengerHandler());

    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MessengerService.SEND_A_MSG:
                    Bundle clientBundle = msg.getData();
                    String clientMessage = clientBundle.getString(MessengerService.MESSAGE);
                    Toast.makeText(AppConfig.getApp(), clientMessage, Toast.LENGTH_SHORT).show();

                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMessenger = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    public void init(Context application) {
        Intent binderIntent = new Intent(application, MessengerService.class);
        application.bindService(binderIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    public void destroy(Context application){
        application.unbindService(mServiceConnection);
    }

    public void sendMessage() {
        Message message = Message.obtain();
        message.what = MessengerService.SEND_A_MSG;
        Bundle bundle = new Bundle();
        bundle.putString(MessengerService.MESSAGE, "message from client");
        message.setData(bundle);
        message.replyTo = mGetReplyMessenger;
        try {
            mMessenger.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
