package com.fakenews.model;

import java.util.List;

public class TwitterTimelineRequest {

    List<String> listOfIds;

    public List<String> getListOfIds() {
        return listOfIds;
    }

    public void setListOfIds(List<String> listOfIds) {
        this.listOfIds = listOfIds;
    }
}
