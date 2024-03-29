package lab.keystore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

public class KeyStoreTest {

	public static void main(String[] args) {
		String keystoreType = "JKS";
		char[] password = "changeit".toCharArray();
		String filepath = System.getenv("JAVA_HOME") + "\\jre\\lib\\security\\cacerts";
		String providerName = "";
		// 获取JVM默认证书库的证书信息
		KeyStoreTest.getKeyStoreInfo(filepath, keystoreType, password, providerName);

		// 获取PkCS12格式的证书信息，即.pfx文件
		// keystoreType = "pkcs12";
		// password = "111111".toCharArray();
		// filepath = "D:\\test.pfx";
		// providerName = new Provider().getName();
		// KeyStoreTest.getKeyStoreInfo(filepath, keystoreType, password,
		// providerName);

		// 获取PkCS12格式的证书信息，即.pfx文件
//		keystoreType = "jks";
//		password = "111111".toCharArray();
//		filepath = "D:\\12306.jks";
		// providerName = new Provider().getName();
		//KeyStoreTest.getKeyStoreInfo(filepath, keystoreType, password, providerName);

	}

	public static void getKeyStoreInfo(String cacertsFilePath, String keystoreType, char[] password, String providerName) {
		try {
			KeyStore ks;
			if (KeyStore.getDefaultType().equals(keystoreType.toLowerCase())
					&& (providerName == null || providerName.equals(""))) {
				ks = KeyStore.getInstance(KeyStore.getDefaultType());
			} else {
				ks = KeyStore.getInstance(keystoreType, providerName);
			}
			InputStream stream = new FileInputStream(new File(cacertsFilePath));
			ks.load(stream, password);
			Enumeration<String> aliases = ks.aliases();
			while (aliases.hasMoreElements()) {
				String alias = aliases.nextElement();
				if (!alias.equals("12306")) {
					continue;
				}
				
				System.out.println("alias:" + alias);
				if (ks.isCertificateEntry(alias)) {
					System.out.println("certificateEntry alias:" + alias);
					System.out.println("certificateEntry cert:" + (X509Certificate) ks.getCertificate(alias));

				}

				if (ks.isKeyEntry(alias)) {
					System.out.println("keyEntry alias:" + alias);
					System.out.println("keyEntry key:" + ks.getKey(alias, password));
				}
			}

		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
