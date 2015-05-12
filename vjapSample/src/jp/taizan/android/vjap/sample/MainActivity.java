package jp.taizan.android.vjap.sample;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import jp.taizan.android.vjap.sample.R;
import jp.taizan.android.vjap.VTextLayout;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
	VTextLayout vTextLayout;
	private static final int BUFFER_SIZE = 256 * 1024;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		String text = "";
		// Assetからテキストを読み込む
		try {
			BufferedInputStream bis = new BufferedInputStream(getAssets().open("SAMPLE.txt"),	
					BUFFER_SIZE);
			ByteArrayOutputStream baos = new ByteArrayOutputStream(BUFFER_SIZE);
			byte[] buffer = new byte[BUFFER_SIZE];
			int length;
			while ((length = bis.read(buffer)) != -1) {
				baos.write(buffer, 0, length);
			}
			text = baos.toString();
		} catch (IOException e) {}
		
		
		vTextLayout = (VTextLayout) findViewById(R.id.vTextLayout);
		vTextLayout.initContent("たいとる",text);

		vTextLayout.setFont( VTextLayout.Font.IPA);
		//vTextLayout.setScrollDisabled(true);

	}

}
