package com.yyz.base.permissions;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2017/7/3.
 */

public class PermissionsManager {
    private static RxPermissions mRxPermissions;

    /**
     *  如果请求是多个权限，会有多次回调。
     * @param act
     * @param permissionsListener
     * @param permissions
     */
    public  void requestEach(@NonNull AppCompatActivity act, @NonNull final PermissionsListener permissionsListener, String... permissions){
        mRxPermissions = new RxPermissions(act);
        mRxPermissions.requestEach(permissions).subscribe(new Consumer<Permission>() {
            @Override
            public void accept(Permission permission) throws Exception {
                if (permissionsListener != null){
                    if (permission.granted){
                        //用户授权
                        permissionsListener.permission(permission.name,1);
                    }else if (permission.shouldShowRequestPermissionRationale){
                        // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                        permissionsListener.permission(permission.name,2);
                    }else {
                        //// 用户拒绝了该权限，并且选中『不再询问』
                        permissionsListener.permission(permission.name,3);
                    }
                }

            }
        });
    }

    /**
     * 请求多个权限 一次回调。
     * @param act
     * @param permissionsListener
     * @param permissions
     */
    public void request(@NonNull AppCompatActivity act, @NonNull final PermissionsListener permissionsListener, String... permissions){
        mRxPermissions = new RxPermissions(act);
        mRxPermissions.request(permissions).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (permissionsListener != null){
                    if (aBoolean){
                        permissionsListener.permission(null,1);
                    }else {
                        permissionsListener.permission(null,2);
                    }
                }

            }
        });
    }

}
