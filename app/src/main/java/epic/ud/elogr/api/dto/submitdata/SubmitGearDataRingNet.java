package epic.ud.elogr.api.dto.submitdata;

/**
 * Created by Udith Perera on 4/24/2020.
 */
public class SubmitGearDataRingNet {
    String id;
    String gearType;
    String lengthOfTheRingNet;
    String heightOfTheRingNet;

    public SubmitGearDataRingNet(String id, String gearType, String lengthOfTheRingNet, String heightOfTheRingNet) {
        this.id = id;
        this.gearType = gearType;
        this.lengthOfTheRingNet = lengthOfTheRingNet;
        this.heightOfTheRingNet = heightOfTheRingNet;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGearType() {
        return gearType;
    }

    public void setGearType(String gearType) {
        this.gearType = gearType;
    }

    public String getLengthOfTheRingNet() {
        return lengthOfTheRingNet;
    }

    public void setLengthOfTheRingNet(String lengthOfTheRingNet) {
        this.lengthOfTheRingNet = lengthOfTheRingNet;
    }

    public String getHeightOfTheRingNet() {
        return heightOfTheRingNet;
    }

    public void setHeightOfTheRingNet(String heightOfTheRingNet) {
        this.heightOfTheRingNet = heightOfTheRingNet;
    }
}
