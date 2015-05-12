package jp.taizan.android.vjap;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import jp.taizan.android.vjap.VTextView.OnPageClacListener;

import com.example.libtest.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;



public class VTextLayout extends RelativeLayout{
	
	private final float PAGING_BAR_SIZE = 60;
	
	Context mContext;
	
	public VTextView vTextView;//あんまり良くないけど…
	
	ReversedViewPager viewPager;
	PagerAdapter adapter;
	
	FontLoader saveFont; 
	
	int currentPage = 1;
	
	ReversedSeekBar pagingBar;
	View pagingBarLayout;
	
	View imageLoadingLayout;
	
	TextView pageNumText;
	
	ProgressBar progressBar;

	OnPageEndListener onPageEndListener = null;
	
	
	private float density;
	
	
	
	public VTextLayout(Context context) {
		super(context);
		init(context);
	}
	
	public VTextLayout(Context context, AttributeSet attrs) {
		super(context,attrs);
		init(context);
	}
	
	public VTextLayout(Context context, AttributeSet attrs ,int def) {
		super(context,attrs,def);
		init(context);
	}
	
	
	class DispView extends ImageView {

		int page;
		RotateAnimation rotate;
		boolean isLoading = false;
		
		public void onStartBitmapLoad(){
			//ローディング開始描画
			this.setImageResource(R.drawable.loading);
			
			//アニメーション指定　コンストラクタでやるとサイズが分からない事に注意
			rotate = new RotateAnimation(0, 360, this.getWidth()/2, this.getHeight()/2); // imgの中心を軸に、0度から360度にかけて回転
			rotate.setDuration(1000); // 3000msかけてアニメーションする
			rotate.setRepeatCount(Animation.INFINITE);
			this.startAnimation(rotate);
			
			this.invalidate();
		}
		
		public void setBitMap(Bitmap bmp){
			this.setImageBitmap(bmp);
			this.clearAnimation();
			this.invalidate();
		}
		
		public DispView(Context context ,int page) {
			super(context);
			this.page = page;
			
		}
		
		@Override
		public void onDraw(Canvas canvas){
			//imageLoadingLayout.setVisibility(View.GONE);
			//画像が設定されていなければ文字の読み込み
			String image = vTextView.checkImage(page);
			if( image == null ){
				vTextView.drawPage(canvas, page , this );
			}else{
				
				if( !isLoading || this.getDrawable() == null){
					DrawImageTask task = new DrawImageTask( image , this );
					task.execute();
					isLoading = true;
				}
				
				super.onDraw(canvas);
			}
			
		};
		
		class DrawImageTask extends AsyncTask<String, Integer, Double> {
			Bitmap bmp;
			//Canvas canvas;
			DispView view;
			String urlStr;

			public DrawImageTask( String url ,DispView v){
				//this.canvas = canvas;
				this.urlStr = url;
				this.view = v;
			}

			@Override protected void onPreExecute() {
				view.onStartBitmapLoad();
			}

			@Override
			protected Double doInBackground(String... params) {
				URL url;
				try {
					url = new URL(this.urlStr);
					bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(Double result) {
				view.setBitMap(bmp);
			}
		};
	}
	
	public void init(Context context){
		LayoutInflater.from(context).inflate(R.layout.vtext, this);
		this.mContext = context;
		
		saveFont = new FontLoader();
		
		//vTextView = (VTextView) findViewById(R.id.vTextView);
		vTextView = new VTextView( context );
		
		//ページ数計算が終ったときの処理
		vTextView.setOnPageClacListener(new OnPageClacListener(){
			@Override
			public void onPageClac(int total) {
				viewPager.totalPage = total;
				progressBar.setVisibility(View.GONE);
				updatePageText();
			}
			
		});
		
		//アダプター作成
		adapter = new PagerAdapter(){
			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				//左スクロールにするためにページとポジションを反転
				//ReversedViewPagerはsetCurrentItemを上書きしているが、ここで来るpositionは生のモノ
				final Integer page = ReversedViewPager.MAX_PAGE - position -1;
				
				//最初のページだけ純正のvTextView
				if( page == 0){
					container.addView(vTextView);
					return vTextView;
				}
				
				//以降のページはonDrawだけvTextViewの描画関数を使い回し
				DispView view = new DispView(mContext , page);
				container.addView(view);
				return view;
			}
			
			@Override
			public int getCount() {
				return ReversedViewPager.MAX_PAGE; //左スクロールにするためにページを確保
			}

			@Override
			public void destroyItem(ViewGroup container, int position, Object object) {
				// コンテナから View を削除
				container.removeView((View) object);
			}
			
			@Override
			public boolean isViewFromObject(View view, Object object) {
				return view == (View) object;
			}
			
		};
		
