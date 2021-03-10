package epic.ud.elogr.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created by Udith Perera on 4/5/2020.
 */
@Entity(tableName = "Trip")
public class Trip implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int tripId;

    private String contact;
    private String language;
    private String vesselId;
    private String skipperId;
    private String licenseNo;

    private String departureDate;
    private String departureHarbour;
    private String departureRemarks;

    private String arrivalDate;
    private String arrivalHarbour;

    private String tripStatus;

    public Trip() {

    }

    public Trip(String tripStatus) {
        this.tripStatus = tripStatus;
    }

    public Trip(String contact, String language, String vesselId, String skipperId, String licenseNo, String tripStatus) {
        this.contact = contact;
        this.language = language;
        this.vesselId = vesselId;
        this.skipperId = skipperId;
        this.licenseNo = licenseNo;
        this.tripStatus = tripStatus;

    }

    public Trip(String departureDate, String departureHarbour, String departureRemarks) {
        this.departureDate = departureDate;
        this.departureHarbour = departureHarbour;
        this.departureRemarks = departureRemarks;
    }

    public Trip(String arrivalDate, String arrivalHarbour) {
        this.arrivalDate = arrivalDate;
        this.arrivalHarbour = arrivalHarbour;
    }


    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getVesselId() {
        return vesselId;
    }

    public void setVesselId(String vesselId) {
        this.vesselId = vesselId;
    }

    public String getSkipperId() {
        return skipperId;
    }

    public void setSkipperId(String skipperId) {
        this.skipperId = skipperId;
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getDepartureHarbour() {
        return departureHarbour;
    }

    public void setDepartureHarbour(String departureHarbour) {
        this.departureHarbour = departureHarbour;
    }

    public String getDepartureRemarks() {
        return departureRemarks;
    }

    public void setDepartureRemarks(String departureRemarks) {
        this.departureRemarks = departureRemarks;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getArrivalHarbour() {
        return arrivalHarbour;
    }

    public void setArrivalHarbour(String arrivalHarbour) {
        this.arrivalHarbour = arrivalHarbour;
    }

    public String getTripStatus() {
        return tripStatus;
    }

    public void setTripStatus(String tripStatus) {
        this.tripStatus = tripStatus;
    }
}
