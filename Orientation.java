package com.xuanyi.cqecar.ui.view.recyclerview;

import android.support.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by lawson on 16/6/22.
 */
@IntDef({
    SpaceLayoutDecoration.LINEAR_LAYOUT_VERTICAL, SpaceLayoutDecoration.LINEAR_LAYOUT_HORIZONTAL,
    SpaceLayoutDecoration.GRID_LAYOUT
}) @Retention(RetentionPolicy.SOURCE) public @interface Orientation {
}
