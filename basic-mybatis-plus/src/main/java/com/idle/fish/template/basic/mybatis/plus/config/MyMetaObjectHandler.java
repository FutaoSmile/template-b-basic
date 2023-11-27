package com.idle.fish.template.basic.mybatis.plus.config;


import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.idle.fish.tamplate.basic.user.BasicUser;
import com.idle.fish.tamplate.basic.user.CurrentUser;
import com.idle.fish.template.basic.db.entity.base.BaseIdUserTime;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 字段自动填充
 *
 * @author idle fish
 * @since 2021/11/21
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        BasicUser user = CurrentUser.getUser();
        this.strictInsertFill(metaObject, BaseIdUserTime.ID_FIELD, Long.class, Optional.ofNullable(user).map(BasicUser::getId).orElse(0L));
        this.setFieldValByName(BaseIdUserTime.CREATE_BY_FIELD, System.currentTimeMillis(), metaObject);

        this.setFieldValByName(BaseIdUserTime.UPDATE_BY_FIELD, Optional.ofNullable(user).map(BasicUser::getId).orElse(0L), metaObject);
        this.setFieldValByName(BaseIdUserTime.UPDATE_DATE_TIME_FIELD, System.currentTimeMillis(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        BasicUser user = CurrentUser.getUser();
        this.setFieldValByName(BaseIdUserTime.UPDATE_BY_FIELD, Optional.ofNullable(user).map(BasicUser::getId).orElse(0L), metaObject);
        this.setFieldValByName(BaseIdUserTime.UPDATE_DATE_TIME_FIELD, System.currentTimeMillis(), metaObject);
    }
}
