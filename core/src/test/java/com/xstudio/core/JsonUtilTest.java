package com.xstudio.core;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author xiaobiao
 * @version 2020/9/14
 */
class JsonUtilTest {

    @Test
    void testToJsonString() {
        A a = new A();

        B b = new B();
        b.setA(a);

        b.setaLong(1L);
        List<B> bs = new ArrayList<>(1);
        bs.add(b);
        a.setB(bs);

        a.setaBoolean(true);

        String json = JsonUtil.toJsonString(a);
        Assertions.assertEquals("{\"aBoolean\":true,\"b\":[{\"aLong\":1}]}", json);
    }

    @Test
    void toObject() {
        String json = "{\"aBoolean\":true,\"b\":[{\"aLong\":1}]}";
        A a = JsonUtil.toObject(json, A.class);
        Assertions.assertEquals(true, a.getaBoolean());
        Assertions.assertNotNull(a.getB().get(0).getA());
    }

    @Test
    void toMap() {
        String json = "{\"aBoolean\":true,\"b\":[{\"aLong\":1}]}";
        Map<String, Object> map = JsonUtil.toMap(json);
        Assertions.assertNotNull(map);

        json = "{\"aBoolean\":true,\"b\":\"[{\\\"aLong\\\": 1}]\"}";
        map = JsonUtil.toMap(json, true);
        Assertions.assertNotNull(map);
    }

    public static class A {
        private Long aLong;

        private Integer aInt;

        private Boolean aBoolean;

        private String aString;

        @JsonManagedReference
        private List<B> b;

        public Long getaLong() {
            return aLong;
        }

        public void setaLong(Long aLong) {
            this.aLong = aLong;
        }

        public Integer getaInt() {
            return aInt;
        }

        public void setaInt(Integer aInt) {
            this.aInt = aInt;
        }

        public Boolean getaBoolean() {
            return aBoolean;
        }

        public void setaBoolean(Boolean aBoolean) {
            this.aBoolean = aBoolean;
        }

        public String getaString() {
            return aString;
        }

        public void setaString(String aString) {
            this.aString = aString;
        }

        public List<B> getB() {
            return b;
        }

        public void setB(List<B> b) {
            this.b = b;
        }
    }

    public static class B {
        private Long aLong;

        @JsonBackReference
        private A a;

        public Long getaLong() {
            return aLong;
        }

        public void setaLong(Long aLong) {
            this.aLong = aLong;
        }

        public A getA() {
            return a;
        }

        public void setA(A a) {
            this.a = a;
        }
    }
}