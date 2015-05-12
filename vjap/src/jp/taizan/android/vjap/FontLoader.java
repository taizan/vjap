package jp.taizan.android.vjap;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class FontLoader extends Handler{
	
	public ProgressDialog progressDialog;
	public AsyncFileDownload asyncfiledownload;
	
	static String FONT_URL = "https://github.com/taizan/ipa_otf_font/raw/master/ipam.otf.zip";
	static String FONT_NAME = "ipam.otf";
	
	//static String FONT_URL = "http://ipafont.ipa.go.jp/ipafont/ipamp00303.php";
	//static String FONT_NAME = "ipamp.ttf";
	static String FONT_FOLDER = "FontFolder";

	Runnable onDownloadEndListener;
	
	public File getFontFile(){
		File sdCard = Environment.getExternalStorageDirectory();
		return new File(sdCard.getAbsolutePath() + "/"+FONT_FOLDER+"/"+FONT_NAME);
	}
	
	public void setOnDownloadEndListener(Runnable onDownloadEndListener){
		this.onDownloadEndListener = onDownloadEndListener;
	}
	
	public void loadFont(Context context ) {
		File sdCard = Environment.getExternalStorageDirectory();
		File directory = new File(sdCard.getAbsolutePath() + "/"+ FONT_FOLDER);
		if(directory.exists() == false){
			if (directory.mkdir() == true){
			}else{
				Toast ts = Toast.makeText( context , "SDにアクセスできません", Toast.LENGTH_LONG);
				ts.show();
				return;
			}
		}
		File outputFile = new File(directory, "temp.zip");
		
	
		//ファイルロードタスク。
		//ダウンロードが終ったら解凍する。（zipで圧縮されている前提）
		asyncfiledownload = new AsyncFileDownload( context , FONT_URL, outputFile){
			@Override
			protected void onPostExecute(Boolean result){ 
				FontLoader.extract( this.outputFile );
				
				onDownloadEndListener.run();
			}
		};
		
		
		
		progressDialog = new ProgressDialog( context );
		progressDialog.setTitle("Downloading font...");
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Hide", 
				new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});

		progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", 
				new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(asyncfiledownload != null) asyncfiledownload.cancel(true);
			}
		});
		
		progressDialog.setProgress(0); 
		this.sendEmptyMessage(0);
		
		progressDialog.show();
		
		asyncfiledownload.execute();
	}
	
	@Override
	public void handleMessage(Message msg){
		super.handleMessage(msg);
		if(asyncfiledownload.isCancelled()){
			progressDialog.dismiss();
		}
		else if(asyncfiledownload.getStatus() == AsyncTask.Status.FINISHED){
			progressDialog.dismiss();
		}else{
			progressDialog.setProgress(asyncfiledownload.getLoadedBytePercent());
			this.sendEmptyMessageDelayed(0, 100);
		}
	}

	public class AsyncFileDownload extends AsyncTask<String, Void, Boolean>{
		private final String TAG = "AsyncFileDownload";
		private final int TIMEOUT_READ = 5000; 
		private final int TIMEOUT_CONNECT = 30000; 

		public Context owner;
		private final int BUFFER_SIZE = 1024;

		private String urlString;
		protected File outputFile;
		private FileOutputStream fileOutputStream;
		private InputStream inputStream;
		private BufferedInputStream bufferedInputStream;

		private int totalByte = 0; 
		private int currentByte = 0;

		private byte[] buffer = new byte[BUFFER_SIZE];

		private URL url;
		private URLConnection urlConnection;

		public AsyncFileDownload(Context context, String url, File oFile) {
			owner = context;
			urlString = url; 
			outputFile = oFile; 
		}

		@Override
		protected Boolean doInBackground(String... url) {
			try{
				connect();
			}catch(IOException e){
				Log.d(TAG, "ConnectError:" + e.toString());
				cancel(true);
			}

			if(isCancelled()){
				return false;
			}
			if (bufferedInputStream !=  null){
				try{
					int len;
					while((len = bufferedInputStream.read(buffer)) != -1){
						fileOutputStream.write(buffer, 0, len);
						currentByte += len;
						//publishProgress();
						if(isCancelled()){
							break;
						}
					}
				}catch(IOException e){
					Log.d(TAG, e.toString());
					return false;
				}
			}else{
				Log.d(TAG, "bufferedInputStream == null");
			}

			try{
				close();
			}catch(IOException e){
				Log.d(TAG, "CloseError:" + e.toString());
			}
			return true; 
		}

		@Override
		protected void onPreExecute(){ 
		}

		@Override
		protected void onPostExecute(Boolean result){ 
		}

		@Override
		protected void onProgressUpdate(Void... progress){
		}

		private void connect() throws IOException { 
			url = new URL(urlString);
			urlConnection = url.openConnection();
			urlConnection.setReadTimeout(TIMEOUT_READ);
			urlConnection.setConnectTimeout(TIMEOUT_CONNECT);
			inputStream = urlConnection.getInputStream();
			bufferedInputStream = new BufferedInputStream(inputStream, BUFFER_SIZE);
			fileOutputStream = new FileOutputStream(outputFile);

			totalByte = urlConnection.getContentLength();
			currentByte = 0;
		}


		private void close() throws IOException {

			fileOutputStream.flush();
			fileOutputStream.close();
			bufferedInputStream.close();
		}

		public int getLoadedBytePercent() {
			if(totalByte <= 0){ 
				return 0; 
			} 
			return (int)Math.floor(100 * currentByte/totalByte); 
		}

	}
	
	public static void extract( File file ) {
		ZipInputStream in = null;
		BufferedOutputStream out = null;

		ZipEntry zipEntry = null;
		int len = 0;

		try {
			in = new ZipInputStream(new FileInputStream(file));

			// ZIPファイルに含まれるエントリに対して順にアクセス
			while ((zipEntry = in.getNextEntry()) != null) {
				File newfile = new File(zipEntry.getName());

				// 出力用ファイルストリームの生成
				out = new BufferedOutputStream(
						new FileOutputStream( file.getParent() +"/"+ newfile.getName())
						);

				Log.d("file extract", "EXTRACT:" + file.getParent() +"/"+ newfile.getName());
				
				// エントリの内容を出力
				byte[] buffer = new byte[1024];
				while ((len = in.read(buffer)) != -1) {
					out.write(buffer, 0, len);
				}

				in.closeEntry();
				out.close();
				out = null;
			}
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	

	
	

}
