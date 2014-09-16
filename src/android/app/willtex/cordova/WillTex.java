package app.willtex.cordova;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.Intent;
import android.graphics.Picture;
import android.os.Environment;

public class WillTex extends CordovaPlugin {

	private static final String WILLTEX_ALBUM = "WillTex";

	@Override
	public boolean execute(String action, final JSONArray args,
			final CallbackContext callbackContext) throws JSONException {
		if (action.equals("sendData")) {
			// http://developer.android.com/training/sharing/send.html
			String text = args.get(0).toString();
			Intent sendIntent = new Intent();
			sendIntent.setAction(Intent.ACTION_SEND);
			sendIntent.putExtra(Intent.EXTRA_TEXT, text);
			sendIntent.setType("text/plain");
			cordova.getActivity().startActivity(sendIntent);
			callbackContext.success("");
			return true;
		} else if (action.equals("saveImage")) {
			// http://developer.android.com/training/basics/data-storage/files.html
			cordova.getThreadPool().execute(new Runnable() {
				public void run() {
					File pictureDir = Environment
							.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
					File file = new File(pictureDir, WILLTEX_ALBUM);
					if (!file.exists() && !file.mkdirs()) {
						callbackContext.error("Album directory not created");
						return;
					}
					try {
						String filename = args.get(0).toString();
						OutputStream out = new FileOutputStream(new File(file,
								filename + ".png"));
						Picture picture = webView.capturePicture();
						picture.writeToStream(out);
						callbackContext.success("");
					} catch (IOException e) {
						callbackContext.error("IO error: " + e.getMessage());
					} catch (JSONException e) {
						callbackContext.error("JSON error: " + e.getMessage());
					}
				}
			});
			return true;
		} else {
			callbackContext.error("Action error: " + action);
			return false;
		}
	}
}