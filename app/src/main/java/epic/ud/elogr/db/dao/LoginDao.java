package epic.ud.elogr.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import epic.ud.elogr.db.entity.Login;

@Dao
public interface LoginDao {

    @Insert
    void saveLogin(Login login);

    @Query("SELECT * FROM login WHERE isLogout = 0 GROUP BY username ")
    List<Login> loginCount();

    @Query("UPDATE login SET isLogout = 1")
    int loginOutAll();

}
