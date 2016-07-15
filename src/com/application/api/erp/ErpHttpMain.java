package com.application.api.erp;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.application.constans.Constans;
import com.application.util.HttpUtil;
import com.application.util.HttpUtil.Response;

/**
 * @author Administrator
 * @功能:uas 接口测试
 */
public class ErpHttpMain {
	public static Map<String, Object> param = new HashMap<String, Object>();
	public static LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
	public static  String url = "http://218.17.158.219:8090/ERP/";
	public static String sessionId;

	public static String formStore="{\"ct_name\":\"wang二\",\"ct_sex\":1,\"ct_cuname\":\"12\",\"ct_dept\":\"研发部\",\"ct_position\":\"工程师\",\"ct_officephone\":\"1132213\",\"ct_mobile\":\"13266699268\",\"ct_personemail\":\"728437832@qq.com\",\"ct_address\":\"深圳市\",\"ct_birthday\":\"1990-08-11\",\"ct_reamrk\":\"似懂非懂\",\"ct_attach\":\"12\"}";
	
	
	public static void main(String[] args) {
	      sessionId=getCookieLogin(url+"mobile/login.action","13266699268", "1", "UAS");
	      TestAddCustomer();
	}


	/**
	 * 
	 */
	public static void TestAddCustomer() {
		formStore="{\"cu_code\":\"232423453\",\"cu_name\":\"华商龙1\",\"cu_source\":\"\",\"cu_defaultlevel\":\"\",\"cu_tel\":\"123213\",\"cu_lastdate\":\"2016-07-12\",\"cu_add1\":\"32123\",\"cu_remark\":\"sfdsd\"}";
	      url=url+"scm/sale/savePreCustomer.action";
//		 / System.out.println(formStore);
		  param.put("formStore", formStore);
		  param.put("caller", "PreCustome");
		//  param.put("param", "[]");
		  System.out.println(param.toString());
		  headers.put("Cookie", "JSESSIONID="+sessionId);
		  try {
			Response response = HttpUtil.sendPostHeaderRequest(url, param, headers, false);
			System.out.println(response.getResponseText());
		  } catch (Exception e) {
			e.printStackTrace();
		  }
	}


