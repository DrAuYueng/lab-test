package lab.keystore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Enumeration;

public class KeyStoreTest {

	public static void main(String[] args) {
		String password = "changeit";
		
		String filepath = System.getenv("JAVA_HOME") +"\\jre\\lib\\security\\cacerts";
		try {
			KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
			InputStream stream = new FileInputStream(new File(filepath));
			ks.load(stream, password.toCharArray());
			Enumeration<String> aliases = ks.aliases();
			while (aliases.hasMoreElements()) {
				String alias = aliases.nextElement();
				System.out.println("alias:"+alias);
				if(ks.isCertificateEntry(alias)){
					System.out.println("certificateEntry alias:"+alias);
					System.out.println("certificateEntry cert:"+ks.getCertificate(alias));
					
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
		}

	}

}
