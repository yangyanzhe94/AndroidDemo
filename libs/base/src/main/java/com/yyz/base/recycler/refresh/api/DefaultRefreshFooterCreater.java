package com.yyz.base.recycler.refresh.api;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * 默认Footer创建器
 *
 */

public interface DefaultRefreshFooterCreater {
    @NonNull
    RefreshFooter createRefreshFooter(Context context, RefreshLayoutImpl layout);
}
