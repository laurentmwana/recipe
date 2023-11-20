package com.example.recipe.controller;

import android.widget.Button;
import android.widget.EditText;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.chart.common.listener.ListenersInterface;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.example.recipe.R;
import com.example.recipe.helper.Flash;
import com.example.recipe.helper.Session;
import com.example.recipe.models.repository.ActionRepository;
import com.example.recipe.views.RapportActivity;

import java.util.ArrayList;


public class RapportController {

    private RapportActivity context;

    private AnyChartView mAnyChartViewRapport;

    private ActionRepository repository;


    public RapportController(RapportActivity context) {
        this.context = context;

        Session.check(context);

        this.repository = new ActionRepository(context);
    }

    public void handle() {

        mAnyChartViewRapport = context.findViewById(R.id.any_chart_view_rapport);

        float recipeTotal = repository.countRecipe();
        float expenseTotal = repository.countExpense();


        if (recipeTotal != 0 && expenseTotal != 0) {
            reload(recipeTotal, expenseTotal);
        }
    }

    private void reload(float recipes, float expenses) {
        Pie pie = AnyChart.pie();

        ArrayList<DataEntry> data = new ArrayList<>();

        data.add(new ValueDataEntry("Recette totale", recipes));
        data.add(new ValueDataEntry("Dépense totale", expenses));

        pie.data(data);

        mAnyChartViewRapport.setChart(pie);
    }
}