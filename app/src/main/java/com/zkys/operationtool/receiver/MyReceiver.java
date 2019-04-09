package com.zkys.operationtool.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.zkys.operationtool.canstant.SharedConstant;
import com.zkys.operationtool.ui.activity.CheckOrderActivity;
import com.zkys.operationtool.ui.activity.HomeActivity;
import com.zkys.operationtool.util.SystemUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
	private static final String TAG = "JIGUANG-Example";

	@Override
	public void onReceive(Context context, Intent intent) {
		try {
			Bundle bundle = intent.getExtras();
			Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

			if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
				String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
				Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
				//send the Registration Id to your server...

			} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
				Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
//				processCustomMessage(context, bundle);

			} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
				Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
				int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
				Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

			} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
				Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
				String msgType = "";
				try {
					String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
					JSONObject extrasJson = new JSONObject(extras);
					msgType = extrasJson.optString("msgType");
				} catch (JSONException e) {
					Log.d(this.getClass().getSimpleName(), "Unexpected: extras is not a valid json");
					e.printStackTrace();
				}
				Log.d("msgType", msgType);
				if (msgType.equals("1")) {
					//打开自定义的Activity
					/*Intent i = new Intent(context, CheckOrderActivity.class);
					i.putExtras(bundle);
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
					context.startActivity(i);*/
					goToActivity(context, bundle);
				}

			} else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
				Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
				//在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

			} else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
				boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
				Log.w(TAG, "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
			} else {
				Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
			}
		} catch (Exception e){

		}

	}

	// 打印所有的 intent extra 数据
	private static String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			}else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
				sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
			} else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
				if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
					Log.i(TAG, "This message has no Extra data");
					continue;
				}

				try {
					JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
					Iterator<String> it =  json.keys();

					while (it.hasNext()) {
						String myKey = it.next();
						sb.append("\nkey:" + key + ", value: [" +
								myKey + " - " +json.optString(myKey) + "]");
					}
				} catch (JSONException e) {
					Log.e(TAG, "Get message extra JSON error!");
				}

			} else {
				sb.append("\nkey:" + key + ", value:" + bundle.get(key));
			}
		}
		return sb.toString();
	}
	
	//send msg to MainActivity
	private void processCustomMessage(Context context, Bundle bundle) {
		/*if (MainActivity.isForeground) {
			String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
			String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
			Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
			msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
			if (!ExampleUtil.isEmpty(extras)) {
				try {
					JSONObject extraJson = new JSONObject(extras);
					if (extraJson.length() > 0) {
						msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
					}
				} catch (JSONException e) {

				}

			}
			LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent);
		}*/
	}

	void goToActivity(Context context, Bundle bundle) {
		//判断app进程是否存活
		if (SystemUtil.isAppRunning(context, context.getPackageName()) ||
				SystemUtil.isProcessRunning(context, SystemUtil.getPackageUid(context, context.getPackageName()))) {
			/**
			 *  //如果存活的话，就直接启动DetailActivity，但要考虑一种情况，就是app的进程虽然仍然在
			 //但Task栈已经空了，比如用户点击Back键退出应用，但进程还没有被系统回收，如果直接启动
			 //DetailActivity,再按Back键就不会返回MainActivity了。所以在启动
			 //DetailActivity前，要先启动MainActivity。
			 */
			Intent mainIntent = new Intent(context, HomeActivity.class);
			//将MainActivity的launchMode设置成SingleTask, 或者在下面flag中加上Intent.FLAG_CLEAR_TOP,
			//如果Task栈中有MainActivity的实例，就会把它移到栈顶，把在它之上的Activity都清理出栈，
			//如果Task栈不存在MainActivity实例，则在栈顶创建
			mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			Intent checkOrderIntent = new Intent(context, CheckOrderActivity.class);
			checkOrderIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
			checkOrderIntent.putExtra(SharedConstant.EXTRA_BUNDLE,bundle);
			Intent[] intents = {mainIntent, checkOrderIntent};
			context.startActivities(intents);
		} else {
			/**
			 * //如果app进程已经被杀死，先重新启动app，将DetailActivity的启动参数传入Intent中，参数经过
			 //SplashActivity传入MainActivity，此时app的初始化已经完成，在MainActivity中就可以根据传入
			 // 参数跳转到DetailActivity中去了
			 */
			Intent launchIntent = context.getPackageManager().
					getLaunchIntentForPackage(context.getPackageName());
			launchIntent.setFlags(
					Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
			launchIntent.putExtra(SharedConstant.EXTRA_BUNDLE, bundle);
			context.startActivity(launchIntent);

		}
	}
}
