package com.codergeezer.contenthub.utils;

/**
 * @author haidv
 * @version 1.0
 */
public class Constant {

    /*-----------------------------------------------GRANT CONSTANT--------------------------------------------------*/
    public static final String PASSWORD_GRANT = "password_grant";

    public static final String REFRESH_TOKEN_GRANT = "refresh_token";

    /*-----------------------------------------------TOKEN CONSTANT--------------------------------------------------*/
    public static final String ACCESS_TOKEN = "access_token";

    public static final String REFRESH_TOKEN = "refresh_token";

    /*----------------------------------------------DICTIONARY CONSTANT----------------------------------------------*/
    public static final String TIER_DEFAULT_DICTIONARY_GROUP_CODE = "TIER_DEFAULT";

    public static final String SERVICE_FEE_DICTIONARY_GROUP_CODE = "SERVICE_FEE";

    /*---------------------------------------------TRANSACTION CONSTANT----------------------------------------------*/
    // transaction status
    public static final Short SUCCESS = 1;

    public static final Short FAILED = 2;

    public static final Short ROLLBACK = 3;

    // transaction type
    public static final Integer UPGRADE_TYPE = 1;

    public static final Integer RECEIVE_UPGRADE_TYPE = 2;

    public static final Integer DONATE_TYPE = 3;

    public static final Integer RECEIVE_DONATE_TYPE = 4;

    public static final Integer UNLOCK_POST_TYPE = 5;

    public static final Integer RECEIVE_UNLOCK_POST_TYPE = 6;

    /*------------------------------------------------PREFIX CONSTANT------------------------------------------------*/


    /*--------------------------------------------CONTACT TYPE CONSTANT----------------------------------------------*/
    public static final Integer PHONE_CONTACT = 1;

    public static final Integer EMAIL_CONTACT = 2;

    /*-------------------------------------------------ROLES CONSTANT------------------------------------------------*/
    public static final String NORMAL_USER = "NORMAL_USER";

    public static final String GROUP_ADMIN = "GROUP_ADMIN";

    public static final String SYSTEM_ADMIN = "SYSTEM_ADMIN";


    private Constant() {
        throw new IllegalStateException("Utility class");
    }
}