		//ページャを作成
		viewPager = (ReversedViewPager) findViewById(R.id.view_pager);
		viewPager.setAdapter(adapter);
		
		//ページ切り替え時の処理
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				currentPage = position;
				updatePageText();
				
				if( position >= viewPager.totalPage && onPageEndListener != null){
					onPageEndListener.onPageEnd();
				}
					
				Log.d("page",currentPage+"");
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
		});
		
		
		imageLoadingLayout = findViewById(R.id.imageLoading);
		
		pagingBar = (ReversedSeekBar) findViewById(R.id.seekBar);
		pageNumText = (TextView) findViewById(R.id.pageNumText);
		pagingBarLayout = findViewById(R.id.seekBarLayout);
		progressBar =(ProgressBar) findViewById(R.id.vtextProgressBar);
		
		density = getResources().getDisplayMetrics().density;//画面クリック位置判定用
		
		// ページ送りバー
		pagingBarLayout.setVisibility(View.GONE);
		pagingBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			public void onProgressChanged(SeekBar seekBar,
					int progress, boolean fromUser) {	
				// ツマミをドラッグしたときに呼ばれる
				if(pagingBarLayout.getVisibility() == View.VISIBLE){
					updatePageText();
					viewPager.setCurrentItem( seekBar.getProgress() , false);
					//vTextView.setPage(seekBar.getProgress());
				}
			}
			public void onStartTrackingTouch(SeekBar seekBar) {}
			public void onStopTrackingTouch(SeekBar seekBar) {}
		});
		
		//ページカウントバー
		progressBar.setVisibility(View.VISIBLE);
		
	}
	
	void updatePageText(){
		String text = vTextView.getTotalPage() < 0 ?  currentPage+"" : currentPage + "/" + (vTextView.getTotalPage()+1 );
		pageNumText.setText( text );
		
		//TEMP
		//pageNumText.setText(this.viewPager.virtualX+"");
	}
	
	
	
	
	public void updatePageNum( final boolean showSeekBar ){
		pagingBar.setMax(vTextView.getTotalPage());
		pagingBar.setProgress( currentPage );
		
		updatePageText();
		progressBar.setVisibility(View.GONE);
		if( showSeekBar )pagingBarLayout.setVisibility(View.VISIBLE);
		
	}
	
	private float touchStartX;
	private float touchStartY;
	
	
	@Override
	public boolean  onInterceptTouchEvent(MotionEvent ev) {
		//Log.d("touch vt layout", ev.getY() +":"+ getHeight()  );
		
		switch (ev.getAction() ) {
		case MotionEvent.ACTION_DOWN :
			//下エリアをタッチした時にバーが非表示なら表示、
			//それ以外をタッチした時にバーが表示なら非表示に

			if( ev.getY() >  getHeight() - PAGING_BAR_SIZE *density ){
				if( pagingBarLayout.getVisibility() != View.VISIBLE  && vTextView.getTotalPage() > 0){
					updatePageNum(true);
					return true;
				}
			}else {
				if( pagingBarLayout.getVisibility() == View.VISIBLE ){
					pagingBarLayout.setVisibility(View.INVISIBLE);
					return true;
				}
			}
			//初期値を保存。ページ送り方向は初期値で決定
			touchStartX = ev.getX();
			touchStartY = ev.getY();
			break;
		case MotionEvent.ACTION_UP:
			if( viewPager.scrollDisabled ){//ページングが有効ならクリックでページ送り
				if( vTextView.virtical ){//縦書きの場合
					int direction =  isClickDirectionLeft ? 1 : -1; //方向によって係数を変える
					if( direction * touchStartX > direction * vTextView.width /2 ) {
						if( currentPage > 1 ) {
							viewPager.setCurrentItem( currentPage -1 , false);
						}
					}else{
						if( currentPage < vTextView.getTotalPage() ||  vTextView.getTotalPage() < 0 ){
							viewPager.setCurrentItem( currentPage +1 , false);
						}
					}
				}else{//横書きの場合
					if( touchStartY > vTextView.height /2 ) {
						if( currentPage > 1 ) {
							viewPager.setCurrentItem( currentPage -1 , false);
						}
					}else{
						if( currentPage < vTextView.getTotalPage() ||  vTextView.getTotalPage() < 0 ){
							viewPager.setCurrentItem( currentPage +1 , false);
						}
					}
				}
			}
			updatePageText();
		}
		
		return super.onInterceptTouchEvent(ev);
	}
	
	//スクロールの無効化。クリックでページ送り
	public void setScrollDisabled(boolean isDisabled){
		 viewPager.setScrollDisabled(isDisabled);
	}
	
	boolean isClickDirectionLeft = true;
	
	//クリックでページ送りの方向
	public void setClickDirectionLeft(boolean isClickDirectionLeft){
		this.isClickDirectionLeft = isClickDirectionLeft;
	}
	
	//VTextViewへのラッパー群
	public void initContent(String title ,String text){
	
		this.vTextView.setText(text);
		this.vTextView.setTitle(title);
		
		//reset();
	}
	
	@Override
	public void onLayout(boolean changed, int l, int t, int r, int b){
		if(changed){
			//画面回転時や画面変形時は最初のページから描画しなおし
			reset();
		}
		super.onLayout(changed, l, t, r, b);
	}
	
	public void reset(){
		viewPager.totalPage = -1;
		//viewPager.removeAllViews();
		viewPager.setCurrentItem(0);
		vTextView.invalidate();
	}
	
	public interface OnPageEndListener {
		void onPageEnd();
	}


	public void setOnPageEndListener( OnPageEndListener onPageEndListener){
		this.onPageEndListener = onPageEndListener;
	}
	
	
	public void setVirtical(boolean isVirtical){
		vTextView.virtical = isVirtical;
	}
	
	
	
	public void setFontSize(int size){
		vTextView.setFontSize(size);;
	}
	
	public enum Font { NORMAL, IPA };
	
	//フォント指定
	public void setFont(Font font ){
		switch(font){
			case NORMAL:
				vTextView.setFont(null);
				reset();
				break;
			case IPA:
				File f = saveFont.getFontFile();
				if( f.exists() ){
					vTextView.setFont( saveFont.getFontFile().getAbsolutePath() );
					reset();
				}else{
					downloadFont();
				}
				break;
		}
	}
	
	public void downloadFont(){
		AlertDialog.Builder alertDialog = new AlertDialog.Builder( mContext );
		
		// ダイアログの設定
		alertDialog.setTitle("フォントのダウンロード");      //タイトル設定
		alertDialog.setMessage("IPA明朝フォントがオススメです。ダウンロードしますか？");  //内容(メッセージ)設定

		// OK(肯定的な)ボタンの設定
		alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				saveFont.setOnDownloadEndListener(new Runnable(){
					@Override
					public void run() {
						vTextView.setFont( saveFont.getFontFile().getAbsolutePath() );
						reset();
					}
				});
				saveFont.loadFont(mContext);
			}
		});

		// NG(否定的な)ボタンの設定
		alertDialog.setNegativeButton("NG", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});

		// ダイアログの作成と描画
		alertDialog.show();

	}

	
	public void setColor(String fontColor,String backgroundColor){
		vTextView.setColor(fontColor, backgroundColor);
		this.setBackgroundColor(Color.parseColor(backgroundColor));
		pageNumText.setTextColor(Color.parseColor(fontColor));
		
		reset();
	}
	
}
