package top.r3944realms.lib39.datagen.value;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

/**
 * 模组各部分的类型枚举，用于数据生成与分类。
 */
public enum ModPartEnum {
    /**
     * 默认/未指定类型
     */
    DEFAULT,

    /**
     * 物品
     */
    ITEM(Item.class),

    /**
     * 方块
     */
    BLOCK(Block.class),

    /**
     * 附魔
     */
    ENCHANTMENT,

    /**
     * 进度标题
     */
    ADVANCEMENT_TITLE,

    /**
     * 成就描述
     */
    ADVANCEMENT_DESCRIPTION,

    /**
     * 创造模式物品栏
     */
    CREATIVE_TAB,

    /**
     * 配置项
     */
    CONFIG,

    /**
     * 实体（生物、载具等）
     */
    ENTITY,

    /**
     * 图形界面
     */
    GUI,
    /**
     * 画作描述
     */
    PAINTING_TITLE,
    /**
     * 画作作者
     */
    PAINTING_AUTHOR,

    /**
     * 标题
     */
    TITLE,

    /**
     * 名称
     */
    NAME,

    /**
     * 游戏规则（/gamerule）
     */
    GAME_RULE,

    /**
     * 描述文本
     */
    DESCRIPTION,

    /**
     * 一般信息
     */
    INFO,

    /**
     * 消息（聊天、提示等）
     */
    MESSAGE,

    /**
     * 生物群系
     */
    BIOME,

    /**
     * 命令
     */
    COMMAND,

    /**
     * 声音资源
     */
    SOUND;
    ;
    @Nullable
    private final Class<?> clazz;
    ModPartEnum() {
        clazz = null;
    }
    ModPartEnum(@Nullable Class<?> clazz) {
        this.clazz = clazz;
    }

    /**
     * Gets full key.
     *
     * @param modId the mod id
     * @param name  the name
     * @return the full key
     */
    public String getFullKey(String modId, String name) {
        return switch (this) {
            case ITEM -> "item." + modId + "." + name;
            case BLOCK -> "block." + modId + "." + name;
            case ENCHANTMENT -> "enchantment.";
            case ADVANCEMENT_TITLE -> "advancement." + modId + "." + name + ".title";
            case ADVANCEMENT_DESCRIPTION -> "advancement." + modId + "." + name + ".description";
            case CREATIVE_TAB -> "creativetab." + modId + "." + name;
            case BIOME -> "biome." + modId + "." + name;
            case CONFIG -> "config." + modId + "." + name;
            case ENTITY -> "entity." + modId + "." + name;
            case GUI -> "gui." + modId + "." + name;
            case PAINTING_AUTHOR -> "painting." + modId + "." + name + ".author";
            case PAINTING_TITLE -> "painting." + modId + "." + name + ".title";
            case TITLE -> "title." + modId + "." + name;
            case NAME -> "name." + modId + "." + name;
            case GAME_RULE -> "gamerule.";
            case DESCRIPTION -> "description.";
            case INFO -> "info." + modId + "." + name;
            case MESSAGE -> "message." + modId + "." + name;
            case COMMAND -> "command." + modId + "." + name;
            case SOUND -> "sound." + modId + "." + name;
            default -> modId + name;
        };
    }


    /**
     * Gets clazz.
     *
     * @return the clazz
     */
    public @Nullable Class<?> getClazz() {
        return clazz;
    }
}
