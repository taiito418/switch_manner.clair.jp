package switch_manner.clair.jp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Created by itou on 2017/03/14.
 */

public class MyWidgetProvider extends AppWidgetProvider {

	public static int clickCount = 0;
	public static int ringerMode;
//	public static AudioManager am;
	public static RemoteViews remoteViews;

	public void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetIds) {

		// ウィジェットレイアウトの初期化
		remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

		//val (intent, code) = createIntentAndRequestCode(context, appWidgetId, ClickType.TYPE1);

		// ボタンイベントを登録
		//remoteViews.setOnClickPendingIntent(R.id.button, clickButton(context));
		Intent intent = new Intent(context, MyWidgetProvider.class);

		remoteViews.setOnClickPendingIntent(R.id.button, PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_IMMUTABLE));

        AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		//MyWidgetIntentReceiver.ringerMode = am.getRingerMode();


		/* 現在のステータスに合わせて画像を変更 */

		if (am.getRingerMode() == 0) {
			remoteViews.setImageViewResource(R.id.button, R.drawable.silent);
		} else if (am.getRingerMode() == 1) {
			remoteViews.setImageViewResource(R.id.button, R.drawable.vibe);
		} else {
			remoteViews.setImageViewResource(R.id.button, R.drawable.normal);
		}
		//Toast.makeText(context, "change", Toast.LENGTH_SHORT).show();

		// テキストフィールドに"初期画面"と表示
		//remoteViews.setTextViewText(R.id.title, "初期画面");

		appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
	}

	public static PendingIntent clickButton(Context context) {
		// クリック回数を増加

		clickCount ++;

		if (ringerMode == 2) {
			ringerMode = 0;
		} else {
			ringerMode++;
		}

		// initiate widget_layout update request
		//Intent intent = new Intent();
		Intent intent = new Intent("click_action");

		Log.i("clairvoyance", "clickButton");
//		Toast.makeText(context, "change", Toast.LENGTH_SHORT).show();

		//intent.setAction("UPDATE_WIDGET");
		return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

//		return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
	}

	// アップデート
	/*
	public static void pushWidgetUpdate(Context context, RemoteViews remoteViews) {
		ComponentName myWidget = new ComponentName(context, MyWidgetProvider.class);
		AppWidgetManager manager = AppWidgetManager.getInstance(context);
		manager.updateAppWidget(myWidget, remoteViews);
	}*/


	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		// There may be multiple widgets active, so update all of them
        //Log.i("clairvoyance", "update");
		//Toast.makeText(context, "update", Toast.LENGTH_SHORT).show();

        for (int appWidgetId : appWidgetIds) {
			updateAppWidget(context, appWidgetManager, appWidgetId);
		}
	}

	@Override
	public void onEnabled(Context context) {
		// Enter relevant functionality for when the first widget is created
	}

	@Override
	public void onDisabled(Context context) {
		// Enter relevant functionality for when the last widget is disabled
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
		// 画面に配置した時も必ず呼ばれる
		//Toast.makeText(context, "click", Toast.LENGTH_SHORT).show();

		//if (intent.getAction().equals("click_action")) {
			// Manifestファイルので指定したnameが取得できる
			//Toast.makeText(context, "click", Toast.LENGTH_SHORT).show();
			// Tapされた時の実行アクションをかく

			remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
			AppWidgetManager manager = AppWidgetManager.getInstance(context);

			int appWidgetId = intent.getIntExtra(
					manager.EXTRA_APPWIDGET_ID, -1);

            AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

            if (am.getRingerMode() == AudioManager.RINGER_MODE_NORMAL) {
				// ノーマルならバイブ
				//Toast.makeText(context, "normal-vibe", Toast.LENGTH_SHORT).show();
				am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
				remoteViews.setImageViewResource(R.id.button, R.drawable.vibe);
			} else if (am.getRingerMode() == AudioManager.RINGER_MODE_VIBRATE) {
				// バイブならサイレント
				//Toast.makeText(context, "vibe-silent", Toast.LENGTH_SHORT).show();
				am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
				remoteViews.setImageViewResource(R.id.button, R.drawable.silent);
			} else {
				// サイレントならノーマル
				//Toast.makeText(context, "silent-normal", Toast.LENGTH_SHORT).show();
				am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
				remoteViews.setImageViewResource(R.id.button, R.drawable.normal);
			}

			//AppWidgetManager manager = AppWidgetManager.getInstance(getApplicationContext());
			ComponentName myWidget = new ComponentName(context, MyWidgetProvider.class);
			manager.updateAppWidget(myWidget, remoteViews);

//			ComponentName myWidget = new ComponentName(context, MyWidgetProvider.class);
//			manager.updateAppWidget(appWidgetId, remoteViews);
		//}
	}
}