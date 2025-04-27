package com.sophicreeper.backmath.item.custom.tool.misc;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.sophicreeper.backmath.dispenser.QLSSDispenseBehavior;
import com.sophicreeper.backmath.entity.BMEntities;
import com.sophicreeper.backmath.misc.BMSounds;
import com.sophicreeper.backmath.util.TagTypes;
import com.sophicreeper.backmath.util.tag.BMEnchantmentTags;
import com.sophicreeper.backmath.util.tag.BMEntityTypeTags;
import com.sophicreeper.backmath.util.tag.BMItemTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SEntityVelocityPacket;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

// The Queen Lucy's Summoner Staff is mostly a copy of the BMSpawnEggItem class
// June 01/06/24 - Mace-ified the Q.L.S.S.
public class QueenLucySummonerStaffItem extends SpawnEggItem implements IVanishable {
    private final Multimap<Attribute, AttributeModifier> attributeModifiers;

    public QueenLucySummonerStaffItem(Properties properties) {
        super(null, 0xFFFFFF, 0xFFFFFF, properties);
        DispenserBlock.registerBehavior(this, new QLSSDispenseBehavior());
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Q.L.S.S. Attack Damage Modifier", 9, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Q.L.S.S. Swing Speed Modifier", -3, AttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) attacker;
            if (canDoSmashAttack(serverPlayer)) {
                ServerWorld world = (ServerWorld) attacker.level;
                serverPlayer.setDeltaMovement(with(serverPlayer.getDeltaMovement(), Direction.Axis.Y, 0.01));
                serverPlayer.connection.send(new SEntityVelocityPacket(serverPlayer));
                target.hurt(DamageSource.playerAttack(serverPlayer), getAttackDamageBonus(DamageSource.playerAttack(serverPlayer)));
                if (target.isOnGround()) {
                    // Smash Particles
                    spawnSmashAttackParticles(world, target, 250);
//                    Vector3d vector3D = Vector3d.atCenterOf(target.blockPosition()).add(0, 0.5, 0);
//                    int particlesPerDistance = (int) MathHelper.clamp(50 * attacker.fallDistance, 0, 500);
//                    world.sendParticles(new BlockParticleData(ParticleTypes.BLOCK, world.getBlockState(target.blockPosition().below())), vector3D.x, vector3D.y, vector3D.z, particlesPerDistance, 0.30000001192092896, 0.30000001192092896,
//                            0.30000001192092896, 0.15000000596046448);

                    SoundEvent smashSound = serverPlayer.fallDistance > 5 ? BMSounds.ITEM_QUEEN_LUCY_SUMMONER_STAFF_SMASH_GROUND_HEAVY : BMSounds.ITEM_QUEEN_LUCY_SUMMONER_STAFF_SMASH_GROUND;
                    world.playSound(null, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), smashSound, serverPlayer.getSoundSource(), 1, 1);
                } else {
                    world.playSound(null, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), BMSounds.ITEM_QUEEN_LUCY_SUMMONER_STAFF_SMASH_AIR, serverPlayer.getSoundSource(), 1, 1);
                }
                knockBack(world, serverPlayer, target);
                serverPlayer.fallDistance = 0;
            }
        }
        stack.hurtAndBreak(1, attacker, player -> player.broadcastBreakEvent(Hand.MAIN_HAND));
        return true;
    }

    public float getAttackDamageBonus(DamageSource source) {
        Entity directEntity = source.getDirectEntity();
        if (directEntity instanceof LivingEntity) {
            LivingEntity livEntity = (LivingEntity) directEntity;
            if (!canDoSmashAttack(livEntity)) {
                return 0;
            } else {
                float fallDistance = livEntity.fallDistance;
                float damageBonus;
                if (fallDistance <= 3) {
                    damageBonus = 4 * fallDistance;
                } else if (fallDistance <= 8) {
                    damageBonus = 12 + 2 * (fallDistance - 3);
                } else {
                    damageBonus = 22 + fallDistance - 8;
                }
                return damageBonus;
            }
        } else {
            return 0;
        }
    }

    public Vector3d with(Vector3d originalVector, Direction.Axis axis, double length) {
        double x = axis == Direction.Axis.X ? length : originalVector.x;
        double y = axis == Direction.Axis.Y ? length : originalVector.y;
        double z = axis == Direction.Axis.Z ? length : originalVector.z;
        return new Vector3d(x, y, z);
    }

    @Override
    @Nonnull
    public EntityType<?> getType(@Nullable CompoundNBT tag) {
        if (tag != null && tag.contains("entity_data", TagTypes.COMPOUND)) {
            CompoundNBT entityTag = tag.getCompound("entity_data");
            if (entityTag.contains("id", TagTypes.STRING)) return EntityType.byString(entityTag.getString("id")).orElse(BMEntities.QUEEN_LUCY_PET.get());
        }
        return BMEntities.QUEEN_LUCY_PET.get();
    }

    @Override
    @Nonnull
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlotType slot) {
        return slot == EquipmentSlotType.MAINHAND ? this.attributeModifiers : super.getDefaultAttributeModifiers(slot);
    }

    @Override
    public boolean canAttackBlock(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        return !player.isCreative();
    }

    @Override
    public int getEnchantmentValue() {
        return 15;
    }

    @Override
    public boolean isValidRepairItem(ItemStack thisStack, ItemStack repairStack) {
        return repairStack.getItem().is(BMItemTags.INGOTS_OBSIDIAN);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);
        // Terraria like tooltip, also says what food is used to tame her. Like parrots that don't like cookies, QLPs don't like aljame.
        tooltip.add(new TranslationTextComponent("tooltip.backmath.queen_lucy_summoner_staff").withStyle(TextFormatting.ITALIC));
        tooltip.add(new TranslationTextComponent("tooltip.backmath.empty"));
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment.isIn(BMEnchantmentTags.APPLICABLE_TO_SUMMONER_STAFF) || super.canApplyAtEnchantingTable(stack, enchantment);
    }

    public static void knockBack(World world, PlayerEntity player, Entity entity) {
        world.getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(3.5), knockbackPredicate(player, entity)).forEach((livEntity) -> {
            Vector3d vector3D = livEntity.position().subtract(entity.position());
            double knockbackPower = getKnockbackPower(player, livEntity, vector3D);
            Vector3d vector3D1 = vector3D.normalize().scale(knockbackPower);
            if (knockbackPower > 0) {
                livEntity.push(vector3D1.x, 0.7, vector3D1.z);
                if (livEntity instanceof ServerPlayerEntity) ((ServerPlayerEntity) livEntity).connection.send(new SEntityVelocityPacket(livEntity));
            }
        });
    }

    // Mace methods backported from 1.21 Pre-Release 1, from Yarn mappings.
    public static Predicate<LivingEntity> knockbackPredicate(PlayerEntity player, Entity entity) {
        return (livEntity) -> {
            boolean bool;
            boolean notSpectator;
            boolean typeCheck;
            boolean notSameTeamAs;
            mainChecks: {
                notSpectator = !livEntity.isSpectator();
                typeCheck = livEntity != player && livEntity != entity;
                notSameTeamAs = !player.isAlliedTo(livEntity);
                if (livEntity instanceof TameableEntity) {
                    TameableEntity tameableEntity = (TameableEntity) livEntity;
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
                if (livEntity instanceof ArmorStandEntity) {
                    ArmorStandEntity armorStand = (ArmorStandEntity) livEntity;
                    if (armorStand.isMarker()) {
                        break notMarkerStand;
                    }
                }

                bool = true;
            }
            return notSpectator && typeCheck && notSameTeamAs && notBool && bool && entity.distanceToSqr(livEntity) <= Math.pow(3.5, 2) && !entity.getType().is(BMEntityTypeTags.IMMUNE_TO_SUMMONER_STAFF_SMASHES);
        };
    }

    private static double getKnockbackPower(PlayerEntity player, LivingEntity livEntity, Vector3d vector3D) {
        return (3.5 - vector3D.length()) * 0.7 * (double) (player.fallDistance > 5 ? 2 : 1) * (1 - livEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
    }

    public static boolean canDoSmashAttack(LivingEntity livEntity) {
        return livEntity.fallDistance > 1.5F && !livEntity.isFallFlying() && !livEntity.getType().is(BMEntityTypeTags.IMMUNE_TO_SUMMONER_STAFF_SMASHES);
    }

    public static void spawnSmashAttackParticles(IWorld world, LivingEntity target, int particles) {
        Vector3d vec3D = new Vector3d(target.getOnPos().getX(), target.getOnPos().getY(), target.getOnPos().getZ());
        BlockParticleData blockParticle = new BlockParticleData(ParticleTypes.BLOCK, world.getBlockState(target.blockPosition().below()));

        int count1;
        double x;
        double y;
        double z;
        double xSpeed;
        double ySpeed;
        double zSpeed;

        if (world instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld) world;
            for (count1 = 0; (float) count1 < (float) particles / 3; ++count1) {
                x = vec3D.x + world.getRandom().nextGaussian() / 2;
                y = vec3D.y;
                z = vec3D.z + world.getRandom().nextGaussian() / 2;
                xSpeed = world.getRandom().nextGaussian() * 0.20000000298023224;
                ySpeed = world.getRandom().nextGaussian() * 0.20000000298023224;
                zSpeed = world.getRandom().nextGaussian() * 0.20000000298023224;
                serverWorld.sendParticles(blockParticle, x, y, z, particles, xSpeed, ySpeed + 1.5, zSpeed, 2);
            }

            for (count1 = 0; (float) count1 < (float) particles / 1.5F; ++count1) {
                x = vec3D.x + 3.5 * Math.cos(count1) + world.getRandom().nextGaussian() / 2;
                y = vec3D.y;
                z = vec3D.z + 3.5 * Math.sin(count1) + world.getRandom().nextGaussian() / 2;
                xSpeed = world.getRandom().nextGaussian() * 0.05000000074505806;
                ySpeed = world.getRandom().nextGaussian() * 0.05000000074505806;
                zSpeed = world.getRandom().nextGaussian() * 0.05000000074505806;
                serverWorld.sendParticles(blockParticle, x, y, z, particles, xSpeed, ySpeed + 1.5, zSpeed, 2);
            }
        }
    }
}
