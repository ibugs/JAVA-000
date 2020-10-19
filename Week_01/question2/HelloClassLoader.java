package question2;

import java.io.*;
import java.lang.reflect.Method;
import java.nio.file.Path;

/**
 * @author wanghao
 * 2020-10-18 22:03
 */
public class HelloClassLoader extends ClassLoader {
	public static void main(String[] args) {
		try {
			Class<?> cls = new HelloClassLoader().findClass("Hello");
			Method method = cls.getDeclaredMethod("hello", null);
			method.invoke(cls.newInstance(), null);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		byte[] bytes;
		try {
			bytes = findBytesArray();
			return defineClass(name, findBytesArray(), 0, bytes.length);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ClassNotFoundException();
		}
	}

	public byte[] findBytesArray() {
		String filePath = HelloClassLoader.class.getResource("/Hello.xlass").getPath();
		File file = new File(filePath);
		Long filelength = file.length();
		byte[] srcFileContent = new byte[filelength.intValue()];
		try {
			FileInputStream in = new FileInputStream(file);
			in.read(srcFileContent);
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] targetFileContent = new byte[srcFileContent.length];
		for (int i = 0; i < srcFileContent.length; i++) {
			targetFileContent[i] = (byte) (255 - srcFileContent[i]);
		}
		return targetFileContent;
	}
}
