package application.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class DBProperties {

	public static void setProperties(String user, String password, String host, String port, String dbname)
			throws IOException {
		FileOutputStream out = new FileOutputStream("db.properties");

		Properties props = loadProperties();
		props.setProperty("user", user);
		props.setProperty("password", password);
		props.setProperty("dburl", "jdbc:mysql://" + host + ":" + port + "/" + dbname);
		props.setProperty("useSSL", "false");
		props.setProperty("serverTimezone", "GMT-3");
		props.setProperty("host", host);
		props.setProperty("port", port);
		props.setProperty("dbname", dbname);
		props.store(out, null);
		out.close();
	}

	public static Properties loadProperties() throws IOException {
		Properties props = new Properties();
		File file = new File("db.properties");
		if (file.createNewFile()) {
			return props;
		} else {
			FileInputStream fs = new FileInputStream("db.properties");
			props.load(fs);
			return props;
		}

	}
	
	public static Properties getProperties() throws IOException{
		return DBProperties.loadProperties();
	}

	public static boolean dbPropertiesExists() {
		File file = new File("db.properties");
		if (file.exists()) {
			return true;
		} else {
			return false;
		}
	}

}
