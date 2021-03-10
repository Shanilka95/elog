package epic.ud.elogr.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import epic.ud.elogr.db.entity.Fish;
import epic.ud.elogr.db.entity.Trip;

@Dao
public interface TripDao {
    @Query("SELECT * FROM trip")
    List<Trip> getAll();


    @Query("SELECT * FROM trip WHERE tripStatus='DRAF'")
    List<Trip> getAllDrafList();

    @Query("SELECT trip.* FROM trip LEFT JOIN tripgear ON tripgear.tripId = trip.tripId LEFT JOIN catch ON catch.gearId = tripgear.id WHERE tripStatus='DRAF'")
    List<Trip> getAllGearCatchedDrafList();

    @Query("SELECT * FROM trip WHERE tripStatus = 'DRAF' AND tripId = (SELECT MAX(tripId) FROM trip WHERE tripStatus = 'DRAF')")
    List<Trip> getAllDraf();

    @Query("SELECT MAX(tripId) FROM trip WHERE tripStatus = 'DRAF'")
    int getAllDrafMaxId();

    @Query("SELECT * FROM trip WHERE tripId IN (:id)")
    List<Trip> loadAllByIds(int[] id);

    @Query("UPDATE trip SET contact = :contact, language = :language, vesselId = :vesselId , skipperId = :skipperId , licenseNo = :licenseNo WHERE tripId = :tripId")
    void updateDraft(String tripId, String contact,String  language, String vesselId ,String  skipperId ,String  licenseNo );

    @Query("UPDATE trip SET departureDate = :date, departureHarbour = :harbour, departureRemarks = :remark WHERE tripId = :tripId")
    void updateDeparture(String tripId, String date,String  harbour, String remark);

    @Query("UPDATE trip SET arrivalDate = :date, arrivalHarbour = :harbour WHERE tripId = :tripId")
    void updateArrival(String tripId, String date,String  harbour);


    @Query("UPDATE trip SET tripStatus = 'COMPLETED' WHERE tripId = :id")
    void updateTripStatus(String id);

    @Query("SELECT MAX(tripId) FROM trip")
    int getMaxId();

    @Query("SELECT ( CASE " +
            "WHEN trip.contact IS NULL OR trip.contact ='' THEN 1 " +
            "WHEN trip.vesselId IS NULL OR trip.vesselId ='' THEN 1 " +
            "WHEN trip.vesselId IS NULL OR trip.vesselId = '' THEN 1 " +
            "WHEN trip.skipperId IS NULL OR trip.skipperId = '' THEN 1 ELSE 0 END ) AS 'SETUP_IS'  FROM trip WHERE tripId = :id")
    int getSettingFormIsValid(int id);

    @Query("SELECT ( CASE " +
            "WHEN trip.departureDate IS NULL OR trip.departureDate ='' THEN 1 " +
            "WHEN trip.departureHarbour IS NULL OR trip.departureHarbour ='' THEN 1 " +
            "WHEN trip.departureRemarks IS NULL OR trip.departureRemarks = '' THEN 1  ELSE 0 END ) AS 'DEPAT_IS'  FROM trip WHERE tripId=:id")
    int getDepatureFormIsValid(int id);

    @Insert
    void insert(Trip trip);

    @Query("DELETE FROM trip")
    void deleteAll();

    @Delete
    void delete(Trip trip);

    @Update
    void update(Trip trip);
}
