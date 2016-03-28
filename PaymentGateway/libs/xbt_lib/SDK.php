<?php

/**
 * Created by Mixiu.
 * Date: 2015/12/2
 * Time: 13:25
 */
include_once("config/OpenSdkConfig.php");
include_once("base/OpenSdkBase.php");
include_once("exception/OpenSdkException.php");
//include_once("log/Log.php");

class SDK extends OpenSdkBase
{

    public function __construct($appId, $keySecret, $sourceNo)
    {
        parent::__construct($appId, $keySecret, $sourceNo);
    }

    private function array_merge($mainParams = array(), $extParams = array()) {
        $mainParams['accessToken'] = $this->getAccessToken($mainParams);
        return array_merge($mainParams, $extParams);
    }
    /**
     * widget_page 统一请求方法
     * @param array $params
     * @return mixed
     */
    protected function _widgetPage($params = array())
    {

        return $this->post($this->wigetPageUrl(), $params);
    }


    /**
     * 获取帮卡页面URL
     * @param array $mainParams
     * @param array $extParams
     * @return mixed
     */
    public function bindCar($mainParams = array(), $extParams = array())
    {
        $params = $this->array_merge($mainParams, $extParams);
        return $this->_widgetPage($params);
    }

    /**
     *获取我的资产
     * @param array $mainParams 系统参数
     * @param array $extParams  业务参数
     * @return mixed
     */
    public function myAssets($mainParams = array(), $extParams = array())
    {
        $params = $this->array_merge($mainParams, $extParams);
        return $this->_widgetPage($params);

    }


    /**
     * 获取收银台页面URL
     * @param array $mainParams
     * @param array $extParams
     * @return mixed
     */
    public function cashDesk($mainParams = array(), $extParams = array())
    {

        $params = $this->array_merge($mainParams, $extParams);
        return $this->post($this->cashDeskUrl(), $params);
    }


    /**
     * 创建会员
     * @param array $mainParams
     * @param array $extParams
     * @return mixed
     */
    public function createUser($mainParams = array(), $extParams = array())
    {

        $params = $this->array_merge($mainParams, $extParams);

        return $this->post($this->createUserUrl(), $params);

    }

    /**
     * 更新会员
     * @param array $mainParams
     * @param array $extParams
     * @return mixed
     * @throws OpenSdkException
     */
    public function updateUser($mainParams = array(), $extParams = array())
    {
        $params = $this->array_merge($mainParams, $extParams);
        return $this->post($this->updateUserUrl(), $params);
    }


    /**
     * 商家二级结算--普通结算
     * @param array $mainParams
     * @param array $extParams
     * @return mixed
     */
    public function childMerchantCommonSettle($mainParams = array(), $extParams = array())
    {

        $params = $this->array_merge($mainParams, $extParams);

        return $this->post($this->childMerchantCommonSettleUrl(), $params);
    }

    /**
     * 商家二级结算--订单结算
     * @param array $mainParams
     * @param array $extParams
     * @return mixed
     */
    public function childMerchantOrderSettle($mainParams = array(), $extParams = array())
    {

        $params = $this->array_merge($mainParams, $extParams);
        return $this->post($this->childMerchantOrderSettleUrl(), $params);
    }
}

