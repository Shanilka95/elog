package epic.ud.elogr.api.Res;

import java.util.List;

import epic.ud.elogr.db.entity.Fish;

/**
 * Created by Udith on 24/5/2018.
 */
public class FishResponse {

    private String status;
    private List<Fish> fishlist;

    public FishResponse() {

    }

    public FishResponse(String status, List<Fish> fishlist) {
        this.status = status;
        this.fishlist = fishlist;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Fish> getFishlist() {
        return fishlist;
    }

    public void setFishlist(List<Fish> fishlist) {
        this.fishlist = fishlist;
    }
}
