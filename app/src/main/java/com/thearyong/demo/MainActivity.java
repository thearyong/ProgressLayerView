package com.thearyong.demo;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import com.thearyong.plv.ProgressLayerView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ProgressLayerView pv = (ProgressLayerView) findViewById(R.id.pv);
        final SeekBar sb = (SeekBar) findViewById(R.id.sb);
        final RadioGroup rg = (RadioGroup) findViewById(R.id.rg);
        final SwitchCompat switch_reverse = (SwitchCompat) findViewById(R.id.switch_reverse);
        final SwitchCompat switch_progress_text = (SwitchCompat) findViewById(R.id.switch_progress_text);
        final SwitchCompat switch_upload_err = (SwitchCompat) findViewById(R.id.switch_upload_err);


        pv.setDirect(ProgressLayerView.DIRECT.UP).setProgress(50);

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                pv.setProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.rb_up) {
                    pv.setDirect(ProgressLayerView.DIRECT.UP);
                } else if (checkedId == R.id.rb_down) {
                    pv.setDirect(ProgressLayerView.DIRECT.DOWN);
                } else if (checkedId == R.id.rb_left) {
                    pv.setDirect(ProgressLayerView.DIRECT.LEFT);
                } else if (checkedId == R.id.rb_right) {
                    pv.setDirect(ProgressLayerView.DIRECT.RIGHT);
                }
            }
        });
        switch_reverse.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                pv.setReverse(isChecked);
            }
        });
        switch_progress_text.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                pv.setHasText(isChecked);
            }
        });
        switch_upload_err.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    pv.setErrorTips("上传失败！");
                else pv.reset();
            }
        });
    }

}
