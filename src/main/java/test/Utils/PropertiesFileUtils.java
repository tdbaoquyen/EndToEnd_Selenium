package test.Utils;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertiesFileUtils {
	private static String CONFIG_PATH = "./configuration/configs.properties";
	
	public static String getProperty(String key) {
		String value = null;
		FileInputStream inputFile = null;
		Properties property = new Properties();
		
	//	bắt ngoại lệ nếu xảy ra lỗi hoặc không tìm thấy đường dẫn
		try {
			inputFile = new FileInputStream(CONFIG_PATH);
			property.load(inputFile);
			value = property.getProperty(key);
			return value;
		}
		catch (Exception ex) {
			System.out.println("Error while getting Properties value of:" + key);
			ex.printStackTrace();
		}
		finally {
			if (inputFile != null) {
				try {
					inputFile.close();
				}
				catch(Exception ex) {
					System.out.println("FileInput was closed");
					ex.printStackTrace();
				}
			}
		}
		return value;
	}

}
