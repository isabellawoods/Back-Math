package com.sophicreeper.backmath.core.world.entity.monster.aljan;

/*public class Janticle extends MonsterEntity {
    public Janticle(World world) {
        this(BMEntities.JANTICLE.get(), world);
    }

    public Janticle(EntityType<Janticle> entity, World world) {
        super(entity, world);
        this.moveController = new FlyingMovementController(this, 10, false);
        this.setPathPriority(PathNodeType.DANGER_FIRE, -1);
        this.setPathPriority(PathNodeType.DAMAGE_FIRE, -1);
        this.setPathPriority(PathNodeType.COCOA, -1);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2d, true));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomFlyingGoal(this, 1));
        this.goalSelector.addGoal(4, new LookAtGoal(this, Janticle.class, 8));
        this.goalSelector.addGoal(4, new LookAtGoal(this, InsomniaZombie.class, 8));
        this.goalSelector.addGoal(4, new LookAtGoal(this, ZombieFabricio.class, 8));
        this.goalSelector.addGoal(5, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, false));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Malaika.class, false));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, QueenSophie.class, false));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, WolfEntity.class, false));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, CatEntity.class, false));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, ParrotEntity.class, false));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, QueenSophiePet.class, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, WandererSophie.class, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, InsomniaSophie.class, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, ArcherInsomniaSophie.class, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, ArcherLucia.class, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, WarriorSophie.class, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, KarateLucia.class, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, ShyFabricio.class, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, SnowGolemEntity.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, false));
        super.registerGoals();
    }

    public static AttributeModifierMap.MutableAttribute createJanticleAttributes() {
        return MonsterEntity.func_234295_eP_()
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3)
                .createMutableAttribute(Attributes.MAX_HEALTH, 15)
                .createMutableAttribute(Attributes.FLYING_SPEED, 0.4f)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 2);
    }

    public void tick() {
        Vector3d vector3D = this.getMotion();
        if (this.world.isRemote) {
            this.world.addParticle(new BlockParticleData(ParticleTypes.BLOCK, Blocks.DIAMOND_BLOCK.getDefaultState()), this.getPosX() - vector3D.x, this.getPosY() - vector3D.y + 0.15D, this.getPosZ() - vector3D.z, 0.0D, 0.0D, 0.0D);
            this.world.addParticle(ParticleTypes.WARPED_SPORE, this.getPosX() - vector3D.x, this.getPosY() - vector3D.y + 0.15D, this.getPosZ() - vector3D.z, 0.0D, 0.0D, 0.0D);
        }
        super.tick();
    }

    @Override
    public boolean onLivingFall(float distance, float damageMultiplier) {
        return false;
    }

    @Override
    protected void updateFallState(double y, boolean onGround, BlockState state, BlockPos pos) {}

    @Override
    protected PathNavigator createNavigator(World world) {
        FlyingPathNavigator flyingPathNavigator = new FlyingPathNavigator(this, world);
        flyingPathNavigator.setCanSwim(true);
        flyingPathNavigator.setCanEnterDoors(true);
        return flyingPathNavigator;
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntitySize size) {
        return 0.25f;
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(AxolotlTest.JANTICLE_SPAWN_EGG.get());
    }
}*/
