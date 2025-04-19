package melonystudios.backmath.item.custom.tool.misc;

import melonystudios.backmath.item.AxolotlTest;
import melonystudios.backmath.misc.BMSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.function.Predicate;

// The Queen Lucy's Summoner Staff is mostly a copy of the BMSpawnEggItem class
// June 01/06/24 - Mace-ified the Q.L.S.S.
public class QueenLucySummonerStaffItem extends Item {
    public QueenLucySummonerStaffItem(Properties properties) {
        super(properties);
        // DispenserBlock.registerBehavior(this, new QLSSDispenseBehavior());
    }

    public static ItemAttributeModifiers createAttributes() {
        return ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, 9, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, -3, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build();
    }

    public static Tool createToolProperties() {
        return new Tool(List.of(), 1, 2);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof ServerPlayer player && canSmashAttack(player)) {
            ServerLevel world = (ServerLevel) attacker.level();
            if (player.isIgnoringFallDamageFromCurrentImpulse() && player.currentImpulseImpactPos != null) {
                if (player.currentImpulseImpactPos.y > player.position().y) {
                    player.currentImpulseImpactPos = player.position();
                }
            } else {
                player.currentImpulseImpactPos = player.position();
            }

            player.setIgnoreFallDamageFromCurrentImpulse(true);
            player.setDeltaMovement(player.getDeltaMovement().with(Direction.Axis.Y, 0.01F));
            player.connection.send(new ClientboundSetEntityMotionPacket(player));
            if (target.onGround()) {
                player.setSpawnExtraParticlesOnFall(true);
                SoundEvent smashSound = player.fallDistance > 5 ? BMSounds.SUMMONER_STAFF_HEAVY_SMASH.get() : BMSounds.SUMMONER_STAFF_SMASH.get();
                world.playSound(null, player.getX(), player.getY(), player.getZ(), smashSound, player.getSoundSource(), 1, 1);
            } else {
                world.playSound(null, player.getX(), player.getY(), player.getZ(), BMSounds.SUMMONER_STAFF_AIR_SMASH, player.getSoundSource(), 1, 1);
            }

            knockBack(world, player, target);
        }
        return true;
    }

    @Override
    public float getAttackDamageBonus(Entity target, float amount, DamageSource source) {
        if (source.getDirectEntity() instanceof LivingEntity livEntity) {
            if (!canSmashAttack(livEntity)) {
                return 0;
            } else {
                float fallDistance = livEntity.fallDistance;
                float baseDamage;
                if (fallDistance <= 3) {
                    baseDamage = 4 * fallDistance;
                } else if (fallDistance <= 8) {
                    baseDamage = 12 + 2 * (fallDistance - 3);
                } else {
                    baseDamage = 22 + fallDistance - 8;
                }

                return livEntity.level() instanceof ServerLevel world ? baseDamage + EnchantmentHelper.modifyFallBasedDamage(world, livEntity.getWeaponItem(), target, source, 0) * fallDistance : baseDamage;
            }
        } else {
            return 0;
        }
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, EquipmentSlot.MAINHAND);
        if (canSmashAttack(attacker)) attacker.resetFallDistance();
    }

    @Override
    public boolean canAttackBlock(BlockState state, Level world, BlockPos pos, Player player) {
        return !player.isCreative();
    }

    @Override
    public int getEnchantmentValue(ItemStack stack) {
        return 15;
    }

    @Override
    public boolean isValidRepairItem(ItemStack thisStack, ItemStack repairStack) {
        return repairStack.is(AxolotlTest.OBSIDIAN_INGOT.get());
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        // Terraria like tooltip, also says what food is used to tame her. Like parrots that don't like cookies, QLPs don't like aljame.
        tooltip.add(Component.translatable("tooltip.backmath.queen_lucy_summoner_staff").withStyle(ChatFormatting.ITALIC));
    }

    public static void knockBack(Level world, Player player, Entity entity) {
        world.levelEvent(2013, entity.getOnPos(), 750);
        world.getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(3.5), knockbackPredicate(player, entity)).forEach(livEntity -> {
            Vec3 vec3 = livEntity.position().subtract(entity.position());
            double knockbackPower = getKnockbackPower(player, livEntity, vec3);
            Vec3 vec31 = vec3.normalize().scale(knockbackPower);
            if (knockbackPower > 0) {
                livEntity.push(vec31.x, 0.7F, vec31.z);
                if (livEntity instanceof ServerPlayer serverPlayer) serverPlayer.connection.send(new ClientboundSetEntityMotionPacket(serverPlayer));
            }
        });
    }

    // Mace methods backported from 1.21 Pre-Release 1, from Yarn mappings.
    public static Predicate<LivingEntity> knockbackPredicate(Player player, Entity entity) {
        return (livEntity) -> {
            boolean bool;
            boolean notSpectator;
            boolean typeCheck;
            boolean notSameTeamAs;
            mainChecks: {
                notSpectator = !livEntity.isSpectator();
                typeCheck = livEntity != player && livEntity != entity;
                notSameTeamAs = !player.isAlliedTo(livEntity);
                if (livEntity instanceof TamableAnimal tameableEntity) {
                    if (tameableEntity.isTame() && player.getUUID().equals(tameableEntity.getOwnerUUID())) {
                        bool = true;
                        break mainChecks;
                    }
                }
                bool = false;
            }

            boolean notBool;
            notMarkerStand: {
                notBool = !bool;
                if (livEntity instanceof ArmorStand armorStand) {
                    if (armorStand.isMarker()) break notMarkerStand;
                }

                bool = true;
            }
            return notSpectator && typeCheck && notSameTeamAs && notBool && bool && entity.distanceToSqr(livEntity) <= Math.pow(3.5, 2); //&& !entity.getType().is(BMEntityTypeTags.IMMUNE_TO_SUMMONER_STAFF_SMASHES);
        };
    }

    private static double getKnockbackPower(Player player, LivingEntity target, Vec3 entityPos) {
        return (3.5 - entityPos.length()) * 0.7F * (double)(player.fallDistance > 5 ? 2 : 1) * (1 - target.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
    }

    public static boolean canSmashAttack(LivingEntity livEntity) {
        return livEntity.fallDistance > 1.5F && !livEntity.isFallFlying(); // && livEntity.getType().is(BMEntityTypeTags.IMMUNE_TO_SUMMONER_STAFF_SMASHES);
    }
}
