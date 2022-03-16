package smart.ai.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import smart.ai.model.NutritionalFormula;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "SQLite";
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "pumpDatabase";

    // Table name: Formula.
    private static final String TABLE_FORMULA = "Formula";

    private static final String COLUMN_FORMULA_ID ="Id";
    private static final String COLUMN_FORMULA_NAME ="formulaName";
    private static final String COLUMN_FORMULA_PERCENTWATER ="percentWater";
    private static final String COLUMN_FORMULA_PERCENTONE ="percentNutritionalOne";
    private static final String COLUMN_FORMULA_PERCENTTWO ="percentNutritionalTwo";
    private static final String COLUMN_FORMULA_PERCENTTHREE ="percentNutritionalThree";
    private static final String COLUMN_FORMULA_PERCENTFOUR ="percentNutritionalFour";

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create table
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "MyDatabaseHelper.onCreate ... ");
        // Script.
        String script = "CREATE TABLE " + TABLE_FORMULA + "("
                + COLUMN_FORMULA_ID + " TEXT PRIMARY KEY,"
                + COLUMN_FORMULA_NAME + " TEXT,"
                + COLUMN_FORMULA_PERCENTWATER + " DOUBLE,"
                + COLUMN_FORMULA_PERCENTONE + " DOUBLE,"
                + COLUMN_FORMULA_PERCENTTWO + " DOUBLE,"
                + COLUMN_FORMULA_PERCENTTHREE + " DOUBLE,"
                + COLUMN_FORMULA_PERCENTFOUR + " DOUBLE"
                + ")";
        // Execute Script.
        db.execSQL(script);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.i(TAG, "MyDatabaseHelper.onUpgrade ... ");
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FORMULA);

        // Create tables again
        onCreate(db);
    }

    public int getFormulaCount() {
        Log.i(TAG, "MyDatabaseHelper.getNotesCount ... " );

        String countQuery = "SELECT  * FROM " + TABLE_FORMULA;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }

    public void addFormula(NutritionalFormula formula) {
        Log.i(TAG, "MyDatabaseHelper.addFormula ... " + formula.getFormulaName());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_FORMULA_ID, formula.getId());
        values.put(COLUMN_FORMULA_NAME, formula.getFormulaName());
        values.put(COLUMN_FORMULA_PERCENTWATER, formula.getPercentWater());
        values.put(COLUMN_FORMULA_PERCENTONE, formula.getPercentNutritionalOne());
        values.put(COLUMN_FORMULA_PERCENTTWO, formula.getPercentNutritionalTwo());
        values.put(COLUMN_FORMULA_PERCENTTHREE, formula.getPercentNutritionalThree());
        values.put(COLUMN_FORMULA_PERCENTFOUR, formula.getPercentNutritionalFour());

        // Inserting Row
        db.insert(TABLE_FORMULA, null, values);

        // Closing database connection
        db.close();
    }

    // If Note table has no data
    // default, Insert 2 records.
    public void createDefaultFormula()  {
        int count = this.getFormulaCount();
        if(count == 0 ) {
            NutritionalFormula formulaOne = new NutritionalFormula(
                    UUID.randomUUID().toString(),
                    "Formula One",
                    60.0,
                    10.0,
                    10.0,
                    10.0,
                    10.0
            );
            NutritionalFormula formulaTwo = new NutritionalFormula(
                    UUID.randomUUID().toString(),
                    "Formula Two",
                    80.0,
                    5.0,
                    5.0,
                    5.0,
                    5.0
            );
            this.addFormula(formulaOne);
            this.addFormula(formulaTwo);
        }
    }

    public NutritionalFormula getFormula(String id) {
        Log.i(TAG, "MyDatabaseHelper.getFormula ... " + id);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_FORMULA, new String[] {
                        COLUMN_FORMULA_ID,
                        COLUMN_FORMULA_NAME,
                        COLUMN_FORMULA_PERCENTWATER,
                        COLUMN_FORMULA_PERCENTONE,
                        COLUMN_FORMULA_PERCENTTWO,
                        COLUMN_FORMULA_PERCENTTHREE,
                        COLUMN_FORMULA_PERCENTFOUR,
                }, COLUMN_FORMULA_ID + "=?",
                new String[] { id }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        NutritionalFormula formula = new NutritionalFormula(
                cursor.getString(0),
                cursor.getString(1),
                Double.parseDouble(cursor.getString(2)),
                Double.parseDouble(cursor.getString(3)),
                Double.parseDouble(cursor.getString(4)),
                Double.parseDouble(cursor.getString(5)),
                Double.parseDouble(cursor.getString(6)));

        // return formula
        return formula;
    }

    public boolean checkExist(String name) {
        Log.i(TAG, "MyDatabaseHelper.checkExist ... " + name);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_FORMULA, new String[] {
                        COLUMN_FORMULA_ID }, COLUMN_FORMULA_NAME + "=? COLLATE NOCASE",
                new String[] { name }, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()){
            return true;
        }else {
            return false;
        }
    }

    public List<NutritionalFormula> getAllFormulas() {
        Log.i(TAG, "MyDatabaseHelper.getAllFormulas ... " );

        List<NutritionalFormula> formulas = new ArrayList<NutritionalFormula>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_FORMULA;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                NutritionalFormula formula = new NutritionalFormula();
                formula.setId(cursor.getString(0));
                formula.setFormulaName(cursor.getString(1));
                formula.setPercentWater(Double.parseDouble(cursor.getString(2)));
                formula.setPercentNutritionalOne(Double.parseDouble(cursor.getString(3)));
                formula.setPercentNutritionalTwo(Double.parseDouble(cursor.getString(4)));
                formula.setPercentNutritionalThree(Double.parseDouble(cursor.getString(5)));
                formula.setPercentNutritionalFour(Double.parseDouble(cursor.getString(6)));

                // Adding formula to list
                formulas.add(formula);
            } while (cursor.moveToNext());
        }

        // return note list
        return formulas;
    }

    public int updateFormula(NutritionalFormula formula) {
        Log.i(TAG, "MyDatabaseHelper.updateFormula ... "  + formula.getFormulaName());

        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(COLUMN_FORMULA_ID, formula.getId());
        values.put(COLUMN_FORMULA_NAME, formula.getFormulaName());
        values.put(COLUMN_FORMULA_PERCENTWATER, formula.getPercentWater());
        values.put(COLUMN_FORMULA_PERCENTONE, formula.getPercentNutritionalOne());
        values.put(COLUMN_FORMULA_PERCENTTWO, formula.getPercentNutritionalTwo());
        values.put(COLUMN_FORMULA_PERCENTTHREE, formula.getPercentNutritionalThree());
        values.put(COLUMN_FORMULA_PERCENTFOUR, formula.getPercentNutritionalFour());

        // updating row
        return db.update(TABLE_FORMULA, values, COLUMN_FORMULA_ID + " = ?",
                new String[]{formula.getId()});
    }

    public void deleteFormula(NutritionalFormula formula) {
        Log.i(TAG, "MyDatabaseHelper.deleteFormula ... " + formula.getFormulaName() );

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FORMULA, COLUMN_FORMULA_ID + " = ?",
                new String[] { formula.getId() });
        db.close();
    }
}
