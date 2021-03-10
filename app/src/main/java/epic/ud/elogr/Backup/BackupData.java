package epic.ud.elogr.Backup;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.FragmentActivity;
import androidx.room.Database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

import epic.ud.elogr.R;
import epic.ud.elogr.config.AppConsts;
import epic.ud.elogr.util.Store;

public class BackupData {



    private Context context;
    // url for database
    private final String dataPath = "//data//epic.ud.elogr//databases//";

    // name of main data
    private final String dataName = ""+AppConsts.DB_NAME;

    // data main
    private final String data = dataPath + dataName;

    // name of temp data
    private final String dataTempName = dataName + "_temp";

    // temp data for copy data from sd then copy data temp into main data
    private final String dataTemp = dataPath + dataTempName;

    // folder on sd to backup data
    private final String folderSD = Environment.getExternalStorageDirectory() + "/"+ AppConsts.BACKUP_DIR;




    public BackupData(Context context) {
        this.context = context;
    }

    // create folder if it not exist
    private void createFolder() {
        File sd = new File(folderSD);
        if (!sd.exists()) {
            sd.mkdir();
            System.out.println("create folder");
        } else {
            System.out.println("exits");
        }
    }

    /**
     * Copy database to sd card
     * name of file = database name + time when copy
     * When finish, we call onFinishExport method to send notify for activity
     */
    public void exportToSD() {

        String error = "No Error";
        try {

            createFolder();

            File sd = new File(folderSD);

            if (sd.canWrite()) {

                SimpleDateFormat formatTime = new SimpleDateFormat("yyyy_MM_dd__HH_mm_ss");
                String backupDBPath = dataName + "_" + formatTime.format(new Date());

                File currentDB = new File(Environment.getDataDirectory(), data);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                } else {
                    System.out.println("db not exist");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            error = "Error backup + "+ e.getMessage();
        }
        onBackupListener.onFinishExport(error);
       // return null;
    }

    /**
     * import data from file backup on sd card
     * we must create a temp database for copy file on sd card to it.
     * Then we copy all row of temp database into main database.
     * It will keep struct of curren database not change when struct backup database is old
     *
     * @param fileNameOnSD name of file database backup on sd card
     */
    public void importData(String fileNameOnSD) {

        File sd = new File(folderSD);

        // create temp database
        // SQLiteDatabase dbBackup = context.openOrCreateDatabase(dataTempName,
        // Context.MODE_PRIVATE,SQLiteDatabase.CREATE_IF_NECESSARY, null);


        SQLiteDatabase dbBackup = context.openOrCreateDatabase(dataTempName,Context.MODE_PRIVATE, null);

        String error = null;

        if (sd.canWrite()) {

            File currentDB = new File(Environment.getDataDirectory(), dataTemp);
            File backupDB = new File(sd, fileNameOnSD);

            if (currentDB.exists()) {
                FileChannel src;
                try {
                    src = new FileInputStream(backupDB).getChannel();
                    FileChannel dst = new FileOutputStream(currentDB)
                            .getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    error = "Error load file";
                } catch (IOException e) {
                    error = "Error import";
                }
            }
        }
        /**
         *when copy old database into temp database success
         * we copy all row of table into main database
         */

        if (error == null) {

        } else {
            onBackupListener.onFinishImport(error);
        }
    }

    /**
     * show dialog for select backup database before import database
     * if user select yes, we will export curren database
     * then show dialog to select old database to import
     * else we onoly show dialog to select old database to import
     */
    public void importFromSD() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Theme_AppCompat_Dialog_Alert);
        builder.setTitle("Data Backup / Restore").setIcon(R.drawable.trip_history)
                .setMessage("Select data");
        builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showDialogListFile(folderSD);
            }
        });
        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                exportToSD();
                showDialogListFile(folderSD);
            }
        });
        builder.show();
    }

    /**
     * show dialog list all backup file on sd card
     * @param forderPath folder conatain backup file
     */
    public void showDialogListFile(String forderPath) {
        createFolder();

        File forder = new File(forderPath);
        File[] listFile = forder.listFiles();

        if(listFile != null){
        final String[] listFileName = new String[listFile.length];
        for (int i = 0, j = listFile.length - 1; i < listFile.length; i++, j--) {
            listFileName[j] = listFile[i].getName();
        }


        if (listFileName.length > 0) {

            // get layout for list
            LayoutInflater inflater = ((FragmentActivity) context).getLayoutInflater();
            View convertView = (View) inflater.inflate(R.layout.list_backup_file, null);

            final AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Theme_AppCompat_Dialog_Alert);

            // set view for dialog
            builder.setView(convertView);
            builder.setTitle("Data Backup / Restore").setIcon(R.drawable.trip_history);

            final AlertDialog alert = builder.create();

            ListView lv = (ListView) convertView.findViewById(R.id.lv_backup);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                    android.R.layout.simple_list_item_1, listFileName);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    alert.dismiss();
                    importData(listFileName[position]);
                }
            });
            alert.show();
        }
        }else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Theme_AppCompat_Dialog_Alert)
                    .setMessage("Backup file is empty");
            builder.show();
        }
    }



    /**
     * AsyncTask for copy data
     */
    class CopyDataAsyncTask extends AsyncTask<Void, Void, Void> {
        ProgressDialog progress = new ProgressDialog(context);
        SQLiteDatabase db;

        public CopyDataAsyncTask(SQLiteDatabase dbBackup) {
            this.db = dbBackup;
        }

        /**
         * will call first
         */

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progress.setMessage("Importing...");
            progress.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            copyData(db);
            return null;
        }

        /**
         * copy all row of temp database into main database
         * @param dbBackup
         */
        private void copyData(SQLiteDatabase dbBackup) {

//            DatabaseHelper db = new DatabaseHelper(context);
//            db.deleteNote(null);

           /* DBAdapter db = new DBAdapter(context);*/

            /** copy all row of subject table */
           /* Cursor cursor = dbBackup.query(true, Database.PAYRECEIPT_SAVE1_TABLE, null, null, null, null, null, null, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                PaymentReceiptModel note = db.cursorToNote(cursor);
                new DBAdapter(context).insertPaymentReceipt(note);

                cursor.moveToNext();
            }
            cursor.close();
            context.deleteDatabase(dataTempName);*/
        }




        /**
         * end process
         */
        @Override
        protected void onPostExecute(Void error) {
            // TODO Auto-generated method stub
            super.onPostExecute(error);
            if (progress.isShowing()) {
                progress.dismiss();
            }
            onBackupListener.onFinishImport(null);
        }
    }




    private OnBackupListener onBackupListener;

    public void setOnBackupListener(OnBackupListener onBackupListener) {
        this.onBackupListener = onBackupListener;
    }

    public interface OnBackupListener {
        public void onFinishExport(String error);

        public void onFinishImport(String error);
    }
}
