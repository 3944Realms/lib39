package top.r3944realms.lib39.util.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The type Command alias helper.
 */
@SuppressWarnings("unused")
public class CommandAliasHelper {

    /**
     * 注册命令及其别名
     *
     * @param dispatcher  the dispatcher
     * @param mainCommand the main command
     * @param aliases     the aliases
     */
    public static void registerWithAliases(@NotNull CommandDispatcher<CommandSourceStack> dispatcher,
                                           LiteralArgumentBuilder<CommandSourceStack> mainCommand,
                                           String @NotNull ... aliases) {
        // 注册主命令
        LiteralCommandNode<CommandSourceStack> mainNode = dispatcher.register(mainCommand);

        // 注册所有别名
        for (String alias : aliases) {
            LiteralArgumentBuilder<CommandSourceStack> aliasCommand = Commands.literal(alias);

            // 复制主命令的所有子命令到别名命令（递归复制）
            copyChildren(mainNode, aliasCommand);

            dispatcher.register(aliasCommand);
        }
    }

    /**
     * 递归复制命令节点的所有子节点
     */
    private static void copyChildren(@NotNull CommandNode<CommandSourceStack> source, ArgumentBuilder<CommandSourceStack, ?> target) {
        for (CommandNode<CommandSourceStack> child : source.getChildren()) {
            ArgumentBuilder<CommandSourceStack, ?> childBuilder = createBuilderFromNode(child);

            if (childBuilder != null) {
                // 递归复制孙子节点
                copyChildren(child, childBuilder);

                // 将子命令添加到目标命令
                target.then(childBuilder);
            }
        }
    }

    /**
     * 根据命令节点类型创建对应的构建器
     */
    private static @Nullable ArgumentBuilder<CommandSourceStack, ?> createBuilderFromNode(CommandNode<CommandSourceStack> node) {
        if (node instanceof LiteralCommandNode<CommandSourceStack> literalNode) {
            // 处理字面量节点
            LiteralArgumentBuilder<CommandSourceStack> builder = Commands.literal(literalNode.getLiteral());
            copyNodeProperties(node, builder);
            return builder;

        } else if (node instanceof ArgumentCommandNode<CommandSourceStack, ?> argumentNode) {
            // 处理参数节点
            RequiredArgumentBuilder<CommandSourceStack, ?> builder = Commands.argument(
                    argumentNode.getName(),
                    argumentNode.getType()
            );

            // 设置参数建议提供器
            if (argumentNode.getCustomSuggestions() != null) {
                builder.suggests(argumentNode.getCustomSuggestions());
            }

            copyNodeProperties(node, builder);
            return builder;
        }

        return null;
    }

    /**
     * 复制命令节点的通用属性
     */
    private static void copyNodeProperties(@NotNull CommandNode<CommandSourceStack> source, ArgumentBuilder<CommandSourceStack, ?> target) {
        // 复制重定向
        if (source.getRedirect() != null) {
            target.redirect(source.getRedirect());
        }

        // 复制权限要求
        if (source.getRequirement() != null) {
            target.requires(source.getRequirement());
        }

        // 复制执行逻辑
        if (source.getCommand() != null) {
            target.executes(source.getCommand());
        }
    }
}