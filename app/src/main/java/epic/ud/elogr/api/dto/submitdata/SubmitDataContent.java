package epic.ud.elogr.api.dto.submitdata;

import java.util.List;

/**
 * Created by Udith Perera on 4/24/2020.
 */
public class SubmitDataContent {

    SubmitTripData tripdata;
    List<Object> Geardata;
    List<SubmitSetData> Setdata;
    List<SubmitCatchData> catchdata;
    String licenseNumber = "";

    public SubmitDataContent() {
    }

    public SubmitDataContent(SubmitTripData tripdata, List<Object> geardata, List<SubmitSetData> setdata, List<SubmitCatchData> catchdata, String licenseNumber) {
        this.tripdata = tripdata;
        Geardata = geardata;
        Setdata = setdata;
        this.catchdata = catchdata;
        this.licenseNumber = licenseNumber;
    }

    public SubmitTripData getTripdata() {
        return tripdata;
    }

    public void setTripdata(SubmitTripData tripdata) {
        this.tripdata = tripdata;
    }

    public List<Object> getGeardata() {
        return Geardata;
    }

    public void setGeardata(List<Object> geardata) {
        Geardata = geardata;
    }

    public List<SubmitSetData> getSetdata() {
        return Setdata;
    }

    public void setSetdata(List<SubmitSetData> setdata) {
        Setdata = setdata;
    }

    public List<SubmitCatchData> getCatchdata() {
        return catchdata;
    }

    public void setCatchdata(List<SubmitCatchData> catchdata) {
        this.catchdata = catchdata;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }
}










