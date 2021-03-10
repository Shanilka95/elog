package epic.ud.elogr.api.dto.submitdata;

/**
 * Created by Udith Perera on 4/24/2020.
 */
public class SubmitTripData {
    String id;
    String vessel_id;
    String skipper_id;
    String phone_id;
    String licenseNumber;
    String departure_port;
    String departure_date_time;
    String departure_remarks;
    String departure_inspector_id;
    String arrival_port;
    String arrival_date_time;

    public SubmitTripData(String id, String vessel_id, String skipper_id, String phone_id, String licenseNumber, String departure_port, String departure_date_time, String departure_remarks, String departure_inspector_id, String arrival_port, String arrival_date_time) {
        this.id = id;
        this.vessel_id = vessel_id;
        this.skipper_id = skipper_id;
        this.phone_id = phone_id;
        this.licenseNumber = licenseNumber;
        this.departure_port = departure_port;
        this.departure_date_time = departure_date_time;
        this.departure_remarks = departure_remarks;
        this.departure_inspector_id = departure_inspector_id;
        this.arrival_port = arrival_port;
        this.arrival_date_time = arrival_date_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVessel_id() {
        return vessel_id;
    }

    public void setVessel_id(String vessel_id) {
        this.vessel_id = vessel_id;
    }

    public String getSkipper_id() {
        return skipper_id;
    }

    public void setSkipper_id(String skipper_id) {
        this.skipper_id = skipper_id;
    }

    public String getPhone_id() {
        return phone_id;
    }

    public void setPhone_id(String phone_id) {
        this.phone_id = phone_id;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getDeparture_port() {
        return departure_port;
    }

    public void setDeparture_port(String departure_port) {
        this.departure_port = departure_port;
    }

    public String getDeparture_date_time() {
        return departure_date_time;
    }

    public void setDeparture_date_time(String departure_date_time) {
        this.departure_date_time = departure_date_time;
    }

    public String getDeparture_remarks() {
        return departure_remarks;
    }

    public void setDeparture_remarks(String departure_remarks) {
        this.departure_remarks = departure_remarks;
    }

    public String getDeparture_inspector_id() {
        return departure_inspector_id;
    }

    public void setDeparture_inspector_id(String departure_inspector_id) {
        this.departure_inspector_id = departure_inspector_id;
    }

    public String getArrival_port() {
        return arrival_port;
    }

    public void setArrival_port(String arrival_port) {
        this.arrival_port = arrival_port;
    }

    public String getArrival_date_time() {
        return arrival_date_time;
    }

    public void setArrival_date_time(String arrival_date_time) {
        this.arrival_date_time = arrival_date_time;
    }
}
