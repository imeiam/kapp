package in.org.kurukshetra.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;

import com.fourmob.panningview.library.PanningView;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class XceedActivity extends AppCompatActivity {
    private PieChart pieChart, eventList;
    private ScrollView scrollView;
    private Map<String, String> eventKeys = new HashMap<>();
    ArrayList<Entry> entries = new ArrayList<>();
    ArrayList<String> categories = new ArrayList<>();
    ArrayList<String> eventlist = new ArrayList<>();

    String[] cats = {"Ahmedabad", "Bangalore"};
    String[] ahmedabad = {"Chaos Theory","Biz Quiz"};
    String[] bangalore = {"Chaos Theory","How Stuff Works"};
    String[] hydrabad = {};
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        initKeys();
        setContentView(R.layout.activity_xceed);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

      /*  Toolbar toolbar = (Toolbar) findViewById(R.id.event_toolbar);
        setSupportActionBar(toolbar);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        final Drawable upArrow = getResources().getDrawable(R.drawable.back_icon);
        //upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        actionBar.setHomeAsUpIndicator(upArrow);
        actionBar.setDisplayHomeAsUpEnabled(true);
        */

        setTitle("Xceed");
        for (int i = 0; i < 2; i++) {
            entries.add(new Entry(1, i));
            categories.add(cats[i]);
        }

        //setting the layout height

        //moving background
        PanningView panningView = (PanningView) findViewById(R.id.panningView);
        panningView.setImageResource(R.drawable.blue);
        panningView.startPanning();


        //setting categories
        pieChart = (PieChart) findViewById(R.id.platinum);
        pieChart.setCenterText("Xceed");
        pieChart.setDescription(null);
        pieChart.animateY(1500, Easing.EasingOption.EaseInOutQuad);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        PieDataSet dataset1 = new PieDataSet(entries, "Xceed");
        dataset1.setColors(ColorTemplate.VORDIPLOM_COLORS);
        dataset1.setDrawValues(false);
        dataset1.setValueTextColor(R.color.white);
        PieData pieData1 = new PieData(categories, dataset1);
        pieData1.setValueTextColor(R.color.white);
        pieChart.setDescriptionTextSize(100);
        pieChart.setDescriptionColor(R.color.white);
        pieChart.setData(pieData1);
        pieChart.setCenterTextColor(R.color.white);
        pieChart.setHoleRadius(40);
        pieChart.setTransparentCircleRadius(50);
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                final Entry e1 = e;
                showEvents(e1.getXIndex());
                View view = findViewById(android.R.id.content);
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollDown();
                    }
                });

            }

            @Override
            public void onNothingSelected() {
                eventList.setVisibility(View.GONE);
            }
        });


        //events list
        eventList = (PieChart) findViewById(R.id.list);
        eventList.setVisibility(View.GONE);

    }

    private void initKeys() {

        eventKeys.put("Chaos Theory Ahmedabad","chaos-theory");
        eventKeys.put("Biz Quiz Ahmedabad","biz-quiz");
        eventKeys.put("How Stuff Works Bangalore","how-stuff-works");
        eventKeys.put("Chaos Theory Bangalore","chaos-theory-edb72509-27cc-46a7-b84b-744cec0c24e4");
    }

    //showing events
    public void scrollDown() {
        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
    }

    public void showEvents(final int index) {
        String cat = categories.get(index);
        entries = new ArrayList<>();
        eventlist = new ArrayList<>();
        String [] events;
        if(cat.equals("Ahmedabad")){
         events = ahmedabad;
        }
        else if(cat.equals("Bangalore")){
            events = bangalore;
        }
        else{
            events = hydrabad;
        }

        for (int i = 0; i < events.length; i++) {
            entries.add(new Entry(1, i));
            eventlist.add(events[i]);
        }
        eventList.setVisibility(View.VISIBLE);
        eventList.clear();
        eventList.setCenterText(categories.get(index));
        eventList.setDescription(null);
        eventList.animateY(1500, Easing.EasingOption.EaseInOutQuad);
        PieDataSet dataset2 = new PieDataSet(entries, "Events");
        dataset2.setColors(ColorTemplate.JOYFUL_COLORS);
        dataset2.setDrawValues(false);
        dataset2.setValueTextColor(R.color.white);
        PieData pieData2 = new PieData(eventlist, dataset2);
        pieData2.setValueTextColor(R.color.white);
        eventList.setDescriptionTextSize(100);
        eventList.setDescriptionColor(R.color.white);
        eventList.setData(pieData2);
        eventList.setCenterTextColor(R.color.white);
        eventList.setHoleRadius(40);
        eventList.setTransparentCircleRadius(50);
        eventList.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                Intent intent = new Intent(XceedActivity.this, XceedDetails.class);
                intent.putExtra("name",eventlist.get(e.getXIndex()));
                intent.putExtra("key",eventKeys.get(eventlist.get(e.getXIndex())+" "+eventList.getCenterText()));
                startActivity(intent);
            }

            @Override
            public void onNothingSelected() {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

}