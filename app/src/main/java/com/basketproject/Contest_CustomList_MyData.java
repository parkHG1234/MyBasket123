package com.basketproject;

/**
 * Created by park on 2016-04-17.
 */
public class Contest_CustomList_MyData {
    private String ContestName;
    private String Address;
    private String Point;
    public Contest_CustomList_MyData(String ContestName, String Address){
        this.ContestName = ContestName;
        this.Address= Address;
    }
    public String getContestName() {
        return ContestName;
    }
    public String getAddress() {
        return Address;
    }
    public String getPoint() {
        return Point;
    }
}
