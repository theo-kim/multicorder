package com.b2ktechnology.multicorder;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Iggy on 11/17/2016.
 */

public class PageFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;

    private String[] getMeasures() {
        List<String> output = new ArrayList<String>();
        String line = "";
        String[] feed = new String[3];

        InputStream file = getResources().openRawResource(R.raw.sensors);
        BufferedReader read = new BufferedReader(new InputStreamReader(file));
        try {
            while ((line = read.readLine()) != null) {
                feed = line.split(":");
                //Log.d("Hello",feed[0]);
                output.add(feed[0]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] the = new String[ output.size() ];
        output.toArray( the );

        return the;
    }

    private List<List<String[]>> getUnits() {
        List<List<String[]>> output = new ArrayList<List<String[]>>();
        List<String[]> small = new ArrayList<String[]>();
        String line = "";
        String[] feed = new String[2];
        int index = 0;

        InputStream file = getResources().openRawResource(R.raw.sensors);
        BufferedReader read = new BufferedReader(new InputStreamReader(file));
        try {
            while ((line = read.readLine()) != null) {
                small.clear();
                for (int i = 0; i < line.split(":")[2].split(";").length; i++) {
                    feed = line.split(":")[2].split(";")[i].split(",");

                    small.add(feed);
                    Log.d("Good",small.toString());
                }

                output.add( new ArrayList<String[]>() );
                for (int i = 0; i < small.size(); i++) {
                    output.get(index).add(small.get(i));
                }

                index++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("Hello",output.toString());
        return output;
    }

    private String[] getSensors() {
        List<String> output = new ArrayList<String>();
        String line = "";
        String[] feed = new String[3];

        InputStream file = getResources().openRawResource(R.raw.sensors);
        BufferedReader read = new BufferedReader(new InputStreamReader(file));
        try {
            while ((line = read.readLine()) != null) {
                feed = line.split(":");
                //Log.d("Hello",feed[1]);
                output.add(feed[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] the = new String[ output.size() ];
        output.toArray( the );

        return the;
    }

    public static PageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        String[] sensors = getSensors();
        List<List<String[]>> units = getUnits();
        String[] measures = getMeasures();

        if (mPage == 0) {
            view = inflater.inflate(R.layout.content_problem_display, container, false);

            ImageButton questionInfo= (ImageButton) view.findViewById(R.id.prob_info);
            questionInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(R.string.question_info).setTitle("Experimental Question");
                    builder.show();
                }
            });

            ImageButton independentInfo = (ImageButton) view.findViewById(R.id.indep_info);
            independentInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(R.string.independent_info).setTitle("Independent Variable");
                    builder.show();
                }
            });

            ImageButton dependentInfo = (ImageButton) view.findViewById(R.id.dep_info);
            dependentInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(R.string.dependent_info).setTitle("Dependent Variable");
                    builder.show();
                }
            });
        } else if (mPage == 1) {
            view = inflater.inflate(R.layout.content_hypothesis_display, container, false);

            ImageButton questionInfo= (ImageButton) view.findViewById(R.id.rel_info);
            questionInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(R.string.relationship_info).setTitle("Variable Relationship");
                    builder.show();
                }
            });

            ImageButton independentInfo = (ImageButton) view.findViewById(R.id.quant_info);
            independentInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(R.string.correlation_info).setTitle("Mathematic Correlation");
                    builder.show();
                }
            });

            ImageButton dependentInfo = (ImageButton) view.findViewById(R.id.qual_info);
            dependentInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(R.string.causality_info).setTitle("Qualitative Causality");
                    builder.show();
                }
            });
        } else if (mPage == 2) {
            view = inflater.inflate(R.layout.content_materials, container, false);
        } else if (mPage == 3) {
            String[] steps = new String[] {
                    "1. Go outside",
                    "2. Set up Multicorder to record",
                    "3. Start measurements"
            };

            view = inflater.inflate(R.layout.content_sensor_display, container, false);
            LinearLayout mLinearLayoutContainer = (LinearLayout) view.findViewById(R.id.main);

            ListView procedure = (ListView) view.findViewById(R.id.procedure);
            procedure.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, steps));

            ListAdapter listAdapter = procedure.getAdapter();
            if (listAdapter != null) {

                int numberOfItems = listAdapter.getCount();

                // Get total height of all items.
                int totalItemsHeight = 0;
                for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                    View item = listAdapter.getView(itemPos, null, procedure);
                    item.measure(0, 0);
                    totalItemsHeight += item.getMeasuredHeight();
                }

                // Get total height of all item dividers.
                int totalDividersHeight = procedure.getDividerHeight() *
                        (numberOfItems - 1);

                // Set list height.
                ViewGroup.LayoutParams params = procedure.getLayoutParams();
                params.height = totalItemsHeight + totalDividersHeight;
                procedure.setLayoutParams(params);
                procedure.requestLayout();
            }

            inflater = getActivity().getLayoutInflater();
            View v;
            TextView sensor_name;
            TextView unit_name;
            TextView value;

            for (int i = 0; i < measures.length; i++) {
                if (i == 0 || i == 7) {
                    v = inflater.inflate(R.layout.sensor_card_layout, mLinearLayoutContainer, false);
                    mLinearLayoutContainer.addView(v);
                    sensor_name = (TextView) v.findViewById(R.id.sensor_name);
                    sensor_name.setText(measures[i]);
                    unit_name = (TextView) v.findViewById(R.id.unit);
                    unit_name.setText(units.get(i).get(0)[1]);
                    //Log.d("Good",units.get(0).get(0)[0]);
                    value = (TextView) v.findViewById(R.id.measurment);
                    value.setText("0");
                }
            }

            ImageButton questionInfo= (ImageButton) view.findViewById(R.id.trial_info);
            questionInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(R.string.trial_info).setTitle("Experimental Trials");
                    builder.show();
                }
            });

            ImageButton independentInfo = (ImageButton) view.findViewById(R.id.procedure_info);
            independentInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(R.string.procedure_info).setTitle("Experimental Procedure");
                    builder.show();
                }
            });

            ImageButton dependentInfo = (ImageButton) view.findViewById(R.id.measure_info);
            dependentInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(R.string.measurement_info).setTitle("Measurements");
                    builder.show();
                }
            });
        } else if (mPage == 4) {
            view = inflater.inflate(R.layout.content_data, container, false);
            LineChart chart = (LineChart) view.findViewById(R.id.chart);

            int[][] dataObjects = new int[][]{
                    {
                            1,
                            2
                    },
                    {
                            2,
                            3
                    },
                    {
                            4,
                            5
                    }
            };

            List<Entry> entries = new ArrayList<Entry>();

            for (int[] data : dataObjects) {

                // turn your data into Entry objects
                entries.add(new Entry(data[0], data[1]));
            }

            LineDataSet dataSet = new LineDataSet(entries, "Trial 1"); // add entries to dataset
            dataSet.setColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
            dataSet.setValueTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
            dataSet.setCircleRadius(8);
            dataSet.setCircleHoleRadius(3);
            dataSet.setCircleColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
            dataSet.setLineWidth(3);
            dataSet.setHighLightColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
            dataSet.setHighlightLineWidth(2);
            dataSet.setValueTextSize(0);

            LineData lineData = new LineData(dataSet);
            chart.setData(lineData);

            XAxis xaxis = chart.getXAxis();
            xaxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xaxis.setTextSize(14f);
            xaxis.setDrawGridLines(false);
            xaxis.setLabelRotationAngle(30);
            xaxis.setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    DecimalFormat df = new DecimalFormat("#.#");
                    df.setRoundingMode(RoundingMode.HALF_UP);
                    value = Float.valueOf(df.format(value));
                    return ((float) value)+" atm";
                }
            });


            chart.getAxisRight().setDrawLabels(false);
            chart.getAxisRight().setDrawAxisLine(false);

            YAxis yaxis = chart.getAxisLeft();
            yaxis.setTextSize(14f);
            yaxis.setDrawZeroLine(true);
            yaxis.setDrawAxisLine(false);
            yaxis.setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    DecimalFormat df = new DecimalFormat("#.#");
                    df.setRoundingMode(RoundingMode.HALF_UP);
                    value = Float.valueOf(df.format(value));
                    return ((float) value)+" \u00B0C";
                }
            });

            Legend legend = chart.getLegend();
            legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
            legend.setTextSize(14);

            chart.setDescription(null);
            chart.setTouchEnabled(true);
            chart.setPinchZoom(true);

            chart.invalidate(); // refresh
        } else if (mPage == 5) {
            view = inflater.inflate(R.layout.content_conclusion_display, container, false);
        } else {
            view = inflater.inflate(R.layout.content_problem_display, container, false);
        }
        return view;
    }
}
