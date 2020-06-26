package com.dpstudio.dev.generator.bean;

import net.ymate.platform.core.IConfig;
import net.ymate.platform.core.YMP;
import net.ymate.platform.persistence.jdbc.IDatabaseModuleCfg;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: 徐建鹏.
 * @Date: 2019/11/5.
 * @Time: 12:00 下午.
 * @Description: 配置类
 */
public class ConfigInfo {

    /**
     * 数据库名称
     */
    private String dbName;

    /**
     * 数据库用户名
     */
    private String dbUserName;

    /**
     * 表前缀集合
     */
    private List<String> tablePrefixes;

    /**
     * 生成表集合
     */
    private List<String> tableList;

    /**
     * 实体类包名
     */
    private String modelPackageName;

    /**
     * vo包名
     */
    private String voPackageName;

    /**
     * dao包名
     */
    private String daoPackageName;

    /**
     * service包名
     */
    private String servicePackageName;

    /**
     * 控制器包名
     */
    private String controllerPackageName;

    /**
     * 是否下载
     */
    private Boolean downLoad;

    /**
     * 是否输出get/set 方法
     */
    private Boolean outGetSet;

    /**
     * 是否生成vo
     */
    private Boolean createVo;

    private ConfigInfo() {
        IConfig config = YMP.get().getConfig();
        this.downLoad = false;
        this.outGetSet = false;
        this.createVo = false;
        this.tableList = new ArrayList<>();
        this.dbName = config.getParam(IDatabaseModuleCfg.PARAMS_JDBC_DB_NAME);
        this.dbUserName = config.getParam(IDatabaseModuleCfg.PARAMS_JDBC_DB_USERNAME);
        this.tablePrefixes = Arrays.asList(StringUtils.split(config.getParam(IDatabaseModuleCfg.PARAMS_JDBC_TABLE_PREFIX, StringUtils.EMPTY), '|'));
    }

    public ConfigInfo tableList(List<String> tableList) {
        this.setTableList(tableList != null ? tableList : new ArrayList<>());
        return this;
    }

    public List<String> tableList() {
        return this.getTableList();
    }

    public ConfigInfo modelPackageName(String modelPackageName) {
        this.setModelPackageName(StringUtils.defaultIfBlank(modelPackageName, "packages.model"));
        return this;
    }

    public String modelPackageName() {
        return this.getModelPackageName();
    }

    public ConfigInfo voPackageName(String voPackageName) {
        this.setVoPackageName(StringUtils.defaultIfBlank(voPackageName, "packages.vo"));
        return this;
    }

    public String voPackageName() {
        return this.getVoPackageName();
    }

    public ConfigInfo daoPackageName(String daoPackageName) {
        this.setDaoPackageName(StringUtils.defaultIfBlank(daoPackageName, "packages.dao"));
        return this;
    }

    public String daoPackageName() {
        return this.getDaoPackageName();
    }

    public ConfigInfo servicePackageName(String servicePackageName) {
        this.setServicePackageName(StringUtils.defaultIfBlank(servicePackageName, "packages.service"));
        return this;
    }

    public String servicePackageName() {
        return this.getServicePackageName();
    }

    public ConfigInfo controllerPackageName(String controllerPackageName) {
        this.setControllerPackageName(StringUtils.defaultIfBlank(controllerPackageName, "packages.controller"));
        return this;
    }

    public String controllerPackageName() {
        return this.getControllerPackageName();
    }

    public ConfigInfo isDownLoad(Boolean isDownLoad) {
        this.setDownLoad(isDownLoad == null ? false : isDownLoad);
        return this;
    }

    public Boolean isDownLoad() {
        return this.getDownLoad();
    }

    public ConfigInfo isOutGetSet(Boolean isOutGetSet) {
        this.setOutGetSet(isOutGetSet == null ? false : isOutGetSet);
        return this;
    }

    public Boolean isOutGetSet() {
        return this.getOutGetSet();
    }

    public ConfigInfo isCreateVo(Boolean isCreateVo) {
        this.setCreateVo(isCreateVo == null ? false : isCreateVo);
        return this;
    }

    public Boolean isCreateVo() {
        return this.getCreateVo();
    }

    public static ConfigInfo create() {
        return new ConfigInfo();
    }


    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbUserName() {
        return dbUserName;
    }

    public void setDbUserName(String dbUserName) {
        this.dbUserName = dbUserName;
    }

    public List<String> getTablePrefixes() {
        return tablePrefixes;
    }

    public void setTablePrefixes(List<String> tablePrefixes) {
        this.tablePrefixes = tablePrefixes;
    }

    public List<String> getTableList() {
        return tableList;
    }

    public void setTableList(List<String> tableList) {
        this.tableList = tableList;
    }

    public String getModelPackageName() {
        return modelPackageName;
    }

    public void setModelPackageName(String modelPackageName) {
        this.modelPackageName = modelPackageName;
    }

    public String getDaoPackageName() {
        return daoPackageName;
    }

    public void setDaoPackageName(String daoPackageName) {
        this.daoPackageName = daoPackageName;
    }

    public String getServicePackageName() {
        return servicePackageName;
    }

    public void setServicePackageName(String servicePackageName) {
        this.servicePackageName = servicePackageName;
    }

    public String getControllerPackageName() {
        return controllerPackageName;
    }

    public void setControllerPackageName(String controllerPackageName) {
        this.controllerPackageName = controllerPackageName;
    }

    public Boolean getDownLoad() {
        return downLoad;
    }

    public void setDownLoad(Boolean downLoad) {
        this.downLoad = downLoad;
    }

    public Boolean getOutGetSet() {
        return outGetSet;
    }

    public void setOutGetSet(Boolean outGetSet) {
        this.outGetSet = outGetSet;
    }

    public Boolean getCreateVo() {
        return createVo;
    }

    public void setCreateVo(Boolean createVo) {
        this.createVo = createVo;
    }

    public String getVoPackageName() {
        return voPackageName;
    }

    public void setVoPackageName(String voPackageName) {
        this.voPackageName = voPackageName;
    }
}
