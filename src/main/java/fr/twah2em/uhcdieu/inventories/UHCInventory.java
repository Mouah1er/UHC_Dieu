package fr.twah2em.uhcdieu.inventories;

import fr.twah2em.uhcdieu.utils.streams.StreamUtils;
import fr.twah2em.uhcdieu.utils.misc.Pair;
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
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public class UHCInventory implements InventoryHolder {
    private final Inventory inventory;

    private final String name;
    private final int lines;

    private final List<ItemStack> items;
    private final boolean glassInEmptySlots;

    private final Consumer<InventoryOpenEvent> openConsumer;
    private final Consumer<InventoryClickEvent> clickConsumer;
    private final Consumer<InventoryCloseEvent> closeConsumer;

    private final boolean cancelCloseEvent;

    // package private
    private UHCInventory(String name, int lines, List<ItemStack> items, boolean glassInEmptySlots, Consumer<InventoryOpenEvent> openConsumer, Consumer<InventoryClickEvent> clickConsumer,
                         Consumer<InventoryCloseEvent> closeConsumer, boolean cancelCloseEvent) {
        this.inventory = Bukkit.createInventory(this, lines * 9, Component.text(name));
        this.name = name;
        this.lines = lines;

        this.items = items;
        StreamUtils.forEachIndexed(items, inventory::setItem);
        this.glassInEmptySlots = glassInEmptySlots;

        this.openConsumer = openConsumer;
        this.clickConsumer = clickConsumer;
        this.closeConsumer = closeConsumer;

        this.cancelCloseEvent = cancelCloseEvent;
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

    public boolean cancelCloseEvent() {
        return cancelCloseEvent;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }

    @Override
    public String toString() {
        return "UHCInventory{" +
                "\ninventory=" + inventory +
                ",\n name='" + name + '\'' +
                ",\n lines=" + lines +
                ",\n items=" + items +
                ",\n glassInEmptySlots=" + glassInEmptySlots +
                ",\n openConsumer=" + openConsumer +
                ",\n clickConsumer=" + clickConsumer +
                ",\n closeConsumer=" + closeConsumer +
                ",\n cancelCloseEvent=" + cancelCloseEvent +
                "\n}";
    }

    public static class UHCInventoryBuilder {
        private final String name;
        private final int lines;

        private final List<ItemStack> items;
        private boolean glassInEmptySlots;

        private Consumer<InventoryOpenEvent> openConsumer;
        private Consumer<InventoryClickEvent> clickConsumer;
        private Consumer<InventoryCloseEvent> closeConsumer;

        private boolean cancelCloseEvent;

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

            this.cancelCloseEvent = false;
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
            return withItems(item);
        }

        public UHCInventoryBuilder withItemInSlot(int slot, ItemStack item) {
            if (slot >= 0 && slot < lines * 9) {
                this.items.set(slot, item);
            }

            return this;
        }

        public UHCInventoryBuilder withItems(ItemStack... items) {
            return withItems(Arrays.asList(items));
        }

        public UHCInventoryBuilder withItems(List<ItemStack> items) {
            return withItemsInSlots(items
                    .stream()
                    .map(item -> {
                        final int slot = items.indexOf(item);

                        return Pair.of(slot, item);
                    }).toList());
        }

        public UHCInventoryBuilder withItemsInSlots(List<? extends Pair<Integer, ItemStack>> items) {
            StreamUtils.forEachIndexed(items, (index, pair) -> {
                final Integer slot = pair.left();
                final ItemStack item = pair.right();

                if (item != null && slot >= 0 && slot < lines * 9) {
                    this.items.set(slot, item);
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
            this.clickConsumer = clickConsumer;

            return this;
        }

        public UHCInventoryBuilder withCloseConsumer(Consumer<InventoryCloseEvent> closeConsumer) {
            this.closeConsumer = closeConsumer;

            return this;
        }

        public UHCInventoryBuilder cancelCloseEvent(boolean cancelCloseEvent) {
            this.cancelCloseEvent = cancelCloseEvent;

            return this;
        }

        public UHCInventoryBuilder cancelCloseEvent(BooleanSupplier supplier) {
            this.cancelCloseEvent = supplier.getAsBoolean();

            return this;
        }

        public UHCInventory buildToUhcInventory() {
            if (glassInEmptySlots) fillEmptyElementsWithGlassPane();

            return new UHCInventory(this.name, this.lines, this.items, this.glassInEmptySlots, this.openConsumer, this.clickConsumer, this.closeConsumer, this.cancelCloseEvent);
        }

        public Inventory buildToBukkitInventory() {
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