package epic.ud.elogr.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Udith Perera on 4/5/2020.
 */
@Entity
public class CatchFish {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int catchId;


    private String fishid;
    private String fishQty;
    private String fishWeight;

    public CatchFish(int catchId, String fishid, String fishQty, String fishWeight) {
        this.catchId = catchId;
        this.fishid = fishid;
        this.fishQty = fishQty;
        this.fishWeight = fishWeight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCatchId() {
        return catchId;
    }

    public void setCatchId(int catchId) {
        this.catchId = catchId;
    }

    public String getFishid() {
        return fishid;
    }

    public void setFishid(String fishid) {
        this.fishid = fishid;
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
