package com.example.hsqbusiness.util.busmanager;

import android.content.Context;

import com.example.hsqbusiness.util.bus.BusObject;

/**
 * 非bus核心，只是基础工具类而已
 */
public class CommonBusinessBusObject extends BusObject {
    /**
     * 初始化
     *
     * @param host 业务的host名称。对应业务调用的接口名 host/funcname 或者url的HOST域 demo://host/pagename，大小写不敏感
     */
    public CommonBusinessBusObject(String host) {
        super(host);
    }

    @Override
    public Object doDataJob(Context context, String bizName, Object... param) {
        Object firstObj = null;
        if (param != null && param.length > 0) {
            firstObj = param[0];
        }
        Object result = null;

        if ("common/base_goHome".equalsIgnoreCase(bizName)) {
            // common base_goHome params: index
            CommonBaseExecutor.getInstance().goHome(context, (Integer) firstObj);
        } else if ("common/base_quit".equalsIgnoreCase(bizName)) {
            // common base_quit params: null
            CommonBaseExecutor.getInstance().quit(context);
        }


        return result;
    }

    @Override
    public void doAsyncDataJob(Context context, String bizName, AsyncCallResultListener resultListener, Object... param) {

    }

    @Override
    public Object doURLJob(Context context, String url) {
        return null;
    }

    @Override
    public void doAsyncURLJob(Context context, String url, AsyncCallResultListener resultListener) {

    }
}
