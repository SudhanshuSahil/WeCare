package com.example.demoapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.SingleValueDataSet;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.LinearGauge;
import com.anychart.charts.Scatter;
import com.anychart.core.scatter.series.Line;
import com.anychart.core.scatter.series.Marker;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Layout;
import com.anychart.enums.MarkerType;
import com.anychart.enums.Orientation;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipDisplayMode;
import com.anychart.graphics.vector.GradientKey;
import com.anychart.graphics.vector.LinearGradientStroke;
import com.anychart.graphics.vector.SolidFill;
import com.anychart.graphics.vector.text.HAlign;
import com.anychart.scales.OrdinalColor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Profile extends DialogFragment {
    private static Patient mpatient;
    DatabaseReference databaseReference;


    public static Profile newInstance(
            Patient patient) {

        Profile infoDialog = new Profile();
        mpatient = patient;
        return infoDialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);

        final Sharedpref sharedpref = new Sharedpref(getContext());
        databaseReference = FirebaseDatabase.getInstance().getReference("Patient");

        TextView name = view.findViewById(R.id.user);
        name.setText(mpatient.getName());

        TextView village = view.findViewById(R.id.village);
        String string = (mpatient.getVillage() + "\n Age : " + mpatient.getAge());
        village.setText(string);



        double bmi1 = (double) mpatient.getBmi();
        BmiChart(view, bmi1);

        double db = (double) mpatient.getBloodsugar();
        dbChart(view, db);

        double hb = (double) mpatient.getHaemobglobin();
        hmChart(view, hb);

        scatterChart(view);


//        FirebaseDatabase.getInstance().getReference("");

//        databaseReference.child(mpatient.getId());
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.hasChild("pulse rate")) {
//                    Toast.makeText(getContext(), "exists ", Toast.LENGTH_SHORT).show();
//                    double bt = (double) Float.parseFloat(dataSnapshot.child("pulse rate").getValue().toString());
//                    prChart(view, bt);
//                } else {
////                    Toast.makeText(getContext() , "oidnkcd" , Toast.LENGTH_SHORT).show();
//                    CardView pulsecard = view.findViewById(R.id.pr_card);
//                    pulsecard.setVisibility(View.GONE);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//        Toast.makeText(getContext() , string1 , Toast.LENGTH_SHORT).show();



        /* ************  Text area *********** */

        TextView bbb = view.findViewById(R.id.bmi_number);
        float bmi = Math.round(100 * mpatient.getBmi());
        bmi = bmi / 100;
        String str = "BMI is : " + bmi;
        bbb.setText(str);

        TextView db123 = view.findViewById(R.id.db_number);
        str = "Blood Sugar Level is : " + mpatient.getBloodsugar();
        db123.setText(str);

        TextView hb123 = view.findViewById(R.id.hp_number);
        str = "Haemoglobin Level is : " + mpatient.getHaemobglobin();
        hb123.setText(str);

//        TextView fhr = view.findViewById(R.id.fhr);
//        str = "Fetal Heart Rate is : " + mpatient.

        databaseReference.child(mpatient.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    Double db = (double) Float.parseFloat(dataSnapshot.child("fetal heart rate").getValue().toString());
                    TextView fhr = view.findViewById(R.id.fhr);

//                    Toast.makeText(getContext(), dataSnapshot.toString() , Toast.LENGTH_SHORT).show();

                    String str = "Fetal Heart Rate : " + dataSnapshot.child("fetal heart rate").getValue().toString();
                    fhr.setText(str);

                    fhrPlot(view, db);
                } catch (Exception e) {
                    CardView fhrcard = view.findViewById(R.id.fhrCard);
                    fhrcard.setVisibility(View.GONE);
                }

                try {
                    Double db = (double) Float.parseFloat(dataSnapshot.child("pulse rate").getValue().toString());
                    TextView fhr = view.findViewById(R.id.pr);

                    String str = "Pulse Rate : " + dataSnapshot.child("pulse rate").getValue().toString();
                    fhr.setText(str);

                    prChart(view, db);
                }
                catch (Exception e){
                    CardView fhrcard = view.findViewById(R.id.prCard);
                    fhrcard.setVisibility(View.GONE);
                }

                try{
                    Double bp1 = (double) Float.parseFloat(dataSnapshot.child("bp1").getValue().toString());
                    Double bp2 = (double) Float.parseFloat(dataSnapshot.child("bp2").getValue().toString());
                    TextView fhr = view.findViewById(R.id.bp1);

                    String str = "Blood Pressure : " + bp1 + " / " + bp2;
                    fhr.setText(str);

                    bpChart(view, bp1, bp2 );
                }
                catch (Exception e){
                    CardView fhrcard = view.findViewById(R.id.prCard);
                    fhrcard.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        /* ************  Recommendation area *********** */


        final EditText recommendation = view.findViewById(R.id.rec);
        Button sub = view.findViewById(R.id.submit);

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rec = recommendation.getText().toString();
                if(rec.equals("")){
                    Toast.makeText(getContext(), "Fill Recommendation Before Submmiting" , Toast.LENGTH_SHORT).show();
                    return;
                }
                recommendation recommendation1 = new recommendation(sharedpref.getName() , rec);

                databaseReference.child(mpatient.getId()).child("recommendation").push().setValue(recommendation1);

                databaseReference.child(mpatient.getId()).child("state").setValue(false);
                Toast.makeText(getContext() , "Updated ", Toast.LENGTH_SHORT).show();

//                dismiss();
            }
        });
        final TextView recom = view.findViewById(R.id.previous);


        databaseReference.child(mpatient.getId()).child("recommendation").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String string = "";
                int i = 1;

                for(DataSnapshot rec1: dataSnapshot.getChildren()){
//                    recommendation recommendation1 = rec1.;
                    recommendation rec123 = rec1.getValue(recommendation.class);

                    string += "Recommendation " + i + "\n " + rec123.getDoctor_name() + " : " + rec123.getMessage() + " \n \n";
                    i++;
                }

