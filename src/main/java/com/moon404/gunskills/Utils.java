package com.moon404.gunskills;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerFunctionManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.scores.Scoreboard;

public class Utils
{
    public static int getScore(Scoreboard scoreboard, String player, String objective)
    {
        return scoreboard.getOrCreatePlayerScore(player, scoreboard.getOrCreateObjective(objective)).getScore();
    }

    public static void setScore(Scoreboard scoreboard, String player, String objective, int score)
    {
        scoreboard.getOrCreatePlayerScore(player, scoreboard.getOrCreateObjective(objective)).setScore(score);
    }

    public static void invokeFunction(ServerPlayer player, String function)
    {
        MinecraftServer server = player.getServer();
        ServerFunctionManager manager = server.getFunctions();
        CommandDispatcher<CommandSourceStack> dispatcher = manager.getDispatcher();
        CommandSourceStack sourceStack = new CommandSourceStack(CommandSource.NULL, player.getPosition(1), player.getRotationVector(), player.serverLevel(), 2, null, null, server, null);
        sourceStack = sourceStack.withEntity(player);
        try {
            dispatcher.execute("function " + function, sourceStack);
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }
    }
}
