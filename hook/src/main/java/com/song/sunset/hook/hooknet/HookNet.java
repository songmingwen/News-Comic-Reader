package com.song.sunset.hook.hooknet;

import android.content.Context;

import com.song.sunset.hook.BaseHook;
import com.song.sunset.hook.bean.RecordData;
import com.song.sunset.hook.config.ConfigManager;
import com.song.sunset.hook.record.RecordInterface;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

import static com.song.sunset.hook.bean.ApiData.SOCKET_CONNECT;

/**
 * Desc:    监控网络请求
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/12/27 10:41
 */
public class HookNet extends BaseHook {

    public static final String TAG = "sunset-HookNet";

    public HookNet(RecordInterface record) {
        super(record);
    }

    @Override
    public void startObserve(Context context) {
        super.startObserve(context);
        if (ConfigManager.getInstance().apiInConfig(SOCKET_CONNECT)) {
            startJavaSocketHook(context);
        }
//        initAllHooks(context);
    }

    /**
     * socket的connect方法监控
     */
    private void startJavaSocketHook(Context context) {
        try {
            XposedHelpers.findAndHookMethod(Socket.class, "connect", SocketAddress.class, new XC_MethodHook() {

                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    InetSocketAddress socketAddress = (InetSocketAddress) param.args[0];
                    int port = socketAddress.getPort();
                    String ip = socketAddress.getAddress().getHostAddress();
                    String host = socketAddress.getAddress().getHostName();
                    String desc = "port=" + port + "; ip=" + ip + "; host=" + host;
                    append(new RecordData("net_call_JavaSocket", desc));

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            XposedHelpers.findAndHookMethod(Socket.class, "connect", SocketAddress.class, int.class, new XC_MethodHook() {

                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    InetSocketAddress socketAddress = (InetSocketAddress) param.args[0];
                    int port = socketAddress.getPort();
                    String ip = socketAddress.getAddress().getHostAddress();
                    String host = socketAddress.getAddress().getHostName();
                    String desc = "port=" + port + "; ip=" + ip + "; host=" + host;
                    append(new RecordData("net_call_JavaSocket", desc));

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//    private void initAllHooks(Context context) {
//
//        try {
//            final Class<?> httpUrlConnection = XposedHelpers.findClass("java.net.HttpURLConnection", context.getClassLoader());
//            XposedBridge.hookAllConstructors(httpUrlConnection, new XC_MethodHook() {
//
//                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//
//                    if (param.args.length != 1 || param.args[0].getClass() != URL.class) {
//                        return;
//                    }
//
//                    Log.i(TAG, "HttpURLConnection: " + param.args[0] + "");
//                    URL url = (URL) param.args[0];
//                    String desc = url.toString();
//                    append(new RecordData("net_call_HttpURLConnection", desc));
//                }
//            });
//        } catch (Error e) {
//
//        }
//
//        XC_MethodHook RequestHook = new XC_MethodHook() {
//
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//
//                HttpURLConnection urlConn = (HttpURLConnection) param.thisObject;
//
//                if (urlConn != null) {
//                    StringBuilder sb = new StringBuilder();
//                    boolean connected = (boolean) XposedHelpers.getObjectField(param.thisObject, "connected");
//
//                    if (!connected) {
//
//                        Map<String, List<String>> properties = urlConn.getRequestProperties();
//                        if (properties != null && properties.size() > 0) {
//
//
//                            for (Map.Entry<String, List<String>> entry : properties.entrySet()) {
//                                sb.append(entry.getKey() + ": " + entry.getValue() + ", ");
//                            }
//
////                            Collection<List<String>> coll = properties.values();
////                            if (coll != null && coll.size() > 0) {
////                                for (List<String> ls : coll) {
////                                    for (String s : ls) {
////                                        sb.append(s + ", ");
////                                    }
////                                }
////                            }
//                        }
//
//                        DataOutputStream dos = (DataOutputStream) param.getResult();
//
//                        Log.i(TAG, "REQUEST: method=" + urlConn.getRequestMethod() + " " +
//                                "URL=" + urlConn.getURL().toString() + " " +
//                                "Params=" + sb.toString());
//
//                        append(new RecordData("net_call_HttpURLConnectionImpl", urlConn.getURL().toString()));
//                    }
//                }
//
//            }
//        };
//
//
//        XC_MethodHook ResponseHook = new XC_MethodHook() {
//
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//
//                HttpURLConnection urlConn = (HttpURLConnection) param.thisObject;
//
//                if (urlConn != null) {
//                    StringBuilder sb = new StringBuilder();
//                    int code = urlConn.getResponseCode();
//                    if (code == 200) {
//
//                        Map<String, List<String>> properties = urlConn.getHeaderFields();
//                        if (properties != null && properties.size() > 0) {
//
//                            for (Map.Entry<String, List<String>> entry : properties.entrySet()) {
//                                sb.append(entry.getKey() + ": " + entry.getValue() + ", ");
//                            }
//                        }
//                    }
//
//                    Log.i(TAG, "RESPONSE: method=" + urlConn.getRequestMethod() + " " +
//                            "URL=" + urlConn.getURL().toString() + " " +
//                            "Params=" + sb.toString());
//
//                    append(new RecordData("net_call_HttpURLConnectionImpl", urlConn.getURL().toString()));
//                }
//
//            }
//        };
//
//        try {
//            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
//                XposedHelpers.findAndHookMethod("libcore.net.http.HttpURLConnectionImpl", context.getClassLoader(), "getOutputStream", RequestHook);
//            } else {
//                //com.squareup.okhttp.internal.http.HttpURLConnectionImpl
//                final Class<?> httpURLConnectionImpl = XposedHelpers.findClass("com.android.okhttp.internal.http.HttpURLConnectionImpl", context.getClassLoader());
//                if (httpURLConnectionImpl != null) {
//                    XposedHelpers.findAndHookMethod("com.android.okhttp.internal.http.HttpURLConnectionImpl", context.getClassLoader(), "getOutputStream", RequestHook);
//                    XposedHelpers.findAndHookMethod("com.android.okhttp.internal.http.HttpURLConnectionImpl", context.getClassLoader(), "getInputStream", ResponseHook);
//                }
//            }
//        } catch (Error e) {
//
//        }
//
//        try {
//            final Class<?> okHttpClient = XposedHelpers.findClass("okhttp3.OkHttpClient", context.getClassLoader());
//            if (okHttpClient != null) {
//                XposedHelpers.findAndHookMethod(okHttpClient, "newCall", Request.class, new XC_MethodHook() {
//                    @Override
//                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                        Request request = null;
//                        if (param.args[0] != null)
//                            request = (Request) param.args[0];
//                        Log.i(TAG, "OkHttpClient: " + request.url().toString() + "");
//                        append(new RecordData("net_call_HttpURLConnectionImpl", request.url().toString()));
//                    }
//                });
//            }
//        } catch (Error e) {
//
//        }
//
//        XposedHelpers.findAndHookMethod(SSLContext.class, "init",
//                KeyManager[].class, TrustManager[].class, SecureRandom.class, new XC_MethodHook() {
//
//                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                        KeyManager[] km = (KeyManager[]) param.args[0];
//                        TrustManager[] tm_ = (TrustManager[]) param.args[1];
//
//                        if (tm_ != null && tm_[0] != null) {
//                            X509TrustManager tm = (X509TrustManager) tm_[0];
//                            X509Certificate[] chain = new X509Certificate[]{};
//
//                            Log.i(TAG, "Possible pinning.");
//                            boolean check = false;
////                            try {
////                                tm.checkClientTrusted(chain, "");
////                                tm.checkServerTrusted(chain, "");
////                            } catch (CertificateException ex) {
////                                check = true;
////                            }
////                            if (check) {
////                                Log.i(TAG, " Custom TrustManager - Possible pinning.");
////                            } else {
////                                Log.i(TAG, " App not verify SSL.");
////                            }
//                        }
//                    }
//                });
//    }

}
