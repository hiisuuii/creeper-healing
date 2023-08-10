package xd.arkosammy.events;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import xd.arkosammy.CreeperHealing;
import xd.arkosammy.handlers.ExplosionHealerHandler;
import xd.arkosammy.util.BlockInfo;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

//Our CreeperExplosion events will each contain a list of the BlockInfos necessary to restore blocks blown up by creepers
public class CreeperExplosionEvent implements Serializable {

    @Serial
    private static final long serialVersionUID = 1212L;
    private static List<CreeperExplosionEvent> explosionEventsForUsage = new CopyOnWriteArrayList<>();
    private List<BlockInfo> blockList;

    private long creeperExplosionDelay;

    private int currentCounter;

    //Create codec for our CreeperExplosionEvent, which will contain a list of BlockInfo codecs.


    public static final Codec<CreeperExplosionEvent> CODEC = RecordCodecBuilder.create(creeperExplosionEventInstance -> creeperExplosionEventInstance.group(

            Codec.list(BlockInfo.CODEC).fieldOf("Block_Info_List").forGetter(CreeperExplosionEvent::getBlockList)

    ).apply(creeperExplosionEventInstance, CreeperExplosionEvent::new));



    public CreeperExplosionEvent(List<BlockInfo> blockList){

        setBlockList(blockList);

        this.creeperExplosionDelay = ExplosionHealerHandler.getExplosionDelay() * 20L;

        this.currentCounter = 0;

    }

    public void setBlockList(List<BlockInfo> blockList){

        this.blockList = blockList;

    }

    public List<BlockInfo> getBlockList(){

        return this.blockList;

    }

    public long getCreeperExplosionDelay(){

        return this.creeperExplosionDelay;

    }

    public void incrementCounter(){

        this.currentCounter++;

    }

    public static List<CreeperExplosionEvent> getExplosionEventsForUsage(){

        return explosionEventsForUsage;

    }

    public static void tickCreeperExplosionEvents(){

        for(CreeperExplosionEvent creeperExplosionEvent : CreeperExplosionEvent.getExplosionEventsForUsage()){

            creeperExplosionEvent.tickSingleEvent();

        }

        for(CreeperExplosionEvent creeperExplosionEvent : CreeperHealing.SCHEDULED_CREEPER_EXPLOSIONS.getScheduledCreeperExplosionsForStoring()){

            creeperExplosionEvent.tickSingleEvent();

        }

    }

    private void tickSingleEvent(){

        this.creeperExplosionDelay--;

    }

    public BlockInfo getCurrentBlockInfo(){

        if(this.currentCounter < this.getBlockList().size()){

            return this.getBlockList().get(currentCounter);

        }

        return null;

    }

}
