package net.oxy.echostalkermod.entity.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;

public class EchostalkerEntity extends Monster implements GeoEntity {

    protected EchostalkerEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    // Data & synchronization
    private static final EntityDataAccessor<Boolean> IS_STARING = SynchedEntityData.defineId(
              EchostalkerEntity.class, EntityDataSerializers.BOOLEAN
    );
    private static final EntityDataAccessor<Boolean> IS_STALKING = SynchedEntityData.defineId(
            EchostalkerEntity.class, EntityDataSerializers.BOOLEAN
    );
    private static final EntityDataAccessor<Boolean> IS_CHASING = SynchedEntityData.defineId(
            EchostalkerEntity.class, EntityDataSerializers.BOOLEAN
    );
    private static final EntityDataAccessor<Boolean> IS_FLEEING = SynchedEntityData.defineId(
            EchostalkerEntity.class, EntityDataSerializers.BOOLEAN
    );
    private static final EntityDataAccessor<Boolean> IS_TRICK_BEHAVIOR = SynchedEntityData.defineId(
            EchostalkerEntity.class, EntityDataSerializers.BOOLEAN
    );
    private static final EntityDataAccessor<Integer> ECHOSTALKER_STATUS_TRACKER = SynchedEntityData.defineId(
            EchostalkerEntity.class, EntityDataSerializers.INT
    );

    public boolean isStaring() { return this.entityData.get(IS_STARING);}
    public boolean isStalking() { return this.entityData.get(IS_STALKING);}
    public boolean isChasing() { return this.entityData.get(IS_CHASING);}
    public boolean isFleeing() { return this.entityData.get(IS_FLEEING);}
    public boolean isTricking() { return this.entityData.get(IS_TRICK_BEHAVIOR);}
    public ECHOSTALKER_STATUS echostalkerStatus() {
        return (ECHOSTALKER_STATUS.values()[this.entityData.get(ECHOSTALKER_STATUS_TRACKER)]);
    }

    public void setStaring(boolean isStaring) {this.entityData.set(IS_STARING, isStaring);}
    public void setStalking(boolean isStalking) {this.entityData.set(IS_STALKING, isStalking);}
    public void setChasing (boolean isChasing) {this.entityData.set(IS_CHASING, isChasing);}
    public void setFleeing(boolean isFleeing) {this.entityData.set(IS_FLEEING, isFleeing);}
    public void setTricking(boolean isTricking) {this.entityData.set(IS_TRICK_BEHAVIOR, isTricking);}

    public boolean getFakeFleeing() {
        return this.entityData.get(IS_FLEEING) && this.entityData.get(IS_TRICK_BEHAVIOR);}
    public boolean getFakeChasing() {
        return this.entityData.get(IS_CHASING) && this.entityData.get(IS_TRICK_BEHAVIOR);}

    public void setFakeFleeing(Boolean toggle) {
        this.setTricking(toggle);
        this.setFleeing(toggle);
    }
    public void setFakeChasing(Boolean toggle) {
        this.setTricking(toggle);
        this.setChasing(toggle);
    }


    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();

        this.entityData.define(IS_STARING, false);
        this.entityData.define(IS_STALKING, false);
        this.entityData.define(IS_CHASING, false);
        this.entityData.define(IS_FLEEING, false);
        this.entityData.define(IS_TRICK_BEHAVIOR, false);
        this.entityData.define(ECHOSTALKER_STATUS_TRACKER, 0);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setStaring(pCompound.getBoolean("isStaring"));
        this.setStalking(pCompound.getBoolean("isStalking"));
        this.setChasing(pCompound.getBoolean("isChasing"));
        this.setFleeing(pCompound.getBoolean("isFleeing"));
        this.setTricking(pCompound.getBoolean("isTricking"));
        this.setEchostalkerStatus(pCompound.getInt("echostalkerStatus"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("isStaring", this.isStaring());
        pCompound.putBoolean("isStalking", this.isStalking());
        pCompound.putBoolean("isChasing", this.isChasing());
        pCompound.putBoolean("isFleeing", this.isFleeing());
        pCompound.putBoolean("isTricking", this.isTricking());
        pCompound.putInt("echostalkerStatus", this.getEchostalkerRawStatus());
    }

    // Condition checks
    private boolean isMoving() {
        return !(this.xOld == this.getX() && this.yOld == this.getY() && this.zOld == this.getZ());
    }

    private boolean isBeingStaredAt(Player player, Double distance) {

        if (!(player == null || player.isCreative() || player.isSpectator() || player.isInvisible())) {
            Vec3 playerEyePos = player.getEyePosition();
            Vec3 playerLookVec = player.getViewVector(1.0F).normalize();
            Vec3 entityPos = this.position();

            Vec3 directionToEntity = entityPos.subtract(playerEyePos).normalize();
            double distanceToEntity = playerEyePos.distanceTo(entityPos);

            if (distanceToEntity > distance) return false;

            double doProduct = directionToEntity.dot(playerLookVec);
            double angleThreshold = Math.cos(Math.toRadians(15));

            return doProduct >= angleThreshold;
        }

        return false;
    }


    // Status management
    private enum ECHOSTALKER_STATUS {
        IDLE,
        WANDER,
        STALK,
        STARE,
        CHASE,
        FAKE_CHASE,
        FLEE,
        FAKE_FLEE
    }

    public ECHOSTALKER_STATUS getEchostalkerStatus() {
        return ECHOSTALKER_STATUS.values()[this.entityData.get(ECHOSTALKER_STATUS_TRACKER)];
    }
    public int getEchostalkerRawStatus() {
        return this.entityData.get(ECHOSTALKER_STATUS_TRACKER);
    }

    public void setEchostalkerStatus(ECHOSTALKER_STATUS status) {
        this.entityData.set(ECHOSTALKER_STATUS_TRACKER,
                status.ordinal());
    }
    public void setEchostalkerStatus(int status) {
        this.entityData.set(ECHOSTALKER_STATUS_TRACKER,
                status);
    }

    public void updateEchostalkerStatus() {

    }

    // Goal & behaviors
    @Override
    protected void registerGoals() {
        super.registerGoals();
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    protected boolean canRide(Entity pVehicle) {
        return super.canRide(pVehicle);
    }

    @Override
    public void checkDespawn() {
        super.checkDespawn();
    }


    // Animation management
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return null;
    }
}
