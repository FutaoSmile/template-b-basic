package com.idle.fish.template.basic.db.entity.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 包含id、创建时间、创建人、更新时间、更新人
 *
 * @author idle fish
 * @since 2023/11/9
 */
@Getter
@Setter
@ToString
public abstract class BaseIdUserTime extends BaseIdCreateDateTime {
    /**
     * 创建人id
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;
    public static final String CREATE_BY_FIELD = "createBy";
    public static final String CREATE_BY_COLUMN = "create_by";

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateDateTime;
    public static final String UPDATE_DATE_TIME_FIELD = "updateDateTime";
    public static final String UPDATE_DATE_TIME_COLUMN = "update_date_time";

    /**
     * 更新人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;
    public static final String UPDATE_BY_FIELD = "updateBy";
    public static final String UPDATE_BY_COLUMN = "update_by";
}
