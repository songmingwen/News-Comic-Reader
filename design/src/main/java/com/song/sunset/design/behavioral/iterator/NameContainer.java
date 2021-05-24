package com.song.sunset.design.behavioral.iterator;

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/17 10:59
 */
public class NameContainer implements Container {

    private final String[] name = {"张三", "李四", "王五", "赵六", "牧辰"};

    @Override
    public Iterator getIterator() {
        return new NameIterator();
    }

    private class NameIterator implements Iterator {

        int index;

        @Override
        public boolean hasNext() {
            if (index >= name.length) {
                return false;
            } else {
                return true;
            }
        }

        @Override
        public Object next() {
            if (hasNext()) {
                return name[index++];
            }
            return null;
        }
    }
}
