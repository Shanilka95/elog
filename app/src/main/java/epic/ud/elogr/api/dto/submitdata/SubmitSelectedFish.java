package epic.ud.elogr.api.dto.submitdata;

/**
 * Created by Udith Perera on 4/24/2020.
 */
public class SubmitSelectedFish {
    String fishId;
    String quantity;
    String weight;

    public SubmitSelectedFish(String fishId, String quantity, String weight) {
        this.fishId = fishId;
        this.quantity = quantity;
        this.weight = weight;
    }

    public String getFishId() {
        return fishId;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getWeight() {
        return weight;
    }
}

