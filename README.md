# vjap
Androidの日本語縦書き用ライブラリ

##基本的な使い方
xmlにvTextLayoutを配置してinflateし、initContentを呼び出す。

xml

    <jp.taizan.android.vjap.VTextLayout
        android:id="@+id/vTextLayout"
        android:layout_width="fill_parent"
        android:layout_height="fill_parent" />

Activity

    vTextLayout = (VTextLayout) findViewById(R.id.vTextLayout);
    vTextLayout.initContent("たいとる","〜〜本文〜〜");
