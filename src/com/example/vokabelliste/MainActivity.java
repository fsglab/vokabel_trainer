package com.example.vokabelliste;

import java.util.ArrayList;
import java.util.List;
import java.util.TooManyListenersException;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.app.NotificationCompat.Action;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends Activity {

	private Button Del, Upd;
	private ListView listView;
	private Button Add;
	private TextView text;

	int Position;

	MySQLiteOpenHelper db;

	Context ctx;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		db = new MySQLiteOpenHelper(this);

		ctx = this;

		setPropeties();
		Position = 0;

	}

	public void putInListView() {

		try {

			List<Vokabel> list = db.getAllVokabeln();

			List valueList = new ArrayList<String>();

			valueList = list;

			ListAdapter adapter = new ArrayAdapter<String>(
					getApplicationContext(),
					android.R.layout.simple_list_item_1, valueList);

			listView.setAdapter(adapter);
		} catch (Exception e) {
		}

		setTextText();

	}

	

	public void setTextText() {
		try {
			List<Vokabel> list = db.getAllVokabeln();
			text.setText(list.get(Position).getDeutsch() + " - "
					+ list.get(Position).getEnglisch());
		} catch (Exception e) {
			text.setText("Zum Auswählen auf Vokabel tippen");
		}

	}

	public void setPropeties() {

		listView = (ListView) findViewById(R.id.listView1);
		listView.setBackgroundColor(Color.LTGRAY);

		Add = (Button) findViewById(R.id.button1);

		Add.setText("Hinzufügen");
		Add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				addVokabelToList();

			}
		});

		Del = (Button) findViewById(R.id.button2);
		Del.setText("Löschen");

		Del.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Del.setText("Löschen");

				delWordFromList(Position);

				setTextText();
			}
		});

		Upd = (Button) findViewById(R.id.button3);
		Upd.setText("Ändern");

		Upd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Upd.setText("Ändern");

				ch(Position);
				setTextText();
				
				putInListView();

			}
		});

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				try {
					List<Vokabel> list = db.getAllVokabeln();

					Position = position;
					String Text = list.get(Position).getDeutsch() + " - "
							+ list.get(Position).getEnglisch();

					text.setText(Text);
				} catch (Exception e) {
				}
			}
		});

		text = (TextView) findViewById(R.id.textView1);
		setTextText();

		putInListView();

	}

	public void ch(final int p) {

		try {
			List<Vokabel> list = db.getAllVokabeln();

			final Dialog d = new Dialog(ctx);
			d.setCancelable(true);
			d.setContentView(R.layout.dialog_add);

			final EditText de = (EditText) d.findViewById(R.id.editText1);
			final EditText en = (EditText) d.findViewById(R.id.editText2);

			de.setText(list.get(p).getDeutsch());
			en.setText(list.get(p).getEnglisch());

			Button ok = (Button) d.findViewById(R.id.button1);
			Button can = (Button) d.findViewById(R.id.button2);

			can.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					d.dismiss();

				}
			});

			ok.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					changeWordFromList(p, de.getText().toString(), en.getText()
							.toString());

					d.dismiss();
					putInListView();

				}
			});

			d.show();
		} catch (Exception e) {
		}
	}

	public void changeWordFromList(int position, String deutsch, String englisch) {

		try {

			List<Vokabel> list = db.getAllVokabeln();

			db.updateVokabel(new Vokabel(deutsch, englisch), list.get(position)
					.getId());

		} catch (Exception e) {
		}
		putInListView();

	}

	public void delWordFromList(int position) {
		try {
			List<Vokabel> list = db.getAllVokabeln();
			db.deleteVokabel(list.get(position));

			putInListView();
		} catch (Exception e) {
		}

	}

	public void addVokabelToList() {
		final Dialog dialog = new Dialog(ctx);
		dialog.setCancelable(false);

		dialog.setContentView(R.layout.dialog_add);

		final EditText de = (EditText) dialog.findViewById(R.id.editText1);
		final EditText en = (EditText) dialog.findViewById(R.id.editText2);

		Button ok = (Button) dialog.findViewById(R.id.button1);
		Button can = (Button) dialog.findViewById(R.id.button2);

		can.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();

			}
		});

		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				db.addVokabel(new Vokabel(de.getText().toString(), en.getText()
						.toString()));

				dialog.dismiss();
				putInListView();

			}
		});

		dialog.show();

		setTextText();
		putInListView();

	}

}
