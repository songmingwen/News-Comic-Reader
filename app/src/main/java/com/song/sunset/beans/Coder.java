package com.song.sunset.beans;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Song on 2016/9/27 0027.
 * Email:z53520@qq.com
 */
@Entity
public class Coder {
    @Id
    private Long id;
    private String name;
    @Generated(hash = 1042567026)
    public Coder(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    @Generated(hash = 138515568)
    public Coder() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Coder{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
