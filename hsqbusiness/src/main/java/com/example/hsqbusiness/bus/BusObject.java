package com.example.hsqbusiness.bus;

import android.content.Context;

public abstract class BusObject {

    /*业务的host名称。对应业务调用的接口名 host/funcname 或者url的HOST域 demo://host/pagename，大小写不敏感*/
    private String prefixAndHost;


    /**
     * 异步任务执行完成之后的callback
     *
     * @author jim
     */
    public interface AsyncCallResultListener {
        /**
         * 异步任务完成后的回调接口
         *
         * @param obj       执行任务
         * @param errorCode 错误码
         */
        public void asyncCallResult(String errorCode, Object... obj);
    }

    /**
     * 初始化
     *
     * @param host 业务的host名称。对应业务调用的接口名 host/funcname 或者url的HOST域 demo://host/pagename，大小写不敏感
     */
    public BusObject(String host) {
        prefixAndHost = host;
    }

    /**
     * 获取BusObject的host名
     *
     * @return 返回BusObject的host名
     */
    public String getHost() {
        return prefixAndHost;
    }

    /**
     * 数据总线，同步执行job
     *
     * @param context 调用上下文。根据调用场景自行决定，常见可选值null/Application/Activity等
     * @param bizName 业务名
     * @param param   业务参数
     * @return 业务返回值
     */
    public abstract Object doDataJob(Context context, String bizName, Object... param);

    /**
     * 数据总线，异步执行job
     *
     * @param context        调用上下文。根据调用场景自行决定，常见可选值null/Application/Activity等
     * @param bizName        业务名
     * @param resultListener 回调数据listener
     * @param param          业务参数
     */
    public abstract void doAsyncDataJob(Context context, String bizName, AsyncCallResultListener resultListener, Object... param);

    /**
     * URL总线，同步执行job
     *
     * @param context 调用上下文。根据调用场景自行决定，常见可选值null/Application/Activity等
     * @param url     业务URL
     * @return 业务返回值
     */
    public abstract Object doURLJob(Context context, String url);

    /**
     * URL总线，异步执行job
     *
     * @param context        调用上下文。根据调用场景自行决定，常见可选值null/Application/Activity等
     * @param url            业务URL
     * @param resultListener 回调数据listener
     */
    public abstract void doAsyncURLJob(Context context, String url, AsyncCallResultListener resultListener);

}
