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

public final class Skywalker extends JavaPlugin implements CommandExecutor {

    @Override
    public void onEnable() {}

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void GenerateBlocks(Location location){
        Block center = location.subtract(0,1,0).getBlock();
        Block[] blocks = {center, center.getRelative(0,0,1)
                , center.getRelative(0,0,-1)
                , center.getRelative(1,0,0)
                , center.getRelative(1,0,1)
                , center.getRelative(1,0,-1)
                , center.getRelative(-1,0,1)
                , center.getRelative(-1,0,0)
                , center.getRelative(-1,0,-1)};
        for(Block block : blocks){
            if(block.isEmpty()){
                block.setType(Material.BARRIER);
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("skywalker")) {
            if (sender.hasPermission("Skywalker.use")) {
                if (args.length == 2) {
                    if(getServer().getOnlinePlayers().contains(args[0])) {
                        Player player = getServer().getPlayer(args[0]);
                        Integer time = Integer.parseInt(args[1])*4;
                        int scheduler = getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
                            @Override
                            public void run() {
                                GenerateBlocks(player.getLocation());
                            }
                        }, 0,5);

                        Bukkit.getScheduler().runTaskLater(this, new Runnable() {
                            @Override
                            public void run() {
                                Bukkit.getScheduler().cancelTask(scheduler);
                            }
                        }, time);
                        return true;
                    } else {
                        sender.sendMessage("玩家不在線!");
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
