package io.github.xbeeant.antdesign;

import java.util.List;

/**
 * @author xiaobiao
 * @version 2021/7/2
 */
public class CurrentUser {

    /**
     * address
     *
     */
    private String address;
    /**
     * avatar
     *
     */
    private String avatar;
    /**
     * country
     *
     */
    private String country;
    /**
     * email
     *
     */
    private String email;
    /**
     * geographic
     *
     */
    private Geographic geographic;
    /**
     * group
     *
     */
    private String group;
    /**
     * name
     *
     */
    private String name;
    /**
     * notify count
     *
     */
    private Integer notifyCount;
    /**
     * phone
     *
     */
    private String phone;
    /**
     * signature
     *
     */
    private String signature;
    /**
     * tags
     *
     */
    private List<Tags> tags;
    /**
     * title
     *
     */
    private String title;
    /**
     * unread count
     *
     */
    private Integer unreadCount;
    /**
     * userid
     *
     */
    private String userid;

    public String getAddress() {
        return address;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getCountry() {
        return country;
    }

    public String getEmail() {
        return email;
    }

    public Geographic getGeographic() {
        return geographic;
    }

    public String getGroup() {
        return group;
    }

    public String getName() {
        return name;
    }

    public Integer getNotifyCount() {
        return notifyCount;
    }

    public String getPhone() {
        return phone;
    }

    public String getSignature() {
        return signature;
    }

    public List<Tags> getTags() {
        return tags;
    }

    public String getTitle() {
        return title;
    }

    public Integer getUnreadCount() {
        return unreadCount;
    }

    public String getUserid() {
        return userid;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGeographic(Geographic geographic) {
        this.geographic = geographic;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNotifyCount(Integer notifyCount) {
        this.notifyCount = notifyCount;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public void setTags(List<Tags> tags) {
        this.tags = tags;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUnreadCount(Integer unreadCount) {
        this.unreadCount = unreadCount;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public static class Geographic {
        /**
         * city
         *
         */
        private City city;
        /**
         * province
         *
         */
        private Province province;

        public City getCity() {
            return city;
        }

        public Province getProvince() {
            return province;
        }

        public void setCity(City city) {
            this.city = city;
        }

        public void setProvince(Province province) {
            this.province = province;
        }

        public static class Province {
            /**
             * key
             *
             */
            private String key;
            /**
             * label
             *
             */
            private String label;

            public String getKey() {
                return key;
            }

            public String getLabel() {
                return label;
            }

            public void setKey(String key) {
                this.key = key;
            }

            public void setLabel(String label) {
                this.label = label;
            }
        }

        public static class City {
            /**
             * key
             *
             */
            private String key;
            /**
             * label
             *
             */
            private String label;

            public String getKey() {
                return key;
            }

            public String getLabel() {
                return label;
            }

            public void setKey(String key) {
                this.key = key;
            }

            public void setLabel(String label) {
                this.label = label;
            }
        }
    }

    public static class Tags {
        /**
         * key
         *
         */
        private String key;
        /**
         * label
         *
         */
        private String label;

        public String getKey() {
            return key;
        }

        public String getLabel() {
            return label;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public void setLabel(String label) {
            this.label = label;
        }
    }
}
