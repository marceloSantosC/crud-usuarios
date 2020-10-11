package test;

import java.io.File;
import java.io.IOException;

public class Test {
	public static void main(String[] args) throws IOException {
		File file = new File("db.properties");
		boolean result = file.createNewFile();
		System.out.println(result);
	}
}
