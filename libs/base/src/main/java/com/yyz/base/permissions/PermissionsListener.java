package com.yyz.base.permissions;

/**
 * Created by Administrator on 2017/7/3.
 */

public interface PermissionsListener {
    /**
     * 权限回调
     * @param permissionsName  权限名称
     * @param granted  1:授权 2：拒绝 3：拒绝并选中不在询问
     */
    void permission(String permissionsName, int granted);
}
