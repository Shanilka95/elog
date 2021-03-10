package epic.ud.elogr.api.dto;
/**
 * Created by Udith on 24/5/2018.
 */
public class AuthDto {
    private String username;
    private String password;

    public AuthDto() {}

    public AuthDto(String username, String password) {
        this.username = username;
        this.password = password;
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
}
