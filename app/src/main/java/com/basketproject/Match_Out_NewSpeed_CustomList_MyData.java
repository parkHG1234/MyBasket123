package com.basketproject;

/**
 * Created by park on 2016-05-31.
 */
public class Match_Out_NewSpeed_CustomList_MyData {
    private String TeamName;
    private String Emblem;
    private String Name;
    private String Time;
    private String Memo;
    public Match_Out_NewSpeed_CustomList_MyData(String Emblem,String TeamName,String Name,String Time,String Memo){
        this.TeamName = TeamName;
        this.Emblem = Emblem;
        this.Name = Name;
        this.Time = Time;
        this.Memo = Memo;
    }
    public String getTeamName() {
        return TeamName;
    }
    public String getEmblem() {
        return Emblem;
    }
    public String getName() {
        return Name;
    }
    public String getTime() {
        return Time;
    }
    public String getMemo() {
        return Memo;
    }
}