//                string += dataSnapshot.toString();
                recom.setText(string);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return view;
    }

    private void bpChart(View view, Double bp1, Double bp2) {
        AnyChartView anyChartView = view.findViewById(R.id.any_chart_view7);
        anyChartView.setProgressBar(view.findViewById(R.id.progress_bar7));
        APIlib.getInstance().setActiveAnyChartView(anyChartView);

        LinearGauge linearGauge = AnyChart.linear();


        linearGauge.data(new SingleValueDataSet(new Double[]{bp1}));

        linearGauge.layout(Layout.HORIZONTAL);

        linearGauge.label(0)
                .position(Position.LEFT_CENTER)
                .anchor(Anchor.LEFT_CENTER)
                .offsetY("-70px")
                .offsetX("50px")
                .fontColor("black")
                .fontSize(17);
        linearGauge.label(0).text("Systolic Blood Pressure ( beats / min )");


        OrdinalColor scaleBarColorScale = OrdinalColor.instantiate();
        scaleBarColorScale.ranges(new String[]{
                "{ from: 40, to: 60, color: ['red 0.5'] }",
                "{ from: 60, to: 60, color: ['yellow 0.5'] }",
                "{ from: 60, to: 80, color: ['green 0.5'] }",
                "{ from: 80, to: 90, color: ['yellow 0.5'] }",
                "{ from: 90, to: 100, color: ['red 0.5'] }"
        });

        linearGauge.scaleBar(0)
                .width("5%")
                .colorScale(scaleBarColorScale);

        linearGauge.marker(0)
                .type(MarkerType.TRIANGLE_UP)
                .color("blue" + "black")
                .offset("4.5%")
                .zIndex(10);

        linearGauge.scale()
                .minimum(40)
                .maximum(100);
//        linearGauge.scale().ticks;

        linearGauge.axis(0)
                .minorTicks(false)
                .width("1%");
        linearGauge.axis(0)
                .offset("-1.5%")
                .orientation(Orientation.TOP)
                .labels("top");

        linearGauge.padding(0, 30, 0, 30);

        anyChartView.setChart(linearGauge);


        AnyChartView anyChartView1 = view.findViewById(R.id.any_chart_view8);
        anyChartView1.setProgressBar(view.findViewById(R.id.progress_bar8));
        APIlib.getInstance().setActiveAnyChartView(anyChartView1);

        LinearGauge linearGauge1 = AnyChart.linear();


        linearGauge1.data(new SingleValueDataSet(new Double[]{bp2}));

        linearGauge1.layout(Layout.HORIZONTAL);

        linearGauge1.label(0)
                .position(Position.LEFT_CENTER)
                .anchor(Anchor.LEFT_CENTER)
                .offsetY("-70px")
                .offsetX("50px")
                .fontColor("black")
                .fontSize(17);
        linearGauge1.label(0).text("Diastolic Blood Pressure ( beats / min )");


        OrdinalColor scaleBarColorScale1 = OrdinalColor.instantiate();
        scaleBarColorScale1.ranges(new String[]{
                "{ from: 70, to: 90, color: ['red 0.5'] }",
                "{ from: 90, to: 90, color: ['yellow 0.5'] }",
                "{ from: 90, to: 120, color: ['green 0.5'] }",
                "{ from: 120, to: 140, color: ['yellow 0.5'] }",
                "{ from: 140, to: 500, color: ['red 0.5'] }"
        });

        linearGauge1.scaleBar(0)
                .width("5%")
                .colorScale(scaleBarColorScale1);

        linearGauge1.marker(0)
                .type(MarkerType.TRIANGLE_UP)
                .color("blue" + "black")
                .offset("4.5%")
                .zIndex(10);

        linearGauge1.scale()
                .minimum(70)
                .maximum(150);
//        linearGauge.scale().ticks;

        linearGauge1.axis(0)
                .minorTicks(false)
                .width("1%");
        linearGauge1.axis(0)
                .offset("-1.5%")
                .orientation(Orientation.TOP)
                .labels("top");

        linearGauge1.padding(0, 30, 0, 30);

        anyChartView1.setChart(linearGauge1);
    }


    private void fhrPlot(View view, Double db) {
        AnyChartView anyChartView = view.findViewById(R.id.any_chart_view5);
        anyChartView.setProgressBar(view.findViewById(R.id.progress_bar5));

        APIlib.getInstance().setActiveAnyChartView(anyChartView);

        LinearGauge linearGauge = AnyChart.linear();


        linearGauge.data(new SingleValueDataSet(new Double[]{db}));

        linearGauge.layout(Layout.HORIZONTAL);

        linearGauge.label(0)
                .position(Position.LEFT_CENTER)
                .anchor(Anchor.LEFT_CENTER)
                .offsetY("-70px")
                .offsetX("50px")
                .fontColor("black")
                .fontSize(17);
        linearGauge.label(0).text("Fetal Heart Rate ( beats / min )");


        OrdinalColor scaleBarColorScale = OrdinalColor.instantiate();
        scaleBarColorScale.ranges(new String[]{
                "{ from: 000, to: 110, color: ['red 0.5'] }",
                "{ from: 110, to: 120, color: ['yellow 0.5'] }",
                "{ from: 120, to: 160, color: ['green 0.5'] }",
                "{ from: 160, to: 170, color: ['yellow 0.5'] }",
                "{ from: 170, to: 325, color: ['red 0.5'] }"
        });

        linearGauge.scaleBar(0)
                .width("5%")
                .colorScale(scaleBarColorScale);

        linearGauge.marker(0)
                .type(MarkerType.TRIANGLE_UP)
                .color("blue" + "black")
                .offset("4.5%")
                .zIndex(10);

        linearGauge.scale()
                .minimum(50)
                .maximum(230);
//        linearGauge.scale().ticks;

        linearGauge.axis(0)
                .minorTicks(false)
                .width("1%");
        linearGauge.axis(0)
                .offset("-1.5%")
                .orientation(Orientation.TOP)
                .labels("top");

        linearGauge.padding(0, 30, 0, 30);

        anyChartView.setChart(linearGauge);
    }


    private  void prChart(View view , double db){
        AnyChartView anyChartView = view.findViewById(R.id.any_chart_view6);
        anyChartView.setProgressBar(view.findViewById(R.id.progress_bar6));

        APIlib.getInstance().setActiveAnyChartView(anyChartView);

        LinearGauge linearGauge = AnyChart.linear();


        linearGauge.data(new SingleValueDataSet(new Double[]{db}));

        linearGauge.layout(Layout.HORIZONTAL);

        linearGauge.label(0)
                .position(Position.LEFT_CENTER)
                .anchor(Anchor.LEFT_CENTER)
                .offsetY("-70px")
                .offsetX("50px")
                .fontColor("black")
                .fontSize(17);
        linearGauge.label(0).text("Pulse Rate ( beats / min )");


        OrdinalColor scaleBarColorScale = OrdinalColor.instantiate();
        scaleBarColorScale.ranges(new String[]{
                "{ from: 0, to: 60, color: ['red 0.5'] }",
                "{ from: 60, to: 60, color: ['yellow 0.5'] }",
                "{ from: 60, to: 90, color: ['green 0.5'] }",
                "{ from: 90, to: 90, color: ['yellow 0.5'] }",
                "{ from: 90, to: 200, color: ['red 0.5'] }"

        });

        linearGauge.scaleBar(0)
                .width("5%")
                .colorScale(scaleBarColorScale);

        linearGauge.marker(0)
                .type(MarkerType.TRIANGLE_UP)
                .color("blue" + "black")
                .offset("4.5%")
                .zIndex(10);

        linearGauge.scale()
                .minimum(20)
                .maximum(140);
//        linearGauge.scale().ticks;

        linearGauge.axis(0)
                .minorTicks(false)
                .width("1%");
        linearGauge.axis(0)
                .offset("-1.5%")
                .orientation(Orientation.TOP)
                .labels("top");

        linearGauge.padding(0, 30, 0, 30);

        anyChartView.setChart(linearGauge);
    }
    private void hmChart(View view, double db) {

        AnyChartView anyChartView = view.findViewById(R.id.any_chart_view3);
        anyChartView.setProgressBar(view.findViewById(R.id.progress_bar3));

        APIlib.getInstance().setActiveAnyChartView(anyChartView);

        LinearGauge linearGauge = AnyChart.linear();


        linearGauge.data(new SingleValueDataSet(new Double[]{db}));

        linearGauge.layout(Layout.HORIZONTAL);

        linearGauge.label(0)
                .position(Position.LEFT_CENTER)
                .anchor(Anchor.LEFT_CENTER)
                .offsetY("-70px")
                .offsetX("50px")
                .fontColor("black")
                .fontSize(17);
        linearGauge.label(0).text("Haemoglobin Scale (Kg / m^2 )");


        OrdinalColor scaleBarColorScale = OrdinalColor.instantiate();
        scaleBarColorScale.ranges(new String[]{
                "{ from: 0, to: 8, color: ['red 0.5'] }",
                "{ from: 8, to: 10, color: ['yellow 0.5'] }",
                "{ from: 10, to: 15, color: ['green 0.5'] }",
                "{ from: 15, to: 17, color: ['yellow 0.5'] }",
                "{ from: 17, to: 25, color: ['red 0.5'] }"
        });

        linearGauge.scaleBar(0)
                .width("5%")
                .colorScale(scaleBarColorScale);

        linearGauge.marker(0)
                .type(MarkerType.TRIANGLE_UP)
                .color("blue" + "black")
                .offset("4.5%")
                .zIndex(10);

        linearGauge.scale()
                .minimum(0)
                .maximum(25);
//        linearGauge.scale().ticks;

        linearGauge.axis(0)
                .minorTicks(false)
                .width("1%");
        linearGauge.axis(0)
                .offset("-1.5%")
                .orientation(Orientation.TOP)
                .labels("top");

        linearGauge.padding(0, 30, 0, 30);

        anyChartView.setChart(linearGauge);
    }

    private void BmiChart(View view, double bmi1) {

        AnyChartView anyChartView = view.findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(view.findViewById(R.id.progress_bar));

        APIlib.getInstance().setActiveAnyChartView(anyChartView);

        LinearGauge linearGauge = AnyChart.linear();


        linearGauge.data(new SingleValueDataSet(new Double[]{bmi1}));

        linearGauge.layout(Layout.HORIZONTAL);

        linearGauge.label(0)
                .position(Position.LEFT_CENTER)
                .anchor(Anchor.LEFT_CENTER)
                .offsetY("-70px")
                .offsetX("50px")
                .fontColor("black")
                .fontSize(17);
        linearGauge.label(0).text("BMI Scale (Kg / m^2 )");


        OrdinalColor scaleBarColorScale = OrdinalColor.instantiate();
        scaleBarColorScale.ranges(new String[]{
                "{ from: 0, to: 15, color: ['red 0.5'] }",
                "{ from: 15, to: 19, color: ['yellow 0.5'] }",
                "{ from: 19, to: 25, color: ['green 0.5'] }",
                "{ from: 25, to: 30, color: ['yellow 0.5'] }",
                "{ from: 30, to: 50, color: ['red 0.5'] }"
        });

        linearGauge.scaleBar(0)
                .width("5%")
                .colorScale(scaleBarColorScale);

        linearGauge.marker(0)
                .type(MarkerType.TRIANGLE_UP)
                .color("blue" + "black")
                .offset("4.5%")
                .zIndex(10);

        linearGauge.scale()
                .minimum(10)
                .maximum(35);
//        linearGauge.scale().ticks;

        linearGauge.axis(0)
                .minorTicks(false)
                .width("1%");
        linearGauge.axis(0)
                .offset("-1.5%")
                .orientation(Orientation.TOP)
                .labels("top");

        linearGauge.padding(0, 30, 0, 30);

        anyChartView.setChart(linearGauge);

    }

    private void dbChart(View view, double bmi1) {

        AnyChartView anyChartView1 = view.findViewById(R.id.any_chart_view2);
        anyChartView1.setProgressBar(view.findViewById(R.id.progress_bar2));

        APIlib.getInstance().setActiveAnyChartView(anyChartView1);

        LinearGauge linearGauge1 = AnyChart.linear();


        linearGauge1.data(new SingleValueDataSet(new Double[]{bmi1}));

        linearGauge1.layout(Layout.HORIZONTAL);

        linearGauge1.label(0)
                .position(Position.LEFT_CENTER)
                .anchor(Anchor.LEFT_CENTER)
                .offsetY("-70px")
                .offsetX("50px")
                .fontColor("black")
                .fontSize(17);
        linearGauge1.label(0).text("Blood Sugar Scale (mg / dl)");


        OrdinalColor scaleBarColorScale1 = OrdinalColor.instantiate();
        scaleBarColorScale1.ranges(new String[]{
                "{ from: 0, to: 50, color: ['red 0.5'] }",
                "{ from: 50, to: 80, color: ['yellow 0.5'] }",
                "{ from: 80, to: 130, color: ['green 0.5'] }",
                "{ from: 130, to: 170, color: ['yellow 0.5'] }",
                "{ from: 170, to: 500, color: ['red 0.5'] }"
        });

        linearGauge1.scaleBar(0)
                .width("5%")
                .colorScale(scaleBarColorScale1);

        linearGauge1.marker(0)
                .type(MarkerType.TRIANGLE_UP)
                .color("blue" + "black")
                .offset("4.5%")
                .zIndex(10);

        linearGauge1.scale()
                .minimum(30)
                .maximum(250);
//        linearGauge.scale().ticks;

        linearGauge1.axis(0)
                .minorTicks(false)
                .width("1%");
        linearGauge1.axis(0)
                .offset("-1.5%")
                .orientation(Orientation.TOP)
                .labels("top");

        linearGauge1.padding(0, 30, 0, 30);

        anyChartView1.setChart(linearGauge1);

    }

    private void scatterChart(View view) {
        AnyChartView anyChartView = view.findViewById(R.id.any_chart_view4);
        anyChartView.setProgressBar(view.findViewById(R.id.progress_bar4));

        APIlib.getInstance().setActiveAnyChartView(anyChartView);

        Scatter scatter = AnyChart.scatter();

        scatter.animation(true);

//        scatter.title("");

//        scatter.xScale()
//                .minimum(1.5d)
//                .maximum(5.5d);
//        scatter.xScale()
//        scatter.yScale()
//                .minimum(40d)
//                .maximum(100d);

//        scatter.yAxis(0).labels().format("${%value}");
        scatter.yAxis(0).ticks().length(0);
        scatter.xAxis(0).ticks().length(0);

//        scatter.tooltip().format("Value: {%Value}");
        scatter.yAxis(0).labels().format(" ");
        scatter.xAxis(0).labels().format(" ");

        scatter.yAxis(0).title("Blood Sugar (mg / dl)");
        scatter.xAxis(0)
                .title("BMI (KG / cm^2)")
                .drawFirstLabel(false)
                .drawLastLabel(false);

        Marker marker = scatter.marker(getMarkerData());
        marker.type(MarkerType.TRIANGLE_UP)
                .size(4d);

        List<DataEntry> list = new ArrayList<>();

        float x = 1 + (mpatient.getBmi() - 13) *(4.0f / 30);
        float y = 40 + (mpatient.getBloodsugar() - 60) * (60.0f / 200);
        list.add(new ValueDataEntry(x, y ));
//        list.add(new ValueDataEntry(15 / 7 , 69 / 3 ));

        Marker marker1 = scatter.marker(list);
        marker1.type(MarkerType.SQUARE).size(5d).fill(new SolidFill("green", 1d));

        TextView cat = view.findViewById(R.id.cat);
        String s = "BMI : " + mpatient.getBmi() + "\n Blood Sugar : " + mpatient.getBloodsugar();
        cat.setText(s);

        anyChartView.setChart(scatter);

    }


    private List<DataEntry> getMarkerData() {
        List<DataEntry> data = new ArrayList<>();

        data.add(new ValueDataEntry(4.4, 78));
        data.add(new ValueDataEntry(3.9, 74));
        data.add(new ValueDataEntry(4, 68));
        data.add(new ValueDataEntry(4, 76));
        data.add(new ValueDataEntry(3.5, 80));
        data.add(new ValueDataEntry(4.1, 84));
        data.add(new ValueDataEntry(2.3, 50));
        data.add(new ValueDataEntry(4.7, 93));
        data.add(new ValueDataEntry(1.7, 55));
        data.add(new ValueDataEntry(4.9, 76));
        data.add(new ValueDataEntry(1.7, 58));
        data.add(new ValueDataEntry(4.6, 74));
        data.add(new ValueDataEntry(3.4, 75));
        data.add(new ValueDataEntry(4.3, 80));
        data.add(new ValueDataEntry(1.7, 56));
        data.add(new ValueDataEntry(3.9, 80));
        data.add(new ValueDataEntry(3.7, 69));
        data.add(new ValueDataEntry(3.1, 57));
        data.add(new ValueDataEntry(4, 90));
        data.add(new ValueDataEntry(1.8, 42));
        data.add(new ValueDataEntry(4.1, 91));
        data.add(new ValueDataEntry(1.8, 51));
        data.add(new ValueDataEntry(3.2, 79));
        data.add(new ValueDataEntry(1.9, 53));
        data.add(new ValueDataEntry(4.6, 82));
        data.add(new ValueDataEntry(2, 51));
        data.add(new ValueDataEntry(4.5, 76));
        data.add(new ValueDataEntry(3.9, 82));
        data.add(new ValueDataEntry(4.3, 84));
        data.add(new ValueDataEntry(2.3, 53));
        data.add(new ValueDataEntry(3.8, 86));
        data.add(new ValueDataEntry(1.9, 51));
        data.add(new ValueDataEntry(4.6, 85));
        data.add(new ValueDataEntry(1.8, 45));
        data.add(new ValueDataEntry(4.7, 88));
        data.add(new ValueDataEntry(1.8, 51));
        data.add(new ValueDataEntry(4.6, 80));
        data.add(new ValueDataEntry(1.9, 49));
        data.add(new ValueDataEntry(3.5, 82));
        data.add(new ValueDataEntry(4, 75));
        data.add(new ValueDataEntry(3.7, 73));
        data.add(new ValueDataEntry(3.7, 67));
        data.add(new ValueDataEntry(4.3, 68));
        data.add(new ValueDataEntry(3.6, 86));
        data.add(new ValueDataEntry(3.8, 72));
        data.add(new ValueDataEntry(3.8, 75));
        data.add(new ValueDataEntry(3.8, 75));
        data.add(new ValueDataEntry(2.5, 66));
        data.add(new ValueDataEntry(4.5, 84));
        data.add(new ValueDataEntry(4.1, 70));
        data.add(new ValueDataEntry(3.7, 79));
        data.add(new ValueDataEntry(3.8, 60));
        data.add(new ValueDataEntry(3.4, 86));
        data.add(new ValueDataEntry(4, 71));
        data.add(new ValueDataEntry(2.3, 67));
        data.add(new ValueDataEntry(4.4, 81));
        data.add(new ValueDataEntry(4.1, 76));
        data.add(new ValueDataEntry(4.3, 83));
        data.add(new ValueDataEntry(3.3, 76));
        data.add(new ValueDataEntry(2, 55));
        data.add(new ValueDataEntry(4.3, 73));
        data.add(new ValueDataEntry(2.9, 56));
        data.add(new ValueDataEntry(4.6, 83));
        data.add(new ValueDataEntry(1.9, 57));
        data.add(new ValueDataEntry(3.6, 71));
        data.add(new ValueDataEntry(3.7, 72));
        data.add(new ValueDataEntry(3.7, 77));
        data.add(new ValueDataEntry(1.8, 55));
        data.add(new ValueDataEntry(4.6, 75));
        data.add(new ValueDataEntry(3.5, 73));
        data.add(new ValueDataEntry(4, 70));
        data.add(new ValueDataEntry(3.7, 83));
        data.add(new ValueDataEntry(1.7, 50));
        data.add(new ValueDataEntry(4.6, 95));
        data.add(new ValueDataEntry(1.7, 51));
        data.add(new ValueDataEntry(4, 82));
        data.add(new ValueDataEntry(1.8, 54));
        data.add(new ValueDataEntry(4.4, 83));
        data.add(new ValueDataEntry(1.9, 51));
        data.add(new ValueDataEntry(4.6, 80));
        data.add(new ValueDataEntry(2.9, 78));
        data.add(new ValueDataEntry(3.5, 81));
        data.add(new ValueDataEntry(2, 53));
        data.add(new ValueDataEntry(4.3, 89));
        data.add(new ValueDataEntry(1.8, 44));
        data.add(new ValueDataEntry(4.1, 78));
        data.add(new ValueDataEntry(1.8, 61));
        data.add(new ValueDataEntry(4.7, 73));
        data.add(new ValueDataEntry(4.2, 75));
        data.add(new ValueDataEntry(3.9, 73));
        data.add(new ValueDataEntry(4.3, 76));
        data.add(new ValueDataEntry(1.8, 55));
        data.add(new ValueDataEntry(4.5, 86));
        data.add(new ValueDataEntry(2, 48));
        data.add(new ValueDataEntry(4.2, 77));
        data.add(new ValueDataEntry(4.4, 73));
        data.add(new ValueDataEntry(4.1, 70));
        data.add(new ValueDataEntry(4.1, 88));
        data.add(new ValueDataEntry(4, 75));
        data.add(new ValueDataEntry(4.1, 83));
        data.add(new ValueDataEntry(2.7, 61));
        data.add(new ValueDataEntry(4.6, 78));
        data.add(new ValueDataEntry(1.9, 61));
        data.add(new ValueDataEntry(4.5, 81));
        data.add(new ValueDataEntry(2, 51));
        data.add(new ValueDataEntry(4.8, 80));
        data.add(new ValueDataEntry(4.1, 79));
        data.add(new ValueDataEntry(4.1, 82));
        data.add(new ValueDataEntry(4.2, 80));
        data.add(new ValueDataEntry(4.5, 76));
        data.add(new ValueDataEntry(1.9, 56));
        data.add(new ValueDataEntry(4.7, 82));
        data.add(new ValueDataEntry(2, 47));
        data.add(new ValueDataEntry(4.7, 76));
        data.add(new ValueDataEntry(2.5, 61));
        data.add(new ValueDataEntry(4.3, 75));
        data.add(new ValueDataEntry(4.4, 72));
        data.add(new ValueDataEntry(4.4, 74));
        data.add(new ValueDataEntry(4.3, 69));
        data.add(new ValueDataEntry(4.6, 78));
        data.add(new ValueDataEntry(2.1, 52));
        data.add(new ValueDataEntry(4.8, 91));
        data.add(new ValueDataEntry(4.1, 66));
        data.add(new ValueDataEntry(4, 71));
        data.add(new ValueDataEntry(4, 75));
        data.add(new ValueDataEntry(4.4, 81));
        data.add(new ValueDataEntry(4.1, 77));
        data.add(new ValueDataEntry(4.3, 74));
        data.add(new ValueDataEntry(4, 70));
        data.add(new ValueDataEntry(3.9, 83));
        data.add(new ValueDataEntry(3.2, 53));
        data.add(new ValueDataEntry(4.5, 82));
        data.add(new ValueDataEntry(2.2, 62));
        data.add(new ValueDataEntry(4.7, 73));
        data.add(new ValueDataEntry(4.6, 84));
        data.add(new ValueDataEntry(2.2, 58));
        data.add(new ValueDataEntry(4.8, 82));
        data.add(new ValueDataEntry(4.3, 77));
        data.add(new ValueDataEntry(3.8, 75));
        data.add(new ValueDataEntry(4, 77));
        data.add(new ValueDataEntry(4.1, 77));
        data.add(new ValueDataEntry(1.8, 53));
        data.add(new ValueDataEntry(4.4, 75));
        data.add(new ValueDataEntry(4, 78));
        data.add(new ValueDataEntry(2.2, 51));
        data.add(new ValueDataEntry(5.1, 81));
        data.add(new ValueDataEntry(1.9, 52));
        data.add(new ValueDataEntry(5, 76));
        data.add(new ValueDataEntry(4.4, 73));
        data.add(new ValueDataEntry(4.5, 84));
        data.add(new ValueDataEntry(3.8, 72));
        data.add(new ValueDataEntry(4.3, 89));
        data.add(new ValueDataEntry(4.4, 75));
        data.add(new ValueDataEntry(2.2, 57));
        data.add(new ValueDataEntry(4.8, 81));
        data.add(new ValueDataEntry(1.9, 49));
        data.add(new ValueDataEntry(4.7, 87));
        data.add(new ValueDataEntry(1.8, 43));
        data.add(new ValueDataEntry(4.8, 94));
        data.add(new ValueDataEntry(2, 45));
        data.add(new ValueDataEntry(4.4, 81));
        data.add(new ValueDataEntry(2.5, 59));
        data.add(new ValueDataEntry(4.3, 82));
        data.add(new ValueDataEntry(4.4, 80));
        data.add(new ValueDataEntry(1.9, 54));
        data.add(new ValueDataEntry(4.7, 75));
        data.add(new ValueDataEntry(4.3, 73));
        data.add(new ValueDataEntry(2.2, 57));
        data.add(new ValueDataEntry(4.7, 80));
        data.add(new ValueDataEntry(2.3, 51));
        data.add(new ValueDataEntry(4.6, 77));
        data.add(new ValueDataEntry(3.3, 66));
        data.add(new ValueDataEntry(4.2, 77));
        data.add(new ValueDataEntry(2.9, 60));
        data.add(new ValueDataEntry(4.6, 86));
        data.add(new ValueDataEntry(3.3, 62));
        data.add(new ValueDataEntry(4.2, 75));
        data.add(new ValueDataEntry(2.6, 67));
        data.add(new ValueDataEntry(4.6, 69));
        data.add(new ValueDataEntry(3.7, 84));
        data.add(new ValueDataEntry(1.8, 58));
        data.add(new ValueDataEntry(4.7, 90));
        data.add(new ValueDataEntry(4.5, 82));
        data.add(new ValueDataEntry(4.5, 71));
        data.add(new ValueDataEntry(4.8, 80));
        data.add(new ValueDataEntry(2, 51));
        data.add(new ValueDataEntry(4.8, 80));
        data.add(new ValueDataEntry(1.9, 62));
        data.add(new ValueDataEntry(4.7, 84));
        data.add(new ValueDataEntry(2, 51));
        data.add(new ValueDataEntry(5.1, 81));
        data.add(new ValueDataEntry(4.3, 83));
        data.add(new ValueDataEntry(4.8, 84));
        data.add(new ValueDataEntry(3, 72));
        data.add(new ValueDataEntry(2.1, 54));
        data.add(new ValueDataEntry(4.6, 75));
        data.add(new ValueDataEntry(4, 74));
        data.add(new ValueDataEntry(2.2, 51));
        data.add(new ValueDataEntry(5.1, 91));
        data.add(new ValueDataEntry(2.9, 60));
        data.add(new ValueDataEntry(4.3, 80));
        data.add(new ValueDataEntry(2.1, 54));
        data.add(new ValueDataEntry(4.7, 80));
        data.add(new ValueDataEntry(4.5, 70));
        data.add(new ValueDataEntry(1.7, 60));
        data.add(new ValueDataEntry(4.2, 86));
        data.add(new ValueDataEntry(4.3, 78));
        data.add(new ValueDataEntry(1.7, 51));
        data.add(new ValueDataEntry(4.4, 83));
        data.add(new ValueDataEntry(4.2, 76));
        data.add(new ValueDataEntry(2.2, 51));
        data.add(new ValueDataEntry(4.7, 90));
        data.add(new ValueDataEntry(4, 71));
        data.add(new ValueDataEntry(1.8, 49));
        data.add(new ValueDataEntry(4.7, 88));
        data.add(new ValueDataEntry(1.8, 52));
        data.add(new ValueDataEntry(4.5, 79));
        data.add(new ValueDataEntry(2.1, 61));
        data.add(new ValueDataEntry(4.2, 81));
        data.add(new ValueDataEntry(2.1, 48));
        data.add(new ValueDataEntry(5.2, 84));
        data.add(new ValueDataEntry(2, 63));

        return data;
    }

}
