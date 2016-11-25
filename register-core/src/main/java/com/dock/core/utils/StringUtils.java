package com.dock.core.utils;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;
import java.util.UUID;

public class StringUtils {
    private static final Logger logger = LoggerFactory.getLogger(StringUtils.class);
    public static final String EMPTY = "";
    public static final int DEFAULT_CODE_LENGTH = 6;
    private static final String FOLDER_SEPARATOR = "/";
    private static final String WINDOWS_FOLDER_SEPARATOR = "\\";
    private static final String TOP_PATH = "..";
    private static final String CURRENT_PATH = ".";
    private static final char EXTENSION_SEPARATOR = '.';

    public StringUtils() {
    }

    public static boolean isNull(String str) {
        return str == null;
    }

    public static boolean isNotNull(String str) {
        return !isNull(str);
    }

    public static String nullToEmpty(String str) {
        return isNull(str)?"":str;
    }

    public static boolean isEmpty(String value) {
        int strLen;
        if(value != null && (strLen = value.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if(!Character.isWhitespace(value.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isTrimEmpty(String str) {
        return isNull(str) || str.trim().isEmpty();
    }

    public static boolean isNotTrimEmpty(String str) {
        return !isTrimEmpty(str);
    }

    public static String msNull(String str) {
        return trim(str);
    }

    public static String msNull(String str, String def) {
        return isNotEmpty(str)?str:def;
    }

    public static String trim(String str) {
        return isTrimEmpty(str)?"":str;
    }

    public static boolean isNumeric(Object obj) {
        if(obj == null) {
            return false;
        } else {
            char[] chars = obj.toString().toCharArray();
            int length = chars.length;
            if(length < 1) {
                return false;
            } else {
                int i = 0;
                if(length > 1 && chars[0] == 45) {
                    i = 1;
                }

                while(i < length) {
                    if(!Character.isDigit(chars[i])) {
                        return false;
                    }

                    ++i;
                }

                return true;
            }
        }
    }

    public static boolean areNotEmpty(String... values) {
        boolean result = true;
        if(values != null && values.length != 0) {
            String[] var2 = values;
            int var3 = values.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                String value = var2[var4];
                result &= !isEmpty(value);
            }
        } else {
            result = false;
        }

        return result;
    }

    public static String unicodeToChinese(String unicode) {
        StringBuilder out = new StringBuilder();
        if(!isEmpty(unicode)) {
            for(int i = 0; i < unicode.length(); ++i) {
                out.append(unicode.charAt(i));
            }
        }

        return out.toString();
    }

    public static String stripNonValidXMLCharacters(String input) {
        if(input != null && !"".equals(input)) {
            StringBuilder out = new StringBuilder();

            for(int i = 0; i < input.length(); ++i) {
                char current = input.charAt(i);
                if(current == 9 || current == 10 || current == 13 || current >= 32 && current <= '\ud7ff' || current >= '\ue000' && current <= '�' || current >= 65536 && current <= 1114111) {
                    out.append(current);
                }
            }

            return out.toString();
        } else {
            return "";
        }
    }

    public static String toObjectString(Object object) {
        return ToStringBuilder.reflectionToString(object);
    }

    public static boolean equals(String s1, String s2) {
        return s1 == null?s2 == null:s1.equals(s2);
    }

    public static boolean equalsIgnoreCase(String s1, String s2) {
        return s1 == null?s2 == null:s1.equalsIgnoreCase(s2);
    }

    public static String getRandomCode() {
        return getRandomCode(6);
    }

    public static int getRandomInt(int length) {
        return NumberUtils.toInteger(getRandomString(length, "0123456789"));
    }

    public static String getRandomCode(int length) {
        return getRandomString(length, "0123456789");
    }

    public static String getRandomString() {
        return getRandomString(6);
    }

    public static String getRandomString(int length) {
        return getRandomString(length, "abcdefghijklmnopqrstuvwxyz0123456789");
    }

    public static String getRandomString(int length, String base) {
        if(length < 6) {
            length = 6;
        }

        Random random = new Random(System.currentTimeMillis());
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < length; ++i) {
            sb.append(base.charAt(random.nextInt(base.length())));
        }

        return sb.toString().replaceAll("^0", "8");
    }

    public static String readUrl(String key) {
        Properties properties = new Properties();
        String path = System.getProperty("webapp.root");

        try {
            properties.load(new FileInputStream(path + "/WEB-INF/classes/properties/urlPath.properties"));
        } catch (Exception var4) {
            return null;
        }

        String value = properties.getProperty(key);
        return isEmpty(value)?null:value;
    }

    public static int strLength(String s, String charsetName) {
        if(isEmpty(s)) {
            return 0;
        } else {
            try {
                return s.getBytes(charsetName).length;
            } catch (UnsupportedEncodingException var3) {
                return s.getBytes().length;
            }
        }
    }

    public static String substring(String s, int length, String charsetName) {
        try {
            byte[] bytes = nullToEmpty(s).getBytes(charsetName);
            if(bytes.length <= length) {
                return s;
            } else if(length < 1) {
                return "";
            } else {
                int len = s.length();

                for(int i = 0; i < len; ++i) {
                    int iDestLength = strLength(s.substring(0, i + 1), charsetName);
                    if(iDestLength > length) {
                        if(i == 0) {
                            return "";
                        }

                        return s.substring(0, i);
                    }
                }

                return s;
            }
        } catch (UnsupportedEncodingException var7) {
            return "";
        }
    }

    public static String substring(String s, int length, String dot, String charsetName) {
        byte[] bytes = nullToEmpty(s).getBytes();
        if(bytes.length <= length) {
            return s;
        } else {
            int len = length - dot.length();
            if(len < 1) {
                len = 1;
            }

            return substring(s, len, charsetName) + dot;
        }
    }

    public static String md5(String s) {
        byte[] digests = null;
        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(s.getBytes());
            digests = messageDigest.digest();
        } catch (Exception var6) {
            logger.error(var6.getMessage(), var6);
        }

        StringBuffer buf = new StringBuffer();

        for(int offset = 0; offset < digests.length; ++offset) {
            int digest = digests[offset];
            if(digest < 0) {
                digest += 256;
            }

            if(digest < 16) {
                buf.append("0");
            }

            buf.append(Integer.toHexString(digest));
        }

        return buf.toString();
    }

    public static boolean hasChineseCharset(String s) {
        if(s != null) {
            for(int i = 0; i < s.length(); ++i) {
                if(s.codePointAt(i) >= 256) {
                    return true;
                }
            }
        }

        return false;
    }

    public static String getUUID() {
        return UUID.fromString(UUID.nameUUIDFromBytes(UUID.randomUUID().toString().getBytes()).toString()).toString().replaceAll("-", "");
    }

    public static String b2q(String str) {
        str = nullToEmpty(str);
        if(str.indexOf("\"") != -1 || str.indexOf("\'") != -1) {
            int isq = 0;
            int idq = 0;

            for(int i = 0; i < str.length(); ++i) {
                if(str.charAt(i) == 39) {
                    ++isq;
                    if(isq % 2 == 0) {
                        str = str.replaceFirst("\'", "’");
                    } else {
                        str = str.replaceFirst("\'", "‘");
                    }
                } else if(str.charAt(i) == 34) {
                    ++idq;
                    if(idq % 2 == 0) {
                        str = str.replaceFirst("\"", "”");
                    } else {
                        str = str.replaceFirst("\"", "“");
                    }
                }
            }
        }

        return str;
    }

    public static boolean isEmpty(Object str) {
        return str == null || "".equals(str);
    }

    public static boolean hasLength(CharSequence str) {
        return str != null && str.length() > 0;
    }

    public static boolean hasLength(String str) {
        return hasLength((CharSequence)str);
    }

    public static boolean hasText(CharSequence str) {
        if(!hasLength(str)) {
            return false;
        } else {
            int strLen = str.length();

            for(int i = 0; i < strLen; ++i) {
                if(!Character.isWhitespace(str.charAt(i))) {
                    return true;
                }
            }

            return false;
        }
    }

    public static boolean hasText(String str) {
        return hasText((CharSequence)str);
    }

    public static boolean containsWhitespace(CharSequence str) {
        if(!hasLength(str)) {
            return false;
        } else {
            int strLen = str.length();

            for(int i = 0; i < strLen; ++i) {
                if(Character.isWhitespace(str.charAt(i))) {
                    return true;
                }
            }

            return false;
        }
    }

    public static boolean containsWhitespace(String str) {
        return containsWhitespace((CharSequence)str);
    }

    public static String trimWhitespace(String str) {
        if(!hasLength(str)) {
            return str;
        } else {
            StringBuilder sb = new StringBuilder(str);

            while(sb.length() > 0 && Character.isWhitespace(sb.charAt(0))) {
                sb.deleteCharAt(0);
            }

            while(sb.length() > 0 && Character.isWhitespace(sb.charAt(sb.length() - 1))) {
                sb.deleteCharAt(sb.length() - 1);
            }

            return sb.toString();
        }
    }

    public static String trimAllWhitespace(String str) {
        if(!hasLength(str)) {
            return str;
        } else {
            int len = str.length();
            StringBuilder sb = new StringBuilder(str.length());

            for(int i = 0; i < len; ++i) {
                char c = str.charAt(i);
                if(!Character.isWhitespace(c)) {
                    sb.append(c);
                }
            }

            return sb.toString();
        }
    }

    public static String trimLeadingWhitespace(String str) {
        if(!hasLength(str)) {
            return str;
        } else {
            StringBuilder sb = new StringBuilder(str);

            while(sb.length() > 0 && Character.isWhitespace(sb.charAt(0))) {
                sb.deleteCharAt(0);
            }

            return sb.toString();
        }
    }

    public static String trimTrailingWhitespace(String str) {
        if(!hasLength(str)) {
            return str;
        } else {
            StringBuilder sb = new StringBuilder(str);

            while(sb.length() > 0 && Character.isWhitespace(sb.charAt(sb.length() - 1))) {
                sb.deleteCharAt(sb.length() - 1);
            }

            return sb.toString();
        }
    }

    public static String trimLeadingCharacter(String str, char leadingCharacter) {
        if(!hasLength(str)) {
            return str;
        } else {
            StringBuilder sb = new StringBuilder(str);

            while(sb.length() > 0 && sb.charAt(0) == leadingCharacter) {
                sb.deleteCharAt(0);
            }

            return sb.toString();
        }
    }

    public static String trimTrailingCharacter(String str, char trailingCharacter) {
        if(!hasLength(str)) {
            return str;
        } else {
            StringBuilder sb = new StringBuilder(str);

            while(sb.length() > 0 && sb.charAt(sb.length() - 1) == trailingCharacter) {
                sb.deleteCharAt(sb.length() - 1);
            }

            return sb.toString();
        }
    }

    public static boolean startsWithIgnoreCase(String str, String prefix) {
        if(str != null && prefix != null) {
            if(str.startsWith(prefix)) {
                return true;
            } else if(str.length() < prefix.length()) {
                return false;
            } else {
                String lcStr = str.substring(0, prefix.length()).toLowerCase();
                String lcPrefix = prefix.toLowerCase();
                return lcStr.equals(lcPrefix);
            }
        } else {
            return false;
        }
    }

    public static boolean endsWithIgnoreCase(String str, String suffix) {
        if(str != null && suffix != null) {
            if(str.endsWith(suffix)) {
                return true;
            } else if(str.length() < suffix.length()) {
                return false;
            } else {
                String lcStr = str.substring(str.length() - suffix.length()).toLowerCase();
                String lcSuffix = suffix.toLowerCase();
                return lcStr.equals(lcSuffix);
            }
        } else {
            return false;
        }
    }

    public static boolean substringMatch(CharSequence str, int index, CharSequence substring) {
        for(int j = 0; j < substring.length(); ++j) {
            int i = index + j;
            if(i >= str.length() || str.charAt(i) != substring.charAt(j)) {
                return false;
            }
        }

        return true;
    }

    public static int countOccurrencesOf(String str, String sub) {
        if(str != null && sub != null && str.length() != 0 && sub.length() != 0) {
            int count = 0;

            int idx;
            for(int pos = 0; (idx = str.indexOf(sub, pos)) != -1; pos = idx + sub.length()) {
                ++count;
            }

            return count;
        } else {
            return 0;
        }
    }

    public static String replace(String inString, String oldPattern, String newPattern) {
        if(hasLength(inString) && hasLength(oldPattern) && newPattern != null) {
            StringBuilder sb = new StringBuilder();
            int pos = 0;
            int index = inString.indexOf(oldPattern);

            for(int patLen = oldPattern.length(); index >= 0; index = inString.indexOf(oldPattern, pos)) {
                sb.append(inString.substring(pos, index));
                sb.append(newPattern);
                pos = index + patLen;
            }

            sb.append(inString.substring(pos));
            return sb.toString();
        } else {
            return inString;
        }
    }

    public static String delete(String inString, String pattern) {
        return replace(inString, pattern, "");
    }

    public static String deleteAny(String inString, String charsToDelete) {
        if(hasLength(inString) && hasLength(charsToDelete)) {
            StringBuilder sb = new StringBuilder();

            for(int i = 0; i < inString.length(); ++i) {
                char c = inString.charAt(i);
                if(charsToDelete.indexOf(c) == -1) {
                    sb.append(c);
                }
            }

            return sb.toString();
        } else {
            return inString;
        }
    }

    public static String quote(String str) {
        return str != null?"\'" + str + "\'":null;
    }

    public static Object quoteIfString(Object obj) {
        return obj instanceof String?quote((String)obj):obj;
    }

    public static String unqualify(String qualifiedName) {
        return unqualify(qualifiedName, '.');
    }

    public static String unqualify(String qualifiedName, char separator) {
        return qualifiedName.substring(qualifiedName.lastIndexOf(separator) + 1);
    }

    public static String capitalize(String str) {
        return changeFirstCharacterCase(str, true);
    }

    public static String uncapitalize(String str) {
        return changeFirstCharacterCase(str, false);
    }

    private static String changeFirstCharacterCase(String str, boolean capitalize) {
        if(str != null && str.length() != 0) {
            StringBuilder sb = new StringBuilder(str.length());
            if(capitalize) {
                sb.append(Character.toUpperCase(str.charAt(0)));
            } else {
                sb.append(Character.toLowerCase(str.charAt(0)));
            }

            sb.append(str.substring(1));
            return sb.toString();
        } else {
            return str;
        }
    }

    public static String getFilename(String path) {
        if(path == null) {
            return null;
        } else {
            int separatorIndex = path.lastIndexOf("/");
            return separatorIndex != -1?path.substring(separatorIndex + 1):path;
        }
    }

    public static String getFilenameExtension(String path) {
        if(path == null) {
            return null;
        } else {
            int extIndex = path.lastIndexOf(46);
            if(extIndex == -1) {
                return null;
            } else {
                int folderIndex = path.lastIndexOf("/");
                return folderIndex > extIndex?null:path.substring(extIndex + 1);
            }
        }
    }

    public static String stripFilenameExtension(String path) {
        if(path == null) {
            return null;
        } else {
            int extIndex = path.lastIndexOf(46);
            if(extIndex == -1) {
                return path;
            } else {
                int folderIndex = path.lastIndexOf("/");
                return folderIndex > extIndex?path:path.substring(0, extIndex);
            }
        }
    }

    public static String applyRelativePath(String path, String relativePath) {
        int separatorIndex = path.lastIndexOf("/");
        if(separatorIndex != -1) {
            String newPath = path.substring(0, separatorIndex);
            if(!relativePath.startsWith("/")) {
                newPath = newPath + "/";
            }

            return newPath + relativePath;
        } else {
            return relativePath;
        }
    }

//    public static String cleanPath(String path) {
//        if(path == null) {
//            return null;
//        } else {
//            String pathToUse = replace(path, "\\", "/");
//            int prefixIndex = pathToUse.indexOf(":");
//            String prefix = "";
//            if(prefixIndex != -1) {
//                prefix = pathToUse.substring(0, prefixIndex + 1);
//                if(prefix.contains("/")) {
//                    prefix = "";
//                } else {
//                    pathToUse = pathToUse.substring(prefixIndex + 1);
//                }
//            }
//
//            if(pathToUse.startsWith("/")) {
//                prefix = prefix + "/";
//                pathToUse = pathToUse.substring(1);
//            }
//
//            String[] pathArray = delimitedListToStringArray(pathToUse, "/");
//            LinkedList pathElements = new LinkedList();
//            int tops = 0;
//
//            int i;
//            for(i = pathArray.length - 1; i >= 0; --i) {
//                String element = pathArray[i];
//                if(!".".equals(element)) {
//                    if("..".equals(element)) {
//                        ++tops;
//                    } else if(tops > 0) {
//                        --tops;
//                    } else {
//                        pathElements.add(0, element);
//                    }
//                }
//            }
//
//            for(i = 0; i < tops; ++i) {
//                pathElements.add(0, "..");
//            }
//
//            return prefix + collectionToDelimitedString(pathElements, "/");
//        }
//    }

//    public static boolean pathEquals(String path1, String path2) {
//        return cleanPath(path1).equals(cleanPath(path2));
//    }

//    public static Locale parseLocaleString(String localeString) {
//        String[] parts = tokenizeToStringArray(localeString, "_ ", false, false);
//        String language = parts.length > 0?parts[0]:"";
//        String country = parts.length > 1?parts[1]:"";
//        validateLocalePart(language);
//        validateLocalePart(country);
//        String variant = "";
//        if(parts.length > 2) {
//            int endIndexOfCountryCode = localeString.indexOf(country, language.length()) + country.length();
//            variant = trimLeadingWhitespace(localeString.substring(endIndexOfCountryCode));
//            if(variant.startsWith("_")) {
//                variant = trimLeadingCharacter(variant, '_');
//            }
//        }
//
//        return language.length() > 0?new Locale(language, country, variant):null;
//    }

    private static void validateLocalePart(String localePart) {
        for(int i = 0; i < localePart.length(); ++i) {
            char ch = localePart.charAt(i);
            if(ch != 95 && ch != 32 && !Character.isLetterOrDigit(ch)) {
                throw new IllegalArgumentException("Locale part \"" + localePart + "\" contains invalid characters");
            }
        }

    }

    public static String toLanguageTag(Locale locale) {
        return locale.getLanguage() + (hasText(locale.getCountry())?"-" + locale.getCountry():"");
    }

    public static TimeZone parseTimeZoneString(String timeZoneString) {
        TimeZone timeZone = TimeZone.getTimeZone(timeZoneString);
        if("GMT".equals(timeZone.getID()) && !timeZoneString.startsWith("GMT")) {
            throw new IllegalArgumentException("Invalid time zone specification \'" + timeZoneString + "\'");
        } else {
            return timeZone;
        }
    }

//    public static String[] addStringToArray(String[] array, String str) {
//        if(ObjectUtils.isEmpty(array)) {
//            return new String[]{str};
//        } else {
//            String[] newArr = new String[array.length + 1];
//            System.arraycopy(array, 0, newArr, 0, array.length);
//            newArr[array.length] = str;
//            return newArr;
//        }
//    }

//    public static String[] concatenateStringArrays(String[] array1, String[] array2) {
//        if(ObjectUtils.isEmpty(array1)) {
//            return array2;
//        } else if(ObjectUtils.isEmpty(array2)) {
//            return array1;
//        } else {
//            String[] newArr = new String[array1.length + array2.length];
//            System.arraycopy(array1, 0, newArr, 0, array1.length);
//            System.arraycopy(array2, 0, newArr, array1.length, array2.length);
//            return newArr;
//        }
//    }
//
//    public static String[] mergeStringArrays(String[] array1, String[] array2) {
//        if(ObjectUtils.isEmpty(array1)) {
//            return array2;
//        } else if(ObjectUtils.isEmpty(array2)) {
//            return array1;
//        } else {
//            ArrayList result = new ArrayList();
//            result.addAll(Arrays.asList(array1));
//            String[] var3 = array2;
//            int var4 = array2.length;
//
//            for(int var5 = 0; var5 < var4; ++var5) {
//                String str = var3[var5];
//                if(!result.contains(str)) {
//                    result.add(str);
//                }
//            }
//
//            return toStringArray((Collection)result);
//        }
//    }
//
//    public static String[] sortStringArray(String[] array) {
//        if(ObjectUtils.isEmpty(array)) {
//            return new String[0];
//        } else {
//            Arrays.sort(array);
//            return array;
//        }
//    }

    public static String[] toStringArray(Collection<String> collection) {
        return collection == null?null:(String[])collection.toArray(new String[collection.size()]);
    }

    public static String[] toStringArray(Enumeration<String> enumeration) {
        if(enumeration == null) {
            return null;
        } else {
            ArrayList list = Collections.list(enumeration);
            return (String[])list.toArray(new String[list.size()]);
        }
    }

//    public static String[] trimArrayElements(String[] array) {
//        if(ObjectUtils.isEmpty(array)) {
//            return new String[0];
//        } else {
//            String[] result = new String[array.length];
//
//            for(int i = 0; i < array.length; ++i) {
//                String element = array[i];
//                result[i] = element != null?element.trim():null;
//            }
//
//            return result;
//        }
//    }
//
//    public static String[] removeDuplicateStrings(String[] array) {
//        if(ObjectUtils.isEmpty(array)) {
//            return array;
//        } else {
//            LinkedHashSet set = new LinkedHashSet();
//            String[] var2 = array;
//            int var3 = array.length;
//
//            for(int var4 = 0; var4 < var3; ++var4) {
//                String element = var2[var4];
//                set.add(element);
//            }
//
//            return toStringArray((Collection)set);
//        }
//    }

    public static String[] split(String toSplit, String delimiter) {
        if(hasLength(toSplit) && hasLength(delimiter)) {
            int offset = toSplit.indexOf(delimiter);
            if(offset < 0) {
                return null;
            } else {
                String beforeDelimiter = toSplit.substring(0, offset);
                String afterDelimiter = toSplit.substring(offset + delimiter.length());
                return new String[]{beforeDelimiter, afterDelimiter};
            }
        } else {
            return null;
        }
    }

//    public static Properties splitArrayElementsIntoProperties(String[] array, String delimiter) {
//        return splitArrayElementsIntoProperties(array, delimiter, (String)null);
//    }

//    public static Properties splitArrayElementsIntoProperties(String[] array, String delimiter, String charsToDelete) {
//        if(ObjectUtils.isEmpty(array)) {
//            return null;
//        } else {
//            Properties result = new Properties();
//            String[] var4 = array;
//            int var5 = array.length;
//
//            for(int var6 = 0; var6 < var5; ++var6) {
//                String element = var4[var6];
//                if(charsToDelete != null) {
//                    element = deleteAny(element, charsToDelete);
//                }
//
//                String[] splittedElement = split(element, delimiter);
//                if(splittedElement != null) {
//                    result.setProperty(splittedElement[0].trim(), splittedElement[1].trim());
//                }
//            }
//
//            return result;
//        }
//    }
//
//    public static String[] tokenizeToStringArray(String str, String delimiters) {
//        return tokenizeToStringArray(str, delimiters, true, true);
//    }
//
//    public static String[] tokenizeToStringArray(String str, String delimiters, boolean trimTokens, boolean ignoreEmptyTokens) {
//        if(str == null) {
//            return null;
//        } else {
//            StringTokenizer st = new StringTokenizer(str, delimiters);
//            ArrayList tokens = new ArrayList();
//
//            while(true) {
//                String token;
//                do {
//                    if(!st.hasMoreTokens()) {
//                        return toStringArray((Collection)tokens);
//                    }
//
//                    token = st.nextToken();
//                    if(trimTokens) {
//                        token = token.trim();
//                    }
//                } while(ignoreEmptyTokens && token.length() <= 0);
//
//                tokens.add(token);
//            }
//        }
//    }

    public static String[] delimitedListToStringArray(String str, String delimiter) {
        return delimitedListToStringArray(str, delimiter, (String)null);
    }

    public static String[] delimitedListToStringArray(String str, String delimiter, String charsToDelete) {
        if(str == null) {
            return new String[0];
        } else if(delimiter == null) {
            return new String[]{str};
        } else {
            ArrayList result = new ArrayList();
            int pos;
            if("".equals(delimiter)) {
                for(pos = 0; pos < str.length(); ++pos) {
                    result.add(deleteAny(str.substring(pos, pos + 1), charsToDelete));
                }
            } else {
                int delPos;
                for(pos = 0; (delPos = str.indexOf(delimiter, pos)) != -1; pos = delPos + delimiter.length()) {
                    result.add(deleteAny(str.substring(pos, delPos), charsToDelete));
                }

                if(str.length() > 0 && pos <= str.length()) {
                    result.add(deleteAny(str.substring(pos), charsToDelete));
                }
            }

            return toStringArray((Collection)result);
        }
    }

    public static String[] commaDelimitedListToStringArray(String str) {
        return delimitedListToStringArray(str, ",");
    }

    public static Set<String> commaDelimitedListToSet(String str) {
        LinkedHashSet set = new LinkedHashSet();
        String[] tokens = commaDelimitedListToStringArray(str);
        String[] var3 = tokens;
        int var4 = tokens.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            String token = var3[var5];
            set.add(token);
        }

        return set;
    }

//    public static String collectionToDelimitedString(Collection<?> coll, String delim, String prefix, String suffix) {
//        if(CollectionUtils.isEmpty(coll)) {
//            return "";
//        } else {
//            StringBuilder sb = new StringBuilder();
//            Iterator it = coll.iterator();
//
//            while(it.hasNext()) {
//                sb.append(prefix).append(it.next()).append(suffix);
//                if(it.hasNext()) {
//                    sb.append(delim);
//                }
//            }
//
//            return sb.toString();
//        }
//    }

//    public static String collectionToDelimitedString(Collection<?> coll, String delim) {
//        return collectionToDelimitedString(coll, delim, "", "");
//    }

//    public static String collectionToCommaDelimitedString(Collection<?> coll) {
//        return collectionToDelimitedString(coll, ",");
//    }
//
//    public static String arrayToDelimitedString(Object[] arr, String delim) {
//        if(ObjectUtils.isEmpty(arr)) {
//            return "";
//        } else if(arr.length == 1) {
//            return ObjectUtils.nullSafeToString(arr[0]);
//        } else {
//            StringBuilder sb = new StringBuilder();
//
//            for(int i = 0; i < arr.length; ++i) {
//                if(i > 0) {
//                    sb.append(delim);
//                }
//
//                sb.append(arr[i]);
//            }
//
//            return sb.toString();
//        }
//    }

//    public static String arrayToCommaDelimitedString(Object[] arr) {
//        return arrayToDelimitedString(arr, ",");
////    }
}
