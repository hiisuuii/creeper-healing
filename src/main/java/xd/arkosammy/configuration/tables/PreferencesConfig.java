package xd.arkosammy.configuration.tables;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import xd.arkosammy.CreeperHealing;
import xd.arkosammy.configuration.ConfigEntry;
import java.util.ArrayList;
import java.util.List;

public final class PreferencesConfig {

    private PreferencesConfig(){}
    private static final List<ConfigEntry<Boolean>> preferencesEntryList = new ArrayList<>();
    private static final String TABLE_NAME = "preferences";
    private static final String TABLE_COMMENT = """
            Toggleable settings to customize the healing of explosions.""";

    static {

        preferencesEntryList.add(new ConfigEntry<>("heal_on_flowing_water", true, """
                (Default = true) Whether or not blocks should be healed where there is currently flowing water."""));

        preferencesEntryList.add(new ConfigEntry<>("heal_on_flowing_lava", true, """
                (Default = true) Whether or not blocks should be healed where there is currently flowing lava."""));

        preferencesEntryList.add(new ConfigEntry<>("block_placement_sound_effect", true, """
                (Default = true) Whether or not a block heal should play a sound effect."""));

        preferencesEntryList.add(new ConfigEntry<>("drop_items_on_explosions", true, """
                (Default = true) Whether or not explosions should drop items."""));

        preferencesEntryList.add(new ConfigEntry<>("requires_light", false, """
                (Default = false) Whether or not explosions will need light to heal."""));

    }

    public static void setHealOnFlowingWater(boolean healOnFlowingWater){

        for(ConfigEntry<Boolean> configEntry : getPreferencesEntryList()){

            if(configEntry.getName().equals("heal_on_flowing_water")){

                configEntry.setValue(healOnFlowingWater);

            }

        }

    }

    public static void setHealOnFlowingLava(boolean healOnFlowingLava){

        for(ConfigEntry<Boolean> configEntry : getPreferencesEntryList()){

            if(configEntry.getName().equals("heal_on_flowing_lava")){

                configEntry.setValue(healOnFlowingLava);

            }

        }

    }

    public static void setBlockPlacementSoundEffect(boolean blockPlacementSoundEffect){

        for(ConfigEntry<Boolean> configEntry : getPreferencesEntryList()){

            if(configEntry.getName().equals("block_placement_sound_effect")){

                configEntry.setValue(blockPlacementSoundEffect);

            }

        }

    }

    public static void setDropItemsOnExplosions(boolean dropItemsOnExplosions){

        for(ConfigEntry<Boolean> configEntry : getPreferencesEntryList()){

            if(configEntry.getName().equals("drop_items_on_explosions")){

                configEntry.setValue(dropItemsOnExplosions);

            }

        }

    }

    public static void setRequiresLight(boolean requiresLight){

        for(ConfigEntry<Boolean> configEntry : getPreferencesEntryList()){

            if(configEntry.getName().equals("requires_light")){

                configEntry.setValue(requiresLight);

            }

        }

    }

    public static Boolean getHealOnFlowingWater(){

        Boolean boolToReturn = getValueForNameFromMemory("heal_on_flowing_water");

        if(boolToReturn == null) return true;

        return boolToReturn;

    }

    public static Boolean getHealOnFlowingLava(){

        Boolean boolToReturn = getValueForNameFromMemory("heal_on_flowing_lava");

        if(boolToReturn == null) return true;

        return boolToReturn;

    }

    public static Boolean getBlockPlacementSoundEffect(){

        Boolean boolToReturn = getValueForNameFromMemory("block_placement_sound_effect");

        if(boolToReturn == null) return true;

        return boolToReturn;

    }

    public static Boolean getDropItemsOnExplosions(){

        Boolean boolToReturn = getValueForNameFromMemory("drop_items_on_explosions");

        if(boolToReturn == null) return true;

        return boolToReturn;

    }

    public static Boolean getRequiresLight(){

        Boolean boolToReturn = getValueForNameFromMemory("requires_light");

        if(boolToReturn == null) return false;

        return boolToReturn;

    }

    private static List<ConfigEntry<Boolean>> getPreferencesEntryList(){
        return preferencesEntryList;
    }

    public static void saveDefaultSettingsToFile(CommentedFileConfig fileConfig){

        for(ConfigEntry<Boolean> configEntry : getPreferencesEntryList()){
            configEntry.resetValue();
        }

        saveSettingsToFile(fileConfig);

    }

    public static void saveSettingsToFile(CommentedFileConfig fileConfig){

        for(ConfigEntry<Boolean> entry : getPreferencesEntryList()){

            fileConfig.set(
                    TABLE_NAME + "." + entry.getName(),
                    entry.getValue()
            );

            String entryComment = entry.getComment();

            if(entryComment != null)
                fileConfig.setComment(
                        TABLE_NAME + "." + entry.getName(),
                        entryComment
                );

        }

        fileConfig.setComment(TABLE_NAME, TABLE_COMMENT);

    }

    public static void loadSettingsToMemory(CommentedFileConfig fileConfig){

        for(ConfigEntry<Boolean> configEntry : getPreferencesEntryList()){

            Object value = fileConfig.getOrElse(TABLE_NAME + "." + configEntry.getName(), configEntry.getDefaultValue());

            if(value instanceof Boolean boolValue){

                configEntry.setValue(boolValue);

            } else {

                CreeperHealing.LOGGER.error("Invalid value in config file for setting: " + configEntry.getName());

            }


        }

    }

    private static Boolean getValueForNameFromMemory(String settingName){

        for(ConfigEntry<Boolean> entry : getPreferencesEntryList()){

            if(entry.getName().equals(settingName)){

                return entry.getValue();

            }

        }

        return null;

    }

}