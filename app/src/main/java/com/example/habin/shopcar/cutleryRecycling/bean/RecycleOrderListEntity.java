package com.example.habin.shopcar.cutleryRecycling.bean;

import java.util.List;

public class RecycleOrderListEntity {

    /**
     * result : 1
     * item : [{"createDate":1573731912000,"id":1,"status":"7","orderNum":"M1911141940202102699","name":"李一百","phone":"15900001111","building":"","address":" 广州市天河区(-D栋)","deliveryDate":1573701802000,"recyclingPrice":0.064,"recyclingContent":"[{\"number\":7,\"tableware\":\"饭盒\",\"id\":1},{\"number\":5,\"tableware\":\"筷勺\",\"id\":2}]","recyclingContentJson":[{"number":7,"tableware":"饭盒","id":1},{"number":5,"tableware":"筷勺","id":2}],"recyclingUserId":691357,"recyclingUserName":"姚柱林","recyclingDate":1573736565000},{"createDate":1573783644000,"id":2,"status":"7","orderNum":"M1911151007076862699","name":"李老师","phone":"13456789000","building":"南区-3栋","address":" 广州市天河区南区3栋403(南区-3栋)","deliveryDate":1573788202000,"recyclingPrice":0.128,"recyclingContent":"[{\"number\":6,\"tableware\":\"饭盒\",\"id\":1},{\"number\":4,\"tableware\":\"筷勺\",\"id\":2}]","recyclingContentJson":[{"number":6,"tableware":"饭盒","id":1},{"number":4,"tableware":"筷勺","id":2}],"recyclingUserId":691357,"recyclingUserName":"姚柱林","recyclingDate":1574063512000}]
     * statistics : {"all":2,"month":2,"day":0}
     */

    private String result;
    private StatisticsBean statistics;
    private List<ItemBean> item;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public StatisticsBean getStatistics() {
        return statistics;
    }

    public void setStatistics(StatisticsBean statistics) {
        this.statistics = statistics;
    }

    public List<ItemBean> getItem() {
        return item;
    }

    public void setItem(List<ItemBean> item) {
        this.item = item;
    }

    public static class StatisticsBean {
        /**
         * all : 2
         * month : 2
         * day : 0
         */

        private int all;
        private int month;
        private int day;

        public int getAll() {
            return all;
        }

        public void setAll(int all) {
            this.all = all;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }
    }

    public static class ItemBean {
        /**
         * createDate : 1573731912000
         * id : 1
         * recyclingStatus:餐具回收状态。1：可回收
         * status : 7
         * orderNum : M1911141940202102699
         * name : 李一百
         * phone : 15900001111
         * building :
         * address :  广州市天河区(-D栋)
         * deliveryDate : 1573701802000
         * recyclingPrice : 0.064
         * recyclingContent : [{"number":7,"tableware":"饭盒","id":1},{"number":5,"tableware":"筷勺","id":2}]
         * recyclingContentJson : [{"number":7,"tableware":"饭盒","id":1},{"number":5,"tableware":"筷勺","id":2}]
         * recyclingUserId : 691357
         * recyclingUserName : 姚柱林
         * recyclingDate : 1573736565000
         */

        private long createDate;
        private int id;
        private String recyclingStatus;
        private String status;
        private String orderNum;
        private String name;
        private String phone;
        private String building;
        private String address;
        private long deliveryDate;
        private double recyclingPrice;
        private String recyclingContent;
        private int recyclingUserId;
        private String recyclingUserName;
        private long recyclingDate;
        private List<RecyclingContentJsonBean> recyclingContentJson;

        public String getRecyclingStatus() {
            return recyclingStatus;
        }

        public void setRecyclingStatus(String recyclingStatus) {
            this.recyclingStatus = recyclingStatus;
        }

        public long getCreateDate() {
            return createDate;
        }

        public void setCreateDate(long createDate) {
            this.createDate = createDate;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(String orderNum) {
            this.orderNum = orderNum;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getBuilding() {
            return building;
        }

        public void setBuilding(String building) {
            this.building = building;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public long getDeliveryDate() {
            return deliveryDate;
        }

        public void setDeliveryDate(long deliveryDate) {
            this.deliveryDate = deliveryDate;
        }

        public double getRecyclingPrice() {
            return recyclingPrice;
        }

        public void setRecyclingPrice(double recyclingPrice) {
            this.recyclingPrice = recyclingPrice;
        }

        public String getRecyclingContent() {
            return recyclingContent;
        }

        public void setRecyclingContent(String recyclingContent) {
            this.recyclingContent = recyclingContent;
        }

        public int getRecyclingUserId() {
            return recyclingUserId;
        }

        public void setRecyclingUserId(int recyclingUserId) {
            this.recyclingUserId = recyclingUserId;
        }

        public String getRecyclingUserName() {
            return recyclingUserName;
        }

        public void setRecyclingUserName(String recyclingUserName) {
            this.recyclingUserName = recyclingUserName;
        }

        public long getRecyclingDate() {
            return recyclingDate;
        }

        public void setRecyclingDate(long recyclingDate) {
            this.recyclingDate = recyclingDate;
        }

        public List<RecyclingContentJsonBean> getRecyclingContentJson() {
            return recyclingContentJson;
        }

        public void setRecyclingContentJson(List<RecyclingContentJsonBean> recyclingContentJson) {
            this.recyclingContentJson = recyclingContentJson;
        }

        public static class RecyclingContentJsonBean {
            /**
             * number : 7
             * tableware : 饭盒
             * id : 1
             */

            private int number;
            private String tableware;
            private int id;

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
            }

            public String getTableware() {
                return tableware;
            }

            public void setTableware(String tableware) {
                this.tableware = tableware;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }
    }


}
