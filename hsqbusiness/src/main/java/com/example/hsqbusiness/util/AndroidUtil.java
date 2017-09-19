package com.example.hsqbusiness.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.hsqbusiness.MyBaseApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by sqhan on 2017/08/21.
 */
public class AndroidUtil {
    public static final String TAG = "hsq";
    private Context context;
    public static final int FILE_SELECT_CODE = 10001;
    public static final String NAME = "Ddt_share_pref";
    public static final String UNREAD = "vbk.im.unread.message";

    public AndroidUtil(Context context) {
        this.context = context;
    }

    public static Toast mToast;

    //全局只显示一个toast，by韩申权
    public static void showToast(Context context, String text) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    /**
     * 获取当前进程名
     */
    public static String getProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (am != null) {
            List<ActivityManager.RunningAppProcessInfo> aps = am.getRunningAppProcesses();
            if (aps != null) {
                for (ActivityManager.RunningAppProcessInfo rap : aps) {
                    if (rap.pid == pid) {
                        return rap.processName;
                    }
                }
            }
        }
        return "";
    }

    /**
     * 获取当前进程是否是主进程
     *
     * @param context
     * @return
     */
    public static boolean isMainProcess(Context context) {
        String curPrcessName = getProcessName(context);
        return curPrcessName.equals(context.getPackageName());
    }

    /**
     * 获取当前进程是否是remote进程
     *
     * @param context
     * @return
     */
    public static boolean isRomteProcess(Context context) {
        try {
            String curPrcessName = getProcessName(context);
            return curPrcessName.equals(context.getPackageName() + ":remote") ||
                    curPrcessName.equals(context.getPackageName() + ":pushsdk.v1") ||
                    curPrcessName.equals(context.getPackageName() + ":pushservice");
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * json object to map
     *
     * @param json
     * @return
     * @throws JSONException
     */
    public static Map<String, Object> jsonToMap(JSONObject json) throws JSONException {
        Map<String, Object> retMap = new HashMap<String, Object>();

        if (json != JSONObject.NULL) {
            retMap = toMap(json);
        }
        return retMap;
    }

    private static Map<String, Object> toMap(JSONObject object) throws JSONException {
        Map<String, Object> map = new HashMap<String, Object>();

        Iterator<String> keysItr = object.keys();
        while (keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);

            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    private static List<Object> toList(JSONArray array) throws JSONException {
        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }

    /**
     * 得到国家与区域
     *
     * @param context
     * @return
     */
    public static String getCountryAndRegion(Context context) {
        return context.getResources().getConfiguration().locale.getCountry();
    }

    /**
     * 当地通自己的打开H5的页面，里面设置了保存有登录态的cookie与在当前webView打开url
     */
    /*public static void openUrl(Context context, String url) {
        Intent intent = new Intent(context, OpenH5Activity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }*/

    /**
     * @param mContext
     * @param webView
     * @param url
     * @deprecated sqhan已改为内部类
     */
    /*public static void setWebView(DdtBaseActivity mContext, MyWebView webView, String url) {
        if (webView != null && TextUtils.isEmpty(url)) {
            return;
        }
        DdtLogUtil.e("url : " + url);
        WebSettings webSettings = webView.getSettings();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            DdtLogUtil.e("SDK大于4.1");
            webView.enablecrossdomain41();
            webSettings.setAllowUniversalAccessFromFileURLs(true);
            webSettings.setAllowFileAccessFromFileURLs(true);
        } else {
            webView.enablecrossdomain();
        }
        //在当前的webview中跳转到新的url
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBlockNetworkImage(false);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setDomStorageEnabled(true);
        if (Build.VERSION.SDK_INT > 20) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webSettings.setAllowContentAccess(true);
        // 设置user agent
        String userAgentString = webView.getSettings().getUserAgentString();
        String userAgentNew = userAgentString + DdtConst.H5_USER_SIGN + AndroidUtil.getStringVersionName(mContext);
        webSettings.setUserAgentString(userAgentNew);

        // 判断是否有网络，有的话，使用LOAD_DEFAULT，无网络时，使用LOAD_CACHE_ELSE_NETWORK
        if (AndroidUtil.isNetworkAvailable(mContext)) {
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        webSettings.setAllowFileAccess(true);

        //安全控制
        webSettings.setSavePassword(false);
        webView.removeJavascriptInterface("searchBoxJavaBridge_");
        webView.removeJavascriptInterface("accessibility");
        webView.removeJavascriptInterface("accessibilityTraversal");
        if (!AndroidUtil.isNetworkAvailable(mContext)) {
//            showError();
            return;
        }
        Map<String, String> extraHeaders = new HashMap<String, String>();
        extraHeaders.put("Access-Control-Allow-Origin", "*");
        extraHeaders.put("Access-Control-Allow-Headers", "Content-Type");
        webView.loadUrl(url, extraHeaders);

        webView.setWebViewClient(new MyWebViewClient(webView, mContext));
    }*/

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        if (null == context) {
            return 0;
        }
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 得到屏幕的宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        if (null == context) {
            return 0;
        }
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 得到屏幕的高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        if (null == context) {
            return 0;
        }
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param context
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        if (null == context) {
            return 0;
        }
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /*判断double数据类型是否相等 a == b*/
    public static boolean dequals(double a, double b) {
        return Math.abs(a - b) < 0.000001;
    }


    /**
     * 展示图片的函数
     *
     * @param imageView
     * @param imgUrl
     * @param isDefaultRectangle 若为true则默认的是长方形的图片，否则为正方形图片
     */
    /*public static void showUrlImageCommon(ImageView imageView, String imgUrl, boolean isDefaultRectangle) {
        DisplayImageOptions.Builder optsBuilder = new DisplayImageOptions.Builder();
        if (isDefaultRectangle) {
            optsBuilder.showStubImage(R.drawable.nopic_rectangle)
                    .showImageForEmptyUri(R.drawable.nopic_rectangle)
                    .showImageOnFail(R.drawable.nopic_rectangle)
                    .cacheInMemory(true)
                    .cacheOnDisc(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .build();
        } else {
            optsBuilder.showStubImage(R.drawable.nopic_square)
                    .showImageForEmptyUri(R.drawable.nopic_square)
                    .showImageOnFail(R.drawable.nopic_square)
                    .cacheInMemory(true)
                    .cacheOnDisc(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .build();
        }
        ImageLoader.getInstance().displayImage(imgUrl, imageView, optsBuilder.build());
    }*/


    /**
     * 以"&"，分离键值对，前提是首先获取链接的query，下面这个函数是非官方的，不保险
     *
     * @param query
     * @return
     */
    public static Map<String, String> splitQuery(String query) {
        Map<String, String> map = new HashMap<String, String>();
        String[] patterns = query.split("&");
        if (patterns != null && patterns.length > 0) {
            for (int i = 0; i < patterns.length; i++) {
                String pattern = patterns[i];
                int index = pattern.indexOf("=");
                if (index != -1) {
                    map.put(pattern.substring(0, index), pattern.substring(index + 1, pattern.length()));
                }
            }
        }
        return map;
    }

    public static boolean getDebugOrRelease(Context context) {
        ApplicationInfo info = context.getApplicationInfo();
        boolean isDug = (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0 ? true : false;
        return isDug;
    }

    /**
     * Apk是debug还是release，不用上面的getDebugOrRelease()方法，用这个方法
     *
     * @return
     */
    public static boolean isApkDebug() {
        try {
            ApplicationInfo info = MyBaseApplication.getInstance().getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {

        }
        return false;
    }

    /**
     * 利用SP存放数据，SP其实也是文件存储
     *
     * @param context
     * @param key
     * @param value
     */
    public static void putString(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }

    /**
     * 带默认值的
     *
     * @param context
     * @param key
     * @param def
     * @return
     */
    public static String getString(Context context, String key, String def) {
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getString(key, def);
    }

    /**
     * 不带默认值的
     *
     * @param context
     * @param key
     * @return
     */
    public static String getString(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getString(key, "");
    }

    /**
     * 获取手机MAC地址
     *
     * @return
     */
    public static String getMacAddress(Context c) {
        WifiManager wifiManager = (WifiManager) c.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        return wifiInfo.getMacAddress();
    }

    /**
     * 获取手机的唯一标识
     *
     * @return
     */
    public static String getImei(Context c) {
        Object telephonyService = c.getSystemService(Context.TELEPHONY_SERVICE);
        TelephonyManager telephonyManager = (TelephonyManager) telephonyService;
        return telephonyManager.getDeviceId();
    }


    /**
     * 把client id存到shared preferences里
     *
     * @param clientId
     */
    public static void putClientIdToSharedPreference(Context c, String clientId) {
        int mode = Activity.MODE_PRIVATE;
        SharedPreferences sharedPreferences = c.getSharedPreferences("ClientId", mode);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ClientId", clientId);
        editor.commit();
    }

    /**
     * 从shared preferences里取出client id
     *
     * @return
     */
    public static String getClientIdFromSharedPreference(Context c) {
        int mode = Activity.MODE_PRIVATE;
        SharedPreferences sharedPreferences = c.getSharedPreferences("ClientId", mode);
        return sharedPreferences.getString("ClientId", "");
    }

    /**
     * 检查当前网络是否可用
     *
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        Object connectivityService = context.getSystemService(Context.CONNECTIVITY_SERVICE);
        ConnectivityManager connectivityManager = (ConnectivityManager) connectivityService;
        if (connectivityManager == null) {
            return false;
        } else {
            NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
            if (networkInfos != null && networkInfos.length > 0) {
                for (NetworkInfo networkInfo : networkInfos) {
                    if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 进入首页判断是否连接网络，否则弹出网络提示
     *
     * @param context
     */
    public static void isConnectNetwork(Context context) {
        if (!AndroidUtil.isNetworkAvailable(context)) {
            Toast.makeText(context, "网络不给力哦，请重试~", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * ● android:versionCode:主要是用于版本升级所用，是INT类型的，第一个版本定义为1，以后递增，
     * 这样只要判断该值就能确定是否需要升级，该值不显示给用户。。
     * return
     */
    public static int getIntVersionCode(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            int flags = PackageManager.GET_CONFIGURATIONS;
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), flags);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return 0;
    }

    public int getVersionCode() {
        try {
            PackageManager packageManager = context.getPackageManager();
            int flags = PackageManager.GET_CONFIGURATIONS;
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), flags);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return 0;
    }

    /**
     * 获取软件版本号,获取的是Main module中的build.gradle的versionName。
     * ● android:versionName:这个是我们常说明的版本号，
     * 由三部分组成<major>.<minor>.<point>,该值是个字符串，可以显示给用户。
     * return 2.0
     */
    public static String getStringVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            int flags = PackageManager.GET_CONFIGURATIONS;
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), flags);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return "";
    }

    /**
     * 从shared preferences里取出client id
     *
     * @return
     */
    public String getClientIdFromSharedPreference() {
        int mode = Activity.MODE_PRIVATE;
        SharedPreferences sharedPreferences = context.getSharedPreferences("ClientId2", mode);
        return sharedPreferences.getString("ClientId2", "");
    }


    /**
     * 获得clientId
     *
     * @return
     */
    /*public String getClientId2() {
        String clientId = getClientIdFromSharedPreference();
        String value;
        if (clientId.isEmpty()) {
            value = generateClientId();
        } else {
            value = clientId;
        }
        // for debug
        value = value.replace(":", "");

        return value;
    }*/

    /**
     * 生成clientId
     *
     * @return
     *//*
    public String generateClientId() {
        String macAddress;
        String imei;
        String value;
        macAddress = getMacAddress();
        imei = getImei();
        if (null == macAddress || macAddress.length() <= 0 || null == imei || imei.length() <= 0) {
            String guid = StringUtil.GenerateGUID();
            putClientIdToSharedPreference(guid);
            value = guid;
        } else {
            putClientIdToSharedPreference(imei + macAddress);
            value = imei + macAddress;
        }
        value = value.replace(":", "");
        return value;
    }*/

    /**
     * 把client id存到shared preferences里
     *
     * @param clientId
     */
    public void putClientIdToSharedPreference(String clientId) {
        int mode = Activity.MODE_PRIVATE;
        SharedPreferences sharedPreferences = context.getSharedPreferences("ClientId2", mode);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ClientId2", clientId);
        editor.apply();
    }

    /**
     * 获取手机的唯一标识
     *
     * @return
     */
    public String getImei() {
        Object telephonyService = context.getSystemService(Context.TELEPHONY_SERVICE);
        TelephonyManager telephonyManager = (TelephonyManager) telephonyService;
        return telephonyManager.getDeviceId();
    }

    /**
     * 获取手机MAC地址
     *
     * @return
     */
    public String getMacAddress() {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        return wifiInfo.getMacAddress();
    }

    /**
     * 获得clientId
     *
     * @return
     */
    /*public static String getClientId(Context c) {
        String clientId = getClientIdFromSharedPreference(c);
        String value;
        if (clientId.isEmpty()) {
            value = generateClientId(c);
            // putClientIdToSharedPreference(value);
        } else {
            value = clientId;
        }
        // for debug
        value = value.replace(":", "");

        return value;
    }*/

    /**
     * 生成clientId
     *
     * @return
     */
    /*public static String generateClientId(Context c) {
        String macAddress;
        String imei;
        String value;
        macAddress = getMacAddress(c);
        imei = getImei(c);
        if (null == macAddress || macAddress.length() <= 0 || null == imei || imei.length() <= 0) {
            String guid = StringUtil.GenerateGUID();
            putClientIdToSharedPreference(c, guid);
            value = guid;
        } else {
            putClientIdToSharedPreference(c, imei + macAddress);
            value = imei + macAddress;
        }
        value = value.replace(":", "");
        return value;
    }*/

    /**
     * 获取Android系统版本号
     *
     * @return
     */
    public static String getAndroidVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return
     */
    public static String getPhoneModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 跳转外部浏览器
     * param url
     */
    public static void openBrowser(Activity context, String url) {
        LogUtil.e(url);
        if (TextUtils.isEmpty(url)) {
            return;
        }
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
//        intent.setDataAndType(Uri.parse(url), "application/pdf");
        context.startActivity(intent);
    }

    /**
     * 跳转第三方播放器
     * param url 文件地址
     */
    public static void playAudio(Activity context, String url) {
        LogUtil.e(url);
        if (TextUtils.isEmpty(url)) {
            return;
        }

        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "video/mp4");
        context.startActivity(intent);
    }

    /**
     * 文件管理器
     */
    public static void showFileChooser(Activity context) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            context.startActivityForResult(Intent.createChooser(intent, "请选择文件"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, "请安装文件管理器", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public static boolean isHasSdcard() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    public static File newFile(String d, String f) {
        File file = null;
        if (isHasSdcard()) {
            file = new File(Environment.getExternalStorageDirectory() + File.separator + d);
            if (!file.exists()) {
                file.mkdirs();
            }
            File[] fs = file.listFiles();
            if (fs != null) {
                for (int i = 0; i < fs.length; i++) {
                    fs[i].delete();
                }
            }
            file = new File(file, f);
        }
        return file;
    }

    /**
     * 检查app是否开启通知
     *
     * @param context
     * @return
     */
    public static boolean checkOp(Context context) {
        try {
            if (Build.VERSION.SDK_INT < 19) {
                return true;
            }
            AppOpsManager ops = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            String packageName = context.getPackageName();
            int uid = context.getApplicationInfo().uid;
            String op = "OP_POST_NOTIFICATION";
            Class<?> cls = Class.forName("android.app.AppOpsManager");
            Method method = cls.getDeclaredMethod("checkOpNoThrow", int.class, int.class, String.class);
            Field oop = cls.getDeclaredField(op);
            int value = (int) oop.get(Intent.class);

            int res = (int) (method.invoke(ops, value, uid, packageName));
            return res == AppOpsManager.MODE_ALLOWED;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取应用详情页面intent
     *
     * @return
     */
    public static Intent getAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        return localIntent;
    }

    public static boolean isWifi(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    /**
     * 将clientId转成JSON格式，然后编码
     *
     * @return
     */
    /*public static String deviceToken(Context context) {
        String value = new AndroidUtil(context).getClientId(context);
        JSONObject jsonObject;
        jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("token", value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return StringUtil.encodeString(jsonObject != null ? jsonObject.toString() : null);
    }*/

    /**
     * 获取string类型的网络类型，-1:unknown, 0:none,1:wifi,2:2G,3:3G,4:4G
     *
     * @return
     */
    public static String GetNetworkTypeString() {
        String strNetworkType = "";

        NetworkInfo networkInfo = ((ConnectivityManager) MyBaseApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                strNetworkType = "WIFI";
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                String _strSubTypeName = networkInfo.getSubtypeName();

                // TD-SCDMA   networkType is 17
                int networkType = networkInfo.getSubtype();
                switch (networkType) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN: //api<8 : replace by 11
                        strNetworkType = "2G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
                    case TelephonyManager.NETWORK_TYPE_EHRPD:  //api<11 : replace by 12
                    case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
                        strNetworkType = "3G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
                        strNetworkType = "4G";
                        break;
                    default:
                        // http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信 三种3G制式
                        if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA") || _strSubTypeName.equalsIgnoreCase("WCDMA") || _strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                            strNetworkType = "3G";
                        } else {
                            strNetworkType = _strSubTypeName;
                        }

                        break;
                }
            }
        }
        return strNetworkType;
    }

    /**
     * 获取int类型的网络类型，-1:unknown, 0:none,1:wifi,2:2G,3:3G,4:4G
     */
    public static int GetNetworkTypeInt() {
        int strNetworkType = -1;

        NetworkInfo networkInfo = ((ConnectivityManager) MyBaseApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                strNetworkType = 1;
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                String _strSubTypeName = networkInfo.getSubtypeName();

                // TD-SCDMA   networkType is 17
                int networkType = networkInfo.getSubtype();
                switch (networkType) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN: //api<8 : replace by 11
                        strNetworkType = 2;
                        break;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
                    case TelephonyManager.NETWORK_TYPE_EHRPD:  //api<11 : replace by 12
                    case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
                        strNetworkType = 3;
                        break;
                    case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
                        strNetworkType = 4;
                        break;
                    default:
                        // http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信 三种3G制式
                        if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA") || _strSubTypeName.equalsIgnoreCase("WCDMA") || _strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                            strNetworkType = 3;
                        }
                        break;
                }
            }
        }
        return strNetworkType;
    }

    /**
     * 获取软件版本号
     * return 2.0
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            int flags = PackageManager.GET_CONFIGURATIONS;
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), flags);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return null;
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 程序是否在前台运行
     */
    public static boolean isAppOnForeground() {
        ActivityManager activityManager = (ActivityManager) MyBaseApplication.getInstance().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = MyBaseApplication.getInstance().getPackageName();
        /**
         * 获取Android设备中所有正在运行的App
         */
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null)
            return false;
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }
}
