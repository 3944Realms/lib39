package top.r3944realms.lib39.core.event;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.EntityLeaveLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.r3944realms.lib39.Lib39;
import top.r3944realms.lib39.api.event.SyncManagerRegisterEvent;
import top.r3944realms.lib39.core.sync.ISyncData;
import top.r3944realms.lib39.core.sync.SyncData2Manager;

public class CommonHandler {
    @Mod.EventBusSubscriber(modid = Lib39.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class Game extends CommonHandler {
        static volatile SyncData2Manager syncData2Manager;
        public static SyncData2Manager getSyncData2Manager() {
            return syncData2Manager;
        }

        @SubscribeEvent
        public static void onServerTick(TickEvent.ServerTickEvent event) {
            if (event.phase == TickEvent.Phase.END) {
                if (syncData2Manager == null) {
                    synchronized (Game.class){
                        if (syncData2Manager == null) {
                            syncData2Manager = new SyncData2Manager();
                            MinecraftForge.EVENT_BUS.post(new SyncManagerRegisterEvent(syncData2Manager));
                        }
                    }
                }
                if (event.getServer().getTickCount() % 10 == 0) {
                    syncData2Manager.forEach(((resourceLocation, iSyncManager) -> iSyncManager.foreach(ISyncData::makeDirty)));
                }
                syncData2Manager.forEach(((resourceLocation, iSyncManager) -> iSyncManager.foreach(ISyncData::checkIfDirtyThenUpdate)));
            }
        }
        @SubscribeEvent
        public static void onEntityJoinWorld(EntityJoinLevelEvent event) {
            Entity entity = event.getEntity();
            if (entity.level().isClientSide) return;

            for (ResourceLocation id : syncData2Manager.getRegisteredKeys()) {
                if (syncData2Manager.isEntityClassAllowed(id, entity.getClass())) {
                    syncData2Manager.trackEntityForManager(entity, id);
                }
            }
        }
        @SubscribeEvent
        public static void onEntityLeaveWorld(EntityLeaveLevelEvent event) {
            Entity entity = event.getEntity();
            if (entity.level().isClientSide) return;

            for (ResourceLocation id : syncData2Manager.getRegisteredKeys()) {
                if (syncData2Manager.isEntityClassAllowed(id, entity.getClass())) {
                    syncData2Manager.untrackEntityForManager(entity, id);
                }
            }
        }
    }
}
