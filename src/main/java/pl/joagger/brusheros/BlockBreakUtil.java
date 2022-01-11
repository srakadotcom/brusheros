package pl.joagger.brusheros;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class BlockBreakUtil {
    private static final Constructor<?> BLOCK_POSITION_CONSTRUCTOR;
    private static final Method BLOCK_BREAK_METHOD;
    private static final Method HANDLE_METHOD;
    private static final Field INTERACT_MANAGER;

    static {
        String packageName = Bukkit.getServer().getClass().getPackage().getName();
        String version = packageName.substring(packageName.lastIndexOf(".") + 1);

        try {
            Class<?> blockPositionClass = Class.forName("net.minecraft.server." + version + "." + "BlockPosition");
            BLOCK_POSITION_CONSTRUCTOR = blockPositionClass.getConstructor(int.class, int.class, int.class);

            Class<?> interactManager = Class.forName("net.minecraft.server." + version + "." + "PlayerInteractManager");
            BLOCK_BREAK_METHOD = interactManager.getMethod("breakBlock", blockPositionClass);

            Class<?> entityPlayer = Class.forName("net.minecraft.server." + version + "." + "EntityPlayer");
            INTERACT_MANAGER = entityPlayer.getField("playerInteractManager");

            Class<?> craftPlayer = Class.forName("org.bukkit.craftbukkit." + version + ".entity.CraftPlayer");
            HANDLE_METHOD = craftPlayer.getMethod("getHandle");
        } catch (ReflectiveOperationException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static void breakBlock(Player player, int x, int y, int z) {
        try {
            BLOCK_BREAK_METHOD.invoke(INTERACT_MANAGER.get(HANDLE_METHOD.invoke(player)), BLOCK_POSITION_CONSTRUCTOR.newInstance(x, y, z));
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}
