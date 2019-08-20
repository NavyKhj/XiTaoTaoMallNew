package com.shangcheng.common_module.common.model;

import com.shangcheng.common_module.utils.T;

/**
 * @author Navy
 */
public class BaseEntity {
    private String iosDownloadUrl;
    private String downloadUrl;

    public String getIosDownloadUrl() {
        return iosDownloadUrl;
    }

    public void setIosDownloadUrl(String iosDownloadUrl) {
        this.iosDownloadUrl = iosDownloadUrl;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
