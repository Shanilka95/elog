package epic.ud.elogr.api.Res;

import java.util.List;

import epic.ud.elogr.db.entity.Harbour;
import epic.ud.elogr.db.entity.Word;

/**
 * Created by Udith on 24/5/2018.
 */
public class WordResponse {

    private String status;
    private List<Word> wordlist;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Word> getWordList() {
        return wordlist;
    }

    public void setWordList(List<Word> wordList) {
        this.wordlist = wordList;
    }

}
