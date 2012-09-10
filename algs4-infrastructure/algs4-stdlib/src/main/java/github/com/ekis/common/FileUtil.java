package github.com.ekis.common;
	
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * @author Erik
 *
 */
public class FileUtil {
	// assume Unicode UTF-8 encoding
    private static String charsetName = "UTF-8";
	
	/**
	 * 
	 * 
	 * @param filePath
	 * @return
	 */
	public static Scanner fileScanner(String filePath) {
		InputStream fis;
		try {
			fis = new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("File not found.");
		}
		
		Scanner scanner = new Scanner(new BufferedInputStream(fis), charsetName);
		return scanner;
	}
	
	/**
	 * 
	 * 
	 * @param filePath
	 * @return
	 */
	public static BufferedReader lineReader(String filePath) {
		InputStream fis;
		try {
			fis = new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("File not found.");
		}
		
		BufferedReader br = new BufferedReader(new InputStreamReader(fis, Charset.forName(charsetName)));
		return br;
	}
}