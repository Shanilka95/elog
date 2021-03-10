package epic.ud.elogr.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import epic.ud.elogr.db.entity.Fish;
import epic.ud.elogr.db.entity.Harbour;

@Dao
public interface HarbourDao {

    @Insert
    void saveHarbour(Harbour harbour);

    @Query("DELETE FROM Harbour")
    void deleteAll();

    @Query("SELECT * FROM Harbour")
    List<Harbour> selectAll();

}
