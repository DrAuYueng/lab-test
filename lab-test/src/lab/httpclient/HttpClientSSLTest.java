package lab.httpclient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpClientSSLTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		KeyStore truststore;
		String filepath = System.getenv("JAVA_HOME") + "\\jre\\lib\\security\\cacerts";
		String passwd="changeit";
		//String filepath ="d:\\12306.jks";
		//String passwd="111111";
		try {
			truststore = KeyStore.getInstance(KeyStore.getDefaultType());
			truststore.load(new FileInputStream(new File(filepath)), passwd.toCharArray());

			SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(truststore, new MyTrustSelfSignedStrategy())
					.build();
			
//			SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(truststore)
//					.build();
			SSLConnectionSocketFactory sslcsf = new SSLConnectionSocketFactory(sslContext, new String[] { "TLSv1" },
					null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
			CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslcsf).build();

			HttpPost httppost = new HttpPost("https://ebank.95559.com.cn/corporbank/NsTrans");

			System.out.println("executing request" + httppost.getRequestLine());

			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();

				System.out.println("----------------------------------------");
				System.out.println(response.getStatusLine());
				if (entity != null) {
					System.out.println("Response content length: " + entity.getContentLength());
				}
				EntityUtils.consume(entity);
			} finally {
				response.close();
			}

		} catch (KeyStoreException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();

		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

class MyTrustSelfSignedStrategy implements TrustStrategy {

    public boolean isTrusted(
            final X509Certificate[] chain, final String authType) throws CertificateException {
    	System.out.println("chain length:"+chain.length);
    	for(X509Certificate x509Cert : chain){
    		System.out.println(x509Cert);
    	}
        return chain.length == 1;
    }

}
