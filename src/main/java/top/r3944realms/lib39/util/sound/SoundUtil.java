package top.r3944realms.lib39.util.sound;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;

public class SoundUtil {
    /**
     * 为实体播放声音
     */
    public static void playSoundForEntity(LivingEntity entity, SoundEvent soundEvent, SoundSource soundCategory, float volume, float pitch) {
        entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), soundEvent, soundCategory, volume, pitch);
    }
}
