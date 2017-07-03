package com.qtin.sexyvc.ui.investor.bean;

import com.qtin.sexyvc.ui.subject.bean.DataTypeInterface;

/**
 * Created by ls on 17/7/3.
 */

public class RoadShowBean implements DataTypeInterface{
    private RoadShowItemBean professional;
    private RoadShowItemBean efficiency;
    private RoadShowItemBean feedback;
    private RoadShowItemBean experience;

    public RoadShowItemBean getProfessional() {
        return professional;
    }

    public void setProfessional(RoadShowItemBean professional) {
        this.professional = professional;
    }

    public RoadShowItemBean getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(RoadShowItemBean efficiency) {
        this.efficiency = efficiency;
    }

    public RoadShowItemBean getFeedback() {
        return feedback;
    }

    public void setFeedback(RoadShowItemBean feedback) {
        this.feedback = feedback;
    }

    public RoadShowItemBean getExperience() {
        return experience;
    }

    public void setExperience(RoadShowItemBean experience) {
        this.experience = experience;
    }

    @Override
    public int getType() {
        return DataTypeInterface.TYPE_ROAD;
    }


}
