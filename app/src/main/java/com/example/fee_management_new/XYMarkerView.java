package com.example.fee_management_new;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import java.text.DecimalFormat;

public class XYMarkerView extends MarkerView {
    private TextView tvContent;
    private TextView submit;
    private ValueFormatter xAxisValueFormatter;

    private  DecimalFormat format;


    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *  @param context
     * @param layoutResource the layout resource to use for the MarkerView*/
    public XYMarkerView(Context context, IndexAxisValueFormatter layoutResource) {
        super(context, R.layout.custom_bar_popup);
        this.xAxisValueFormatter = xAxisValueFormatter;
        tvContent = findViewById(R.id.tvContent);
        submit=findViewById(R.id.submit_tv);
        format = new DecimalFormat("###.0");

    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        tvContent.setText(String.format("%s payment requested", format.format(e.getY())));
        submit.setText(String.format("\u20B9 %s",format.format(e.getY())));
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());

    }
}
