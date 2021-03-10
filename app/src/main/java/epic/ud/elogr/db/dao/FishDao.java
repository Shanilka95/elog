package epic.ud.elogr.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import epic.ud.elogr.db.entity.Fish;


@Dao
public interface FishDao {

    @Insert
    void saveFish(Fish fish);

    @Query("SELECT * FROM fish")
    List<Fish> getAll();

    @Query("SELECT * FROM fish WHERE id= :id")
    List<Fish> getAllById(int id);

    @Query("SELECT * FROM fish WHERE id= :id")
    Fish getFishById(int id);

    @Query("SELECT * FROM fish WHERE parent_id = 0")
    List<Fish> getAllFishCat();

    @Query("DELETE FROM Fish")
    public void deleteAll();

}
