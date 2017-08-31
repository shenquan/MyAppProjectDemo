package com.example.hsqbusiness.util.android.bundle.config;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yb.wang on 15/11/18.
 */
public class BundleConfigFactory {

    private static final List<BundleConfigModel> bundleConfigModels;

    static {
        bundleConfigModels = new ArrayList<BundleConfigModel>();

        /*bundleConfigModels.add(new BundleConfigModel(
                "local",//"if (bizName.equalsIgnoreCase("local/goMain")) {}"中的区分字符串，bizName的host部分。例如：hotel/list则返回host部分"hotel"
                "ctr_hsq_ip.link.ctlocal",//模块包名，看该模块中的manifest文件中的名字
                "ctr_hsq_ip.link.ctlocal.bus.LocalBus",//该模块bus的文件名
                BundleConfigModel.BundleLoadType.AutoLoad,//加载方式
                true
        ));*/

        bundleConfigModels.add(new BundleConfigModel(
                "module1",
                "com.example.module1",
                "com.example.module1.bus.Module1Bus",
                BundleConfigModel.BundleLoadType.AutoLoad,
                true
        ));

    }

    public static List<BundleConfigModel> getBundleConfigModels() {
        return bundleConfigModels;
    }

    public static BundleConfigModel getBundleConfigModelByModuleName(String moduleName) {

        for (BundleConfigModel bundleConfigModel : bundleConfigModels) {
            if (bundleConfigModel.moduleName.equalsIgnoreCase(moduleName)) {
                return bundleConfigModel;
            }
        }
        return null;
    }

    public static List<BundleConfigModel> getLocalLoadBundleConfigModels() {

        List<BundleConfigModel> bundlesModles = new ArrayList<BundleConfigModel>();
        for (BundleConfigModel bundleConfigModel : bundleConfigModels) {
            if (bundleConfigModel.bundleLoadType != BundleConfigModel.BundleLoadType.RemoteLoad) {
                bundlesModles.add(bundleConfigModel);
            }
        }
        return bundlesModles;
    }

    public static List<BundleConfigModel> getLazyLoadInBackgroundConfigModels() {
        List<BundleConfigModel> bundlesModles = new ArrayList<BundleConfigModel>();
        for (BundleConfigModel bundleConfigModel : bundleConfigModels) {
            if (bundleConfigModel.bundleLoadType == BundleConfigModel.BundleLoadType.LazyLoad && bundleConfigModel.isLoadInBackground) {
                bundlesModles.add(bundleConfigModel);
            }
        }
        return bundlesModles;
    }

    public static boolean isDelayLoadBundle(String packageName) {
        for (BundleConfigModel bundleConfigModel : bundleConfigModels) {
            if (bundleConfigModel.packageName.equals(packageName) && bundleConfigModel.bundleLoadType == BundleConfigModel.BundleLoadType.LazyLoad) {
                return true;
            }
        }
        return false;
    }

    public static List<BundleConfigModel> getDelayLoadBundle() {
        List<BundleConfigModel> bundlesModles = new ArrayList<BundleConfigModel>();
        for (BundleConfigModel bundleConfigModel : bundleConfigModels) {
            if (bundleConfigModel.bundleLoadType == BundleConfigModel.BundleLoadType.LazyLoad) {
                bundlesModles.add(bundleConfigModel);
            }
        }
        return bundlesModles;
    }
}
