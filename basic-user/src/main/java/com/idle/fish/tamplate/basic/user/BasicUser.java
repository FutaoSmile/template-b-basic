package com.idle.fish.tamplate.basic.user;

import com.alibaba.fastjson2.JSON;
import com.idle.fish.template.basic.db.entity.base.BaseIdUserTime;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户基类
 *
 * @author idle fish
 * @since 2023/11/21
 */
@Getter
@Setter
public class BasicUser extends BaseIdUserTime {
    /**
     * 用户名
     */
    private String username;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
