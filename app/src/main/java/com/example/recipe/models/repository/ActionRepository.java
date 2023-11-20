package com.example.recipe.models.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;


import com.example.recipe.database.AppDatabase;
import com.example.recipe.exception.NotFoundException;
import com.example.recipe.helper.Moment;
import com.example.recipe.models.entity.Action;
import com.example.recipe.models.query.Delete;
import com.example.recipe.models.query.Insert;
import com.example.recipe.models.query.Select;
import com.example.recipe.models.query.Update;

import java.util.ArrayList;

public class ActionRepository {

    public static final String TABLE = "actions";

    private final AppDatabase database;
    private Context context;

    private String[] columns = new String[]{
            "a.id", "a.start_time", "a.end_time", "a.amount_daily_recipe", "a.amount_daily_expense",
            "a.state", "a.created_at", "a.updated_at"};

    public ActionRepository(Context context) {

        this.database = AppDatabase.getDatabaseInstance(context.getApplicationContext());
        this.context = context;
    }

    /**
     * Permet d'ajouter les données dans la table events
     *
     * @param start
     * @return
     */
    public boolean startedAdd(String start) {

        ContentValues values = new ContentValues();
        values.put("start_time", start);
        values.put("created_at", Moment.at());
        values.put("updated_at", Moment.at());

        return (new Insert(database)).from(TABLE).values(values).save();
    }

    public ArrayList<Action> findAll() {
        // on recupère les données depuis la base de données
        Cursor cs = (new Select(database))
                .from(TABLE, "a")
                .select(columns)
                .orderBy("a.id", "DESC")
                .orderBy("a.created_at", "DESC")
                .execute();

        ArrayList<Action> ev = new ArrayList<Action>();

        // on fait l'hydratation
        // pour avoir les données sous forme d'objets
        while (cs.moveToNext()) {
            ev.add(getHydrate(cs));
        }
        return ev;
    }

    public Action find(String id) throws NotFoundException {
        // on recupère les données depuis la base de données qui repondent à la condition id = id
        Cursor cs = (new Select(database))
                .from(TABLE, "a")
                .select(columns)
                .where("a.id = ?")
                .params(id)
                .limit(1)
                .execute();
        if (null == cs) {
            throw new NotFoundException("nous n'avons pas trouvé l'évènement #" + id);
        }
        cs.moveToFirst();

        return getHydrate(cs);
    }


    private Action getHydrate(Cursor cs) {

        return  (new Action())
                .setId(cs.getInt(0))
                .setStartTime(cs.getString(1))
                .setEndTime(cs.getString(2))
                .setAmountDailyRecipe(cs.getFloat(3))
                .setAmountDailyExpense(cs.getFloat(4))
                .setState(cs.getInt(5))
                .setCreatedAt(cs.getString(6))
                .setUpdatedAt(cs.getString(7));
    }

    public Action findState() {
        // on recupère les données depuis la base de données qui repondent à la condition id = id
        Cursor cs = (new Select(database))
                .from(TABLE, "a")
                .select(columns)
                .where("a.state = 0")
                .orderBy("a.created_at", "DESC")
                .limit(1).execute();
        if (cs.getCount() <= 0) {
            return null;
        }
        cs.moveToFirst();

        return getHydrate(cs);
    }

    public boolean startedUpdate(Action action) {
        ContentValues vs = new ContentValues();
        vs.put("start_time", action.getStartTime());
        return (new Update(database))
                .from(TABLE, null)
                .values(vs)
                .where("id = ?").args(String.valueOf(action.getId()))
                .save();
    }

    public boolean delete(Action action) {
        return (new Delete(database))
                .from(TABLE, null)
                .where("id = ?")
                .args(String.valueOf(action.getId()))
                .save();
    }


    public boolean update(Action action) {
        ContentValues vs = new ContentValues();
        vs.put("start_time", action.getStartTime());
        vs.put("end_time", action.getEndTime());
        vs.put("amount_daily_recipe", action.getAmountDailyRecipe());
        vs.put("amount_daily_expense", action.getAmountDailyExpense());
        vs.put("state", 1);
        vs.put("updated_at", Moment.at());
        return (new Update(database))
                .from(TABLE, null)
                .values(vs)
                .where("id = ?").args(String.valueOf(action.getId()))
                .save();
    }

    public ArrayList<Action> findByDate(String date) {
        // on recupère les données depuis la base de données

        Cursor cs = database.writable.rawQuery("SELECT * FROM actions WHERE created_at LIKE  ?",
                new String[]{date});

        ArrayList<Action> ev = new ArrayList<Action>();

        // on fait l'hydratation
        // pour avoir les données sous forme d'objets
        while (cs.moveToNext()) {
            ev.add(getHydrate(cs));
        }
        return ev;
    }

    public ArrayList<Action> biggest(String v, boolean b) {
        String by = b ? "amount_daily_expense" : "amount_daily_recipe";
        Cursor cs;
        if (null == v || v.isEmpty()) {
            cs = database.writable
                    .rawQuery("SELECT * FROM actions ORDER BY "+ by + " DESC  LIMIT 1", null);
        } else {
            cs = database.writable
                    .rawQuery("SELECT * FROM actions WHERE created_at LIKE ? ORDER BY "+ by + " DESC  LIMIT 1",
                            new String[]{by});
        }

        ArrayList<Action> ev = new ArrayList<Action>();

        while (cs.moveToNext()) {
            ev.add(getHydrate(cs));
        }
        return ev;
    }

    public ArrayList<Action> smallest(String v, boolean b) {
        String by = b ? "amount_daily_expense" : "amount_daily_recipe";
        Cursor cs;
        if (null == v || v.isEmpty()) {
            cs = database.writable
                    .rawQuery("SELECT * FROM actions ORDER BY "+ by + " ASC  LIMIT 1", null);
        } else {
            cs = database.writable
                    .rawQuery("SELECT * FROM actions WHERE created_at LIKE ? ORDER BY "+ by + " ASC  LIMIT 1",
                            new String[]{by});
        }

        ArrayList<Action> ev = new ArrayList<Action>();

        while (cs.moveToNext()) {
            ev.add(getHydrate(cs));
        }
        return ev;
    }

    public ArrayList<Action> expenses() {
        Cursor cs = database.writable
                .rawQuery("SELECT * FROM actions ORDER BY created_at DESC ", null);

        ArrayList<Action> ev = new ArrayList<Action>();

        while (cs.moveToNext()) {
            ev.add(getHydrate(cs));
        }
        return ev;
    }

    public Float countExpense() {
        Cursor cs = database.writable
                .rawQuery("SELECT SUM(amount_daily_expense) AS ade FROM actions", null);

        cs.moveToFirst();

        return cs.getFloat(0);
    }

    public float countRecipe() {
        Cursor cs = database.writable
                .rawQuery("SELECT SUM(amount_daily_recipe) AS ade FROM actions", null);

        cs.moveToFirst();

        return cs.getFloat(0);
    }

    public int times() {
        String sql = "SELECT " +
                "SUM(STRFTIME('%s', end_time) - STRFTIME('%s', start_time)) AS ade FROM" +
                " actions GROUP BY ade";
        Cursor cs = database.writable.rawQuery(sql, null);

        cs.moveToFirst();

        return cs.getInt(0);
    }

    public void deleteAll() {
        (new Delete(database))
                .from(TABLE, null)
                .save();
    }
}
