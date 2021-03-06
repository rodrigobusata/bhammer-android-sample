package com.busata.bhammer_sample.activities.snackbar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.busata.bhammer.activities.BAppCompatActivity;
import com.busata.bhammer.views.snackbar.Snackbar;
import com.busata.bhammer.views.snackbar.SnackbarManager;
import com.busata.bhammer_sample.R;

import java.util.ArrayList;
import java.util.List;

import static android.widget.AdapterView.OnItemClickListener;

/**
 * Reference https://github.com/nispok/snackbar
 */
public class SnackbarShowInDialogSampleActivity extends BAppCompatActivity {

    private static final String FRAGMENT_TAG_MY_DIALOG = "MyDialog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_in_dialog_sample);

        setToolBar(getString(R.string.show_in_dialog));

        final Button button = (Button) findViewById(android.R.id.button1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }

    private void openDialog() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(new MyDialogFragment(), FRAGMENT_TAG_MY_DIALOG)
                .commit();
    }

    public static class MyDialogFragment extends DialogFragment implements OnItemClickListener {
        private ViewGroup mSnackbarContainer;
        private ListView mListView;

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Context context = getActivity();
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            View view = LayoutInflater.from(context).inflate(R.layout.fragment_dialog_list, null, false);

            mListView = (ListView) view.findViewById(android.R.id.list);
            mSnackbarContainer = (ViewGroup) view.findViewById(R.id.snackbar_container);

            List<String> data = new ArrayList<String>();

            for (int i = 0; i < 25; i++) {
                data.add(String.format("Item %d", (i + 1)));
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    context, android.R.layout.simple_list_item_1, data);

            mListView.setAdapter(adapter);
            mListView.setOnItemClickListener(this);

            builder.setView(view);
            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });

            return builder.create();
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            super.onDismiss(dialog);

            mListView.setOnItemClickListener(null);
            mListView = null;
            mSnackbarContainer = null;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            SnackbarManager.show(
                    Snackbar.with(getActivity())
                            .text(String.format("Item %d pressed", (position + 1)))
                            .actionLabel("Close")
                            .actionColor(Color.parseColor("#FF8A80"))
                            .duration(Snackbar.SnackbarDuration.LENGTH_LONG)
                            .attachToAbsListView(mListView),
                    mSnackbarContainer, true);

        }
    }
}