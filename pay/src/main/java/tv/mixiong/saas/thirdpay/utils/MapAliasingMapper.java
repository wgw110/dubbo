package tv.mixiong.saas.thirdpay.utils;

import com.thoughtworks.xstream.mapper.ClassAliasingMapper;
import com.thoughtworks.xstream.mapper.Mapper;

import java.util.Map;

/**
 * wechat支付，xml根节点名为xml
 * */
public class MapAliasingMapper extends ClassAliasingMapper {

	public MapAliasingMapper(Mapper wrapped) {
		super(wrapped);
	}

	@Override
	public Class<?> realClass(String elementName) {
		try {
			if (elementName.equalsIgnoreCase("xml"))
				return Map.class;
		} catch (Exception e) {
			// do nothing we fall back on super's implementation
		}
		return super.realClass(elementName);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public String serializedClass( Class type) {
		try {
			if (type == Map.class || Map.class.isAssignableFrom(type))
				return "xml";
		} catch (Exception e) {
		}
		return super.serializedClass(type);
	}
}
