package com.cretin.www.externalmaputilslibrary.web;

import android.app.Activity;

/**
 * 所有需要对WebView提供url参数支持的，都统一在这里配置
 * 
 * @author zhaoxianlie
 */
public class TbUrlBridge {
	public static boolean overrideUrl(Activity activity, String url) {
		// 关闭webview
		if (url.contains("jump=closewebview")) {
			activity.finish();
		}
		return false;
	}
}
