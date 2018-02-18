package com.example.andrey.newtmpclient.fragments.map;

import com.example.andrey.newtmpclient.base.basemvp.BaseView;
import com.example.andrey.newtmpclient.entities.UserCoords;
import com.example.andrey.newtmpclient.entities.map.RouteResponse;

import java.util.List;

public interface MapNewView extends BaseView{
    void setUserCoordes(List<UserCoords>userCoordes);

    void drawDirections(
//            RouteResponse routeResponse,
            List<UserCoords> userCoordes);
}
