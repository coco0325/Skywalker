package coco0325;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class Skywalker extends JavaPlugin implements CommandExecutor {

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void GenerateBlocks(Player player) {
            Block center = player.getLocation().subtract(0, 1, 0).getBlock();
            ArrayList<Block> blocks = new ArrayList<Block>();
            ArrayList<Block> block_data = new ArrayList<Block>();
            blocks.add(center);
            blocks.add(center.getRelative(0, 0, 1));
            blocks.add(center.getRelative(0, 0, -1));
            blocks.add(center.getRelative(1, 0, 0));
            blocks.add(center.getRelative(1, 0, 1));
            blocks.add(center.getRelative(1, 0, -1));
            blocks.add(center.getRelative(-1, 0, 1));
            blocks.add(center.getRelative(-1, 0, 0));
            blocks.add(center.getRelative(-1, 0, -1));
            for (Block block : blocks) {
                if (block.isEmpty()) {
                    block.setType(Material.BARRIER);
                    block_data.add(block);
                }
            }
            Bukkit.getScheduler().runTaskLater(this, new Runnable() {
                @Override
                public void run() {
                    for(Block block : block_data) {
                        block.setType(Material.AIR);
                    }
                }
            }, 100);
        }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("skywalker")) {
            if (sender.hasPermission("Skywalker.use")) {
                if (args.length == 2) {
                    try {
                        Player player = getServer().getPlayer(args[0]);
                        Integer time = Integer.parseInt(args[1]);
                        int scheduler = getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
                            @Override
                            public void run() {
                                if(player.isOnline()) {
                                    GenerateBlocks(player);
                                } else{
                                }
                            }
                        }, 0,1);

                        Bukkit.getScheduler().runTaskLater(this, new Runnable() {
                            @Override
                            public void run() {
                                    Bukkit.getScheduler().cancelTask(scheduler);
                            }
                        }, time*20);
                        sender.sendMessage("對玩家 "+player.getName()+ "使用 "+time+"秒的天行者效果");
                        player.sendMessage("啟用 "+time+"秒的天行者效果~");
                        return true;
                    } catch (Exception e){
                        sender.sendMessage("指令用法:/skywalker 玩家 秒數");
                    }
                } else {
                    sender.sendMessage("指令用法:/skywalker 玩家 秒數");
                }
            } else {
                sender.sendMessage("你沒有權限使用!");
            }
        }
        return false;
    }
}
