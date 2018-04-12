package cn.test.framework.tests;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONObject;
import com.test.framework.utils.AssertUtil;
import com.test.framework.utils.HttpClientUtil;
import com.test.framework.utils.HttpUtil;
import com.test.framework.utils.MD5Util;

public class BakeTest {

	public static final String MD5_KEY = "f652724b231d0cc23122a6b3e1646036";

	public static final String MD5_KEY_TH = "f652724b231d0cc23122a6b33e1646033";

	public static final String DEV_repaymentPlanUpload = "http://dev-pim-api.baketechfin.com/api/repaymentPlanUpload";

	public static final String DEV_contractUploadd = "http://dev-pim-api.baketechfin.com/api/contractUpload";

	@Test
	public void repaymentPlanUpload() throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		List<Map<String, String>> maps = HttpClientUtil.getExcelData("getPlan");
		for (int i = 0; i < maps.size(); i++) {
			params = maps.get(i);
			params.put("projectId", "1");
			params.put("contractNo", params.get("contractNo"));
			params.put("planJson", params.get("planJson"));
			params = getSignParams2(params);
			String result = HttpClientUtil.sendPost4File(
					DEV_repaymentPlanUpload, null, params);
			System.out.println("上传还款计划表:" + result);
		}

	}

	@Test
	public void contractUpload() throws Exception {
		List<Map<String, String>> maps = HttpClientUtil.getExcelData("getContract");
		File[] fileArray = { new File("C:\\Users\\admin\\Desktop\\test.png"),
				new File("C:\\Users\\admin\\Desktop\\test.png") };
		Map<String, String> params = new HashMap<String, String>();
		Map<String, File[]> files = new HashMap<String, File[]>();
		files.put("contractFile", fileArray);
		files.put("invoiceFile", fileArray);
		for (int i = 0; i < maps.size(); i++) {
			params = maps.get(i);
			params.put("projectId", "1");
			params.put("contractNo", params.get("contractNo"));
			params.put("dealerName", params.get("dealerName"));
			params.put("dealerAccount", params.get("dealerAccount"));
			params.put("dealerArea", params.get("dealerArea"));
			params.put("lenderAge", params.get("lenderAge"));
			params.put("lenderAccount", params.get("lenderAccount"));
			params.put("loanDate", params.get("loanDate"));
			params.put("invoicePrice", params.get("invoicePrice"));
			params.put("contractPrice", params.get("contractPrice"));
			params.put("firstPayment", params.get("firstPayment"));
			params.put("loanRate", params.get("loanRate"));
			params.put("loanType", params.get("loanType"));
			params.put("loanPeriods", params.get("loanPeriods"));
			params.put("repaymentDate", params.get("repaymentDate"));
			params.put("totalInterest", params.get("totalInterest"));
			params.put("totalPrincipalAndInterest",
					params.get("totalPrincipalAndInterest"));
			params.put("monthRepayment", params.get("monthRepayment"));
			params.put("carType", params.get("carType"));
			params.put("isNewCar", params.get("isNewCar"));
			params = getSignParams2(params);
			String result = HttpClientUtil.sendPost4FileArray(
					DEV_contractUploadd, files, params);
			System.out.println("合同上传:" + result);
		}
		// params.put("invalidContractNo", "1116129680");
		// params.put("contractNo", "1116129681");//合同编号
		// params.put("dealerName", "车商2");//经销商名称
		// params.put("dealerAccount", "6228480030631260000");//经销商帐号
		// params.put("dealerArea", "江苏");//经销商区域
		// params.put("lenderAge", "18");//借款人年龄
		// params.put("lenderAccount", "456");//借款人帐号
		// params.put("loanDate", "2016-11-10");//放款日期
		// params.put("invoicePrice", "75000");//开票价格
		// params.put("contractPrice", "60000");//合同借款金额
		// params.put("firstPayment", "15000");//首付金额
		// params.put("loanRate", "16.07");//放款利率
		// params.put("loanType", "1");//还款类型【1、等额本息2、等额本金】
		// params.put("repaymentDate", "31");//还款日
		// params.put("loanPeriods", "24");//总期数
		// params.put("totalInterest", "10555.44");//利息总计
		// params.put("totalPrincipalAndInterest", "70555.44");//本息总计
		// params.put("monthRepayment", "2939.81");//月供
		// params.put("carType", "1");//车辆类型【1、客车2、货车3、商用车】
		// params.put("isNewCar", "1");//是否新车【1|0】

	}

	@Test
	public void saveStrategy() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", "sdfdsfsdf");
		String result = HttpClientUtil.sendPost4Map(
				"http://dev-pim-admin.baketechfin.com/alarmStrategy/save",
				params);
		System.out.println("获取预警策略详情:" + result);
	}

	@Test
	public void login() {
		// 登录接口
		String loginParams = "password=t6%2FhQYro0GPgRD4okeeEclKQPCYn%2BkUEF60qStO7VI3q2ToagWz0DEQ3fRiwlH6iOHakVplbdlCYUuN7N8G2V7HcYWBktbFFyvTfsyjIRHaQOPBm0UEn8wwq9Z2C%2B5rjL3C4ad0Wbw2bm%2F4crevx9mQnqwmG6fTJ4%2B5UKRrGq2c&t=1513662038940&username=13800138000&apiSign=aea0786219fdf2d5838d99e0b4169516";
		String loginResult = HttpUtil.byPost(
				"http://test-api.bakejinfu.com/user/login", null, loginParams);
		JSONObject jo = (JSONObject) JSONObject.parse(loginResult);
		JSONObject data = (JSONObject) JSONObject.parse(jo.getString("data"));
		System.out.println("登录客户经理：" + data.getString("name"));
		AssertUtil.verifyEquals(data.getString("name"), "张鹏");
	}

	@Test
	public void addCustomer() {
		Map<String, String> params = new HashMap<String, String>();
		Map<String, File> files = new HashMap<String, File>();
		params.put("userId", "f4361b8446cd43ed9d27a14a512d1fab");
		params.put("customerTypeId", "f3cf07fa0e61428db4776590092d63e5");
		params.put(
				"data",
				"{\"base_info_company_addr\":\"{\\\"position_doorplate\\\":\\\"\\\",\\\"position_latitude\\\":31.298510605498247,\\\"position_longitude\\\":121.49328052997592,\\\"position_name\\\":\\\"上海市杨浦区五角场街道纪念路8号\\\"}\",\"base_info_company_name\":\"乐百氏\",\"base_info_gender\":\"男\",\"base_info_idcard\":\"120104198901019079\",\"base_info_idcard_photo\":[\"idcard_1.jpg\",\"idcard_2.jpg\"],\"base_info_name\":\"客户2\"}");
		params.put("memo", "测试并发");
		params = getSignParams2(params);
		File file = new File("C:\\Users\\admin\\Desktop\\test.png");
		files.put("left_test.png", file);
		files.put("right_test.png", file);
		String addCustomerRusult = HttpClientUtil.sendPost4File(
				"http://test-api.bakejinfu.com/customer", files, params);
		System.out.println("添加客户：" + addCustomerRusult);
	}

	@Test
	public void getCustomer() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", "f4361b8446cd43ed9d27a14a512d1fab");
		params.put("customerId", "6ef113f42b8146209637688edf38c151");
		String apiSign = getSignParams(params);
		String getCustomer = HttpUtil
				.byGet("http://test-api.bakejinfu.com/customer?t="
						+ System.currentTimeMillis()
						+ "&apiSign="
						+ apiSign
						+ "&userId=f4361b8446cd43ed9d27a14a512d1fab&customerId=6ef113f42b8146209637688edf38c151",
						null);
		System.out.println("获取客户：" + getCustomer);
	}

	@Test
	public void getPersonBaseInfo() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", "f4361b8446cd43ed9d27a14a512d1fab");
		params.put("personId", "b3595792ff9c4777bdcb3b4238730496");
		String apiSign = getSignParams(params);
		String baseInfo = HttpUtil
				.byGet("http://test-api.bakejinfu.com/customer/person?t="
						+ params.get("t")
						+ "&apiSign="
						+ apiSign
						+ "&personId=b3595792ff9c4777bdcb3b4238730496&userId=f4361b8446cd43ed9d27a14a512d1fab",
						null);
		System.out.println("获取人员基本信息：" + baseInfo);
	}

	@Test
	public void updateCustomerInfo() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", "f4361b8446cd43ed9d27a14a512d1fab");
		params.put("customerId", "6ef113f42b8146209637688edf38c151");
		params.put(
				"data",
				"{\"code\":200,\"data\":{\"idLocked\":\"0\",\"updateDate\":1513667347000,\"star\":\"0\",\"customerTypeCode\":\"5\",\"loanCount\":0,\"permission\":15,\"portrait\":\"[\"group1/M00/00/17/Ch3Fylo4uxKANnBLAAOqXpPf0P8728.jpg\",\"group1/M00/00/17/Ch3Fylo4uxKAIt2oAAOqXpPf0P8162.jpg\"]\",\"title\":\"马超\",\"currentAfterLoan\":false,\"personInfo\":\"{\"base_info_tel\": \"13412341234\", \"base_info_name\": \"马超\", \"base_info_gender\": \"男\", \"base_info_idcard\": \"230404199201017273\", \"base_info_shop_addr\": \"{\"position_doorplate\":\"\",\"position_latitude\":31.298517481118196,\"position_longitude\":121.49324834346768,\"position_name\":\"上海市杨浦区五角场街道纪念路8号\\\"}\", \"base_info_shop_name\": \"西凉军\", \"base_info_shop_photo\": [\"group1/M00/00/17/Ch3Fylo4uxKANnBLAAOqXpPf0P8728.jpg\", \"group1/M00/00/17/Ch3Fylo4uxKAIt2oAAOqXpPf0P8162.jpg\"]}\",\"activityCount\":1,\"riskNotifyCount\":0,\"customerTypeId\":\"1eb28a19c4394da7b056b825bbea72b2\",\"customerTypeName\":\"生产型\",\"createBy\":\"f4361b8446cd43ed9d27a14a512d1fab\",\"updateBy\":\"f4361b8446cd43ed9d27a14a512d1fab\",\"afterLoanCount\":0,\"subtitle\":\"西凉军\",\"personId\":\"b3595792ff9c4777bdcb3b4238730496\",\"id\":\"6ef113f42b8146209637688edf38c151\",\"createDate\":1513667347000},\"success\":true}");
		String apiSign = getSignParams(params);
		String param = "userId=f4361b8446cd43ed9d27a14a512d1fab&t="
				+ params.get("t") + "&apiSign=" + apiSign
				+ "&data={'idLocked':'1'}";
		String baseInfo = HttpUtil.byPost(
				"http://test-api.bakejinfu.com/customer/put", null, param);
		System.out.println("修改客户信息：" + baseInfo);
	}

	@Test
	public void updateCustomerType() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", "f4361b8446cd43ed9d27a14a512d1fab");
		params.put("customerId", "6ef113f42b8146209637688edf38c151");
		params.put("customerTypeId", "1eb28a19c4394da7b056b825bbea72b2");
		String apiSign = getSignParams(params);
		String param = "userId=f4361b8446cd43ed9d27a14a512d1fab&t="
				+ params.get("t")
				+ "&apiSign="
				+ apiSign
				+ "&customerTypeId=1eb28a19c4394da7b056b825bbea72b2&customerId=6ef113f42b8146209637688edf38c151";
		String baseInfo = HttpUtil.byPost(
				"http://test-api.bakejinfu.com/customer/type", null, param);
		System.out.println("修改客户信息：" + baseInfo);
	}

	@Test
	public void customerRelationList() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", "f4361b8446cd43ed9d27a14a512d1fab");
		params.put("customerId", "6ef113f42b8146209637688edf38c151");
		String apiSign = getSignParams(params);
		String baseInfo = HttpUtil
				.byGet("http://test-api.bakejinfu.com/customer/relation/list?customerId=6ef113f42b8146209637688edf38c151&userId=f4361b8446cd43ed9d27a14a512d1fab&t="
						+ params.get("t") + "&apiSign=" + apiSign, null);
		System.out.println("获取客户关系人列表：" + baseInfo);
	}

	@Test
	public void customerRelationDetails() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", "f4361b8446cd43ed9d27a14a512d1fab");
		params.put("relationId", "9e878c0a82ec4524a0f1193a64c6c2c6");
		String apiSign = getSignParams(params);
		String baseInfo = HttpUtil
				.byGet("http://test-api.bakejinfu.com/customer/relation?relationId=9e878c0a82ec4524a0f1193a64c6c2c6&userId=f4361b8446cd43ed9d27a14a512d1fab&t="
						+ params.get("t") + "&apiSign=" + apiSign, null);
		System.out.println("获取客户关系人详情：" + baseInfo);
	}

	@Test
	public void saveCustomerRelationInfo() {
		Map<String, String> params = new HashMap<String, String>();
		String data = "{\"code\":200,\"data\":{\"idLocked\":\"0\",\"updateDate\":1513667347000,\"star\":\"0\",\"customerTypeCode\":\"5\",\"loanCount\":0,\"permission\":15,\"portrait\":\"[\"group1/M00/00/17/Ch3Fylo4uxKANnBLAAOqXpPf0P8728.jpg\",\"group1/M00/00/17/Ch3Fylo4uxKAIt2oAAOqXpPf0P8162.jpg\"]\",\"title\":\"马超\",\"currentAfterLoan\":false,\"personInfo\":\"{\"base_info_tel\": \"13412341234\", \"base_info_name\": \"马超\", \"base_info_gender\": \"男\", \"base_info_idcard\": \"230404199201017273\", \"base_info_shop_addr\": \"{\"position_doorplate\":\"\",\"position_latitude\":31.298517481118196,\"position_longitude\":121.49324834346768,\"position_name\":\"上海市杨浦区五角场街道纪念路8号\\\"}\", \"base_info_shop_name\": \"西凉军\", \"base_info_shop_photo\": [\"group1/M00/00/17/Ch3Fylo4uxKANnBLAAOqXpPf0P8728.jpg\", \"group1/M00/00/17/Ch3Fylo4uxKAIt2oAAOqXpPf0P8162.jpg\"]}\",\"activityCount\":1,\"riskNotifyCount\":0,\"customerTypeId\":\"1eb28a19c4394da7b056b825bbea72b2\",\"customerTypeName\":\"生产型\",\"createBy\":\"f4361b8446cd43ed9d27a14a512d1fab\",\"updateBy\":\"f4361b8446cd43ed9d27a14a512d1fab\",\"afterLoanCount\":0,\"subtitle\":\"西凉军\",\"personId\":\"b3595792ff9c4777bdcb3b4238730496\",\"id\":\"6ef113f42b8146209637688edf38c151\",\"createDate\":1513667347000},\"success\":true}";
		params.put("userId", "f4361b8446cd43ed9d27a14a512d1fab");
		params.put("customerId", "6ef113f42b8146209637688edf38c151");
		params.put("relationType", "5");
		// params.put("data", data);
		String apiSign = getSignParams(params);
		String param = "userId=f4361b8446cd43ed9d27a14a512d1fab&customerId=6ef113f42b8146209637688edf38c151&t="
				+ params.get("t")
				+ "&apiSign="
				+ apiSign
				+ "&data="
				+ data
				+ "&relationType=5";
		String baseInfo = HttpUtil.byPost(
				"http://test-api.bakejinfu.com/customer/relation", null, param);
		System.out.println("保存客户关系人信息：" + baseInfo);
	}

	@Test
	public void deleteCustomerRelationInfo() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", "f4361b8446cd43ed9d27a14a512d1fab");
		params.put("customerId", "6ef113f42b8146209637688edf38c151");
		params = getSignParams2(params);
		String baseInfo = HttpClientUtil.sendPost4Map(
				"http://test-api.bakejinfu.com/customer/relation/del", params);
		System.out.println("删除客户关系人详情：" + baseInfo);
	}

	@Test
	public void personAssetList() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", "f4361b8446cd43ed9d27a14a512d1fab");
		params.put("customerId", "6ef113f42b8146209637688edf38c151");
		params = getSignParams2(params);
		String param = "?userId=f4361b8446cd43ed9d27a14a512d1fab&customerId=6ef113f42b8146209637688edf38c151&t="
				+ params.get("t") + "&apiSign=" + params.get("apiSign");
		String baseInfo = HttpClientUtil
				.sendGet("http://test-api.bakejinfu.com/customer/person_asset_list"
						+ param);
		System.out.println("家庭主要资产列表：" + baseInfo);
	}

	@Test
	public void addPersonAssetCar() {
		Map<String, String> params = new HashMap<String, String>();
		Map<String, File> headers = new HashMap<String, File>();
		headers.put("image", new File("C:\\Users\\admin\\Desktop\\test.png"));
		params.put("userId", "f4361b8446cd43ed9d27a14a512d1fab");
		params.put("customerId", "6ef113f42b8146209637688edf38c151");
		params.put(
				"data",
				"{\"assets_car_proof\":\"行驶证\",\"assets_car_number\":\"123\",\"assets_car_proof_photo\":[\"image_0.jpg\"],\"assets_car_worth\":\"321\",\"assets_car_model\":\"捷达\",\"assets_car_loan_balance\":\"111\"}");
		params = getSignParams2(params);
		String baseInfo = HttpClientUtil.sendPost4File(
				"http://test-api.bakejinfu.com/customer/add_person_assets_car",
				headers, params);
		System.out.println("添加客户车辆资产：" + baseInfo);
	}

	@Test
	public void deletePersonAssetCar() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("carId", "72dfe90ba7314a40a95d0e5666cec33f");
		params.put("customerId", "6ef113f42b8146209637688edf38c151");
		params.put("userId", "f4361b8446cd43ed9d27a14a512d1fab");
		params = getSignParams2(params);
		String baseInfo = HttpClientUtil.sendPost4Map(
				"http://test-api.bakejinfu.com/customer/del_person_assets_car",
				params);
		System.out.println("删除客户车辆资产：" + baseInfo);
	}

	@Test
	public void addPersonAssetBuilding() {
		Map<String, String> params = new HashMap<String, String>();
		Map<String, File> headers = new HashMap<String, File>();
		headers.put("image", new File("C:\\Users\\admin\\Desktop\\test.png"));
		params.put("userId", "f4361b8446cd43ed9d27a14a512d1fab");
		params.put("customerId", "6ef113f42b8146209637688edf38c151");
		params.put("personIds", "['b3595792ff9c4777bdcb3b4238730496']");
		params.put(
				"data",
				"{\"assets_building_area\":\"123\",\"assets_building_worth\":\"123456789\",\"assets_building_proof_photo\":[\"image_0.jpg\"],\"assets_building_street\":\"纪念路88\",\"assets_building_loan_balance\":\"123456\",\"assets_building_proof\":\"购房合同\"}");
		params = getSignParams2(params);
		String baseInfo = HttpClientUtil
				.sendPost4File(
						"http://test-api.bakejinfu.com/customer/add_person_assets_building",
						headers, params);
		System.out.println("添加客户房产资产：" + baseInfo);
		Assert.assertEquals(1, 0);
	}

	@Test
	public void deletePersonAssetBuilding() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("personId", "b3595792ff9c4777bdcb3b4238730496");
		params.put("buildingId", "f431b847957747628e2f8e28008e4e5a");
		params.put("userId", "f4361b8446cd43ed9d27a14a512d1fab");
		params = getSignParams2(params);
		String baseInfo = HttpClientUtil
				.sendPost4Map(
						"http://test-api.bakejinfu.com/customer/del_person_assets_building",
						params);
		System.out.println("删除客户房产资产：" + baseInfo);
	}

	@Test
	public void favoriteCutomer() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("customerId", "6ef113f42b8146209637688edf38c151");
		params.put("star", "0");
		params.put("userId", "f4361b8446cd43ed9d27a14a512d1fab");
		params = getSignParams2(params);
		String param = "?userId=f4361b8446cd43ed9d27a14a512d1fab&customerId=6ef113f42b8146209637688edf38c151&t="
				+ params.get("t")
				+ "&apiSign="
				+ params.get("apiSign")
				+ "&star=0";
		String baseInfo = HttpClientUtil
				.sendGet("http://test-api.bakejinfu.com/customer/favorite"
						+ param);
		System.out.println("收藏客户：" + baseInfo);
	}

	@Test
	public void loanList() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("customerId", "910054eaf5684a92b3ff08c5f203812e");
		params.put("userId", "f4361b8446cd43ed9d27a14a512d1fab");
		params = getSignParams2(params);
		String param = "?userId=f4361b8446cd43ed9d27a14a512d1fab&customerId=910054eaf5684a92b3ff08c5f203812e&t="
				+ params.get("t") + "&apiSign=" + params.get("apiSign");
		String baseInfo = HttpClientUtil
				.sendGet("http://test-api.bakejinfu.com/customer/application_list"
						+ param);
		System.out.println("客户贷款列表：" + baseInfo);
	}

	@Test
	public void traceList() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("customerId", "910054eaf5684a92b3ff08c5f203812e");
		params.put("userId", "f4361b8446cd43ed9d27a14a512d1fab");
		params = getSignParams2(params);
		String param = "?userId=f4361b8446cd43ed9d27a14a512d1fab&customerId=910054eaf5684a92b3ff08c5f203812e&t="
				+ params.get("t") + "&apiSign=" + params.get("apiSign");
		String baseInfo = HttpClientUtil
				.sendGet("http://test-api.bakejinfu.com/customer/trace_list"
						+ param);
		System.out.println("客户贷款列表：" + baseInfo);
	}

	@Test
	public void addTrace() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("customerId", "910054eaf5684a92b3ff08c5f203812e");
		params.put("category", "8");
		params.put("userId", "f4361b8446cd43ed9d27a14a512d1fab");
		params = getSignParams2(params);
		String baseInfo = HttpClientUtil.sendPost4Map(
				"http://test-api.bakejinfu.com/customer/add_trace", params);
		System.out.println("添加客户动态：" + baseInfo);
	}

	@Test
	public void customerAllList() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("customerId", "910054eaf5684a92b3ff08c5f203812e");
		params.put("userId", "f4361b8446cd43ed9d27a14a512d1fab");
		params = getSignParams2(params);
		String param = "?userId=f4361b8446cd43ed9d27a14a512d1fab&customerId=910054eaf5684a92b3ff08c5f203812e&t="
				+ params.get("t") + "&apiSign=" + params.get("apiSign");
		String baseInfo = HttpClientUtil
				.sendGet("http://test-api.bakejinfu.com/customer/alllist"
						+ param);
		System.out.println("全部客户列表：" + baseInfo);
	}

	@Test
	public void nearbyCustomer() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("longitude", "11");
		params.put("latitude", "22");
		params.put("distance", "33");
		params.put("userId", "f4361b8446cd43ed9d27a14a512d1fab");
		params = getSignParams2(params);
		String param = "?userId=f4361b8446cd43ed9d27a14a512d1fab&longitude=11&t="
				+ params.get("t")
				+ "&apiSign="
				+ params.get("apiSign")
				+ "&latitude=22" + "&distance=33";
		String baseInfo = HttpClientUtil
				.sendGet("http://test-api.bakejinfu.com/customer/nearby/list"
						+ param);
		System.out.println("附近客户列表：" + baseInfo);
	}

	@Test
	public void checkLoan() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", "f4361b8446cd43ed9d27a14a512d1fab");
		params.put("customerId", "910054eaf5684a92b3ff08c5f203812e");
		params = getSignParams2(params);
		String param = "?userId=f4361b8446cd43ed9d27a14a512d1fab&t="
				+ params.get("t") + "&apiSign=" + params.get("apiSign")
				+ "&customerId=910054eaf5684a92b3ff08c5f203812e";
		String baseInfo = HttpClientUtil
				.sendGet("http://test-api.bakejinfu.com/application/add_check"
						+ param);
		System.out.println("检查是否可以发起贷款：" + baseInfo);
	}

	@Test
	public void addCreditAuth() {
		Map<String, String> params = new HashMap<String, String>();
		Map<String, File> files = new HashMap<String, File>();
		files.put("authorization_file", new File(
				"C:\\Users\\admin\\Desktop\\test.png"));
		files.put("business_license_file", new File(
				"C:\\Users\\admin\\Desktop\\test.png"));
		params.put("userId", "f4361b8446cd43ed9d27a14a512d1fab");
		params.put("category", "企业");
		params.put("name", "阿里妈妈");
		params.put("applicationId", "19efbe5e0d134fdba6322fd8f145aca7");
		params.put("relationType", "企业");
		params = getSignParams2(params);
		String baseInfo = HttpClientUtil.sendPost4File(
				"http://test-api.bakejinfu.com/application/save_credit_auth",
				files, params);
		System.out.println("添加或更新征信授权信息：" + baseInfo);
	}

	@Test
	public void addLoan() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("customerId", "b28eb92b770e427988d41376f0b71e23");
		params.put("userId", "f4361b8446cd43ed9d27a14a512d1fab");
		params.put("productVersion", "temp1515464092960");
		params.put("productId", "e9a996b137c3460493db29d3a9bba266");
		params.put("idCardFront",
				"group1/M00/00/18/Ch3FylpUac2Ad3PpAAEr9FHBwjo768.jpg");
		params.put("idCardBack",
				"group1/M00/00/18/Ch3FylpUac2AKEpdAAEr9FHBwjo123.jpg");
		params.put(
				"data",
				"{\"apply_limit\":\"8\",\"loan_check_repayment_category\":\"按月清息，本金随借随还\",\"loan_check_fund\":\"8888\",\"loan_check_interest_rate\":\"8\",\"apply_use\":\"测试并发\",\"loan_check_guarantee_category\":\"复合\"}");
		params = getSignParams2(params);
		for (int i = 0; i < 10; i++) {
			String baseInfo = HttpClientUtil.sendPost4File(
					"http://test-api.bakejinfu.com/application/add", null,
					params);
			System.out.println("添加贷款：" + baseInfo);
		}

	}

	@Test
	public void getApplyInfo() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", "f4361b8446cd43ed9d27a14a512d1fab");
		params.put("productId", "a318d15f56934b3484ec5b11ecf99b04");
		params = getSignParams2(params);
		String param = "?userId=f4361b8446cd43ed9d27a14a512d1fab&t="
				+ params.get("t") + "&apiSign=" + params.get("apiSign")
				+ "&productId=a318d15f56934b3484ec5b11ecf99b04";
		String baseInfo = HttpClientUtil
				.sendGet("http://test-api.bakejinfu.com/product/applyinfo"
						+ param);
		System.out.println("获取产品信息：" + baseInfo);
	}

	@Test
	public void getLoanList() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", "f4361b8446cd43ed9d27a14a512d1fab");
		params.put("searchName", "11");
		params = getSignParams2(params);
		String param = "?userId=f4361b8446cd43ed9d27a14a512d1fab&t="
				+ params.get("t") + "&apiSign=" + params.get("apiSign")
				+ "&searchName=11";
		String baseInfo = HttpClientUtil
				.sendGet("http://test-api.bakejinfu.com/application/list"
						+ param);
		System.out.println("获取产品信息：" + baseInfo);
	}

	@Test
	public void getLoanDetail() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", "f4361b8446cd43ed9d27a14a512d1fab");
		params.put("applicationId", "19efbe5e0d134fdba6322fd8f145aca7");
		params = getSignParams2(params);
		String param = "?userId=f4361b8446cd43ed9d27a14a512d1fab&t="
				+ params.get("t") + "&apiSign=" + params.get("apiSign")
				+ "&applicationId=19efbe5e0d134fdba6322fd8f145aca7";
		String baseInfo = HttpClientUtil
				.sendGet("http://test-api.bakejinfu.com/application/detail"
						+ param);
		System.out.println("获取贷款详情：" + baseInfo);
	}

	@Test
	public void saveDualNote() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", "f4361b8446cd43ed9d27a14a512d1fab");
		params.put("applicationId", "19efbe5e0d134fdba6322fd8f145aca7");
		params = getSignParams2(params);
		String baseInfo = HttpClientUtil.sendPost4Map(
				"http://test-api.bakejinfu.com/application/dualnote/save",
				params);
		System.out.println("保存双录：" + baseInfo);
	}

	@Test
	public void getDualNoteList() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", "f4361b8446cd43ed9d27a14a512d1fab");
		params.put("applicationId", "19efbe5e0d134fdba6322fd8f145aca7");
		params = getSignParams2(params);
		String baseInfo = HttpClientUtil.sendPost4Map(
				"http://test-api.bakejinfu.com/application/dualnote/list",
				params);
		System.out.println("双录列表：" + baseInfo);
	}

	@Test
	public void saveSurveyPhotos() {
		Map<String, String> params = new HashMap<String, String>();
		Map<String, File> files = new HashMap<String, File>();
		files.put("1", new File("C:\\Users\\admin\\Desktop\\test.png"));
		params.put("userId", "f4361b8446cd43ed9d27a14a512d1fab");
		params.put("applicationId", "19efbe5e0d134fdba6322fd8f145aca7");
		params = getSignParams2(params);
		String baseInfo = HttpClientUtil.sendPost4File(
				"http://test-api.bakejinfu.com/application/save_survey_photos",
				files, params);
		System.out.println("保存调查照片：" + baseInfo);
	}

	@Test
	public void getSimpleConfig() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", "f4361b8446cd43ed9d27a14a512d1fab");
		params = getSignParams2(params);
		String param = "?userId=f4361b8446cd43ed9d27a14a512d1fab&t="
				+ params.get("t") + "&apiSign=" + params.get("apiSign");
		String baseInfo = HttpClientUtil
				.sendGet("http://test-api.bakejinfu.com/other/base_conf"
						+ param);
		System.out.println("获取简单配置id：" + baseInfo);
	}

	@Test
	public void getSimpleMoudle() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", "f4361b8446cd43ed9d27a14a512d1fab");
		params.put("applicationId", "19efbe5e0d134fdba6322fd8f145aca7");
		params.put("moduleId", "22222222-2222-2222-2222-222222222222");
		params = getSignParams2(params);
		String param = "?userId=f4361b8446cd43ed9d27a14a512d1fab&t="
				+ params.get("t") + "&apiSign=" + params.get("apiSign")
				+ "&applicationId=19efbe5e0d134fdba6322fd8f145aca7"
				+ "&moduleId=22222222-2222-2222-2222-222222222222";
		String baseInfo = HttpClientUtil
				.sendGet("http://test-api.bakejinfu.com/application/simple_module"
						+ param);
		System.out.println("简单逻辑的模块容器查询：" + baseInfo);
	}

	@Test
	public void saveRecognizor() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", "f4361b8446cd43ed9d27a14a512d1fab");
		params.put("applicationId", "e8aa689b6fc34241937c0dbe2cdce480");
		params.put("data", "{\"surety_memo\":\"担保人\"}");
		params.put("relationType", "朋友");
		params.put("originalId", "daa9e12a16c34ae59c98cb21da9950aa");
		params.put("id", "3c3e92d9124843628f4cc2c4f540dddc");
		params = getSignParams2(params);
		String baseInfo = HttpClientUtil.sendPost4Map(
				"http://test-api.bakejinfu.com/application/save_recognizor",
				params);
		System.out.println("设置担保人：" + baseInfo);
	}

	@Test
	public void surveyPhotoList() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("applicationId", "e8aa689b6fc34241937c0dbe2cdce480");
		params.put("userId", "f4361b8446cd43ed9d27a14a512d1fab");
		params = getSignParams2(params);
		String param = "?userId=f4361b8446cd43ed9d27a14a512d1fab&t="
				+ params.get("t") + "&apiSign=" + params.get("apiSign")
				+ "&applicationId=e8aa689b6fc34241937c0dbe2cdce480";
		String baseInfo = HttpClientUtil
				.sendGet("http://test-api.bakejinfu.com/application/survey_photo_list"
						+ param);
		System.out.println("调查照片列表：" + baseInfo);
	}

	@Test
	public void deleteRecognizor() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", "f4361b8446cd43ed9d27a14a512d1fab");
		params.put("id", "3c3e92d9124843628f4cc2c4f540dddc");
		params = getSignParams2(params);
		String baseInfo = HttpClientUtil.sendPost4Map(
				"http://test-api.bakejinfu.com/application/del_recognizor",
				params);
		System.out.println("删除担保人：" + baseInfo);
	}

	@Test
	public void recognizorList() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("applicationId", "e8aa689b6fc34241937c0dbe2cdce480");
		params.put("userId", "f4361b8446cd43ed9d27a14a512d1fab");
		params = getSignParams2(params);
		String param = "?userId=f4361b8446cd43ed9d27a14a512d1fab&t="
				+ params.get("t") + "&apiSign=" + params.get("apiSign")
				+ "&applicationId=e8aa689b6fc34241937c0dbe2cdce480";
		String baseInfo = HttpClientUtil
				.sendGet("http://test-api.bakejinfu.com/application/recognizor_list"
						+ param);
		System.out.println("担保人列表：" + baseInfo);
	}

	@Test
	public void building_mortgage_list() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("applicationId", "e8aa689b6fc34241937c0dbe2cdce480");
		params.put("userId", "f4361b8446cd43ed9d27a14a512d1fab");
		params = getSignParams2(params);
		String param = "?userId=f4361b8446cd43ed9d27a14a512d1fab&t="
				+ params.get("t") + "&apiSign=" + params.get("apiSign")
				+ "&applicationId=e8aa689b6fc34241937c0dbe2cdce480";
		String baseInfo = HttpClientUtil
				.sendGet("http://test-api.bakejinfu.com/application/building_mortgage_list"
						+ param);
		System.out.println("房产列表：" + baseInfo);
	}

	@Test
	public void saveBuildingMortgage() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", "f4361b8446cd43ed9d27a14a512d1fab");
		params.put("applicationId", "e8aa689b6fc34241937c0dbe2cdce480");
		params.put("data", "{}");
		params.put("originalId", "02e8d05669f2412a83e784e8ee627ce5");
		params = getSignParams2(params);
		String baseInfo = HttpClientUtil
				.sendPost4Map(
						"http://test-api.bakejinfu.com/application/save_building_mortgage",
						params);
		System.out.println("保存抵押房产：" + baseInfo);
	}

	@Test
	public void all_building_list_application() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("applicationId", "e8aa689b6fc34241937c0dbe2cdce480");
		params.put("userId", "f4361b8446cd43ed9d27a14a512d1fab");
		params = getSignParams2(params);
		String param = "?userId=f4361b8446cd43ed9d27a14a512d1fab&t="
				+ params.get("t") + "&apiSign=" + params.get("apiSign")
				+ "&applicationId=e8aa689b6fc34241937c0dbe2cdce480";
		String baseInfo = HttpClientUtil
				.sendGet("http://test-api.bakejinfu.com/application/all_building_list_application"
						+ param);
		System.out.println("当前贷款可以作为抵押的房产列表：" + baseInfo);
	}

	private String getSignParams(Map<String, String> params) {
		StringBuffer source = new StringBuffer();
		if (params == null || params.isEmpty()) {
			params = new HashMap<String, String>();
		}
		List<String> invalidValue = new ArrayList<>();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			if (entry.getValue().isEmpty()) {
				invalidValue.add(entry.getKey());
			}
		}
		for (String key : invalidValue) {
			params.remove(key);
		}
		params.put("t", System.currentTimeMillis() + "");
		params.put("apiKey", MD5_KEY);
		Set<String> keys = params.keySet();
		TreeSet<String> set = new TreeSet<>(keys);
		Iterator<String> it = set.iterator();
		String key = it.next();
		source.append(key);
		source.append("=");
		source.append(params.get(key));
		while (it.hasNext()) {
			source.append(",");
			key = it.next();
			source.append(key);
			source.append("=");
			source.append(params.get(key));
		}
		String md5 = MD5Util.getMD5(source.toString());

		return md5;
	}

	private Map<String, String> getSignParams2(Map<String, String> params) {
		StringBuffer source = new StringBuffer();
		if (params == null || params.isEmpty()) {
			params = new HashMap<String, String>();
		}
		List<String> invalidValue = new ArrayList<>();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			if (entry.getValue().isEmpty()) {
				invalidValue.add(entry.getKey());
			}
		}
		for (String key : invalidValue) {
			params.remove(key);
		}
		params.put("t", System.currentTimeMillis() + "");
		params.put("apiKey", MD5_KEY_TH);
		Set<String> keys = params.keySet();
		TreeSet<String> set = new TreeSet<>(keys);
		Iterator<String> it = set.iterator();
		String key = it.next();
		source.append(key);
		source.append("=");
		source.append(params.get(key));
		while (it.hasNext()) {
			source.append(",");
			key = it.next();
			source.append(key);
			source.append("=");
			source.append(params.get(key));
		}
		String md5 = MD5Util.getMD5(source.toString());
		params.put("apiSign", md5);
		params.remove("apiKey");
		params.put("apiSign", md5);
		params.remove("apiKey");
		return params;
	}

}
