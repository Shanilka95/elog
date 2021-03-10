package epic.ud.elogr.api.Res;

/**
 * Created by Udith on 24/5/2018.
 */
public class AuthResponse {

    private String status;
    private String message;
    private String user_id;
    public AuthResponse() {
    }


    public AuthResponse(String status, String message, String user_id) {
        this.status = status;
        this.message = message;
        this.user_id = user_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