	/**
	 * url 字符转码
	 */
	public static void TestURLEncoder() {
		try {
		  String mytext1 = java.net.URLEncoder.encode("中国", "utf-8"); 
		  String mytext2 = java.net.URLEncoder.encode("中国", "utf-8"); 
		  String mytext5 = java.net.URLDecoder.decode(mytext1, "utf-8");
		  String mytext6 = java.net.URLEncoder.encode(mytext2, "utf-8");
		  System.out.println(mytext5);
		  System.out.println(mytext6);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * 新建联系人
	 */
	public static void TestAddContact() {
		  url=url+"crm/customermgr/saveContact.action";
//		 / System.out.println(formStore);
		  param.put("formStore", formStore);
		  param.put("caller", "Contact");
		  param.put("param", "[]");
		  System.out.println(param.toString());
		  headers.put("Cookie", "JSESSIONID="+sessionId);
		  try {
			Response response = HttpUtil.sendPostHeaderRequest(url, param, headers, false);
			System.out.println(response.getResponseText());
		  } catch (Exception e) {
			e.printStackTrace();
		  }
	}

	public static void test1() {
		// Constans.ERP_BASIC="http://xjxmy.saas.ubtob.com/";
		sessionId = getCookieLogin(url,"13352991628", "az00213381", "SAAS_10041495");
		getAllHrorgEmps(Constans.ERP_GETALLHRORGEMPS, "SAAS_10041495", "", sessionId);
	}

	public static void TestLogin(String phone, String password) {
		String url = "http://manage.ubtob.com/public/account";
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("user", phone);
		param.put("password", password);
		try {
			Response response = HttpUtil.sendGetRequest(url, param, true);
			System.out.println("login 状态码：" + response.getStatusCode());
			System.out.println("login 正文：" + response.getResponseText());
		} catch (Exception e) {
			System.out.println("超时");
			System.out.println(e.getMessage());
		}
	}

	public static String getCookieLogin(String url,String phone, String password, String master) {
		Response response = getERPLogin(url,phone, password, master);// 标准版
		sessionId = JSON.parseObject(response.getResponseText()).getString("sessionId");
		return sessionId;
	}

	public static Response getERPLogin(String url,String phone, String password, String master) {
		cleardata();
		final Map<String, Object> param = new HashMap<>();
		param.put("username", phone);
		param.put("password", password);
		param.put("master", master);
		return loadNewsList(url, param, headers, null, "get");
	}

	public static void getERPNewsList(String sessionId) {
		cleardata();
		url = Constans.ERP_NEWS;
		final Map<String, Object> param = new HashMap<>();
		param.put("caller", "News");
		param.put("sessionId", sessionId);
		param.put("page", "1");
		param.put("pageSize", "100");
		param.put("condition", "1=1");
		headers.put("Cookie", "JSESSIONID=" + sessionId);
		loadNewsList(url, param, headers, null, "get");
	}

	/**
	 * @author Administrator
	 * @功能:获取组织架构全部信息
	 */
	public static Response getAllHrorgEmps(String url, String master, String date, String sessionId) {
		cleardata();
		final Map<String, Object> param = new HashMap<>();
		String datastr = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		System.out.println(datastr);
		param.put("master", master);
		param.put("lastdate", date);
		headers.put("Cookie", "JSESSIONID=" + sessionId);
		return loadNewsList(url, param, headers, null, "get");
	}

	public static void getERPNoticesDetail(int id) {
		cleardata();
		url = Constans.ERP_NOTICES_DETAIL;
		final Map<String, Object> param = new HashMap<>();
		param.put("caller", "News");
		param.put("id", id);
		headers.put("Cookie", "JSESSIONID=" + sessionId);
		loadNewsList(url, param, headers, null, "get");
	}

	public static void getERPNoticesList(String sessionId) {
		cleardata();
		url = Constans.ERP_NEWS;
		final Map<String, Object> param = new HashMap<>();
		param.put("caller", "Note");
		param.put("sessionId", sessionId);
		param.put("page", "1");
		param.put("pageSize", "100");
		// param.put("condition", "NO_INFOTYPE='TZ'");
		param.put("condition", "NO_INFOTYPE='TZ'");
		headers.put("Cookie", "JSESSIONID=" + sessionId);
		loadNewsList(url, param, headers, null, "get");
	}

	public static void getERPNewsDetail(String id, String sessionId) {
		cleardata();
		url = Constans.ERP_NEWS_DETAIL;
		final Map<String, Object> param = new HashMap<>();
		param.put("caller", "News");
		param.put("id", id);
		headers.put("Cookie", "JSESSIONID=" + sessionId);
		loadNewsList(url, param, headers, null, "get");
	}

	/**
	 * @author Administrator
	 * @功能:下载附件
	 */
	public static void getERPDownload(String id, String sessionId) {
		cleardata();
		url = Constans.ERP_DOWNLOAD_ID;
		final Map<String, Object> param = new HashMap<>();
		param.put("id", id);
		headers.put("Cookie", "JSESSIONID=" + sessionId);
		// loadNewsList(url, param, headers, null, "get");
		System.out.println(url + "?id=" + id);
		HttpUtil.download(url + "?id=" + id, "C:/Users/Administrator/Desktop/" + id + ".amr");
	}

	/**
	 * @author Administrator
	 * @功能:响应函数
	 */
	public static Response loadNewsList(String url, Map<String, Object> param, LinkedHashMap<String, Object> headers,
			String bodyJson, String post) {
		Response response = ErpApiUtil.commomHttpMethod(url, param, headers, bodyJson, post);
		System.out.println("--------------------------------------------------------------");
		System.out.println(response.getResponseText());
		System.out.println(response.getStatusCode());
		System.out.println(url);
		// System.out.println("-- "+response.getCookieStore());
		System.out.println("--------------------------------------------------------------");
		return response;
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
