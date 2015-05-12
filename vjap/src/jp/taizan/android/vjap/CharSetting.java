package jp.taizan.android.vjap;



class CharSetting {
	
	public final String charcter;
	public final float angle;

	/**
	 * xの差分
	 * Paint#getFontSpacing() * xが足される
	 * -0.5fが設定さsれた場合、1/2文字分左にずれる
	 */
	public final float x;

	/**
	 * yの差分
	 * Paint#getFontSpacing() * yが足される
	 * -0.5fが設定された場合、1/2文字分上にずれる
	 */
	public final float y;

	public CharSetting(String charcter, float angle, float x, float y) {
		super();
		this.charcter = charcter;
		this.angle = angle;
		this.x = x;
		this.y = y;
	}

	public static final CharSetting[] settings = {
		new CharSetting("、", 0.0f, 0.7f, -0.6f), new CharSetting("。", 0.0f, 0.7f, -0.6f),
		new CharSetting("，", 0.0f, 0.7f, -0.6f), new CharSetting("．", 0.0f, 0.7f, -0.6f),

		new CharSetting("「", 90.0f, -1.0f, -0.3f), new CharSetting("」", 90.0f, -0.7f, 0.0f),
		new CharSetting("『", 90.0f, -1.0f, -0.3f), new CharSetting("』", 90.0f, -0.7f, 0.0f),
		new CharSetting("（", 90.0f,-0.8f, -0.13f), new CharSetting("）", 90.0f, -0.8f, -0.13f),
		new CharSetting("【", 90.0f,-0.8f, -0.13f), new CharSetting("】", 90.0f, -0.8f, -0.13f),
		new CharSetting("［", 90.0f,-0.8f, -0.13f), new CharSetting("］", 90.0f, -0.8f, -0.13f),
		new CharSetting("〔", 90.0f,-0.8f, -0.13f), new CharSetting("〕", 90.0f, -0.8f, -0.13f),
		new CharSetting("〈", 90.0f,-0.8f, -0.13f), new CharSetting("〉", 90.0f, -0.8f, -0.13f),
		new CharSetting("《", 90.0f,-0.8f, -0.13f), new CharSetting("》", 90.0f, -0.8f, -0.13f),
		new CharSetting("＜", 90.0f,-0.8f, -0.13f), new CharSetting("＞", 90.0f, -0.8f, -0.13f),
		new CharSetting("：", 90.0f, -0.8f, -0.1f),new CharSetting("；", 90.0f, 0.8f, -0.1f),
		new CharSetting("／", 90.0f, -0.9f, -0.1f),new CharSetting("｜", 90.0f, -0.8f, -0.1f),
		new CharSetting("＝", 90.0f, -0.8f, -0.1f),new CharSetting("÷", 90.0f, -0.8f, -0.1f),

		new CharSetting("“", 0.0f, -0.0f, 0.6f), new CharSetting("”", 0.0f, -0.0f, 0.1f),
		new CharSetting("゛", 0.0f, 0.9f, -1.0f),new CharSetting("゜", 0.0f, 0.9f, -1.0f),

		//別の文字なので注意
		new CharSetting("～", 90.0f, -0.8f, -0.1f),new CharSetting("〜", 90.0f, -0.8f, -0.1f),
		new CharSetting("─", 90.0f, -0.8f, -0.1f),new CharSetting("—", 90.0f, -0.8f, -0.1f),new CharSetting("―", 90.0f, -0.8f, -0.1f),
		new CharSetting("―", 90.0f, -0.8f, -0.1f),new CharSetting("−", 90.0f, -0.8f, -0.1f),

		new CharSetting(".", 0.0f, 0.7f, -0.6f), new CharSetting(",", 0.0f, 0.7f, -0.6f),
		new CharSetting("(", 90.0f, -0.3f, -0.15f), new CharSetting(")", 90.0f, -0.3f, -0.15f),
		new CharSetting("[", 90.0f, -0.3f, -0.13f), new CharSetting("]", 90.0f, -0.3f, -0.13f),
		new CharSetting("{", 90.0f, -0.3f, -0.13f), new CharSetting("}", 90.0f, -0.3f, -0.13f),
		new CharSetting(":", 90.0f, -0.4f, -0.1f),new CharSetting(";", 90.0f, -0.4f, -0.1f), 
		new CharSetting("~", 90.0f, -0.4f, -0.1f), new CharSetting("|", 90.0f, -0.4f, -0.1f),
		new CharSetting("/", 90.0f, -0.4f, -0.1f),new CharSetting("…", 90.0f, -0.8f, -0.1f),
		new CharSetting("=", 90.0f, -0.4f, -0.1f),new CharSetting("-", 90.0f, -0.4f, -0.1f),


		new CharSetting("ぁ", 0.0f, 0.1f, -0.1f), new CharSetting("ぃ", 0.0f, 0.1f, -0.1f),
		new CharSetting("ぅ", 0.0f, 0.1f, -0.1f), new CharSetting("ぇ", 0.0f, 0.1f, -0.1f),
		new CharSetting("ぉ", 0.0f, 0.1f, -0.1f), new CharSetting("っ", 0.0f, 0.1f, -0.1f),
		new CharSetting("ゃ", 0.0f, 0.1f, -0.1f), new CharSetting("ゅ", 0.0f, 0.1f, -0.1f),
		new CharSetting("ょ", 0.0f, 0.1f, -0.1f), new CharSetting("ァ", 0.0f, 0.1f, -0.1f),
		new CharSetting("ィ", 0.0f, 0.1f, -0.1f), new CharSetting("ゥ", 0.0f, 0.1f, -0.1f),
		new CharSetting("ェ", 0.0f, 0.1f, -0.1f), new CharSetting("ォ", 0.0f, 0.1f, -0.1f),
		new CharSetting("ッ", 0.0f, 0.1f, -0.1f), new CharSetting("ャ", 0.0f, 0.1f, -0.1f),
		new CharSetting("ュ", 0.0f, 0.1f, -0.1f), new CharSetting("ョ", 0.0f, 0.1f, -0.1f),
		new CharSetting("ー", -90.0f, -0.05f, 0.9f), new CharSetting("a", 90.0f, -0.4f, -0.1f),
		new CharSetting("b", 90.0f, -0.4f, -0.1f), new CharSetting("c", 90.0f, -0.4f, -0.1f),
		new CharSetting("d", 90.0f, -0.4f, -0.1f), new CharSetting("e", 90.0f, -0.4f, -0.1f),
		new CharSetting("f", 90.0f, -0.4f, -0.1f), new CharSetting("g", 90.0f, -0.4f, -0.1f),
		new CharSetting("h", 90.0f, -0.4f, -0.1f), new CharSetting("i", 90.0f, -0.4f, -0.1f),
		new CharSetting("j", 90.0f, -0.4f, -0.1f), new CharSetting("k", 90.0f, -0.4f, -0.1f),
		new CharSetting("l", 90.0f, -0.4f, -0.1f), new CharSetting("m", 90.0f, -0.4f, -0.1f),
		new CharSetting("n", 90.0f, -0.4f, -0.1f), new CharSetting("o", 90.0f, -0.4f, -0.1f),
		new CharSetting("p", 90.0f, -0.4f, -0.1f), new CharSetting("q", 90.0f, -0.4f, -0.1f),
		new CharSetting("r", 90.0f, -0.4f, -0.1f), new CharSetting("s", 90.0f, -0.4f, -0.1f),
		new CharSetting("t", 90.0f, -0.4f, -0.1f), new CharSetting("u", 90.0f, -0.4f, -0.1f),
		new CharSetting("v", 90.0f, -0.4f, -0.1f), new CharSetting("w", 90.0f, -0.4f, -0.1f),
		new CharSetting("x", 90.0f, -0.4f, -0.1f), new CharSetting("y", 90.0f, -0.4f, -0.1f),
		new CharSetting("z", 90.0f, -0.4f, -0.1f), new CharSetting("A", 90.0f, -0.4f, -0.1f),
		new CharSetting("B", 90.0f, -0.4f, -0.1f), new CharSetting("C", 90.0f, -0.4f, -0.1f),
		new CharSetting("D", 90.0f, -0.4f, -0.1f), new CharSetting("E", 90.0f, -0.4f, -0.1f),
		new CharSetting("F", 90.0f, -0.4f, -0.1f), new CharSetting("G", 90.0f, -0.4f, -0.1f),
		new CharSetting("H", 90.0f, -0.4f, -0.1f), new CharSetting("I", 90.0f, -0.4f, -0.1f),
		new CharSetting("J", 90.0f, -0.4f, -0.1f), new CharSetting("K", 90.0f, -0.4f, -0.1f),
		new CharSetting("L", 90.0f, -0.4f, -0.1f), new CharSetting("M", 90.0f, -0.4f, -0.1f),
		new CharSetting("N", 90.0f, -0.4f, -0.1f), new CharSetting("O", 90.0f, -0.4f, -0.1f),
		new CharSetting("P", 90.0f, -0.4f, -0.1f), new CharSetting("Q", 90.0f, -0.4f, -0.1f),
		new CharSetting("R", 90.0f, -0.4f, -0.1f), new CharSetting("S", 90.0f, -0.4f, -0.1f),
		new CharSetting("T", 90.0f, -0.4f, -0.1f), new CharSetting("U", 90.0f, -0.4f, -0.1f),
		new CharSetting("V", 90.0f, -0.4f, -0.1f), new CharSetting("W", 90.0f, -0.4f, -0.1f),
		new CharSetting("X", 90.0f, -0.4f, -0.1f), new CharSetting("Y", 90.0f, -0.4f, -0.1f),
		new CharSetting("Z", 90.0f, -0.4f, -0.1f), 
		/* 全角英字は縦表示に
        new CharSetting("ａ", 90.0f, -0.9f, -0.1f),
        new CharSetting("ｂ", 90.0f, -0.9f, -0.1f), new CharSetting("ｃ", 90.0f, -0.9f, -0.1f),
        new CharSetting("ｄ", 90.0f, -0.9f, -0.1f), new CharSetting("ｅ", 90.0f, -0.9f, -0.1f),
        new CharSetting("ｆ", 90.0f, -0.9f, -0.1f), new CharSetting("ｇ", 90.0f, -0.9f, -0.1f),
        new CharSetting("ｈ", 90.0f, -0.9f, -0.1f), new CharSetting("ｉ", 90.0f, -0.9f, -0.1f),
        new CharSetting("ｊ", 90.0f, -0.9f, -0.1f), new CharSetting("ｋ", 90.0f, -0.9f, -0.1f),
        new CharSetting("ｌ", 90.0f, -0.9f, -0.1f), new CharSetting("ｍ", 90.0f, -0.9f, -0.1f),
        new CharSetting("ｎ", 90.0f, -0.9f, -0.1f), new CharSetting("ｏ", 90.0f, -0.9f, -0.1f),
        new CharSetting("ｐ", 90.0f, -0.9f, -0.1f), new CharSetting("ｑ", 90.0f, -0.9f, -0.1f),
        new CharSetting("ｒ", 90.0f, -0.9f, -0.1f), new CharSetting("ｓ", 90.0f, -0.9f, -0.1f),
        new CharSetting("ｔ", 90.0f, -0.9f, -0.1f), new CharSetting("ｕ", 90.0f, -0.9f, -0.1f),
        new CharSetting("ｖ", 90.0f, -0.9f, -0.1f), new CharSetting("ｗ", 90.0f, -0.9f, -0.1f),
        new CharSetting("ｘ", 90.0f, -0.9f, -0.1f), new CharSetting("ｙ", 90.0f, -0.9f, -0.1f),
        new CharSetting("ｚ", 90.0f, -0.9f, -0.1f), new CharSetting("Ａ", 90.0f, -0.9f, -0.1f),
        new CharSetting("Ｂ", 90.0f, -0.9f, -0.1f), new CharSetting("Ｃ", 90.0f, -0.9f, -0.1f),
        new CharSetting("Ｄ", 90.0f, -0.9f, -0.1f), new CharSetting("Ｅ", 90.0f, -0.9f, -0.1f),
        new CharSetting("Ｆ", 90.0f, -0.9f, -0.1f), new CharSetting("Ｇ", 90.0f, -0.9f, -0.1f),
        new CharSetting("Ｈ", 90.0f, -0.9f, -0.1f), new CharSetting("Ｉ", 90.0f, -0.9f, -0.1f),
        new CharSetting("Ｊ", 90.0f, -0.9f, -0.1f), new CharSetting("Ｋ", 90.0f, -0.9f, -0.1f),
        new CharSetting("Ｌ", 90.0f, -0.9f, -0.1f), new CharSetting("Ｍ", 90.0f, -0.9f, -0.1f),
        new CharSetting("Ｎ", 90.0f, -0.9f, -0.1f), new CharSetting("Ｏ", 90.0f, -0.9f, -0.1f),
        new CharSetting("Ｐ", 90.0f, -0.9f, -0.1f), new CharSetting("Ｑ", 90.0f, -0.9f, -0.1f),
        new CharSetting("Ｒ", 90.0f, -0.9f, -0.1f), new CharSetting("Ｓ", 90.0f, -0.9f, -0.1f),
        new CharSetting("Ｔ", 90.0f, -0.9f, -0.1f), new CharSetting("Ｕ", 90.0f, -0.9f, -0.1f),
        new CharSetting("Ｖ", 90.0f, -0.9f, -0.1f), new CharSetting("Ｗ", 90.0f, -0.9f, -0.1f),
        new CharSetting("Ｘ", 90.0f, -0.9f, -0.1f), new CharSetting("Ｙ", 90.0f, -0.9f, -0.1f),
        new CharSetting("Ｚ", 90.0f, -0.9f, -0.1f), 
		 */

		new CharSetting("", 90.0f, 0.0f, -0.1f), 



	};

	public static CharSetting getSetting(String character) {
		for (int i = 0; i < settings.length; i++) {
			if (settings[i].charcter.equals(character)) {
				return settings[i];
			}
		}
		return null;
	}

	private static final String[] PUNCTUATION_MARK = {
		"、", "。", "「", "」"
	};

	public static boolean isPunctuationMark(String s) {
		for (String functuantionMark : PUNCTUATION_MARK) {
			if (functuantionMark.equals(s)) {
				return true;
			}
		}
		return false;
	}

}
