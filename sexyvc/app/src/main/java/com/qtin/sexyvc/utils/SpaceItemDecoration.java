package com.qtin.sexyvc.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by ls on 17/6/11.
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    int mSpace ;
    int mCount;
    /**
     * @param space 传入的值，其单位视为dp
     */
    public SpaceItemDecoration(int space,int count) {
        this.mSpace = space;
        this.mCount=count;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int pos = parent.getChildAdapterPosition(view);

        outRect.left = mSpace;
        outRect.top = 0;
        outRect.bottom = 0;
        if (pos != (mCount -1)) {
            outRect.right = 0;
        } else {
            outRect.right = 0;
        }
    }
}
