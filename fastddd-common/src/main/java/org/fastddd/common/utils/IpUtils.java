package org.fastddd.common.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.regex.Pattern;

/**
 * @author: frank.li
 * @date: 2021-05-28
 */
public class IpUtils {

    private static final String LOCALHOST = "127.0.0.1";

    private static final String ANY_HOST = "0.0.0.0";

    private static volatile InetAddress LOCAL_ADDRESS = null;

    private static final Pattern IP_PATTERN = Pattern.compile("\\d{1,3}(\\.\\d{1,3}){3,5}$");

    /**
     * Gets local ip.
     *
     * @return the local ip
     */
    public static String getLocalIp() {
        InetAddress address = getLocalAddress();
        return address == null ? LOCALHOST : address.getHostAddress();
    }

    /**
     * Gets local address.
     *
     * @return the local address
     */
    public static InetAddress getLocalAddress() {
        if (LOCAL_ADDRESS != null) {
            return LOCAL_ADDRESS;
        }
        InetAddress localAddress = getLocalAddress0();
        LOCAL_ADDRESS = localAddress;
        return localAddress;
    }

    private static InetAddress getLocalAddress0() {
        InetAddress localAddress = null;
        try {
            localAddress = InetAddress.getLocalHost();
            if (isValidAddress(localAddress)) {
                return localAddress;
            }
        } catch (Throwable e) {
        }
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            if (interfaces != null) {
                while (interfaces.hasMoreElements()) {
                    try {
                        NetworkInterface network = interfaces.nextElement();
                        Enumeration<InetAddress> addresses = network.getInetAddresses();
                        while (addresses.hasMoreElements()) {
                            try {
                                InetAddress address = addresses.nextElement();
                                if (isValidAddress(address)) {
                                    return address;
                                }
                            } catch (Throwable e) {
                            }
                        }
                    } catch (Throwable e) {
                    }
                }
            }
        } catch (Throwable e) {
        }
        return localAddress;
    }

    /**
     * is valid address
     *
     * @param address
     * @return true if the given address is valid
     */
    private static boolean isValidAddress(InetAddress address) {
        if (address == null || address.isLoopbackAddress()) {
            return false;
        }
        return isValidIp(address.getHostAddress(), false);
    }

    /**
     * is valid IP
     *
     * @param ip
     * @param validLocalAndAny Are 127.0.0.1 and 0.0.0.0 valid IPs?
     * @return true if the given IP is valid
     */
    private static boolean isValidIp(String ip, boolean validLocalAndAny) {
        if (ip == null) {
            return false;
        }
        ip = convertIpIfNecessary(ip);
        if (validLocalAndAny) {
            return IP_PATTERN.matcher(ip).matches();
        } else {
            return !ANY_HOST.equals(ip) && !LOCALHOST.equals(ip) && IP_PATTERN.matcher(ip).matches();
        }
    }

    /**
     * convert ip if necessary
     *
     * @param ip
     * @return java.lang.String
     */
    private static String convertIpIfNecessary(String ip) {
        if (IP_PATTERN.matcher(ip).matches()) {
            return ip;
        } else {
            try {
                return InetAddress.getByName(ip).getHostAddress();
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
