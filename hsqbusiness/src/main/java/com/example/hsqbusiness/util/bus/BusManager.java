package com.example.hsqbusiness.util.bus;

import android.text.TextUtils;

import com.example.hsqbusiness.util.bus.config.BundleConfigFactory;
import com.example.hsqbusiness.util.bus.config.BundleConfigModel;
import com.example.hsqbusiness.util.util.LogUtil;

import java.lang.reflect.Constructor;

/**
 * 总线注册管理。
 */
public class BusManager {

    private static final String COMMON_HOST_NAME = "common";
    private static final String COMMON_BUS_OBJECT = "com.example.hsqbusiness.util.bus.busmanager.CommonBusinessBusObject";


    public static BusObject registerBusObjectWithHost(String hostName) {
        if (TextUtils.isEmpty(hostName)) {
            return null;
        }
        BusObject retObj = null;
        if (COMMON_HOST_NAME.equalsIgnoreCase(hostName)) {
            try {
                Class<BusObject> clazz = (Class<BusObject>) Class.forName(COMMON_BUS_OBJECT);
                Constructor<BusObject> constructor = clazz.getConstructor(String.class);//动态获取构造函数
                BusObject tmpRetObj = constructor.newInstance(hostName);
                if (Bus.register(tmpRetObj)) {//注册这个object
                    return tmpRetObj;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        BundleConfigModel bundleConfigModel = BundleConfigFactory.getBundleConfigModelByModuleName(hostName);
        if (null == bundleConfigModel)
            return null;
        String className = bundleConfigModel.busObjectName;
        if (!TextUtils.isEmpty(className)) {
            LogUtil.e("BusManager", "BusManager执行：" + className);
            try {
                /*String packageName = bundleConfigModel.packageName;
                if (!TextUtils.isEmpty(packageName)) {
                    if (bundleConfigModel.bundleLoadType == BundleConfigModel.BundleLoadType.RemoteLoad) {
                        if (!BundleFacade.isRemotePackageInstall(packageName)) {
                            LogUtil.f("BusManager", "Remoting Load must call asyncCall method");
                        }
                    } else {
                        BundleFacade.delayLoadInstall(packageName);
                    }

                }*/
                Class<BusObject> clazz = (Class<BusObject>) Class.forName(className);
                Constructor<BusObject> constructor = clazz.getConstructor(String.class);    //动态获取构造函数
                BusObject tmpRetObj = constructor.newInstance(hostName);
                if (Bus.register(tmpRetObj)) {//注册这个object
                    retObj = tmpRetObj;
                }
            } catch (Exception e) {
                String model = bundleConfigModel.packageName;
                LogUtil.f("BusManager", "BusManager error");
                e.printStackTrace();
            }
        }

        return retObj;
    }


}


