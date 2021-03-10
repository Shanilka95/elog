package epic.ud.elogr.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(tableName = "Login")
public class Login implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String username;
    private String password;
    private String userType;
    private int isLogout = 1;

    public Login(String username, String password, String userType, int isLogout) {
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.isLogout = isLogout;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public int isLogout() {
        return isLogout;
    }

    public void setLogout(int logout) {
        isLogout = logout;
    }
}
