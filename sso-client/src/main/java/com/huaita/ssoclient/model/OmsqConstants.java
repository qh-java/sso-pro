/*
 * Copyright (C), 2002-2015, 苏宁易购电子商务有限公司
 * FileName: OmsqNumberEnum.java
 * Author:   14050236
 * Date:     2015-5-26 上午11:21:24
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.huaita.ssoclient.model;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author 14050236
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class OmsqConstants {
    public static final String IDX_BEAN_LIST = "idxBeanList";

    public static final String OPERATETYPE_EXECUTESQL = "EXECUTE";

    public static final String ONE = "1";

    private OmsqConstants() {
    }

    public static enum OmsqNumberEnum {
        ZORE(0),ONE(1), TWO(2), TEN(10),THREE(3),EIGHT(8),FOUR(4),FIVE(5),SEVEN(7),SIX(6),ELEVEN(11),ZREO(0),NINE(9),EIGHTEEN(18),
        // 订单头的长度
        TWELVE(12),

        STATUS_15(15),STATUS_18(18), STATUS_20(20), STATUS_30(30), STATUS_35(35), STATUS_40(40),
        STATUS_50(50), STATUS_58(58), STATUS_60(60), STATUS_70(70), STATUS_75(75), STATUS_80(80),
        // 订单行的长度
        FOURTEEN(14), TWENTY(20), TWENTY_TWO(22), FIVE_HUNDRED(500), THOUSAND(1000), FIVE_THOUSAND(5000), MAXCONNPERROUTE(
                128), MAXTOTALCONN(384), HUNDRED_EIGHTY(180);



        private int value;

        private OmsqNumberEnum(int num) {
            this.value = num;
        }

        public int toValue() {
            return value;
        }

        public String toValueStr() {
            return String.valueOf(value);
        }
    }

    public static enum OmsqNumberEnumStr {

        ZREO("00"), ONE("01"), TWO("02"), THREE("03"), FOUR("04"), FIVE("05"),
        SIX("06"), SEVEN("07"), EIGHT("08"), NINE("09"), TEN("10");

        private String value;

        private OmsqNumberEnumStr(String num) {
            this.value = num;
        }

        public String toValue() {
            return value;
        }
    }
    public static final String YES = "Y";

    public static final String NO = "N";
    /**
     * 多活版本  新增创建方
     */
    public static final String CREATED_BY = "createdBy";
    /**
     * 多活版本  新增更新方
     */
    public static final String LAST_UPD_BY = "lastUpdBy";

    /**
     * 多活版本  新增更新时间
     */
    public static final String LAST_UPD_TIME = "lastUpdTime";

    /**
     * 多活版本  新增创建时间
     */
    public static final String CREATED_TIME = "createdTime";

    public static final String BILL_TYPE = "billType";

    public static final String STATUS_TYPE = "statusType";

    public static final String STATUS_CODE = "statusCode";

    public static final String ACTIVE_FLAG = "activeFlag";

    public static final String ORDER_ID = "orderId";

    public static final String ORDER_IDS = "orderIds";

    public static final String OMS_ORDER_ID = "omsOrderId";

    /**
     * 分表标识字段
     */
    public static final String CUST_NUM = "custNum";

    public static final String ORDER_ITEM_ID = "orderItemId";

    public static final String ORDER_ITEM_IDS = "orderItemIds";

    public static final String RECEIVER_PHONE = "receiverPhone";
    /*
     * 数据源拆分不同数据源所用常量
     */
    public static final String OMS_CATEGORY_ID = "omsCategoryID";
    public static final String DS_BUS = "bus";
    public static final String DS_IDX = "idx";
    public static final String DS_PUB = "pub";
    public static final String MEM_IN_CARD_NO = "memInCardNo";

    public static final String A = "A";
    public static final String B = "B";
    public static final String C = "C";
    public static final String D = "D";
    public static final String E = "E";
    public static final String G = "G";
    public static final String H = "H";
    public static final String I = "I";
    public static final String J = "J";

    /**
     * 同步失败，增量临时表新增一条记录
     */
    public static final int ADD_NEW = 0;

    /**
     * 同步失败，增量临时表失败次数加1
     */
    public static final int FAILED_SYN = -1;

    public static final String TIMEMONTHSTART = "0101";

    public static final String TIMESTART = "000000";

    public static final String TIMEEND = "235959";

    public static final String TIMESTART2 = " 00:00:00";

    public static final String TIMEEND2 = " 23:59:59";

    public static final int YEAR_2015 = 2015;

    public static enum PlatformOrderNoEnum {

        ORDERID("0"), ORDERITEMID("1"), NEITHER("2");

        private String value;

        private PlatformOrderNoEnum(String num) {
            this.value = num;
        }

        public String toValue() {
            return value;
        }
    }

    public static final String ZZ = "00";
    public static final String DATA_NODE = "dataNode";
    public static final String TABLE_NUM = "tableNum";

    //日志标识
    public static final String GOMSO = "GOMSO";

    public static final String OMSD = "OMSD";

    public static final String OMSQ = "OMSQ";
    /**
     * 表操作
     */
    public static final String DATA_TYPE_TABLE = "0";
    /**
     * redis操作
     */
    public static final String DATA_TYPE_REDIS = "1";
    /**
     * 数据增加
     */
    public static final String  ALTER_TYPE_ADD = "0";
    /**
     * 数据删除
     */
    public static final String  ALTER_TYPE_DEL = "1";
    /**
     * 数据修改
     */
    public static final String  ALTER_TYPE_UPDATE = "2";
    /**
     * 数据导出
     */
    public static final String  ALTER_TYPE_REPORT = "3";
    /**
     *数据查询
     */
    public static final String  ALTER_TYPE_SEL = "4";

    //SCM列表配置功能相关常量
    /**
     * SCM列表配置文件,列表展示文件名皆在此文件中配置，文件名以英文逗号“,”号间隔，且必须以英文逗号结尾
     */
    public static String SCM_MENU_FILE = "SCMConfig.properties";
    /**
     * 新增状态
     */
    public static String SCM_STATUS_ADD = "add";
    /**
     *修改编辑状态
     */
    public static String SCM_STATUS_EDIT = "edit";

    public static final String QEOO02 = "Q_E0002";
    public static final String QEOO01 = "Q_E0001";
    public static final String QEOO03 = "Q_E0003";
    public static final String QEOO04 = "Q_E0004";
    public static final String QEOO05 = "Q_E0005";
    public static final String QEOO06 = "Q_E0006";
    public static final String QE0010 = "Q_E0010";

    // 输入、输出标识
    public static final String INPUT = "input";
    public static final String OUTPUT = "output";
    public static final String DIST_CHANNEL_50 = "50";


    /** 物流状态编码 */
    public static final String DELI_OP_TYPE_TMS_ACCEPT = "TMS_ACCEPT";


    public static final String MOBILE_PHONE_NUM = "mobilePhoneNum";

}
