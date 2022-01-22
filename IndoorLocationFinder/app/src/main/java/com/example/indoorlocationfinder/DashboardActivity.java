package com.example.indoorlocationfinder;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.util.ArrayList;

public class DashboardActivity extends Activity {
    private static final String TAG = "DashboardActivity";

    private int click = 0;
    private Button findLocation;
    private Button logout;
    //private EditText mX,mY;
    private TextView PositionText;

    GraphView mScatterPlot;

    //make xyValueArray global
    private ArrayList<CoordinatesXY> xyValueArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locationmap);
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //declare variables in on create
        findLocation = findViewById(R.id.findLocation);
        /* mX = (EditText) findViewById(R.id.numX);
        mY = (EditText) findViewById(R.id.numY);*/
        mScatterPlot = (GraphView) findViewById(R.id.scatterPlot);
        xyValueArray = new ArrayList<>();

        findLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //double x = Double.parseDouble(mX.getText().toString());
                //double y = Double.parseDouble(mY.getText().toString());
                //Log.d(TAG, "onClick: Adding a new point. (x,y): (" + x + "," + y + ")" );
                click++;

                createScatterPlot(15,84,click);
                //initGraph(mScatterPlot);
                PositionText = findViewById(R.id.position);
                PositionText.setText("\nFloor No : 1 \n \n  Position : 1 ");
                createScatterPlot(15,84,click);
                PositionText = findViewById(R.id.position);
                PositionText.setText("\nFloor No : 2 \n \n  Position : 2 ");

                /*
                if(!mX.getText().toString().equals("") && !mY.getText().toString().equals("") ){
                    double x = Double.parseDouble(mX.getText().toString());
                    double y = Double.parseDouble(mY.getText().toString());
                    Log.d(TAG, "onClick: Adding a new point. (x,y): (" + x + "," + y + ")" );
                    xyValueArray.add(new CoordinatesXY(x,y));

                    //little bit of exception handling for if there is no data.
                    if(xyValueArray.size() != 0){
                        createScatterPlot();
                    }else{
                        Log.d(TAG, "onCreate: No data to plot.");
                    }
                }else {
                    toastMessage("You must fill out both fields!");
                }*/
            }
        });
    }

    public void initGraph(GraphView graph) {
        DataPoint[] minPoints = new DataPoint[30];
        DataPoint[] maxPoints = new DataPoint[30];
        DataPoint[] teoricPoints = new DataPoint[30];
        DataPoint[] basePoint = new DataPoint[1];
        for (int i = 0; i < 30; i++) {
            //points[i] = new DataPoint(i, Math.sin(i*0.5) * 20*(Math.random()*10+1));
            minPoints[i] = new DataPoint(i, 20);
            maxPoints[i] = new DataPoint(i, 45);
            teoricPoints[i] = new DataPoint(i, 30);
        }
        basePoint[0] = new DataPoint(0, 0);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(minPoints);
        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>(maxPoints);
        LineGraphSeries<DataPoint> series3 = new LineGraphSeries<>(teoricPoints);
        LineGraphSeries<DataPoint> series4 = new LineGraphSeries<>(basePoint);

        graph.getGridLabelRenderer().setGridColor(Color.RED);
        graph.getGridLabelRenderer().setHighlightZeroLines(false);
        graph.getGridLabelRenderer().setHorizontalLabelsColor(Color.GREEN);
        graph.getGridLabelRenderer().setVerticalLabelsColor(Color.RED);
        graph.getGridLabelRenderer().setVerticalLabelsVAlign(GridLabelRenderer.VerticalLabelsVAlign.ABOVE);
        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);
        graph.getGridLabelRenderer().reloadStyles();


        // styling viewport
        graph.getViewport().setBackgroundColor(Color.argb(255, 222, 222, 222));
        graph.getViewport().setDrawBorder(true);
        graph.getViewport().setBorderColor(Color.BLUE);

        // styling series
        series.setTitle("Random Curve 1");
        series.setColor(Color.GREEN);

        series.setThickness(8);

        series2.setTitle("Random Curve 2");
        series2.setColor(Color.GREEN);

        series2.setThickness(8);

        series3.setTitle("Random Curve 3");
        series3.setColor(Color.GREEN);

        series3.setThickness(8);

        series4.setTitle("Random Curve 4");
        series4.setColor(Color.GREEN);

        series4.setThickness(8);

        graph.getLegendRenderer().setFixedPosition(150, 0);

        graph.addSeries(series);
        graph.addSeries(series2);
        graph.addSeries(series3);
        graph.addSeries(series4);

    }


    private void createScatterPlot(double x,double y, int cnt) {
        PointsGraphSeries<DataPoint> layout   = new PointsGraphSeries<>();
        PointsGraphSeries<DataPoint> location = new PointsGraphSeries<>();
        PointsGraphSeries<DataPoint> elevator = new PointsGraphSeries<>();
        PointsGraphSeries<DataPoint> rooms    = new PointsGraphSeries<>();
        mScatterPlot.removeAllSeries();

        double lv_count = 5;
        double y_count  = 0;

        Log.d(TAG, "createScatterPlot: Creating scatter plot.");
        try{
            // Layout
            while(lv_count < x * 10.0){
                if (lv_count == 5){
                        int lv_ycount = 120;
                        while (lv_ycount > -120){
                            layout.appendData(new DataPoint(lv_count, lv_ycount), true, 2000);
                            lv_ycount--;
                        }
                } else if (lv_count == (x * 10)-1){

                }

                layout.appendData(new DataPoint(lv_count, (y+50)),true, 2000);
                if (lv_count < 65 || lv_count > 95) {
                    layout.appendData(new DataPoint(lv_count, 0), true, 2000);
                }


                if (lv_count == 65){
                    int lv_ycount = 120;
                    while (lv_ycount > -120){
                        layout.appendData(new DataPoint(lv_count, lv_ycount), true, 2000);
                        lv_ycount--;
                    }
                }

                if (lv_count == 95){
                    int lv_ycount = 120;
                    while (lv_ycount > -120){
                        layout.appendData(new DataPoint(lv_count, lv_ycount), true, 2000);
                        lv_ycount--;
                    }
                }


                if (lv_count == 149){
                    int lv_ycount = 120;
                    while (lv_ycount > -120){
                        layout.appendData(new DataPoint(lv_count, lv_ycount), true, 2000);
                        lv_ycount--;
                    }
                }
                layout.appendData(new DataPoint(lv_count, -125),true, 2000);
                lv_count = lv_count + 1;
            }

            //Location
            if (cnt == 1){
                location.appendData(new DataPoint(40, 120), true, 2000);

            } else if (cnt == 2){
                location.appendData(new DataPoint(20, -50), true, 2000);

            }
            else {
                location.appendData(new DataPoint(120, 50), true, 2000);
            }
            //Elevator
            elevator.appendData(new DataPoint( 72    ,120),true, 2000);
            elevator.appendData(new DataPoint( 72    ,110),true, 2000);
            elevator.appendData(new DataPoint( 74    ,120),true, 2000);
            elevator.appendData(new DataPoint( 74    ,110),true, 2000);
            elevator.appendData(new DataPoint( 80    ,120),true, 2000);
            elevator.appendData(new DataPoint( 80    ,110),true, 2000);
            elevator.appendData(new DataPoint( 82    ,120),true, 2000);
            elevator.appendData(new DataPoint( 82    ,110),true, 2000);
            elevator.appendData(new DataPoint( 84    ,120),true, 2000);
            elevator.appendData(new DataPoint( 84    ,110),true, 2000);
            elevator.appendData(new DataPoint( 86    ,120),true, 2000);
            elevator.appendData(new DataPoint( 86    ,110),true, 2000);
            elevator.appendData(new DataPoint( 86    ,120),true, 2000);
            elevator.appendData(new DataPoint( 86    ,110),true, 2000);
            elevator.appendData(new DataPoint( 88    ,110),true, 2000);
            elevator.appendData(new DataPoint( 88    ,120),true, 2000);
            elevator.appendData(new DataPoint( 89    ,110),true, 2000);
            elevator.appendData(new DataPoint( 89    ,120),true, 2000);

            //Room Structure

            } catch (IllegalArgumentException e){
                Log.e(TAG, "createScatterPlot: IllegalArgumentException: " + e.getMessage() );
        }

        //Properties for layout
        layout.setShape(PointsGraphSeries.Shape.RECTANGLE);
        layout.setColor(Color.BLUE);
        layout.setSize(20f);

        //Properties for room structure
        rooms.setShape(PointsGraphSeries.Shape.RECTANGLE);
        rooms.setColor(Color.BLUE);
        rooms.setSize(10f);

        //Properties for location
        location.setShape(PointsGraphSeries.Shape.TRIANGLE);
        location.setColor(Color.GREEN);
        location.setSize(40f);

        //Properties for elevator
        elevator.setShape(PointsGraphSeries.Shape.RECTANGLE);
        elevator.setColor(Color.RED);
        elevator.setSize(20f);

        //Scrollable and Scalable
        mScatterPlot.getViewport().setScalable(true);
        mScatterPlot.getViewport().setScalableY(true);
        mScatterPlot.getViewport().setScrollable(true);
        mScatterPlot.getViewport().setScrollableY(true);

        //X bounds and Y bounds
        mScatterPlot.getViewport().setYAxisBoundsManual(true);
        mScatterPlot.getViewport().setMaxY(150);
        mScatterPlot.getViewport().setMinY(-150);

        mScatterPlot.getViewport().setXAxisBoundsManual(true);
        mScatterPlot.getViewport().setMaxX(150);
        mScatterPlot.getViewport().setMinX(0);

        //Add all points to the layout
        mScatterPlot.addSeries(layout);
        mScatterPlot.addSeries(location);
        mScatterPlot.addSeries(elevator);
        mScatterPlot.addSeries(rooms);

    }

    /**
     * Sorts an ArrayList<XYValue> with respect to the x values.
     * @param array
     * @return
     */
    private ArrayList<CoordinatesXY> sortArray(ArrayList<CoordinatesXY> array){
        /*
        //Sorts the xyValues in Ascending order to prepare them for the PointsGraphSeries<DataSet>
         */
        int factor = Integer.parseInt(String.valueOf(Math.round(Math.pow(array.size(),2))));
        int m = array.size() - 1;
        int count = 0;
        Log.d(TAG, "sortArray: Sorting the XYArray.");


        while (true) {
            m--;
            if (m <= 0) {
                m = array.size() - 1;
            }
            Log.d(TAG, "sortArray: m = " + m);
            try {
                //print out the y entrys so we know what the order looks like
                //Log.d(TAG, "sortArray: Order:");
                //for(int n = 0;n < array.size();n++){
                //Log.d(TAG, "sortArray: " + array.get(n).getY());
                //}
                double tempY = array.get(m - 1).getY();
                double tempX = array.get(m - 1).getX();
                if (tempX > array.get(m).getX()) {
                    array.get(m - 1).setY(array.get(m).getY());
                    array.get(m).setY(tempY);
                    array.get(m - 1).setX(array.get(m).getX());
                    array.get(m).setX(tempX);
                } else if (tempX == array.get(m).getX()) {
                    count++;
                    Log.d(TAG, "sortArray: count = " + count);
                } else if (array.get(m).getX() > array.get(m - 1).getX()) {
                    count++;
                    Log.d(TAG, "sortArray: count = " + count);
                }
                //break when factorial is done
                if (count == factor) {
                    break;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                Log.e(TAG, "sortArray: ArrayIndexOutOfBoundsException. Need more than 1 data point to create Plot." +
                        e.getMessage());
                break;
            }
        }
        return array;
    }

    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}