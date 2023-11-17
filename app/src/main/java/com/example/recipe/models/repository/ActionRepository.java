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
            "id", "start_time", "end_time", "amount_daily_recipe", "amount_daily_expense", "state", "created_at", "updated_at"};

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

        return (new Insert(database)).from(TABLE).values(values).save();
    }

    public ArrayList<Action> findAll() {
        // on recupère les données depuis la base de données
        Cursor cs = (new Select(database))
                .from(TABLE, null)
                .select(columns)
                .orderBy("id", "DESC")
                .orderBy("created_at", "DESC")
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
                .from(TABLE, null)
                .select(columns)
                .where("id = ?")
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
                .from(TABLE, null)
                .select(columns)
                .where("state = 0")
                .orderBy("created_at", "DESC")
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

    public ArrayList<Action> like(String v) {
        // on recupère les données depuis la base de données
        Cursor cs = (new Select(database))
                 .from(TABLE, null)
                .select(columns)
                .where("start_time LIKE ?")
                .orWhere("end_time LIKE ?", "amount_daily_recipe LIKE ?")
                .orderBy("created_at", "DESC")
                .params(v, v, v)
                .execute();

        ArrayList<Action> ev = new ArrayList<Action>();

        // on fait l'hydratation
        // pour avoir les données sous forme d'objets
        while (cs.moveToNext()) {
            ev.add(getHydrate(cs));
        }
        return ev;
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
}
