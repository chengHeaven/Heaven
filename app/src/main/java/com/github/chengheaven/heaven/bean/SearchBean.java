package com.github.chengheaven.heaven.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Heaven・Cheng Created on 17/6/15.
 */

public class SearchBean {

    /**
     * "count":20,
     * "error":false,
     * results:[{...}]
     */

    @SerializedName("count")
    private int count; // FIXME check this code
    private boolean error;
    private List<ResultBean> results;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<ResultBean> getResult() {
        return results;
    }

    public void setResult(List<ResultBean> results) {
        this.results = results;
    }

    public static class ResultBean {
        /**
         * desc : 当Rxjava遇上databinding
         * ganhuo_id : 57fb4987421aa95dd351b0f2
         * publishedAt : 2016-10-11T11:42:22.814000
         * type : Android
         * url : https://github.com/TangoAgency/android-data-binding-rxjava
         * who : 有时放纵
         */

        private String desc;
        private String ganhuo_id;
        private String publishedAt;
        private String type;
        private String url;
        private String who;

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getGanhuo_id() {
            return ganhuo_id;
        }

        public void setGanhuo_id(String ganhuo_id) {
            this.ganhuo_id = ganhuo_id;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getWho() {
            return who;
        }

        public void setWho(String who) {
            this.who = who;
        }
    }
}
