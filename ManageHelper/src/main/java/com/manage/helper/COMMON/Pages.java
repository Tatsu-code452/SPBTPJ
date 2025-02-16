package com.manage.helper.COMMON;

import java.util.HashMap;
import java.util.Map;

public final class Pages {
	private Pages() {
		// インスタンス化の防止
	}

	public static final String PAGE_SPBTPJ1 = "views/spbtpj1";
	public static final String PAGE_SPBTPJ1_REDIRECT = "redirect:/spbtpj1/init";

	public static final String PAGE_SPBTPJ2 = "views/spbtpj2";
	public static final String PAGE_SPBTPJ2_REDIRECT = "redirect:/spbtpj2/init";

	public static final String PAGE_SPBTPJ3 = "views/spbtpj3";
	public static final String PAGE_SPBTPJ3_REDIRECT = "redirect:/spbtpj3/init";

	public static Map<String, String> PAGE_REDIRECT_MAP = new HashMap<String, String>() {
		{
			put("spbtpj1", PAGE_SPBTPJ1_REDIRECT);
			put("spbtpj2", PAGE_SPBTPJ2_REDIRECT);
			put("spbtpj3", PAGE_SPBTPJ3_REDIRECT);
		}
	};

}
