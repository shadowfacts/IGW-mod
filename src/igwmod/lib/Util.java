package igwmod.lib;

import java.awt.*;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry.EntityRegistration;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class Util{

    private static Map<String, ModContainer> entityOwners;

    public static String getOwnerModForEntity(Class<? extends Entity> entity){
        if (entityOwners == null) buildOwnerMap();


        EntityRegistration entityReg = EntityRegistry.instance().lookupModSpawn(entity, true);
        if(entityReg == null) return "minecraft";
        ModContainer mod = entityOwners.get(entityReg.getEntityName());
        if(mod == null) {
            IGWLog.info("Couldn't find the owning mod of the entity " + entityReg.getEntityName() + " even though it's registered through the EntityRegistry!");
            return "minecraft";
        } else {
            return mod.getModId().toLowerCase();
        }
    }

    private static void buildOwnerMap() {
        entityOwners = new HashMap<>();
        for (EntityEntry e : ForgeRegistries.ENTITIES) {
            EntityRegistration reg = EntityRegistry.instance().lookupModSpawn(e.getEntityClass(), true);
            if (reg != null) {
                entityOwners.put(e.getName(), reg.getContainer());
            }
        }
    }

    public static Entity getEntityForClass(Class<? extends Entity> entityClass){
        try {
            return entityClass.getConstructor(World.class).newInstance(Minecraft.getMinecraft().world);
        } catch(Exception e) {
            IGWLog.error("[LocatedEntity.java] An entity class doesn't have a constructor with a single World parameter! Entity = " + entityClass.getName());
            e.printStackTrace();
            return null;
        }
    }

    public static void openBrowser(String url) {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(new URI(url));
            } catch (Throwable t) {
                IGWLog.error("Couldn't open link");
                t.printStackTrace();
            }
        }
    }

}
