package com.application.api.news;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.application.constans.Constans;
import com.application.entity.NewsListEntity;
import com.application.entity.NewsListEntity.NewsListBody.Pagebean.Contentlist;
import com.application.http.httpclient.StaticUtil;
import com.application.util.HttpUtil.Response;

public class NewsMain {
	private static Map<String, Object> param = new HashMap<String, Object>();
	private static LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
	private static String url = Constans.API_NEWS;

	public static void getNewsList(int page,String channelName) {
		cleardata();
		url = Constans.API_NEWS;
		String datetime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		final Map<String, Object> param = new HashMap<>();
		param.put("showapi_appid", "12041");
		param.put("showapi_sign", "67f7892db890407f95cdf39f870b1234");
		param.put("showapi_timestamp", datetime);
		param.put("page", page);

		// param.put("channelId", value);
		param.put("channelName", channelName);
		loadNewsList(url, param, headers, null, "get");
	}

	public static void getNewsChannel() {
		cleardata();
		url = Constans.API_NEWS_CHANNEL;
		String datetime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		final Map<String, Object> param = new HashMap<>();
		param.put("showapi_appid", "12041");
		param.put("showapi_sign", "67f7892db890407f95cdf39f870b1234");
		param.put("showapi_timestamp", datetime);
		loadNewsList(url, param, headers, null, "get");
	}

	/**
	 * @author Administrator
	 * @功能:解析新闻列表
	 */
	public static void loadNewsList(String url, Map<String, Object> param, LinkedHashMap<String, Object> headers,
			String bodyJson, String post) {
		Response response = StaticUtil.commomHttpMethod(url, param, headers, bodyJson, post);
		System.out.println(response.getStatusCode());
		System.out.println(response.getResponseText());
	}

	public static void parseNewsList(Response response) {
		// JSONObject root= JSON.parseObject(response.getResponseText());
		NewsListEntity newsList = JSON.parseObject(response.getResponseText(), NewsListEntity.class);
		// System.out.println("新闻数据："+newsList.getShowapi_res_body().getPagebean().getContentlist().size());
		List<Contentlist> clist = newsList.getShowapi_res_body().getPagebean().getContentlist();
		System.out.println(clist.get(0).getLong_desc());
	}

	/**
	 * @author Administrator
	 * @功能:清空全局变量的值
	 */
	public static void cleardata() {
		param.clear();
		headers.clear();
	}

}