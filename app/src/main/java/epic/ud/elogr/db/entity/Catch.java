package epic.ud.elogr.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Udith Perera on 4/5/2020.
 */
@Entity
public class Catch {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int gearId;

    //catchData
    private String catchType;
    private String fishType;

    public Catch() {
    }

    public Catch(int gearId, String catchType, String fishType) {
        this.gearId = gearId;
        this.catchType = catchType;
        this.fishType = fishType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
