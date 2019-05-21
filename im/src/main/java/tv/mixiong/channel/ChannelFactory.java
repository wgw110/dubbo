package tv.mixiong.channel;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;
import tv.mixixiong.im.enums.ChannelEnum;

import java.util.List;
import java.util.Map;


/**
 * channel
 * *
 */
@Component
public class ChannelFactory {

    private Map<ChannelEnum, BaseChannel> channelMap = Maps.newHashMap();

    public BaseChannel getChannel(ChannelEnum channel) {
        return channelMap.get(channel);
    }

    public void put(ChannelEnum channelEnum, BaseChannel baseChannel) {
        channelMap.put(channelEnum, baseChannel);
    }

    public List<BaseChannel> getChannels(int channelBit) {
        List<ChannelEnum> channels = ChannelEnum.valueOf(channelBit);
        List<BaseChannel> list = Lists.newArrayList();
        for (ChannelEnum channel : channels) {
            list.add(getChannel(channel));
        }
        return list;
    }

    private boolean needChannel(int channelBit, ChannelEnum channelEnum) {
        return (channelBit & channelEnum.getBit()) > 0;
    }
}
