package epic.ud.elogr.model;

/**
 * Created by Udith Perera on 4/18/2020.
 */
public class ShowTripGearModel {

    String lineId;
    String tripId;
    String gearWordId;
    String gearWordSi;
    String gearWordEn;
    String gearWordTa;
    String number;

    public ShowTripGearModel(String lineId, String tripId, String gearWordId, String gearWordSi, String gearWordEn, String gearWordTa, String number) {
        this.lineId = lineId;
        this.tripId = tripId;
        this.gearWordId = gearWordId;
        this.gearWordSi = gearWordSi;
        this.gearWordEn = gearWordEn;
        this.gearWordTa = gearWordTa;
        this.number = number;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getGearWordId() {
        return gearWordId;
    }

    public void setGearWordId(String gearWordId) {
        this.gearWordId = gearWordId;
    }

    public String getGearWordSi() {
        return gearWordSi;
    }

    public void setGearWordSi(String gearWordSi) {
        this.gearWordSi = gearWordSi;
    }

    public String getGearWordEn() {
        return gearWordEn;
    }

    public void setGearWordEn(String gearWordEn) {
        this.gearWordEn = gearWordEn;
    }

    public String getGearWordTa() {
        return gearWordTa;
    }

    public void setGearWordTa(String gearWordTa) {
        this.gearWordTa = gearWordTa;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
