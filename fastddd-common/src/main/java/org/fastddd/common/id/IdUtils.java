package org.fastddd.common.id;

/**
 * @author: frank.li
 * @date: 2021-05-28
 */
public class IdUtils {

    private static volatile IdWorker idWorker;

    /**
     * generate Id using snowflake algorithm
     * @return Id
     */
    public static long generateId() {
        if (idWorker == null) {
            synchronized (IdUtils.class) {
                if (idWorker == null) {
                    init(null);
                }
            }
        }
        return idWorker.nextId();
    }


    /**
     * init IdWorker
     * @param serverNode the server node id, consider as machine id in snowflake
     */
    public static void init(Long serverNode) {
        idWorker = new IdWorker(serverNode);
    }

}
