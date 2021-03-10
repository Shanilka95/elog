package epic.ud.elogr.api.Res;

import java.util.List;

import epic.ud.elogr.db.entity.Fish;
import epic.ud.elogr.db.entity.Harbour;

public class HarbourResponse {

    private String status;
    private List<Harbour> harborlist;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Harbour> getHarbourList() {
        return harborlist;
    }

    public void setHarbourList(List<Harbour> harbourList) {
        this.harborlist = harbourList;
    }
}
