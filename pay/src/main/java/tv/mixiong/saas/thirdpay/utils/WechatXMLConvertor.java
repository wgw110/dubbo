package tv.mixiong.saas.thirdpay.utils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.thoughtworks.xstream.mapper.MapperWrapper;

import java.io.Writer;
import java.util.Map;

public class WechatXMLConvertor {
    static XStream xStream;

    static {
        xStream = new XStream(new XppDriver(){
            @Override
            public HierarchicalStreamWriter createWriter(Writer out) {
                return new WechatPrettyPrintWriter(out, new NoNameCoder());
            }
        }) {
            @Override
            protected MapperWrapper wrapMapper(MapperWrapper next) {
                return new MapAliasingMapper(next);
            }
        };
        xStream.registerConverter(new PojoMapConverter());
    }

    public static Map<String, String> XML2Map(String xml) {
        return (Map<String, String>)xStream.fromXML(xml);
    }

    public static String map2XML(Map<String, ?> map) {
        String str = xStream.toXML(map);
        return str;
    }

}
