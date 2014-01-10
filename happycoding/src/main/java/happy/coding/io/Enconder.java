package happy.coding.io;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;

public class Enconder
{
	
	private enum EncryptionMethod
	{
		MD5, SHA
	};
	
	@Test
	public void test()
	{
		// md5
		System.out.println("Empty String : " + md5("dailycoding"));
		System.out.println("abc : " + md5("abc"));
		System.out.println("123 : " + md5("123"));
		System.out.println("MD5 Length : " + md5("123").length());
		
		// sha
		System.out.println("Empty String : " + sha(""));
		System.out.println("abc : " + sha("abc"));
		System.out.println("123 : " + sha("123"));
		System.out.println("MD5 Length : " + sha("123").length());
		
		// null tests
		System.out.println(encrypt(null, EncryptionMethod.MD5));
		System.out.println(encrypt(null, null));
		System.out.println(encrypt("", null));
	}
	
	public static String md5(String rawText)
	{
		return encrypt(rawText, EncryptionMethod.MD5);
	}
	
	public static String sha(String rawText)
	{
		return encrypt(rawText, EncryptionMethod.SHA);
	}

	private static String encrypt(String rawText, EncryptionMethod method)
	{
		// assert rawText != null;
		assert rawText != null : "input text cannot be null";
		assert method != null : "use uncorrect method";
		
		try
		{
			MessageDigest mds = MessageDigest.getInstance(method.name());
			mds.update(rawText.getBytes("utf8"));
			byte[] sbytes = mds.digest();
			
			return hex(sbytes);
		} catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}

		assert false: "cannot return null";
		return null;

	}
	
	// return hex string of bytes array
	private static String hex(byte[] byteArray)
	{
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteArray.length; ++i)
		{
			sb.append(Integer.toHexString((byteArray[i] & 0xFF) | 0x100).substring(1, 3));
		}
		return sb.toString();
	}

}
