package mobi.mixiong.util;

import com.google.common.collect.ImmutableList;
import com.google.common.net.InetAddresses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.*;
import java.util.Collections;
import java.util.List;

public class IpUtils {

    // this is recorded locally
    private static String localhost;
    private static long localIp;

    // statically initialize localhost.
    static {
        if (localhost == null) {
            InetAddress internalIp = findPublicIp();
            localhost = InetAddresses.toAddrString(internalIp);
            localIp = InetAddresses.coerceToInteger(internalIp);
        }
    }

    // this is really to avoid throwing an exception in the constructor.
    public static String localhost() {
        return localhost;
    }

    public static long localIpLong(){
        return localIp;
    }

    private static InetAddress findPublicIp() {
        // Check if local host address is a good v4 address
        InetAddress localAddress = null;
        try {
            localAddress = InetAddress.getLocalHost();
            if (isV4Address(localAddress)
                    && getGoodAddresses().contains(localAddress)) {
                return localAddress;
            }
        } catch (UnknownHostException ignored) {
        }
        if (localAddress == null) {
            try {
                localAddress = InetAddress.getByAddress(new byte[]{127, 0, 0,
                        1});
            } catch (UnknownHostException e) {
                throw new AssertionError("Could not get local ip address");
            }
        }

        // check all up network interfaces for a good v4 address
        for (InetAddress address : getGoodAddresses()) {
            if (isV4Address(address)) {
                return address;
            }
        }

        // check all up network interfaces for a good v6 address
        for (InetAddress address : getGoodAddresses()) {
            if (isV6Address(address)) {
                return address;
            }
        }

        // just return the local host address
        // it is most likely that this is a disconnected developer machine
        return localAddress;
    }

    private static List<InetAddress> getGoodAddresses() {
        ImmutableList.Builder<InetAddress> list = ImmutableList.builder();
        for (NetworkInterface networkInterface : getGoodNetworkInterfaces()) {
            for (InetAddress address : Collections.list(networkInterface
                    .getInetAddresses())) {
                if (isGoodAddress(address)) {
                    list.add(address);
                }
            }
        }
        return list.build();
    }

    private static List<NetworkInterface> getGoodNetworkInterfaces() {
        ImmutableList.Builder<NetworkInterface> builder = ImmutableList
                .builder();
        try {
            for (NetworkInterface networkInterface : Collections
                    .list(NetworkInterface.getNetworkInterfaces())) {
                try {
                    if (!networkInterface.isLoopback()
                            && networkInterface.isUp()) {
                        builder.add(networkInterface);
                    }
                } catch (Exception ignored) {
                }
            }
        } catch (SocketException ignored) {
        }
        return builder.build();
    }

    private static boolean isV4Address(InetAddress address) {
        return address instanceof Inet4Address;
    }

    private static boolean isV6Address(InetAddress address) {
        return address instanceof Inet6Address;
    }

    private static boolean isGoodAddress(InetAddress address) {
        return !address.isAnyLocalAddress() && !address.isLoopbackAddress()
                && !address.isMulticastAddress();
    }
}
