package epic.ud.elogr.api.dto.submitdata;

/**
 * Created by Udith Perera on 4/24/2020.
 */
public class SubmitRingNet {
    String lengthOfTheRingNet;
    String heightOfTheRingNet;

    public SubmitRingNet(String lengthOfTheRingNet, String heightOfTheRingNet) {
        this.lengthOfTheRingNet = lengthOfTheRingNet;
        this.heightOfTheRingNet = heightOfTheRingNet;
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
