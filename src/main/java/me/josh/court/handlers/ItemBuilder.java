package me.josh.court.handlers;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder {

    private ItemStack stack;
    private ItemMeta meta;

    public ItemBuilder(final Material material) {
        this.stack = new ItemStack(material);
        this.meta = this.stack.getItemMeta();
    }

    public ItemBuilder setDisplayName(final String displayName) {
        this.meta.setDisplayName(ChatHandler.translate(displayName));
        return this;
    }

    public ItemBuilder setLore(final List<String> lines) {
        final List<String> lore = new ArrayList<>();
        for (final String line : lines) {
            lore.add(ChatHandler.translate(line));
        }
        this.meta.setLore((List)lore);
        return this;
    }

    public ItemStack build() {
        final ItemStack clonedStack = this.stack.clone();
        clonedStack.setItemMeta(this.meta.clone());
        return clonedStack;
    }
}
