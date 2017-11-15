package cn.test.framework.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import net.sf.json.JSONObject;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;
import com.test.framework.dataprovider.ExcelDataProvider;
import com.test.framework.report.ExtentBase;
import com.test.framework.utils.AssertUtil;
import com.test.framework.utils.HttpUtil;
import com.test.framework.utils.Log;

@Listeners({ com.test.framework.utils.AssertListener.class })
public class InterfaceTest extends ExtentBase {
	

	Log log = new Log(InterfaceTest.class);
	private JSONObject object;
	private static String ip;
	private static String port;
	private static String protocol;

	static {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(System.getProperty("user.dir")
					+ "/data/config.properties"));
			ip = prop.getProperty("ip");
			port = prop.getProperty("port");
			protocol = prop.getProperty("protocol");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	
	@Test(dataProvider = "testImageRec", dataProviderClass = ExcelDataProvider.class, enabled = true)
	public void test_image_rec(Map<String, String> param) throws Exception {
		log.info("--------图片" + param.get("image_name") + "开始测试--------");
		test = extent.startTest((this.getClass().getSimpleName() + "_"
				+ "test_image_rec" + "_" + param.get("image_name")));
		logKey = param.get("image_name");
		Map<String, String> params = new HashMap<String, String>();
		List<File> files = new ArrayList<File>();
		File file = new File(System.getProperty("user.dir") + "/photos/"
				+ logKey);
		System.out.println("图片路径===" + file.getCanonicalPath());
		files.add(file);
		String url = protocol + "://" + ip + ":" + port + param.get("url");
		String result = HttpUtil.uploadFile(url, files, params);
		object = JSONObject.fromObject(result);
		for (Map.Entry<String, String> en : param.entrySet()) {
			String key = en.getKey();
			if (object.containsKey(key)) {
				if ((object.getString(key).equals(param.get(key)))) {
					test.log(LogStatus.PASS, key + " 验证通过");
					AssertUtil.verifyEquals(1, 1);
				} else {
					test.log(LogStatus.FAIL,
							key + " 验证错误" + " 期望值: " + param.get(key)
									+ " 实际值: " + object.getString(key));
					AssertUtil.verifyFail(" 期望值: " + param.get(key) + " 实际值: "
							+ object.getString(key));
				}
			} else {
				if (key.equals("url") || key.equals("image_name")) {
					continue;
				} else {
					if (param.get(key).equals("")) {
						test.log(LogStatus.PASS, key + " 验证通过");
						AssertUtil.verifyEquals(1, 1);
					} else {
						test.log(LogStatus.FAIL, key + " 验证错误" + " 期望值: "
								+ param.get(key) + " 实际值: " + "null");
						AssertUtil.verifyFail("期望值: null");

					}
				}
			}
		}
	}

}
