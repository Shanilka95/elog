package epic.ud.elogr.api.dto.submitdata;

/**
 * Created by Udith Perera on 4/24/2020.
 */
public class SubmitGearDataGillNet {
    String id;
    String gearType;


    String netMaterial;
    String meshSize;
    String plyOfTheNet;
    String heightOfNet;
    String DepthAtWhichTheNetSet;
    String numberOfNetPieces;

    public SubmitGearDataGillNet(String id, String gearType, String netMaterial, String meshSize, String plyOfTheNet, String heightOfNet, String depthAtWhichTheNetSet, String numberOfNetPieces) {
        this.id = id;
        this.gearType = gearType;
        this.netMaterial = netMaterial;
        this.meshSize = meshSize;
        this.plyOfTheNet = plyOfTheNet;
        this.heightOfNet = heightOfNet;
        DepthAtWhichTheNetSet = depthAtWhichTheNetSet;
        this.numberOfNetPieces = numberOfNetPieces;
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

    public String getNetMaterial() {
        return netMaterial;
    }

    public void setNetMaterial(String netMaterial) {
        this.netMaterial = netMaterial;
    }

    public String getMeshSize() {
        return meshSize;
    }

    public void setMeshSize(String meshSize) {
        this.meshSize = meshSize;
    }

    public String getPlyOfTheNet() {
        return plyOfTheNet;
    }

    public void setPlyOfTheNet(String plyOfTheNet) {
        this.plyOfTheNet = plyOfTheNet;
    }

    public String getHeightOfNet() {
        return heightOfNet;
    }

    public void setHeightOfNet(String heightOfNet) {
        this.heightOfNet = heightOfNet;
    }

    public String getDepthAtWhichTheNetSet() {
        return DepthAtWhichTheNetSet;
    }

    public void setDepthAtWhichTheNetSet(String depthAtWhichTheNetSet) {
        DepthAtWhichTheNetSet = depthAtWhichTheNetSet;
    }

    public String getNumberOfNetPieces() {
        return numberOfNetPieces;
    }

    public void setNumberOfNetPieces(String numberOfNetPieces) {
        this.numberOfNetPieces = numberOfNetPieces;
    }
}