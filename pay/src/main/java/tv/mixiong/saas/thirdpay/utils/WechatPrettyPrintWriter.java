package tv.mixiong.saas.thirdpay.utils;

import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.naming.NameCoder;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;

import java.io.Writer;

public class WechatPrettyPrintWriter extends PrettyPrintWriter {

    public WechatPrettyPrintWriter(Writer writer) {
        super(writer);
    }

    public WechatPrettyPrintWriter(Writer writer, NameCoder nameCoder) {
        super(writer, nameCoder);
    }

    @Override
    public void writeText(QuickWriter writer, String text) {
        writer.write(text);
    }

}
