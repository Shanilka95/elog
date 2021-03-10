package epic.ud.elogr.api.dto.submitdata;

/**
 * Created by Udith Perera on 4/24/2020.
 */
public  class SubmitSetData {
    String id;
    String gearType;
    String startDate;
    String nubmer;
    String startGpsDerec;
    String startGps;
    String startGpsE;
    String endGpsDerec;
    String endGps;
    String endGpsE;
    String endDate;

    public SubmitSetData(String id, String gearType, String startDate, String nubmer, String startGpsDerec, String startGps, String startGpsE, String endGpsDerec, String endGps, String endGpsE, String endDate) {
        this.id = id;
        this.gearType = gearType;
        this.startDate = startDate;
        this.nubmer = nubmer;
        this.startGpsDerec = startGpsDerec;
        this.startGps = startGps;
        this.startGpsE = startGpsE;
        this.endGpsDerec = endGpsDerec;
        this.endGps = endGps;
        this.endGpsE = endGpsE;
        this.endDate = endDate;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getNubmer() {
        return nubmer;
    }

    public void setNubmer(String nubmer) {
        this.nubmer = nubmer;
    }

    public String getStartGpsDerec() {
        return startGpsDerec;
    }

    public void setStartGpsDerec(String startGpsDerec) {
        this.startGpsDerec = startGpsDerec;
    }

    public String getStartGps() {
        return startGps;
    }

    public void setStartGps(String startGps) {
        this.startGps = startGps;
    }

    public String getStartGpsE() {
        return startGpsE;
    }

    public void setStartGpsE(String startGpsE) {
        this.startGpsE = startGpsE;
    }

    public String getEndGpsDerec() {
        return endGpsDerec;
    }

    public void setEndGpsDerec(String endGpsDerec) {
        this.endGpsDerec = endGpsDerec;
    }

    public String getEndGps() {
        return endGps;
    }

    public void setEndGps(String endGps) {
        this.endGps = endGps;
    }

    public String getEndGpsE() {
        return endGpsE;
    }

    public void setEndGpsE(String endGpsE) {
        this.endGpsE = endGpsE;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}