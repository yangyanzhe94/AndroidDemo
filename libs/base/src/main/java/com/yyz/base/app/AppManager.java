package com.yyz.base.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.ColorStateList;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.yyz.base.R;
import com.yyz.base.db.DbManager;
import com.yyz.base.header.HeaderManager;
import com.yyz.base.http.DownLoadManager;
import com.yyz.base.http.HttpManager;
import com.yyz.base.http.utils.CommonUtils;
import com.yyz.base.image.ImageManager;
import com.yyz.base.permissions.PermissionsManager;
import com.yyz.base.statistics.EventManager;
import com.yyz.base.utils.UtilsManager;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/6/30.
 */

public final class AppManager {
    public enum Type {
        /**
         * 联调
         */
        DEBUG, /**
         * 开发环境
         */
        DEV, /**
         * 测试环境
         */
        TEST, /**
         * 预发布环境
         */
        VERIFY, /**
         * 生产环境
         */
        PRODUCTION,
    }

    private AppManager() {
    }

    public static Application app() {
        if (Manager.app == null) {
            try {
                // 在IDE进行布局预览时使用
                Class<?> renderActionClass =
                    Class.forName("com.android.layoutlib.bridge.impl.RenderAction");
                Method method = renderActionClass.getDeclaredMethod("getCurrentContext");
                Context context = (Context) method.invoke(null);
                Manager.app = new MockApplication(context);
            } catch (Throwable ignored) {
                throw new RuntimeException(
                    "please invoke AppManager.Manager.init(app) on Application#onCreate()"
                        + " and register your Application in manifest.");
            }
        }
        return Manager.app;
    }

    public static boolean isDebug() {
        return Manager.debug;
    }

    /**
     * 保存文件名称
     */
    public static String name() {
        return "/daodao/cp/";
    }

    /**
     * 应用密钥
     */
    public static String getAppKey() {
        return Manager.mAppKey;
    }

    /**
     * 是否可以打印请求log
     * @param type
     * @return
     */
    private static boolean isLogRequest(Enum type) {

        return type == Type.DEV || type == Type.TEST || type == Type.DEBUG;
    }

    /**
     * 网络请求
     */
    public static HttpManager http() {
        if (Manager.mHttpManager == null) {
            Manager.setHttpManager(HttpManager.getInstance(Manager.app, Manager.mBaseUrl,
                isLogRequest(Manager.mType)));
        }
        return Manager.mHttpManager;
    }

    /**
     * Type
     */
    public static Type getType() {
        return Manager.mType;
    }

    /**
     * 下载
     */
    public static DownLoadManager down() {
        if (Manager.mDownLoadManager == null) {
            Manager.setDownLoadManager(DownLoadManager.getInstance(Manager.app));
        }
        return Manager.mDownLoadManager;
    }

    /**
     * 线程池
     */
    public static ExecutorService executor() {
        if (Manager.mExecutorService == null) {
            Manager.setExecutorService(Executors.newFixedThreadPool(CommonUtils.getNumCores() + 1));
        }
        return Manager.mExecutorService;
    }

    /**
     * 关闭线程池
     */
    public static void executorShutdown() {
        if (Manager.mExecutorService != null) {
            Manager.mExecutorService.shutdown();
            Manager.mExecutorService = null;
        }
    }

    /**
     * 图片加载、图片处理
     */
    public static ImageManager image() {
        if (Manager.mImageManager == null) {
            Manager.setImageManager(ImageManager.getInstance());
        }
        return Manager.mImageManager;
    }

    /**
     * 数据库操作
     */
    public static DbManager db() {
        if (Manager.mDbManager == null) {
            Manager.setDbManager(DbManager.getInstance(Manager.app));
        }
        return Manager.mDbManager;
    }

    /**
     * 工具类  （暂不启用）
     */
    private static UtilsManager utils() {
        if (Manager.mUtilsManager == null) {
            Manager.setUtilsManager(UtilsManager.getInstance(Manager.app));
        }
        return Manager.mUtilsManager;
    }

    /**
     * 权限管理
     */
    public static PermissionsManager permissions() {
        if (Manager.mPermissionsManager == null) {
            Manager.setPermissions(new PermissionsManager());
        }
        return Manager.mPermissionsManager;
    }

    /**
     * 请求头封装 针对UA签名。
     */
    public static HeaderManager header() {
        if (Manager.mHeaderManager == null) {
            Manager.setHeaderManager(HeaderManager.getInstance(Manager.app));
        }
        return Manager.mHeaderManager;
    }

    /**
     * 统计
     */
    public static EventManager event() {
        if (Manager.mEventManager == null) {
            Manager.setEventManager(EventManager.getInstance().init(Manager.app, "", "", false));
        }
        return Manager.mEventManager;
    }

    /**
     * 添加 Activity
     */
    public static void addAct(@NonNull Activity act) {
        if (act != null) Manager.setActInit().add(act);
    }

