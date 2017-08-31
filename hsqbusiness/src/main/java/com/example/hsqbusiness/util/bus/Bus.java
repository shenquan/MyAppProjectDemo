package com.example.hsqbusiness.util.bus;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import com.example.hsqbusiness.util.bus.config.BundleConfigFactory;
import com.example.hsqbusiness.util.bus.config.BundleConfigModel;
import com.example.hsqbusiness.util.util.LogUtil;

import java.util.HashMap;


/**
 * 总线，各BU需先注册自己的Bus实现
 */
public class Bus {

    /**
     * 各bu注册的host和具体BusObject实现对应表，大小写不敏感，会被转为小写。
     */
    private static final HashMap<String, BusObject> hostMap = new HashMap<String, BusObject>();

    /**
     * 注册业务对象到总线
     *
     * @param busObject 处理业务job的对象
     * @throws IllegalArgumentException 如果host已被注册，抛出异常不可重复注册
     */
    public synchronized static boolean register(BusObject busObject) {
        if (busObject == null) {
            return false;
        }

        String host = busObject.getHost().toLowerCase();
        if (hostMap.containsKey(host)) {
            LogUtil.f("BusManager", host + " :已注册，不可重复注册");
        }

        hostMap.put(host, busObject);
        return true;
    }

    /**
     * 数据总线，跨业务模块异步调用
     *
     * @param context        调用上下文。根据调用场景自行决定，常见可选值null/Application/Activity等
     * @param bizName        业务接口名，大小写不敏感
     * @param resultListener 业务job处理完成回调数据
     * @param param          附加参数列表
     */
    public static void asyncCallData(final Context context, final String bizName, final BusObject.AsyncCallResultListener resultListener, final Object... param) {
        String hostName = parseBizNameHost(bizName);
        final BundleConfigModel bundleConfigModel = BundleConfigFactory.getBundleConfigModelByModuleName(hostName);
        if (bundleConfigModel != null) {
            BusObject obj = findBusObject(parseBizNameHost((bizName)));
            if (obj != null)
                obj.doAsyncDataJob(context, bizName, resultListener, param);
        }


    }

    /**
     * 数据总线，跨业务模块同步调用
     *
     * @param context 调用上下文。根据调用场景自行决定，常见可选值null/Application/Activity等
     * @param bizName 业务接口名，大小写不敏感。--by hsq：比如"module1/goMain"
     * @param param   附加参数
     * @return 直接返回对方接口的返回值
     */
    public static Object callData(Context context, String bizName, Object... param) {
        BusObject obj = findBusObject(parseBizNameHost(bizName));
        if (obj != null) {
            return obj.doDataJob(context, bizName, param);
        }

        LogUtil.f("Bus中未找到" + bizName);

        return null;
    }

    /**
     * 解析bizName的host部分。例如：hotel/list则返回host部分"hotel"
     *
     * @param bizName 业务接口名，大小写不敏感
     * @return 业务名称的host部分
     */
    private static String parseBizNameHost(String bizName) {
        if (TextUtils.isEmpty(bizName)) {
            return null;
        }
        int slashIndex = bizName.indexOf('/');
        if (slashIndex != -1) {
            return bizName.substring(0, slashIndex);
        } else {
            throw new IllegalArgumentException("Bus url error: " + bizName);
        }
    }

    /**
     * 解析url的host部分。例如：demo://hotel/list则返回host部分"hotel"
     *
     * @param url URL链接
     * @return HOST段
     */
    private static String parseUrlHost(String url) {
        Uri uri = Uri.parse(url);
        return uri.getHost();
    }

    /**
     * 根据host找已注册的对应BusObject
     *
     * @param host 业务的host名称。对应业务调用的接口名 host/funcname 或者url的HOST域 demo://host/pagename
     * @return 已注册的对应BusObject
     */
    private static BusObject findBusObject(String host) {
        if (TextUtils.isEmpty(host)) {
            return null;
        }

        BusObject obj = hostMap.get(host.toLowerCase());
        if (obj == null) {
            obj = BusManager.registerBusObjectWithHost(host);
        }
        return obj;
    }


    /**
     * URL总线，跨业务模块同步调用
     *
     * @param context 调用上下文。根据调用场景自行决定，常见可选值null/Application/Activity等
     * @param url     业务url，大小写不敏感
     * @return 直接返回对方接口的返回值
     */
    public static Object callURL(Context context, String url) {
        BusObject obj = findBusObject(parseUrlHost(url));
        if (obj != null) {
            return obj.doURLJob(context, url);
        }

        return null;
    }

    /**
     * URL总线，跨业务模块异步调用（不建议使用）
     *
     * @param context        调用上下文。根据调用场景自行决定，常见可选值null/Application/Activity等
     * @param url            业务url，大小写不敏感
     * @param resultListener 业务job处理完成回调数据
     */
    public static void asyncCallURL(Context context, String url, BusObject.AsyncCallResultListener resultListener) {
        BusObject obj = findBusObject(parseUrlHost(url));
        if (obj != null) {
            obj.doAsyncURLJob(context, url, resultListener);
        }
    }


}
