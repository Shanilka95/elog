package epic.ud.elogr.model;

/**
 * Created by Udith Perera on 4/23/2020.
 */
public class CatchDataFishModel {
    String id = "";
    String catchId = "";
    String fishId = "";
    String fishPerentId= "";
    String fishName= "";
    String fishQty= "";
    String fishWeight= "";

    public CatchDataFishModel() {
        this.id = "";
        this.fishId = "";
        this.fishPerentId = "";
        this.fishName= "";
        this.fishQty= "";
        this.fishWeight= "";

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCatchId() {
        return catchId;
    }

    public void setCatchId(String catchId) {
        this.catchId = catchId;
    }

    public String getFishId() {
        return fishId;
    }

    public void setFishId(String fishId) {
        this.fishId = fishId;
    }

    public String getFishPerentId() {
        return fishPerentId;
    }

    public void setFishPerentId(String fishPerentId) {
        this.fishPerentId = fishPerentId;
    }

    public String getFishName() {
        return fishName;
    }

    public void setFishName(String fishName) {
        this.fishName = fishName;
    }

    public String getFishQty() {
        return fishQty;
    }

    public void setFishQty(String fishQty) {
        this.fishQty = fishQty;
    }

    public String getFishWeight() {
        return fishWeight;
    }

    public void setFishWeight(String fishWeight) {
        this.fishWeight = fishWeight;
    }
}
