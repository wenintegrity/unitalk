package com.unitalk.utils.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.unitalk.R;

public class StepsView extends LinearLayout {
    private ImageView[] ivSteps;

    public StepsView(final Context context) {
        this(context, null);
    }

    public StepsView(final Context context, final AttributeSet attrs) {
         super(context, attrs);
         View.inflate(context, R.layout.bottom_steps, this);
         init();
     }

    private void init() {
        ivSteps = new ImageView[3];
        ivSteps[0] = findViewById(R.id.ivStep1);
        ivSteps[1] = findViewById(R.id.ivStep2);
        ivSteps[2] = findViewById(R.id.ivStep3);
    }

    public void setStepState(final int step, final boolean state) {
        ivSteps[step].setSelected(state);
    }
 }