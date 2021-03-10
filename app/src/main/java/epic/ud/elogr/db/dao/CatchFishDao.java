package epic.ud.elogr.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import epic.ud.elogr.db.entity.Catch;
import epic.ud.elogr.db.entity.CatchFish;

/**
 * Created by Udith Perera on 4/8/2020.
 */
@Dao
public interface CatchFishDao {
    @Query("SELECT * FROM catchfish")
    List<CatchFish> getAll();

    @Query("SELECT * FROM catchfish WHERE catchId = :id")
    List<CatchFish> getAllByCatchId(int id);


    @Query("SELECT MAX(id) FROM catchfish")
    int getMaxId();



    @Query("UPDATE catchfish SET fishQty = :qty, fishWeight =:weight WHERE id =:id")
    void updateById(String id,String qty, String weight);

    @Insert
    void insertAll(CatchFish c);

    @Query("DELETE FROM catchfish")
    void deleteAll();

    @Delete
    void delete(CatchFish c);
}
