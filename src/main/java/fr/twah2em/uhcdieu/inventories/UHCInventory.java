package fr.twah2em.uhcdieu.inventories;

import fr.twah2em.uhcdieu.streams.StreamUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class UHCInventory implements InventoryHolder {
    private Inventory inventory;

    private String name;
    private int lines;

    private List<ItemStack> items;
    private final boolean glassInEmptySlots;

    private final Consumer<InventoryOpenEvent> openConsumer;
    private final Consumer<InventoryClickEvent> clickConsumer;
    private final Consumer<InventoryCloseEvent> closeConsumer;

    private UHCInventory(String name, int lines, List<ItemStack> items, boolean glassInEmptySlots, Consumer<InventoryOpenEvent> openConsumer, Consumer<InventoryClickEvent> clickConsumer,
                         Consumer<InventoryCloseEvent> closeConsumer) {
        this.inventory = Bukkit.createInventory(this, lines * 9, Component.text(name));
        this.name = name;
        this.lines = lines;

        this.items = items;
        StreamUtils.forEachIndexed(items, inventory::setItem);
        this.glassInEmptySlots = glassInEmptySlots;

        this.openConsumer = openConsumer;
        this.clickConsumer = clickConsumer;
        this.closeConsumer = closeConsumer;
    }

    public String name() {
        return name;
    }

    public int lines() {
        return lines;
    }

    public List<ItemStack> items() {
        return items;
    }

    public Consumer<InventoryOpenEvent> openConsumer() {
        return openConsumer;
    }

    public Consumer<InventoryClickEvent> clickConsumer() {
        return clickConsumer;
    }

    public Consumer<InventoryCloseEvent> closeConsumer() {
        return closeConsumer;
    }

    public boolean glassInEmptySlots() {
        return glassInEmptySlots;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }

    public static class UHCInventoryBuilder {
        private final String name;
        private final int lines;

        private final List<ItemStack> items;
        private boolean glassInEmptySlots;

        private Consumer<InventoryOpenEvent> openConsumer;
        private Consumer<InventoryClickEvent> clickConsumer;
        private Consumer<InventoryCloseEvent> closeConsumer;

        public UHCInventoryBuilder(String name, int lines) {
            this.name = name;
            this.lines = lines;

            this.items = new ArrayList<>(lines * 9);
            IntStream.range(0, lines * 9).forEach(i -> items.add(null));
            this.glassInEmptySlots = false;

            this.openConsumer = inventoryOpenEvent -> {
            };
            this.clickConsumer = inventoryClickEvent -> {
            };
            this.closeConsumer = inventoryCloseEvent -> {
            };

            System.out.println("constructeur");
        }

        public UHCInventoryBuilder(UHCInventory uhcInventory) {
            this.name = uhcInventory.name();
            this.lines = uhcInventory.lines();

            this.items = uhcInventory.items();
            this.glassInEmptySlots = uhcInventory.glassInEmptySlots();

            this.openConsumer = uhcInventory.openConsumer();
            this.clickConsumer = uhcInventory.clickConsumer();
            this.closeConsumer = uhcInventory.closeConsumer();
        }

        public UHCInventoryBuilder withItem(ItemStack item) {
            System.out.println("with item");
            return withItems(item);
        }

        public UHCInventoryBuilder withItemInSlot(int slot, ItemStack item) {
            this.items.set(slot, item);

            return this;
        }

        public UHCInventoryBuilder withItems(ItemStack... items) {
            System.out.println("with items");
            return withItems(Arrays.asList(items));
        }

        public UHCInventoryBuilder withItems(List<ItemStack> items) {
            System.out.println("withItems");
            StreamUtils.forEachIndexed(items, (index, item) -> {
                if (item.getType() != Material.AIR) {
                    this.items.set(index, item);
                }
            });

            return this;
        }

        public UHCInventoryBuilder withGlassInEmptySlots() {
            this.glassInEmptySlots = !glassInEmptySlots;

            return this;
        }

        public UHCInventoryBuilder withOpenConsumer(Consumer<InventoryOpenEvent> openConsumer) {
            this.openConsumer = openConsumer;

            return this;
        }

        public UHCInventoryBuilder withClickConsumer(Consumer<InventoryClickEvent> clickConsumer) {
            System.out.println("withClickConsumer");
            this.clickConsumer = clickConsumer;

            return this;
        }

        public UHCInventoryBuilder withCloseConsumer(Consumer<InventoryCloseEvent> closeConsumer) {
            this.closeConsumer = closeConsumer;

            return this;
        }

        public UHCInventory buildToUhcInventory() {
            if (glassInEmptySlots) fillEmptyElementsWithGlassPane();

            return new UHCInventory(this.name, this.lines, this.items, this.glassInEmptySlots, this.openConsumer, this.clickConsumer, this.closeConsumer);
        }

        public Inventory buildToBukkitInventory() {
            System.out.println("buildToBukkitInventory");
            return buildToUhcInventory().getInventory();
        }

        private void fillEmptyElementsWithGlassPane() {
            StreamUtils.fillEmptyElements(this.items, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE)
                    .withName("§c")
                    .withFlags(ItemFlag.HIDE_ATTRIBUTES)
                    .build()
            );
        }
    }
}