package org.midheaven.lang.model;

import org.midheaven.lang.Maybe;

import java.util.Optional;

public class TestPojo {

    private String name;
    private int age = 0;
    private Double height;
    private Double width;

    public static TestPojo fromName(String name){
        return new TestPojo(name);
    }

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

    public Optional<Double> getHeight() {
        return Optional.ofNullable(height);
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Maybe<Double> getWidth() {
        return Maybe.of(width);
    }

    public void setWidth(Double width) {
        this.width = width;
    }
}
