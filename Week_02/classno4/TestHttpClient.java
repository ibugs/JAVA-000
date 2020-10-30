package Week_02.classno4;

import org.apache.http.HttpVersion;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author wanghao
 * 2020-10-28 18:00
 */
public class TestHttpClient {
	public static void main(String[] args) {
		//1. 获得一个httpclient对象
		CloseableHttpClient httpclient = HttpClients.createDefault();
		//2. 生成一个get请求，获取老师给的jar包的请求 ko -jar ./gateway-server-0.0.1-SNAPSHOT.jar
		HttpGet httpget = new HttpGet("http://127.0.0.1:8088/api/hello");
		httpget.setProtocolVersion(HttpVersion.HTTP_1_0);
		httpget.addHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_CLOSE);
		httpget.setHeader("Connection", "keep-alive");
		httpget.setHeader(HTTP.USER_AGENT, "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
		CloseableHttpResponse response = null;
		try {
			// 3.执行get请求并返回结果
			response = httpclient.execute(httpget);
			if (response != null && response.getStatusLine().getStatusCode() == 200) {
				// 4.处理结果
				String result = EntityUtils.toString(response.getEntity(), "UTF-8");
				System.out.println("最后的结果为：".concat(result));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				if (httpclient != null) {
					httpclient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
