package epic.ud.elogr.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import epic.ud.elogr.db.entity.Fish;
import epic.ud.elogr.db.entity.TripGear;
import epic.ud.elogr.model.ShowTripGearModel;

/**
 * Created by Udith Perera on 4/8/2020.
 */
@Dao
public interface TripGearDao {
    @Query("SELECT * FROM tripgear")
    List<TripGear> getAll();


    @Query("SELECT * FROM tripgear WHERE tripId = :id")
    List<TripGear> getAllByTripId(int id);

    @Query("SELECT tripgear.* FROM tripgear LEFT JOIN catch ON catch.gearId == tripgear.id WHERE tripId = :id AND catch.gearId == tripgear.id")
    List<TripGear> getAllByCatchedTripId(int id);



    @Query("SELECT tripgear.id AS 'lineId', tripgear.tripId AS 'tripId', tripgear.number AS 'number', word.id AS 'gearWordId', word.si_name AS 'gearWordSi', word.en_name AS 'gearWordEn', word.tm_name AS 'gearWordTa'  FROM tripgear LEFT JOIN word ON word.id = tripgear.gearType WHERE tripgear.tripId = :tripId")
    List<ShowTripGearModel> getAll_(int tripId);

    @Query("SELECT tripgear.id AS 'lineId', tripgear.tripId AS 'tripId', tripgear.number AS 'number', word.id AS 'gearWordId', word.si_name AS 'gearWordSi', word.en_name AS 'gearWordEn', word.tm_name AS 'gearWordTa'  FROM tripgear LEFT JOIN word ON word.id = tripgear.gearType WHERE tripgear.tripId = :tripId AND tripgear.number <> '' ")
    List<ShowTripGearModel> getSetDataAll_(int tripId);

    @Query("SELECT * FROM tripgear WHERE id IN (:id)")
    List<TripGear> loadAllByIds(int id);

    @Insert
    void insert(TripGear tripGear);

    @Query("DELETE FROM tripgear")
    void deleteAll();

    @Delete
    void delete(TripGear tripGear);

    @Query("UPDATE tripgear SET  number = :number , startDate = :startDate , startGpsLat = :startGpsLat, startGpsLon = :startGpsLon, endGpsLat = :endGpsLat,endGpsLon = :endGpsLon, endDate = :endDate WHERE id = :lineId")
    void updateSetData(int lineId, String number, String startDate, String  startGpsLat, String  startGpsLon, String  endGpsLat , String endGpsLon, String  endDate);
}
