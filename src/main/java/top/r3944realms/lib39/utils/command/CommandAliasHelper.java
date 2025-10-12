package top.r3944realms.lib39.utils.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.CommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class CommandAliasHelper {

    /**
     * 注册命令及其别名
     */
    public static void registerWithAliases(CommandDispatcher<CommandSourceStack> dispatcher,
                                           LiteralArgumentBuilder<CommandSourceStack> mainCommand,
                                           String... aliases) {
        // 注册主命令
        dispatcher.register(mainCommand);

        // 注册所有别名
        for (String alias : aliases) {
            LiteralArgumentBuilder<CommandSourceStack> aliasCommand = Commands.literal(alias);

            // 复制主命令的所有子命令到别名命令
            for (CommandNode<CommandSourceStack> child : mainCommand.getArguments()) {
                aliasCommand.then(child.createBuilder());
            }

            dispatcher.register(aliasCommand);
        }
    }
}