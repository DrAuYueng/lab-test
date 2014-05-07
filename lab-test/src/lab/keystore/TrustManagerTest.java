package lab.keystore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import org.apache.http.conn.ssl.TrustStrategy;

public class TrustManagerTest {

	public static void main(String[] args) {
		KeyStore truststore;
		// String filepath = System.getenv("JAVA_HOME") +
		// "\\jre\\lib\\security\\cacerts";
		// String passwd="changeit";
		String filepath = "d:\\12306.jks";
		String passwd = "111111";
		try {
			truststore = KeyStore.getInstance(KeyStore.getDefaultType());
			truststore.load(new FileInputStream(new File(filepath)), passwd.toCharArray());
			TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			tmf.init(truststore);
			TrustManager[] tm = tmf.getTrustManagers();

			for (TrustManager tmpTm : tm) {
				System.out.println(tmpTm);
				if (tmpTm instanceof X509TrustManager) {
					X509TrustManager x509tm = (X509TrustManager) tmpTm;
					// System.out.println(x509tm.);
				}
			}
			// sun.security.ssl.X509TrustManagerImp

		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

class TrustManagerDelegate implements X509TrustManager {
	private final X509TrustManager trustManager;
	private final TrustStrategy trustStrategy;

	TrustManagerDelegate(final X509TrustManager trustManager, final TrustStrategy trustStrategy) {
		super();
		this.trustManager = trustManager;
		this.trustStrategy = trustStrategy;
	}

	public void checkClientTrusted(final X509Certificate[] chain, final String authType) throws CertificateException {
		this.trustManager.checkClientTrusted(chain, authType);
	}

	public void checkServerTrusted(final X509Certificate[] chain, final String authType) throws CertificateException {
		if (!this.trustStrategy.isTrusted(chain, authType)) {
			this.trustManager.checkServerTrusted(chain, authType);
		}
	}

	public X509Certificate[] getAcceptedIssuers() {
		return this.trustManager.getAcceptedIssuers();
	}

}

