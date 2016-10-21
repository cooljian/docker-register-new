package com.dock.utils;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.UUID;

public class StringUtils
{
    public static final String EMPTY = "";

    public static boolean isNull(String str)
    {
        return str == null;
    }

    public static boolean isNotNull(String str) {
        return !isNull(str);
    }

    public static String nullToEmpty(String str) {
        return isNull(str) ? "" : str;
    }

    public static boolean isEmpty(String str) {
        return (isNull(str)) || (str.isEmpty());
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isTrimEmpty(String str) {
        return (isNull(str)) || (str.trim().isEmpty());
    }

    public static boolean isNotTrimEmpty(String str) {
        return !isTrimEmpty(str);
    }

    public static String trim(String str) {
        return isTrimEmpty(str) ? "" : str;
    }

    public static String toObjectString(Object object) {
        return ToStringBuilder.reflectionToString(object);
    }

    public static boolean equals(String s1, String s2) {
        if (s1 == null) {
            return s2 == null;
        }

        return s1.equals(s2);
    }

    public static boolean equalsIgnoreCase(String s1, String s2) {
        if (s1 == null) {
            return s2 == null;
        }

        return s1.equalsIgnoreCase(s2);
    }

    public static String replace(String string, String oldString, String newString) {
        if (string == null) {
            return "";
        }

        int i = 0;

        if ((i = string.indexOf(oldString, i)) >= 0) {
            char[] string2 = string.toCharArray();
            char[] newString2 = newString.toCharArray();

            StringBuilder buf = new StringBuilder(string2.length);
            buf.append(string2, 0, i).append(newString2);

            int oldStrLength = oldString.length();
            i += oldStrLength;
            int j = i;

            while ((i = string.indexOf(oldString, i)) > 0) {
                buf.append(string2, j, i - j).append(newString2);
                i += oldStrLength;
                j = i;
            }

            return buf.append(string2, j, string2.length - j).toString();
        }

        return string;
    }

    public static String replace(String string, String oldString, String newString, int[] count) {
        if (string == null) {
            return "";
        }

        int i = 0;

        if ((i = string.indexOf(oldString, i)) >= 0) {
            char[] string2 = string.toCharArray();
            char[] newString2 = newString.toCharArray();

            StringBuilder buf = new StringBuilder(string2.length);
            buf.append(string2, 0, i).append(newString2);

            int counter = 1;
            int oldStrLength = oldString.length();
            i += oldStrLength;
            int j = i;

            while ((i = string.indexOf(oldString, i)) > 0) {
                counter++;
                buf.append(string2, j, i - j).append(newString2);
                i += oldStrLength;
                j = i;
            }

            count[0] = counter;
            buf.append(string2, j, string2.length - j);

            return buf.toString();
        }

        return string;
    }

    public static String replaceIgnoreCase(String string, String oldString, String newString) {
        if (string == null) {
            return "";
        }

        String lcString = string.toLowerCase();
        String lcOldString = oldString.toLowerCase();

        int i = 0;

        if ((i = lcString.indexOf(lcOldString, i)) >= 0) {
            char[] string2 = string.toCharArray();
            char[] newString2 = newString.toCharArray();

            StringBuilder buf = new StringBuilder(string2.length);
            buf.append(string2, 0, i).append(newString2);

            int oldStrLength = oldString.length();
            i += oldStrLength;
            int j = i;

            while ((i = lcString.indexOf(lcOldString, i)) > 0) {
                buf.append(string2, j, i - j).append(newString2);
                i += oldStrLength;
                j = i;
            }

            return buf.append(string2, j, string2.length - j).toString();
        }

        return string;
    }

    public static String replaceIgnoreCase(String string, String oldString, String newString, int[] count) {
        if (string == null) {
            return "";
        }

        String lcString = string.toLowerCase();
        String lcOldString = oldString.toLowerCase();

        int i = 0;

        if ((i = lcString.indexOf(lcOldString, i)) >= 0) {
            char[] string2 = string.toCharArray();
            char[] newString2 = newString.toCharArray();

            StringBuilder buf = new StringBuilder(string2.length);
            buf.append(string2, 0, i).append(newString2);

            int counter = 1;
            int oldStrLength = oldString.length();
            i += oldStrLength;
            int j = i;

            while ((i = lcString.indexOf(lcOldString, i)) > 0) {
                counter++;
                buf.append(string2, j, i - j).append(newString2);
                i += oldStrLength;
                j = i;
            }

            count[0] = counter;
            buf.append(string2, j, string2.length - j);

            return buf.toString();
        }

        return string;
    }

    public static int strLength(String s, String charsetName) {
        if (isEmpty(s)) {
            return 0;
        }
        try
        {
            return s.getBytes(charsetName).length;
        } catch (UnsupportedEncodingException e) {
        }
        return s.getBytes().length;
    }

    public static String substring(String s, int length, String charsetName) {
        try {
            byte[] bytes = nullToEmpty(s).getBytes(charsetName);

            if (bytes.length <= length) {
                return s;
            }

            if (length < 1) {
                return "";
            }

            int len = s.length();

            for (int i = 0; i < len; i++) {
                int iDestLength = strLength(s.substring(0, i + 1), charsetName);

                if (iDestLength > length) {
                    if (i == 0) {
                        return "";
                    }

                    return s.substring(0, i);
                }

            }

            return s;
        } catch (UnsupportedEncodingException ex) {
        }
        return "";
    }

    public static String substring(String s, int length, String dot, String charsetName) {
        byte[] bytes = nullToEmpty(s).getBytes();

        if (bytes.length <= length) {
            return s;
        }

        int len = length - dot.length();

        if (len < 1) {
            len = 1;
        }

        return new StringBuilder().append(substring(s, len, charsetName)).append(dot).toString();
    }

    public static String md5(String s) {
        byte[] digests = null;
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(s.getBytes());
            digests = messageDigest.digest();
        }
        catch (Exception e) {
        }
        StringBuffer buf = new StringBuffer();

        for (int offset = 0; offset < digests.length; offset++) {
            int digest = digests[offset];

            if (digest < 0) {
                digest += 256;
            }

            if (digest < 16) {
                buf.append("0");
            }

            buf.append(Integer.toHexString(digest));
        }

        return buf.toString();
    }

    public static boolean hasChineseCharset(String s) {
        if (s != null) {
            for (int i = 0; i < s.length(); i++) {
                if (s.codePointAt(i) >= 256) {
                    return true;
                }
            }
        }

        return false;
    }

    public static String getUUID() {
        return new StringBuilder().append(UUID.randomUUID()).append("").toString().replaceAll("-", "");
    }

    public static String b2q(String str) {
        str = nullToEmpty(str);

        if ((str.indexOf("\"") != -1) || (str.indexOf("'") != -1)) {
            int isq = 0;
            int idq = 0;

            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) == '\'') {
                    isq++;

                    if (isq % 2 == 0)
                        str = str.replaceFirst("'", "’");
                    else
                        str = str.replaceFirst("'", "‘");
                }
                else if (str.charAt(i) == '"') {
                    idq++;

                    if (idq % 2 == 0)
                        str = str.replaceFirst("\"", "”");
                    else {
                        str = str.replaceFirst("\"", "“");
                    }
                }
            }
        }

        return str;
    }
}