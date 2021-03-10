package epic.ud.elogr.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import epic.ud.elogr.db.entity.Catch;
import epic.ud.elogr.db.entity.Word;

@Dao
public interface WordDao {

    @Insert
    void saveWords(Word word);

    @Query("DELETE FROM Word")
    public void deleteAll();

    @Query("SELECT * FROM word")
    List<Word> getAll();

    @Query("SELECT en_name FROM Word WHERE id LIKE:id")
    public String getEnglishWord(String id);

    @Query("SELECT si_name FROM Word WHERE id LIKE:id")
    public String getSinhalaWord(String id);

    @Query("SELECT tm_name FROM Word WHERE id LIKE:id")
    public String getTamilWord(String id);

}
