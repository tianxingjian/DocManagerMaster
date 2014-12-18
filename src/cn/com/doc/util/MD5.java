package cn.com.doc.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import sun.misc.BASE64Encoder;

public class MD5
{
  public static final String Md(String plainText, boolean judgeMD)
  {
    StringBuffer buf = new StringBuffer("");
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      md.update(plainText.getBytes());
      byte[] b = md.digest();

      for (int offset = 0; offset < b.length; offset++) {
        int i = b[offset];
        if (i < 0)
          i += 256;
        if (i < 16)
          buf.append("0");
        buf.append(Integer.toHexString(i));
      }
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    if (judgeMD) {
      return buf.toString();
    }
    return buf.toString().substring(8, 24);
  }

  public static final String EncoderPwdByMd5(String str)
    throws NoSuchAlgorithmException, UnsupportedEncodingException
  {
    MessageDigest md5 = MessageDigest.getInstance("MD5");
    BASE64Encoder base64en = new BASE64Encoder();

    String newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
    return newstr;
  }
}