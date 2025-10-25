package top.r3944realms.lib39.example.content.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.r3944realms.lib39.Lib39;

/**
 * The type Test sync cap provider.
 */
public class TestSyncCapProvider implements ICapabilitySerializable<CompoundTag> {

    /**
     * The constant TEST_SYNC_REL.
     */
    public static final ResourceLocation TEST_SYNC_REL = new ResourceLocation(Lib39.MOD_ID, "test_sync_data");
    private final AbstractedTestSyncData instance;
    private final LazyOptional<AbstractedTestSyncData> optional;

    /**
     * Instantiates a new Test sync cap provider.
     *
     * @param entity the entity
     */
    public TestSyncCapProvider(Entity entity) {
        this.instance = new TestSyncData(entity);
        this.optional = LazyOptional.of(() -> instance);
    }
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return ExCapabilityHandler.TEST_CAP.orEmpty(cap, optional);
    }

    @Override
    public CompoundTag serializeNBT() {
        return instance.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        instance.deserializeNBT(nbt);
    }
}
