package fr.twah2em.uhcdieu.inventories.internal;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class ItemBuilder {
    private final ItemStack item;
    private final ItemMeta meta;

    public ItemBuilder(Material material) {
        this(new ItemStack(material));
    }

    public ItemBuilder(Material material, byte id) {
        this(new ItemStack(material, 1, id));
    }

    public ItemBuilder(ItemStack item) {
        this.item = Objects.requireNonNull(item, "item");
        this.meta = item.getItemMeta();

        if (this.meta == null) {
            throw new IllegalArgumentException("The type " + item.getType() + " doesn't support item meta");
        }
    }

    public ItemBuilder withType(Material material) {
        this.item.setType(material);
        return this;
    }

    public ItemBuilder withData(int data) {
        return withDurability((short) data);
    }

    @SuppressWarnings("deprecation")
    public ItemBuilder withDurability(short durability) {
        this.item.setDurability(durability);
        return this;
    }

    public ItemBuilder withAmout(int amount) {
        this.item.setAmount(amount);
        return this;
    }

    public ItemBuilder withEnchant(Enchantment enchantment) {
        return withEnchant(enchantment, 1);
    }

    public ItemBuilder withEnchant(Enchantment enchantment, int level) {
        this.meta.addEnchant(enchantment, level, true);
        return this;
    }

    public ItemBuilder removeEnchant(Enchantment enchantment) {
        this.meta.removeEnchant(enchantment);
        return this;
    }

    public ItemBuilder removeEnchants() {
        this.meta.getEnchants().keySet().forEach(this.meta::removeEnchant);
        return this;
    }

    public ItemBuilder withMeta(Consumer<ItemMeta> metaConsumer) {
        metaConsumer.accept(this.meta);
        return this;
    }

    public <T extends ItemMeta> ItemBuilder withMeta(Class<T> metaClass, Consumer<T> metaConsumer) {
        if (metaClass.isInstance(this.meta)) {
            metaConsumer.accept(metaClass.cast(this.meta));
        }
        return this;
    }

    public ItemBuilder withName(String name) {
        this.meta.setDisplayName(name);
        return this;
    }

    public ItemBuilder withLore(String lore) {
        return withLore(Collections.singletonList(lore));
    }

    public ItemBuilder withLore(String... lore) {
        return withLore(Arrays.asList(lore));
    }

    public ItemBuilder withLore(List<String> lore) {
        this.meta.setLore(lore);
        return this;
    }

    public ItemBuilder addLore(String line) {
        List<String> lore = this.meta.getLore();

        if (lore == null) {
            return withLore(line);
        }

        lore.add(line);
        return withLore(lore);
    }

    public ItemBuilder addLore(String... lines) {
        return addLore(Arrays.asList(lines));
    }

    public ItemBuilder addLore(List<String> lines) {
        List<String> lore = this.meta.getLore();

        if (lore == null) {
            return withLore(lines);
        }

        lore.addAll(lines);
        return withLore(lore);
    }

    public ItemBuilder withFlags(ItemFlag... flags) {
        this.meta.addItemFlags(flags);
        return this;
    }

    public ItemBuilder withAllFlags() {
        return withFlags(ItemFlag.values());
    }

    public ItemBuilder removeFlags(ItemFlag... flags) {
        this.meta.removeItemFlags(flags);
        return this;
    }

    public ItemBuilder removeFlags() {
        return removeFlags(ItemFlag.values());
    }

    public ItemBuilder withArmorColor(Color color) {
        return withMeta(LeatherArmorMeta.class, m -> m.setColor(color));
    }

    public ItemStack build() {
        this.item.setItemMeta(this.meta);
        return this.item;
    }
}