package com.qtin.sexyvc.ui.bean;

import com.qtin.sexyvc.ui.subject.bean.DataTypeInterface;

/**
 * Created by ls on 17/9/13.
 */

public class FooterBean implements DataTypeInterface {
    @Override
    public int getType() {
        return DataTypeInterface.TYPE_FOOTER;
    }
}
