package com.bartuabihd.noiceslip;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class NoIceSlip extends JavaPlugin implements CommandExecutor {

    @Override
    public void onEnable() {
    	Bukkit.getPluginManager().registerEvents(new PlayerMoveListener(), this);
        getCommand("noiceslip").setExecutor(this);
        loadConfig();
        getLoggerColored("[NoIceSlip] " + ChatColor.GREEN + "Plugin is enabled!");
        
        new UpdateChecker(this, 114913).getLatestVersion(version -> {
            if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
                getLoggerColored(ChatColor.GRAY + "[" + ChatColor.BLUE + "NoIceSlip" + ChatColor.GRAY + "] " + ChatColor.RED + "A new update of the plugin is not available.");
            }
            else {
                getLoggerColored(ChatColor.GRAY + "[" + ChatColor.BLUE + "NoIceSlip" + ChatColor.GRAY + "] " + ChatColor.GREEN + "The plugin has a new update. Download link: https://bit.ly/noiceslip");
            }
        });
    }
    
    public void loadConfig(){
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
    
    @Override
    public void onDisable()
	{
		getLoggerColored("[NoIceSlip] " + ChatColor.RED + "Plugin is disabled!");
	}
    
    public class PlayerMoveListener implements Listener {

    	@EventHandler
        public void onPlayerMove(PlayerMoveEvent event) {
            Player player = event.getPlayer();
            if (player.getLocation().subtract(0, 1, 0).getBlock().getType() == Material.ICE || player.getLocation().subtract(0, 1, 0).getBlock().getType() == Material.PACKED_ICE) {
                float walkSpeed = (float) getConfig().getDouble("walk-speed", 0.4f);
                if (player.getWalkSpeed() != walkSpeed) {
                    player.setWalkSpeed(walkSpeed);
                }
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        
    	if (sender.hasPermission("noiceslip.cmd")){
	    	if (args.length == 0) {
	    		sender.sendMessage(ChatColor.YELLOW + "NoIceSlip's Author: " + ChatColor.GREEN + "BartuAbiHD");
	            return true;
	    	}else {
	    		if (command.getName().equalsIgnoreCase("noiceslip") && args.length > 0 && args[0].equalsIgnoreCase("reload")) {
		            reloadConfig();
		            loadConfig();
		            sender.sendMessage(ChatColor.GREEN + "NoIceSlip config reloaded.");
		            return true;
		        }
	    	}
    	}
        return false;
    }
    
    private void getLoggerColored(String msg) {
		Bukkit.getConsoleSender().sendMessage(msg);
	}
}
