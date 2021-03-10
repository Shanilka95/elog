package epic.ud.elogr.model;

import java.util.List;

import epic.ud.elogr.db.entity.CatchFish;

/**
 * Created by Udith Perera on 4/23/2020.
 */
public class CatchDataFishListModel {

    private int catchid;
    private int gearId;
    private String catchType;
    private String fishType;
    private List<CatchFish> fishList;

    public CatchDataFishListModel() {
    }


    public int getCatchid() {
        return catchid;
    }

    public void setCatchid(int catchid) {
        this.catchid = catchid;
    }

    public int getGearId() {
        return gearId;
    }

    public void setGearId(int gearId) {
        this.gearId = gearId;
    }

    public String getCatchType() {
        return catchType;
    }

    public void setCatchType(String catchType) {
        this.catchType = catchType;
    }

    public String getFishType() {
        return fishType;
    }

    public void setFishType(String fishType) {
        this.fishType = fishType;
    }

    public List<CatchFish> getFishList() {
        return fishList;
    }

    public void setFishList(List<CatchFish> fishList) {
        this.fishList = fishList;
    }
}
