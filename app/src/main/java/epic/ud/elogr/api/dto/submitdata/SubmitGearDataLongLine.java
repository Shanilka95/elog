package epic.ud.elogr.api.dto.submitdata;

/**
 * Created by Udith Perera on 4/24/2020.
 */
public class SubmitGearDataLongLine {
    String id;
    String gearType;
    String mainLine;
    String branchLine;
    String noHooks;
    String hooksTypes;
    String depth;
    String bait;
    SubmitGillNet gillNet;
    SubmitRingNet ringNet;

    public SubmitGearDataLongLine(String id, String gearType, String mainLine, String branchLine, String noHooks, String hooksTypes, String depth, String bait, SubmitGillNet gillNet, SubmitRingNet ringNet) {
        this.id = id;
        this.gearType = gearType;
        this.mainLine = mainLine;
        this.branchLine = branchLine;
        this.noHooks = noHooks;
        this.hooksTypes = hooksTypes;
        this.depth = depth;
        this.bait = bait;
        this.gillNet = gillNet;
        this.ringNet = ringNet;
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

    public String getMainLine() {
        return mainLine;
    }

    public void setMainLine(String mainLine) {
        this.mainLine = mainLine;
    }

    public String getBranchLine() {
        return branchLine;
    }

    public void setBranchLine(String branchLine) {
        this.branchLine = branchLine;
    }

    public String getNoHooks() {
        return noHooks;
    }

    public void setNoHooks(String noHooks) {
        this.noHooks = noHooks;
    }

    public String getHooksTypes() {
        return hooksTypes;
    }

    public void setHooksTypes(String hooksTypes) {
        this.hooksTypes = hooksTypes;
    }

    public String getDepth() {
        return depth;
    }

    public void setDepth(String depth) {
        this.depth = depth;
    }

    public String getBait() {
        return bait;
    }

    public void setBait(String bait) {
        this.bait = bait;
    }

    public SubmitGillNet getGillNet() {
        return gillNet;
    }

    public void setGillNet(SubmitGillNet gillNet) {
        this.gillNet = gillNet;
    }

    public SubmitRingNet getRingNet() {
        return ringNet;
    }

    public void setRingNet(SubmitRingNet ringNet) {
        this.ringNet = ringNet;
    }
}

