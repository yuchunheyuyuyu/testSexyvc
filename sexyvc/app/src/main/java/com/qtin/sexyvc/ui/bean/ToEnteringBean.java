package com.qtin.sexyvc.ui.bean;

import com.qtin.sexyvc.ui.subject.bean.DataTypeInterface;

/**
 * Created by ls on 17/7/17.
 */

public class ToEnteringBean implements DataTypeInterface {
    @Override
    public int getType() {
        return DataTypeInterface.TYPE_TO_ENTERING;
    }
}