    /**
     * 删除 Activity
     */
    public static void removeAct(@NonNull Activity act) {
        if (act != null) Manager.setActInit().remove(act);
    }

    /**
     * 应用baseUrl
     */
    public static String getBaseUrl() {
        if (Manager.mBaseUrl == null) {
            return "";
        }
        return Manager.mBaseUrl;
    }

    /**
     * 删除所有 Activity
     */
    public static synchronized void removeActs() {
        Set<Activity> actSet = Manager.setActInit();
        for (Activity act : actSet) {
            act.finish();
        }
        actSet.clear();
    }

    public static ColorStateList colorStateList() {
        if (Manager.mColorStateList == null) {
            return Manager.setColorStateList();
        }
        return Manager.mColorStateList;
    }

    public static class Manager {
        private static boolean debug;
        private static Application app;
        private static HttpManager mHttpManager;
        private static String mBaseUrl;
        private static Type mType;
        private static DownLoadManager mDownLoadManager;
        private static HeaderManager mHeaderManager;
        private static ImageManager mImageManager;
        private static UtilsManager mUtilsManager;
        private static PermissionsManager mPermissionsManager;
        private static DbManager mDbManager;
        private static ExecutorService mExecutorService;
        private static EventManager mEventManager;
        private static Set<Activity> mActs;
        private static ColorStateList mColorStateList;
        private static String mAppKey;

        private Manager() {
        }

        /**
         * 初始化 Manager
         *
         * @param app application
         * @param type 打包环境
         * @param baseUrl baseUrl 主机地址  Type.DEBUG 不为空，其他为空
         */
        @NonNull public static void init(Application app, Type type, String baseUrl,
            String appKey) {
            if (Manager.app == null) {
                Manager.app = app;
            }
            mAppKey = appKey;
            Manager.mType = type;
            if (type == Type.PRODUCTION) {
                setDebug(false);
                mBaseUrl = "http://mapi-cp-2.idaodao.net";
            } else if (type == Type.VERIFY) {
                setDebug(false);
                mBaseUrl = "http://mapi-cp-v-2.idaodao.net";
            } else if (type == Type.TEST) {
                setDebug(true);
                mBaseUrl = "http://mapi-cp-2.test.dongdaodao.com";
            } else if (type == Type.DEV) {
                setDebug(true);
                mBaseUrl = "http://mapi-cp-2.dev.dongdaodao.com";
            } else if (type == Type.DEBUG) {
                setDebug(true);
                Manager.mBaseUrl = baseUrl;
            } else {
                setDebug(true);
                mBaseUrl = "http://mapi-cp-2.idaodao.net";
            }

            //开启crash日志
            CrashHandler.getInstance().init(app);
        }

        public static void setDebug(boolean debug) {
            Manager.debug = debug;
        }

        public static void setHttpManager(HttpManager httpManager) {
            Manager.mHttpManager = httpManager;
        }

        public static void setDownLoadManager(DownLoadManager downLoadManager) {
            Manager.mDownLoadManager = downLoadManager;
        }

        public static void setExecutorService(ExecutorService executorService) {
            Manager.mExecutorService = executorService;
        }

        public static void setImageManager(ImageManager imageManager) {
            Manager.mImageManager = imageManager;
        }

        public static void setUtilsManager(UtilsManager utilsManager) {
            Manager.mUtilsManager = utilsManager;
        }

        public static void setPermissions(PermissionsManager permissionsManager) {
            Manager.mPermissionsManager = permissionsManager;
        }

        public static void setHeaderManager(HeaderManager headerManager) {
            Manager.mHeaderManager = headerManager;
        }

        public static void setDbManager(DbManager dbManager) {
            Manager.mDbManager = dbManager;
        }

        public static void setEventManager(EventManager eventManager) {
            Manager.mEventManager = eventManager;
        }

        public static Set<Activity> setActInit() {
            if (mActs == null) {
                mActs = new HashSet<>();
            }
            return mActs;
        }

        public static ColorStateList setColorStateList() {
            if (mColorStateList == null) {
                int[][] states = new int[6][];
                states[0] = new int[] { android.R.attr.state_checked };  //可选
                states[1] = new int[] { android.R.attr.state_pressed };  //按下
                states[2] = new int[] { android.R.attr.state_selected };  //选择
                states[3] = new int[] {};
                states[4] = new int[] {};
                states[5] = new int[] {};
                int colorNormal = ContextCompat.getColor(app, R.color.base_color_ffffff);
                int colorHighLight = ContextCompat.getColor(app, R.color.base_color_ffc114);
                int[] colors = new int[] {
                    colorHighLight, colorHighLight, colorHighLight, colorNormal, colorNormal,
                    colorNormal
                };
                mColorStateList = new ColorStateList(states, colors);
            }
            return mColorStateList;
        }
    }

    private static class MockApplication extends Application {
        public MockApplication(Context baseContext) {
            this.attachBaseContext(baseContext);
        }
    }
}
