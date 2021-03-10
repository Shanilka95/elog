package epic.ud.elogr.api.dto.submitdata;

/**
 * Created by Udith Perera on 4/24/2020.
 */
public class SubmitGillNet {
    String netMaterial;
    String meshSize;
    String plyOfTheNet;
    String heightOfNet;
    String lengthOfNetPiece;
    String numberOfNetPieces;

    public SubmitGillNet(String netMaterial, String meshSize, String plyOfTheNet, String heightOfNet, String lengthOfNetPiece, String numberOfNetPieces) {
        this.netMaterial = netMaterial;
        this.meshSize = meshSize;
        this.plyOfTheNet = plyOfTheNet;
        this.heightOfNet = heightOfNet;
        this.lengthOfNetPiece = lengthOfNetPiece;
        this.numberOfNetPieces = numberOfNetPieces;
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

    public String getLengthOfNetPiece() {
        return lengthOfNetPiece;
    }

    public void setLengthOfNetPiece(String lengthOfNetPiece) {
        this.lengthOfNetPiece = lengthOfNetPiece;
    }

    public String getNumberOfNetPieces() {
        return numberOfNetPieces;
    }

    public void setNumberOfNetPieces(String numberOfNetPieces) {
        this.numberOfNetPieces = numberOfNetPieces;
    }
}