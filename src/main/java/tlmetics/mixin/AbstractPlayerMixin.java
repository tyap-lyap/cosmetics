package tlmetics.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.encryption.PlayerPublicKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tlmetics.cosmetics.Capes;
import tlmetics.data.Entries;

@Mixin(AbstractClientPlayerEntity.class)
public abstract class AbstractPlayerMixin extends PlayerEntity {

    public AbstractPlayerMixin(World world, BlockPos pos, float yaw, GameProfile profile, @Nullable PlayerPublicKey key) {
        super(world, pos, yaw, profile, key);
    }

    @Inject(method = "getCapeTexture", at = @At("HEAD"), cancellable = true)
    void getCapeTexture(CallbackInfoReturnable<Identifier> cir) {
        Entries.get(getUuid(), getName().getString()).flatMap(entry -> Capes.get(entry.cape.name)).ifPresent(cape -> {
            if (cape.getLocationProvider() != null) {
                cir.setReturnValue(cape.getLocationProvider().locate(this, world));
            }
        });
    }
}
