package com.benparksfordesigner.initiativepicker;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Initiative_Activity extends Activity {
	ArrayList<Character> characterNames = new ArrayList<Character>();
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    public void classicInitiative(View view) {
    	startActivity(new Intent(this, Classic_Initiative_Activity.class));
    }
}
