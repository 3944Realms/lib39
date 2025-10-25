package top.r3944realms.lib39.util.sound;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

/**
 * The type Sound util.
 */
public class SoundUtil {
    /**
     * 为实体播放声音
     *
     * @param entity        the entity
     * @param soundEvent    the sound event
     * @param soundCategory the sound category
     * @param volume        the volume
     * @param pitch         the pitch
     */
    public static void playSoundForEntity(@NotNull LivingEntity entity, SoundEvent soundEvent, SoundSource soundCategory, float volume, float pitch) {
        entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), soundEvent, soundCategory, volume, pitch);
    }
}
