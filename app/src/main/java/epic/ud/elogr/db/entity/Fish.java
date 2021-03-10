package epic.ud.elogr.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(tableName = "Fish")
public class Fish implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int fid;

    private String id;
    private String parent_id;
    private String en_name;
    private String si_name;
    private String tm_name;

    public Fish(int fid, String id, String parent_id, String en_name, String si_name, String tm_name) {
        this.fid = fid;
        this.id = id;
        this.parent_id = parent_id;
        this.en_name = en_name;
        this.si_name = si_name;
        this.tm_name = tm_name;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getEn_name() {
        return en_name;
    }

    public void setEn_name(String en_name) {
        this.en_name = en_name;
    }

    public String getSi_name() {
        return si_name;
    }

    public void setSi_name(String si_name) {
        this.si_name = si_name;
    }

    public String getTm_name() {
        return tm_name;
    }

    public void setTm_name(String tm_name) {
        this.tm_name = tm_name;
    }
}
