package tv.mixiong.dao;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 这里有一个大坑！！！！
 * #{args}这个前后都不能有空格，一定注意
 * 用于mybatis 集合使用，格式为(${})
 */
public class SimpleSelectInExtendedLanguageDriver extends XMLLanguageDriver implements LanguageDriver {
    private final Pattern inPattern = Pattern.compile("\\(#\\{(\\w+)\\}\\)");

    @Override
    public SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType) {
        script = StringUtils.replace(script, "<", "&lt;");
        script = StringUtils.replace(script, ">", "&gt;");

        Matcher matcher = inPattern.matcher(script);
        if (matcher.find()) {
            script = matcher.replaceAll("(<foreach collection=\"$1\" item=\"__item\" separator=\",\" >#{__item}</foreach>)");
        }

        script = "<script>" + script + "</script>";
        return super.createSqlSource(configuration, script, parameterType);
    }

}
