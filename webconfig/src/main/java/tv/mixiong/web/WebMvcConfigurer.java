package tv.mixiong.web;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import tv.mixiong.web.log.LoggerFilter;

import javax.servlet.Filter;
import java.nio.charset.Charset;
import java.util.List;

/**
 * spring mvc统一处理
 *
 */
@Configuration
@Slf4j
public class WebMvcConfigurer extends WebMvcConfigurationSupport {
    @Bean()
    public Filter CharacterEncodingFilter() {
        return new CharacterEncodingFilter("utf-8", true);
    }

    @Bean()
    public Filter logFilter() {
        return new LoggerFilter();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }

    //使用阿里 FastJson 作为JSON MessageConverter
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(
                SerializerFeature.QuoteFieldNames, SerializerFeature.SortField,
                SerializerFeature.DisableCircularReferenceDetect);
        converter.setFastJsonConfig(config);
        converter.setDefaultCharset(Charset.forName("UTF-8"));
        converter.setSupportedMediaTypes(Lists.newArrayList(MediaType.APPLICATION_JSON));
        converters.add(converter);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("OPTIONS", "GET", "POST","DELETE")
                .allowedHeaders("uid","token","channel_id","sver","plat","ppinfo","passport","pid","DNT","X-CustomHeader","Keep-Alive","User-Agent","X-Requested-With","If-Modified-Since","Cache-Control","Content-Type")
                .allowCredentials(true).maxAge(3600);
    }

}
