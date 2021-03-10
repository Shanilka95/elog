package epic.ud.elogr.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import epic.ud.elogr.db.dao.CatchDao;
import epic.ud.elogr.db.dao.CatchFishDao;
import epic.ud.elogr.db.dao.FishDao;
import epic.ud.elogr.db.dao.HarbourDao;
import epic.ud.elogr.db.dao.LoginDao;
import epic.ud.elogr.db.dao.TripDao;
import epic.ud.elogr.db.dao.TripGearDao;
import epic.ud.elogr.db.dao.WordDao;
import epic.ud.elogr.db.entity.Catch;
import epic.ud.elogr.db.entity.CatchFish;
import epic.ud.elogr.db.entity.Fish;
import epic.ud.elogr.db.entity.Harbour;
import epic.ud.elogr.db.entity.Login;
import epic.ud.elogr.db.entity.Trip;
import epic.ud.elogr.db.entity.TripGear;
import epic.ud.elogr.db.entity.Word;

@Database(entities = {
        Login.class,
        Fish.class,
        Word.class,
        Harbour.class,
        Trip.class,
        TripGear.class,
        Catch.class,
        CatchFish.class,
}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    public abstract LoginDao loginDao();

    public abstract FishDao fishDao();

    public abstract WordDao wordDao();

    public abstract HarbourDao harbourDao();

    public abstract TripDao tripDao();

    public abstract TripGearDao tripGearDao();

    public abstract CatchDao catchDao();

    public abstract CatchFishDao catchfishDao();


}
