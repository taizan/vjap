# vjap
Androidの日本語縦書き用ライブラリ

縦書きフォントのないANdroidで縦書き表示する為にcanvasに位置を調整して直接描画しています。

![Alt Text](https://github.com/taizan/vjap/blob/garage/capture.jpg)　　![Alt Text](https://github.com/taizan/vjap/blob/garage/capture3.gif)

##主な対応機能

-ページスクロールバー

-ページ送りのフリップ/クリック切り替え

-挿絵の表示

-改行時の禁則処理

-ルビ振り

-文字フォント変更（デフォルトフォントとIPA明朝フォントに対応）

-文字サイズ変更

-文字色変更


##基本的な使い方
xmlにvTextLayoutを配置してinflateし、`vTextLayout.initContent(title,text)`を呼び出す。

xml

    <jp.taizan.android.vjap.VTextLayout
        android:id="@+id/vTextLayout"
        android:layout_width="fill_parent"
        android:layout_height="fill_parent" />

Activity

    import jp.taizan.android.vjap.VTextLayout;
    ~
    ~
    vTextLayout = (VTextLayout) findViewById(R.id.vTextLayout);
    vTextLayout.initContent("たいとる","〜〜本文〜〜");


###ページスクロールバー
画面下部をクリックするとスクロールバーが表示され、ページ送りが可能です。
画面下部にプログレスバーが回っている時はページ数計算中で、この時はスクロールバーが表示できません。

###ページ送りのフリップ/クリック切り替え
下記の関数でフリップを無効化し、クリック送りに切り替えられます。解除の時はfalseを渡してください。

    vTextLayout.setScrollDisabled(true);

###挿絵の表示
本文中に`%$画像のURL$`という形式でURLを設定すると改ページして画像を表示します。

    
###ルビ振り
基本的に青空文庫の形式で、|ルビ対象《るび》という形式で本文を入力すればOKです。

###フォントの設定
下記のようにフォントが指定でき、IPAフォントを設定するとSDにIPAフォントをダウンロードしてフォントの設定を切り替えます。
ダウンロードされていれば、以降は単に切り替え可能です。

    vTextLayout.setFont( VTextLayout.Font.IPA);
    vTextLayout.setFont( VTextLayout.Font.NORMAL);
    
サイズや色を指定する場合は

    vTextLayout.setFontSize(size);
    vTextLayout.setColor( "white" , "black" ); //白文字黒背景
    

