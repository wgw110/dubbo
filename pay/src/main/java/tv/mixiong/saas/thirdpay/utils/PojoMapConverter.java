package tv.mixiong.saas.thirdpay.utils;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 仅处理一层xml
 * */
public class PojoMapConverter implements Converter {

	public PojoMapConverter() {
		super();
	}

	@SuppressWarnings("rawtypes")
	public boolean canConvert(Class clazz) {
		String classname = clazz.getName();
		if (classname.indexOf("Map") >= 0)
			return true;
		else
			return false;
	}

	public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {

		map2xml(value, writer, context);
	}

	@SuppressWarnings("unchecked")
	protected void map2xml(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
		Map<String, Object> map;
		String key;
		Object subvalue;
		map = (Map<String, Object>) value;
		for (Iterator<Entry<String, Object>> iterator = map.entrySet().iterator(); iterator.hasNext();) {
			Entry<String, Object> entry =  iterator.next();
			key =  entry.getKey();
			subvalue = entry.getValue();
			writer.startNode(key);
			if(subvalue == null){

			}else if (Map.class.isAssignableFrom(subvalue.getClass())) {
				map2xml(subvalue, writer, context);
			} else if (String.class.isAssignableFrom(subvalue.getClass())) {
				writer.setValue(PREFIX_CDATA + String.valueOf(subvalue) + SUFFIX_CDATA);
			} else {
				writer.setValue(String.valueOf(subvalue));
			}
			writer.endNode();
		}

	}

	private static String PREFIX_CDATA = "<![CDATA[";
	private static String SUFFIX_CDATA = "]]>";

	@SuppressWarnings("unchecked")
	public Map<String, Object> unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		Map<String, Object> map = (Map<String, Object>) populateMap(reader, context);
		return map;
	}

	protected Object populateMap(HierarchicalStreamReader reader, UnmarshallingContext context) {
		Map<String, Object> map = new HashMap<String, Object>();
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			String key = reader.getNodeName();
			Object value = null;
			if (reader.hasMoreChildren()) {
				value = populateMap(reader, context);
			} else {
				value = reader.getValue();
			}
			map.put(key, value);
			reader.moveUp();
		}
		return map;
	}

}
