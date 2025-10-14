package top.r3944realms.lib39.datagen.value;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * 模组各部分的类型枚举，用于数据生成与分类。
 */
public enum ModPartEnum {
    /** 默认/未指定类型 */
    DEFAULT,

    /** 物品 */
    ITEM,

    /** 方块 */
    BLOCK,

    /** 附魔 */
    ENCHANTMENT,

    /** 成就 / 进度 */
    ADVANCEMENT,

    /** 创造模式物品栏 */
    CREATIVE_TAB,

    /** 配置项 */
    CONFIG,

    /** 实体（生物、载具等） */
    ENTITY,

    /** 图形界面 */
    GUI,

    /** 作者信息 */
    AUTHOR,

    /** 标题 */
    TITLE,

    /** 名称 */
    NAME,

    /** 游戏规则（/gamerule） */
    GAME_RULE,

    /** 描述文本 */
    DESCRIPTION,

    /** 一般信息 */
    INFO,

    /** 消息（聊天、提示等） */
    MESSAGE,

    /** 命令 */
    COMMAND,

    /** 声音资源 */
    SOUND;
    /**
     * 根据枚举类型生成标准化 key 前缀
     * 例如 ITEM -> "item.", BLOCK -> "block."
     */
    @Contract(pure = true)
    public @NotNull String getKeyPrefix() {
        return switch (this) {
            case ITEM -> "item.";
            case BLOCK -> "block.";
            case ENCHANTMENT -> "enchantment.";
            case ADVANCEMENT -> "advancement.";
            case CREATIVE_TAB -> "creative_tab.";
            case CONFIG -> "config.";
            case ENTITY -> "entity.";
            case GUI -> "gui.";
            case AUTHOR -> "author.";
            case TITLE -> "title.";
            case NAME -> "name.";
            case GAME_RULE -> "gamerule.";
            case DESCRIPTION -> "description.";
            case INFO -> "info.";
            case MESSAGE -> "message.";
            case COMMAND -> "command.";
            case SOUND -> "sound.";
            default -> "";
        };
    }

    /**
     * 根据枚举类型和具体名称生成完整 key
     * 例如 ITEM + "example_item" -> "item.example_item"
     */
    @Contract(pure = true)
    public @NotNull String getFullKey(String name) {
        return getKeyPrefix() + name;
    }
}
