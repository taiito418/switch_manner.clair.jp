package switch_manner.clair.jp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RemoteViews;
import android.widget.TextView;

import jp.co.imobile.sdkads.android.ImobileSdkAd;

public class MainActivity extends Activity {

    private AudioManager am;
    private Button btn_normalMode;
    private Button btn_silentMode;
    private Button btn_vibrateMode;
    private TextView tv_ringerMode;
    static final String IMOBILE_BANNER_PID = "49769";
    static final String IMOBILE_BANNER_MID = "400840";
    static final String IMOBILE_BANNER_SID = "1360430";
    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NotificationManager notificationManager =
                (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && !notificationManager.isNotificationPolicyAccessGranted()) {

/*
			new AlertDialog.Builder(this)
					.setTitle("ご確認ください")
					.setMessage(getResources().getString(R.string.app_name) + "のすべての機能を利用する為はに「おやすみモード」へのアクセス権が必要です")
					.setPositiveButton("許可します", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// OK button pressed
							Intent intent = new Intent(
									android.provider.Settings
											.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);

							startActivityForResult(intent, REQUEST_CODE);
						}
					})
					.setNegativeButton("終了",  new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							finish();
						}
					})
					.show();

 */

        }

/*
		// スポット情報を設定します
		ImobileSdkAd.registerSpotInline(this, IMOBILE_BANNER_PID, IMOBILE_BANNER_MID, IMOBILE_BANNER_SID);
		// 広告の取得を開始します
		ImobileSdkAd.start(IMOBILE_BANNER_SID);

		// 広告を表示するViewを作成します
		FrameLayout imobileAdLayout = new FrameLayout(this);
		FrameLayout.LayoutParams imobileAdLayoutParam = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		// 広告の表示位置を指定
		imobileAdLayoutParam.gravity = (Gravity.BOTTOM | Gravity.CENTER);
		//広告を表示するLayoutをActivityに追加します
		addContentView(imobileAdLayout, imobileAdLayoutParam);
		// 広告を表示します
		ImobileSdkAd.showAd(this, IMOBILE_BANNER_SID, imobileAdLayout);
*/

        // AudioManager取得
        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

		/*
		// サイレントモードに設定
		am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
		*/

        int ringerMode = am.getRingerMode();

        // マナーモード表示用TextView
        tv_ringerMode = (TextView) findViewById(R.id.ringerMode);

        // 現在のマナーモードを表示
        showRingerMode();

        // ノーマルモード設定用ボタン
        btn_normalMode = (Button) findViewById(R.id.normalMode);
        btn_normalMode.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // setRingerModeメソッドでノーマルモードに設定
                am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);

                RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(), R.layout.widget_layout);
                remoteViews.setImageViewResource(R.id.button, R.drawable.normal);
                ComponentName myWidget = new ComponentName(getApplicationContext(), MyWidgetProvider.class);
                AppWidgetManager manager = AppWidgetManager.getInstance(getApplicationContext());
                manager.updateAppWidget(myWidget, remoteViews);
                // 現在のマナーモードを表示
                showRingerMode();

            }
        });

        // サイレントモード設定用ボタン
        btn_silentMode = (Button) findViewById(R.id.silentMode);
        btn_silentMode.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // setRingerModeメソッドでサイレントモードに設定
                am.setRingerMode(AudioManager.RINGER_MODE_SILENT);

                RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(), R.layout.widget_layout);
                remoteViews.setImageViewResource(R.id.button, R.drawable.silent);
                ComponentName myWidget = new ComponentName(getApplicationContext(), MyWidgetProvider.class);
                AppWidgetManager manager = AppWidgetManager.getInstance(getApplicationContext());
                manager.updateAppWidget(myWidget, remoteViews);

                // 現在のマナーモードを表示
                showRingerMode();
            }
        });

        // バイブレートモード設定用ボタン
        btn_vibrateMode = (Button) findViewById(R.id.vibrateMode);
        btn_vibrateMode.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // setRingerModeメソッドでバイブレートモードに設定
                am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(), R.layout.widget_layout);
                remoteViews.setImageViewResource(R.id.button, R.drawable.vibe);
                ComponentName myWidget = new ComponentName(getApplicationContext(), MyWidgetProvider.class);
                AppWidgetManager manager = AppWidgetManager.getInstance(getApplicationContext());
                manager.updateAppWidget(myWidget, remoteViews);

                // 現在のマナーモードを表示
                showRingerMode();
            }
        });
    }

    // RingerMode表示メソッド
    void showRingerMode() {
        // getRingerModeメソッドで現在のマナーモードを取得
        switch (am.getRingerMode()) {
            // ノーマルモードの場合
            case AudioManager.RINGER_MODE_NORMAL:
                tv_ringerMode.setText("現在のモード：ノーマル");
                break;
            // サイレントモードの場合
            case AudioManager.RINGER_MODE_SILENT:
                tv_ringerMode.setText("現在のモード：サイレント");
                break;
            // バイブレートモードの場合
            case AudioManager.RINGER_MODE_VIBRATE:
                tv_ringerMode.setText("現在のモード：バイブレーション");
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        NotificationManager notificationManager =
                (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && !notificationManager.isNotificationPolicyAccessGranted()) {


			new AlertDialog.Builder(this)
					.setTitle("ご確認ください")
					.setMessage(getResources().getString(R.string.app_name) + "のすべての機能を利用する為はに「おやすみモード」へのアクセス権が必要です")
					.setPositiveButton("許可します", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// OK button pressed
							Intent intent = new Intent(
									android.provider.Settings
											.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);

							startActivityForResult(intent, REQUEST_CODE);
						}
					})
					.setNegativeButton("終了",  new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							finish();
						}
					})
					.show();



        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            //SecondActivityから戻ってきた場合
            case (REQUEST_CODE):

                NotificationManager notificationManager =
                        (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                        && !notificationManager.isNotificationPolicyAccessGranted()) {
                    new AlertDialog.Builder(this)
                            .setTitle("制限の解除に失敗しました")
                            .setMessage("アプリを再起動して再度解除設定をお願いいたします。")
                            .setPositiveButton("終了", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            })
                            .show();
                }

                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        showRingerMode();

    }

    protected void onDestroy() {
        super.onDestroy();
    }

}
