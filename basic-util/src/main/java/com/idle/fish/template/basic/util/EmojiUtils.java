package com.idle.fish.template.basic.util;

import com.vdurmont.emoji.EmojiParser;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

/**
 * emoji工具类
 *
 * @author idle fish
 * @since 2023/11/23
 */
@UtilityClass
public class EmojiUtils {

    /**
     * 将emoji转为unicode纯文本，比如讲😄转为:smile:
     * 请注意，识别率并不是百分百
     *
     * @param content 包含emoji的纯文本😄
     * @return 转换之后的纯文本
     */
    public String parseToUnicode(String content) {
        if (StringUtils.isNotBlank(content)) {
            return EmojiParser.parseToAliases(content);
        }
        return content;
    }

    /**
     * 将unicode纯文本转为包含emoji的纯文本，比如将:smile:转为😄
     *
     * @param content 包含转换之后的emoji的纯文本:smile:
     * @return 转换之后的包含emoji的文本
     */
    public String parseToEmoji(String content) {
        if (StringUtils.isNotBlank(content)) {
            return EmojiParser.parseToUnicode(content);
        }
        return content;
    }

}
