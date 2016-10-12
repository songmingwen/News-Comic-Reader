/*
Copyright 2014 David Morrissey

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package com.subscaleview.sample.eventhandling;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.song.sunset.R;

import java.util.Arrays;
import java.util.List;

public class EventHandlingActivity extends Activity implements OnClickListener, OnLongClickListener {

    private static final String BUNDLE_POSITION = "position";

    private int position;

    private List<Note> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_activity);
//        getActionBar().setTitle("Event handling");
//        getActionBar().setDisplayHomeAsUpEnabled(true);
        findViewById(R.id.next).setOnClickListener(this);
        findViewById(R.id.previous).setOnClickListener(this);
        findViewById(R.id.imageView).setOnClickListener(this);
        findViewById(R.id.imageView).setOnLongClickListener(this);
        if (savedInstanceState != null && savedInstanceState.containsKey(BUNDLE_POSITION)) {
            position = savedInstanceState.getInt(BUNDLE_POSITION);
        }
        notes = Arrays.asList(
                new Note("Simple events", "Touch handling by the image view doesn't prevent normal events from working."),
                new Note("OnClickListener", "This view has an OnClickListener. Tap once to activate the click."),
                new Note("OnLongClickListener", "This view has an OnLongClickListener. Press and hold to activate it.")
        );

        initialiseImage();
        updateNotes();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(BUNDLE_POSITION, position);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.next) {
            position++;
            updateNotes();
        } else if (view.getId() == R.id.previous) {
            position--;
            updateNotes();
        } else if (view.getId() == R.id.imageView) {
            Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if (view.getId() == R.id.imageView) {
            Toast.makeText(this, "Long clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    private void initialiseImage() {
        SubsamplingScaleImageView imageView = (SubsamplingScaleImageView)findViewById(R.id.imageView);
        imageView.setImage(ImageSource.asset("squirrel.jpg"));
    }

    private void updateNotes() {
        if (position > notes.size() - 1) {
            return;
        }
//        getActionBar().setSubtitle(notes.get(position).subtitle);
        ((TextView)findViewById(R.id.note)).setText(notes.get(position).text);
        findViewById(R.id.next).setVisibility(position >= notes.size() - 1 ? View.INVISIBLE : View.VISIBLE);
        findViewById(R.id.previous).setVisibility(position <= 0 ? View.INVISIBLE : View.VISIBLE);
    }

    private static final class Note {
        private final String text;
        private final String subtitle;
        private Note(String subtitle, String text) {
            this.subtitle = subtitle;
            this.text = text;
        }
    }

}
