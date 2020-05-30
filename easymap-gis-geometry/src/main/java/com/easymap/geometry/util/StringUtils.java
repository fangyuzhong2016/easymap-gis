package com.easymap.geometry.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    /**
     * 判断是否是空字符串
     *
     * @param value
     * @return
     */
    public static boolean isSpace(String value) {
        if ((value == null) || ("".equals(value))) {
            return true;
        }
        if (value.trim().length() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否是空字符串
     *
     * @param value
     * @return
     */
    public static boolean isNotSpace(String value) {
        return !isSpace(value);
    }

    /**
     * 判断字符串是否有长度
     *
     * @param value
     * @return
     */
    public static boolean hasLength(String value) {
        if ((null == value) || ("".equals(value.trim()))) {
            return false;
        }
        return true;
    }

    /**
     * @param obj
     * @return
     */
    public static int objectToInt(Object obj) {
        int result = 0;
        if ((null == obj) || ("".equals(obj))) {
            return 0;
        }
        try {
            result = Integer.parseInt(obj.toString());
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * @param strBool
     * @return
     */
    public static boolean getBool(String strBool) {
        if (isSpace(strBool)) {
            return false;
        }
        strBool = strBool.trim().toUpperCase();
        if ((strBool.equalsIgnoreCase("1")) || (strBool.equalsIgnoreCase("TRUE")) || (strBool.equalsIgnoreCase("是")) || (strBool.equalsIgnoreCase("YES"))) {
            return true;
        }
        return false;
    }

    /**
     * @param object
     * @return
     */
    public static boolean getBool(Object object) {
        if (object == null) return false;
        return getBool(object.toString());
    }

    /**
     * @param value
     * @return
     */
    public static boolean isEmpty(String value) {
        if ((value == null) || ("".equals(value))) {
            return true;
        }
        return false;
    }

    /**
     * @param value
     * @return
     */
    public static boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }

    /**
     * @param value
     * @return
     */
    public static boolean isEmpty(Object value) {
        if ((value == null) || ("".equals(value.toString()))) {
            return true;
        }
        return false;
    }

    /**
     * @param value
     * @return
     */
    public static boolean isNotEmpty(Object value) {
        return !isEmpty(value);
    }

    public static String padLeft(String str, int padLength, char padLeftStr) {
        if (str.length() < padLength) {
            int padLengthIng = padLength - str.length();
            for (int i = 0; i < padLengthIng; i++) {
                str = padLeftStr + str;
            }
        }
        return str;
    }

    /**
     * @param str
     * @param padLength
     * @param padLeftStr
     * @return
     */
    public static String padRight(String str, int padLength, char padLeftStr) {
        if (str.length() < padLength) {
            int padLengthIng = padLength - str.length();
            for (int i = 0; i < padLengthIng; i++) {
                str = str + padLeftStr;
            }
        }
        return str;
    }


    /**
     * @param url
     * @return
     */
    public static String handelUrl(String url) {
        if (url == null) {
            return null;
        }
        url = url.trim();
        if ((url.equals("")) || (url.startsWith("http://")) || (url.startsWith("https://"))) {
            return url;
        }
        return "http://" + url.trim();
    }

    /**
     * @param str
     * @param sep
     * @param sep2
     * @return
     */
    public static String[] splitAndTrim(String str, String sep, String sep2) {
        if (org.apache.commons.lang3.StringUtils.isBlank(str)) {
            return null;
        }
        if (!org.apache.commons.lang3.StringUtils.isBlank(sep2)) {
            str = org.apache.commons.lang3.StringUtils.replace(str, sep2, sep);
        }
        String[] arr = org.apache.commons.lang3.StringUtils.split(str, sep);

        int i = 0;
        for (int len = arr.length; i < len; i++) {
            arr[i] = arr[i].trim();
        }
        return arr;
    }

    /**
     * @param txt
     * @return
     */
    public static String txt2htm(String txt) {
        if (org.apache.commons.lang3.StringUtils.isBlank(txt)) {
            return txt;
        }
        StringBuilder sb = new StringBuilder((int) (txt.length() * 1.2D));

        boolean doub = false;
        for (int i = 0; i < txt.length(); i++) {
            char c = txt.charAt(i);
            if (c == ' ') {
                if (doub) {
                    sb.append(' ');
                    doub = false;
                } else {
                    sb.append("&nbsp;");
                    doub = true;
                }
            } else {
                doub = false;
                switch (c) {
                    case '&':
                        sb.append("&amp;");
                        break;
                    case '<':
                        sb.append("&lt;");
                        break;
                    case '>':
                        sb.append("&gt;");
                        break;
                    case '"':
                        sb.append("&quot;");
                        break;
                    case '\n':
                        sb.append("<br/>");
                        break;
                    default:
                        sb.append(c);
                }
            }
        }
        return sb.toString();
    }

    /**
     * @param s
     * @param len
     * @param append
     * @return
     */
    public static String textCut(String s, int len, String append) {
        if (s == null) {
            return null;
        }
        int slen = s.length();
        if (slen <= len) {
            return s;
        }
        int maxCount = len * 2;
        int count = 0;
        for (int i = 0; (count < maxCount) && (i < slen); i++) {
            if (s.codePointAt(i) < 256) {
                count++;
            } else {
                count += 2;
            }
        }
        return s;
    }


    /**
     * @param str
     * @param search
     * @return
     */
    public static boolean contains(String str, String search) {
        if ((org.apache.commons.lang3.StringUtils.isBlank(str)) || (org.apache.commons.lang3.StringUtils.isBlank(search))) {
            return false;
        }
        String reg = org.apache.commons.lang3.StringUtils.replace(search, "*", ".*");
        Pattern p = Pattern.compile(reg);
        return p.matcher(str).matches();
    }

    /**
     * @param str
     * @return
     */
    public static boolean containsKeyString(String str) {
        if (org.apache.commons.lang3.StringUtils.isBlank(str)) {
            return false;
        }
        if ((str.contains("'")) || (str.contains("\"")) || (str.contains("\r")) || (str.contains("\n")) || (str.contains("\t")) || (str.contains("\b")) || (str.contains("\f"))) {
            return true;
        }
        return false;
    }

    /**
     * @param str
     * @return
     */
    public static String replaceKeyString(String str) {
        if (containsKeyString(str)) {
            return str.replace("'", "\\'").replace("\"", "\\\"").replace("\r", "\\r").replace("\n", "\\n").replace("\t", "\\t").replace("\b", "\\b").replace("\f", "\\f");
        }
        return str;
    }

    /**
     * @param str
     * @return
     */
    public static String getSuffix(String str) {
        int splitIndex = str.lastIndexOf(".");
        return str.substring(splitIndex + 1);
    }

    /**
     * @param value
     * @return
     */
    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
        }
        return false;
    }

    /**
     * @param str
     * @return
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    public static boolean isBlank(final String... strs) {
        if (strs == null || strs.length < 1) {
            return true;
        }
        for (final String str : strs) {
            if (isEmpty(str)) {
                return true;
            }
        }
        return false;
    }

    public static String camelCase(final String underline) {
        final StringBuilder camelCaseProperty = new StringBuilder();
        final String[] underlineProperty = underline.split("_");
        for (int i = 0; i < underlineProperty.length; ++i) {
            if (i > 0) {
                final Character initial = underlineProperty[i].charAt(0);
                final String upperInitial = initial.toString().toUpperCase();
                final String initialProperty = underlineProperty[i].replaceFirst(initial.toString(), upperInitial);
                camelCaseProperty.append(initialProperty);
            } else {
                camelCaseProperty.append(underlineProperty[i]);
            }
        }
        return camelCaseProperty.toString();
    }

    public static String camelLowerCase(final String word) {
        final StringBuilder camelCaseProperty = new StringBuilder();
        final Character initial = word.charAt(0);
        final String upperInitial = initial.toString().toLowerCase();
        final String initialProperty = word.replaceFirst(initial.toString(), upperInitial);
        camelCaseProperty.append(initialProperty);
        return camelCaseProperty.toString();
    }

    /**
     * 提取字符串小括号里面的内容
     *
     * @param contents 待提取的数据
     * @return 提取的内容
     */
    public static List<String> extractContentsOfParentheses(String contents) {
        List<String> list = new ArrayList<String>();
        Pattern p = Pattern.compile("(?<=\\()[^\\)]+");
        Matcher m = p.matcher(contents);
        while (m.find()) {
            list.add(m.group());
        }
        return list;
    }
}