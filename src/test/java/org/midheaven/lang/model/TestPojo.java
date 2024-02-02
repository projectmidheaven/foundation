package org.midheaven.lang.model;

public class TestPojo {

    private String name;
    private int age = 0;

    public TestPojo() {

    }

    public TestPojo(String name) {
        this.name = name;

    }

    public TestPojo(int a) {
        this.age = a;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
