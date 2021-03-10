package epic.ud.elogr.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import epic.ud.elogr.db.entity.Catch;
import epic.ud.elogr.db.entity.Fish;

/**
 * Created by Udith Perera on 4/8/2020.
 */
@Dao
public interface CatchDao {
    @Query("SELECT * FROM catch")
    List<Catch> getAll();


    @Query("SELECT catch.* FROM catch LEFT JOIN tripgear ON tripgear.id = catch.gearId LEFT JOIN trip ON trip.tripId = tripgear.tripId WHERE trip.tripId = :tripId")
    List<Catch> getAllByTripId(int tripId);

    @Query("SELECT * FROM catch WHERE gearId = :id")
    List<Catch> getAllByGearId(int id);

    @Query("SELECT * FROM catch WHERE id IN (:id)")
    List<Catch> loadAllByIds(int[] id);

    @Query("SELECT MAX(id) FROM catch")
    int getMaxId();

    @Insert
    void insertAll(Catch c);

    @Query("DELETE FROM catch")
    void deleteAll();

    @Delete
    void delete(Catch c);
}
