package epic.ud.elogr.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Udith Perera on 4/5/2020.
 */
@Entity
public class TripGear {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int tripId;
    //setGears
    private String gearType;
    private String gearTypeName;
    private String mainLine; //longline
    private String branchLine; //longline
    private String noHooks; //longline
    private String hooksTypes; //longline
    private String depth; //longline
    private String baitType; //longline
    private String netMaterial; //gillnet
    private String meshSize; //gillnet
    private String plyOfTheNet; //gillnet
    private String heightOfNet; //gillnet
    private String lengthOfNetPiece; //gillnet
    private String numberOfNetPieces; //gillnet
    private String lengthOfTheRingNet; //rignNet
    private String heightOfTheRingNet; //rignNet

    //setDate
    private String startDate;
    private String number;
    private String startGpsLat;
    private String startGpsLon;
    private String endGpsLat;
    private String endGpsLon;
    private String endDate;

    public TripGear() {
    }

    public TripGear(int tripId, String gearType, String gearTypeName, String mainLine, String branchLine, String noHooks, String hooksTypes, String depth, String baitType, String netMaterial, String meshSize, String plyOfTheNet, String heightOfNet, String lengthOfNetPiece, String numberOfNetPieces, String lengthOfTheRingNet, String heightOfTheRingNet) {
        this.tripId = tripId;
        this.gearType = gearType;
        this.gearTypeName = gearTypeName;
        this.mainLine = mainLine;
        this.branchLine = branchLine;
        this.noHooks = noHooks;
        this.hooksTypes = hooksTypes;
        this.depth = depth;
        this.baitType = baitType;
        this.netMaterial = netMaterial;
        this.meshSize = meshSize;
        this.plyOfTheNet = plyOfTheNet;
        this.heightOfNet = heightOfNet;
        this.lengthOfNetPiece = lengthOfNetPiece;
        this.numberOfNetPieces = numberOfNetPieces;
        this.lengthOfTheRingNet = lengthOfTheRingNet;
        this.heightOfTheRingNet = heightOfTheRingNet;
    }

    public TripGear(int id, String startDate, String number, String startGpsLat, String startGpsLon, String endGpsLat, String endGpsLon, String endDate) {
        this.id = id;
        this.startDate = startDate;
        this.number = number;
        this.startGpsLat = startGpsLat;
        this.startGpsLon = startGpsLon;
        this.endGpsLat = endGpsLat;
        this.endGpsLon = endGpsLon;
        this.endDate = endDate;
    }

    public String getGearTypeName() {
        return gearTypeName;
    }

    public void setGearTypeName(String gearTypeName) {
        this.gearTypeName = gearTypeName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
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

    public String getBaitType() {
        return baitType;
    }

    public void setBaitType(String baitType) {
        this.baitType = baitType;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStartGpsLat() {
        return startGpsLat;
    }

    public void setStartGpsLat(String startGpsLat) {
        this.startGpsLat = startGpsLat;
    }

    public String getStartGpsLon() {
        return startGpsLon;
    }

    public void setStartGpsLon(String startGpsLon) {
        this.startGpsLon = startGpsLon;
    }

    public String getEndGpsLat() {
        return endGpsLat;
    }

    public void setEndGpsLat(String endGpsLat) {
        this.endGpsLat = endGpsLat;
    }

    public String getEndGpsLon() {
        return endGpsLon;
    }

    public void setEndGpsLon(String endGpsLon) {
        this.endGpsLon = endGpsLon;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
