package xd.arkosammy.creeperhealing.explosions;

import net.minecraft.util.math.random.Random;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import xd.arkosammy.creeperhealing.blocks.AffectedBlock;
import xd.arkosammy.creeperhealing.configuration.DelaysConfig;

import java.util.List;

public class DifficultyBasedExplosionEvent extends AbstractExplosionEvent {

    public DifficultyBasedExplosionEvent(List<AffectedBlock> affectedBlocks, long healTimer, int blockCounter) {
        super(affectedBlocks, healTimer, blockCounter);
    }

    DifficultyBasedExplosionEvent(List<AffectedBlock> affectedBlocks){
        super(affectedBlocks);
    }

    @Override
    ExplosionHealingMode getHealingMode(){
        return ExplosionHealingMode.DIFFICULTY_BASED_HEALING_MODE;
    }

    @Override
    public void setupExplosion(World world){
        int difficultyMultiplier = switch (world.getDifficulty()) {
            case PEACEFUL -> -2;
            case EASY -> -1;
            case NORMAL -> 1;
            case HARD -> 2;
        };
        long newBlockTimer = Math.max(1, (DelaysConfig.getBlockPlacementDelayAsTicks()) + (difficultyMultiplier * 20));
        long newExplosionTimer = Math.max(1, (DelaysConfig.getExplosionHealDelayAsTicks()) + (difficultyMultiplier * 20));
        this.setHealTimer(newExplosionTimer);
        this.getAffectedBlocks().forEach(affectedBlock -> affectedBlock.setAffectedBlockTimer(newBlockTimer));
    }

    // 1/50 chance of the explosion stopping its healing process if the difficulty is hard
    @Override
    public boolean shouldKeepHealing(World world){
        if (world.getDifficulty() != Difficulty.HARD) {
            return true;
        }
        Random random = world.getRandom();
        return random.nextBetween(0, 50) != 1;
    }

}
