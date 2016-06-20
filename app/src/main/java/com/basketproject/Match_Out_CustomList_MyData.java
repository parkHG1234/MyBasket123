package com.basketproject;

import android.app.Activity;

/**
 * Created by park on 2016-03-28.
 */
public class Match_Out_CustomList_MyData {
    private String CourtName;
    private String CourtAddress;
    private String CourtPlayer;
    public Match_Out_CustomList_MyData(String CourtName,String CourtAddress,String CourtPlayer){
        this.CourtName = CourtName;
        this.CourtAddress= CourtAddress;
        this.CourtPlayer=CourtPlayer;
    }
    public String getCourtName() {
        return CourtName;
    }
    public String getCourtAddress() {
        return CourtAddress;
    }
    public String getCourtPlayer() {
        return CourtPlayer;
    }
}
