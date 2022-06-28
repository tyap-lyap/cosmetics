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
import tlmetics.data.CosmeticsData;

@Mixin(AbstractClientPlayerEntity.class)
public abstract class AbstractClientPlayerMixin extends PlayerEntity {

    public AbstractClientPlayerMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile, @Nullable PlayerPublicKey publicKey) {
        super(world, pos, yaw, gameProfile, publicKey);
    }

    @Inject(method = "getCapeTexture", at = @At("HEAD"), cancellable = true)
    void getCapeTexture(CallbackInfoReturnable<Identifier> cir) {
        CosmeticsData.getEntry(this.getUuid(), this.getName().getString())
                .flatMap(entry -> Capes.get(entry.cape.name)).ifPresent(cloak -> {
                    if(cloak.getLocationProvider() != null) {
                        cir.setReturnValue(cloak.getLocationProvider().locate(this, world));
                    }
                });
    }
}
