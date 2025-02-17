package com.Kuhouinn.fetch2.database.migration

import androidx.sqlite.db.SupportSQLiteDatabase
import com.Kuhouinn.fetch2.database.DownloadDatabase
import com.Kuhouinn.fetch2.util.EMPTY_JSON_OBJECT_STRING

class MigrationFiveToSix : Migration(5, 6) {

    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE '${DownloadDatabase.TABLE_NAME}' "
                + "ADD COLUMN '${DownloadDatabase.COLUMN_EXTRAS}' TEXT NOT NULL DEFAULT '$EMPTY_JSON_OBJECT_STRING'")
    }

}