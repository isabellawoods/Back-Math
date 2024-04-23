package com.sophicreeper.backmath.entity.goal.queenlucy;

import com.sophicreeper.backmath.entity.BMEntities;
import com.sophicreeper.backmath.entity.custom.ArcherInsomniaSophie;
import com.sophicreeper.backmath.entity.custom.QueenLucy;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.SpawnReason;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class SummonArcherInsomniaSophiesGoal extends CastSpellGoal {
    private final EntityPredicate predicate = new EntityPredicate().range(16).allowInvulnerable().allowSameTeam();
    private final QueenLucy queenLucy;

    public SummonArcherInsomniaSophiesGoal(QueenLucy queenLucy) {
        super(queenLucy);
        this.queenLucy = queenLucy;
    }

    @Override
    public boolean canUse() {
        if (super.canUse()) {
            int nearbyEntityCount = this.queenLucy.level.getNearbyEntities(ArcherInsomniaSophie.class, this.predicate, this.queenLucy, this.queenLucy.getBoundingBox().inflate(16)).size();
            return this.queenLucy.getRandom().nextInt(8) + 1 > nearbyEntityCount;
        }
        return false;
    }

    @Override
    protected void castSpell() {
        ServerWorld world = (ServerWorld) this.queenLucy.level;

        for (int i = 0; i < 3; ++i) {
            BlockPos pos = this.queenLucy.blockPosition().offset(-2 + this.queenLucy.getRandom().nextInt(5), 1, -2 + this.queenLucy.getRandom().nextInt(5));
            ArcherInsomniaSophie archerInsomniaSophie = BMEntities.ARCHER_INSOMNIA_SOPHIE.get().create(this.queenLucy.level);
            archerInsomniaSophie.moveTo(pos, 0, 0);
            archerInsomniaSophie.finalizeSpawn(world, this.queenLucy.level.getCurrentDifficultyAt(pos), SpawnReason.MOB_SUMMONED, null, null);
            world.addFreshEntity(archerInsomniaSophie);
        }
    }

    @Override
    protected int getCastingTime() {
        return 100;
    }

    @Override
    protected int getCastingInterval() {
        return 340;
    }

    @Override
    protected QueenLucySpells getSpellType() {
        return QueenLucySpells.SUMMON_ARCHER_INSOMNIA_SOPHIES;
    }
}
