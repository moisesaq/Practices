package com.apaza.moises.practices.countdowntimer;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.apaza.moises.practices.R;
import com.apaza.moises.practices.pinnedlistview.QueueItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ListCountDownTimer extends Fragment implements OnClickListener, OnItemClickListener{
	
	public static String TAG = "TAG_LIST_COUNT_DOWN_TIMER";
	public static long TIMER_INTERVAL = 1000;
	private View view;
	private EditText etItem, etTime;
	private Button btnAdd;
	private ListView lvListItem;
	
	private CustomAdapter customAdapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		view = inflater.inflate(R.layout.list_count_down_timer, container, false);
		setup();
		return view;
	}
	
	public void setup(){
		etItem = (EditText)view.findViewById(R.id.etItem);
		etTime = (EditText)view.findViewById(R.id.etTime);
		btnAdd = (Button)view.findViewById(R.id.btnAdd);
		btnAdd.setOnClickListener(this);
		lvListItem = (ListView)view.findViewById(R.id.listItem);
		lvListItem.setOnItemClickListener(this);
		customAdapter = new CustomAdapter(getActivity());
		lvListItem.setAdapter(customAdapter);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == btnAdd.getId()){
			QueueItem item = new QueueItem()
			.setAddressFrom(etItem.getText().toString().trim())
			.setTimerCount(Long.parseLong(etTime.getText().toString())*1000);
			
			customAdapter.add(item);
			//customAdapter.notifyDataSetChanged();
		}
	}

	public class CustomAdapter extends ArrayAdapter<QueueItem> {

		private List<ViewHolder> lstHolders;
		private Handler mHandler = new Handler();
		private Runnable updateRemainingTimeRunnable = new Runnable() {
			@Override
			public void run() {
				synchronized (lstHolders) {
					for (ViewHolder holder : lstHolders) {
						holder.updateTimeRemaining();
					}
				}
			}
		};

		public CustomAdapter(Context context){
			super(context, 0);
			//this.counters = new HashMap<TextView, CountDownTimer>();
			lstHolders = new ArrayList<>();
			startUpdateTimer();
		}

		private void startUpdateTimer() {
			Timer tmr = new Timer();
			tmr.schedule(new TimerTask() {
				@Override
				public void run() {
					//Log.d("TEST", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
					mHandler.post(updateRemainingTimeRunnable);
				}
			}, 2000, 1000);
		}
		
		public class ViewHolder{
			TextView tvAddressFrom;
			TextView tvTimerCount;
			QueueItem queueItem;

			public void setData(QueueItem item) {
				queueItem = item;
				tvAddressFrom.setText(item.getAddressFrom());
				//updateTimeRemaining();
			}

			public void updateTimeRemaining() {
				long timer = queueItem.getTimerCount();
				Log.d("TEST", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "+queueItem.getTimerCount());
				if (timer > 0) {
					Log.d("TEST", ">>>> ok "+timer);
					tvTimerCount.setText(String.valueOf(timer));
					queueItem.setTimerCount(timer-1000);
				} else {
					tvTimerCount.setText("Done!");
					queueItem.setTimerCount(0);
				}
			}
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			ViewHolder holder;
			if(view == null){
				holder = new ViewHolder();
				LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = inflater.inflate(R.layout.model_item, null);
				holder.tvAddressFrom = (TextView)view.findViewById(R.id.text);
				holder.tvTimerCount = (TextView)view.findViewById(R.id.timer);
				view.setTag(holder);

				synchronized (lstHolders) {
					lstHolders.add(holder);
				}
			}else{
				holder = (ViewHolder)view.getTag();
			}
			/*QueueItem item = getItem(position);
			holder.text.setText(item.getAddressFrom());
			
			if(item.getTimerCount() > 0){
				TextView textView = (TextView)view.findViewById(R.id.timer);
				new CustomCountDownTimer(item.getTimerCount(), TIMER_INTERVAL, textView, item).start();
			}else{
				holder.timer.setText("DONE!");
			}*/
			//lstHolders.add(holder);
			holder.setData(getItem(position));
			return view;
		}

	}

	public class CustomCountDownTimer extends CountDownTimer{

		private TextView tvTimer;
		private QueueItem queueItem;
		
		public CustomCountDownTimer(long millisInFuture, long countDownInterval, TextView textView, QueueItem queueItem) {
			super(millisInFuture, countDownInterval);
			this.tvTimer = textView;
			this.queueItem = queueItem;
		}

		@Override
		public void onFinish() {
			tvTimer.setText("DONE!");
			queueItem.setTimerCount(0);
		}

		@Override
		public void onTick(long millisUntilFinished) {
			queueItem.setTimerCount(millisUntilFinished);
			tvTimer.setText(String.valueOf(millisUntilFinished/1000));
		}
	}

	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int position, long parent) {
		QueueItem item = (QueueItem)adapter.getAdapter().getItem(position);
		if(item.getTimerCount() != 0){
			Toast.makeText(getActivity(), "TIME OF ITEM IS 0", Toast.LENGTH_SHORT).show();
		}
	}
}
