package epic.ud.elogr.api.dto.submitdata;

import java.util.List;

/**
 * Created by Udith Perera on 4/24/2020.
 */
public class SubmitCatchData {
    String id;
    String setType;
    String catchType;
    String fishType;
    List<SubmitSelectedFish> selectedFish;

    public SubmitCatchData(String id, String setType, String catchType, String fishType, List<SubmitSelectedFish> selectedFish) {
        this.id = id;
        this.setType = setType;
        this.catchType = catchType;
        this.fishType = fishType;
        this.selectedFish = selectedFish;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSetType() {
        return setType;
    }

    public void setSetType(String setType) {
        this.setType = setType;
    }

    public String getCatchType() {
        return catchType;
    }

    public void setCatchType(String catchType) {
        this.catchType = catchType;
    }

    public String getFishType() {
        return fishType;
    }

    public void setFishType(String fishType) {
        this.fishType = fishType;
    }

    public List<SubmitSelectedFish> getSelectedFish() {
        return selectedFish;
    }

    public void setSelectedFish(List<SubmitSelectedFish> selectedFish) {
        this.selectedFish = selectedFish;
    }
}
