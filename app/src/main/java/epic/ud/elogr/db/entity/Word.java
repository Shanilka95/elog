package epic.ud.elogr.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Word")
public class Word implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int wid;

    private String id;
    private String en_name;
    private String si_name;
    private String tm_name;

    public int getWid() {
        return wid;
    }

    public void setWid(int wid) {
        this.wid = wid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
