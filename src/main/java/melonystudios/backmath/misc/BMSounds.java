package melonystudios.backmath.misc;

import melonystudios.backmath.BackMath;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BMSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, BackMath.MOD_ID);

    // Items
    public static final DeferredHolder<SoundEvent, SoundEvent> MID_TERM_ACTIVATE_ENDER_EYE = SOUNDS.register("item.mid_term.activate.ender_eye", () -> SoundEvent.createVariableRangeEvent(BackMath.backMath("item.mid_term.activate.ender_eye")));
    public static final DeferredHolder<SoundEvent, SoundEvent> MID_TERM_ACTIVATE_BEACON = SOUNDS.register("item.mid_term.activate.beacon", () -> SoundEvent.createVariableRangeEvent(BackMath.backMath("item.mid_term.activate.beacon")));
    public static final DeferredHolder<SoundEvent, SoundEvent> MID_TERM_DEACTIVATE_ENDER_EYE = SOUNDS.register("item.mid_term.deactivate.ender_eye", () -> SoundEvent.createVariableRangeEvent(BackMath.backMath("item.mid_term.deactivate.ender_eye")));
    public static final DeferredHolder<SoundEvent, SoundEvent> MID_TERM_DEACTIVATE_BEACON = SOUNDS.register("item.mid_term.deactivate.beacon", () -> SoundEvent.createVariableRangeEvent(BackMath.backMath("item.mid_term.deactivate.beacon")));
    public static final DeferredHolder<SoundEvent, SoundEvent> CHOCOGLUE_SHOOT = SOUNDS.register("item.chocoglue.shoot", () -> SoundEvent.createVariableRangeEvent(BackMath.backMath("item.chocoglue.shoot")));
    public static final DeferredHolder<SoundEvent, SoundEvent> SUMMONER_STAFF_HEAVY_SMASH = SOUNDS.register("item.summoner_staff.heavy_smash", () -> SoundEvent.createVariableRangeEvent(BackMath.backMath("item.summoner_staff.heavy_smash")));
    public static final DeferredHolder<SoundEvent, SoundEvent> SUMMONER_STAFF_SMASH = SOUNDS.register("item.summoner_staff.smash", () -> SoundEvent.createVariableRangeEvent(BackMath.backMath("item.summoner_staff.smash")));
    public static final DeferredHolder<SoundEvent, SoundEvent> SUMMONER_STAFF_AIR_SMASH = SOUNDS.register("item.summoner_staff.air_smash", () -> SoundEvent.createVariableRangeEvent(BackMath.backMath("item.summoner_staff.air_smash")));

    // Music Discs
    public static final DeferredHolder<SoundEvent, SoundEvent> MUSIC_DISC_WELLERMAN = SOUNDS.register("music_disc.wellerman", () -> SoundEvent.createVariableRangeEvent(BackMath.backMath("music_disc.wellerman")));
    public static final DeferredHolder<SoundEvent, SoundEvent> MUSIC_DISC_SNOWMAN = SOUNDS.register("music_disc.snowman", () -> SoundEvent.createVariableRangeEvent(BackMath.backMath("music_disc.snowman")));
    public static final DeferredHolder<SoundEvent, SoundEvent> MUSIC_DISC_DUET_CHALLENGE = SOUNDS.register("music_disc.duet_challenge", () -> SoundEvent.createVariableRangeEvent(BackMath.backMath("music_disc.duet_challenge")));
    public static final DeferredHolder<SoundEvent, SoundEvent> MUSIC_DISC_ARCADE = SOUNDS.register("music_disc.arcade", () -> SoundEvent.createVariableRangeEvent(BackMath.backMath("music_disc.arcade")));
    public static final DeferredHolder<SoundEvent, SoundEvent> MUSIC_DISC_SUGAR_CRASH = SOUNDS.register("music_disc.sugar_crash", () -> SoundEvent.createVariableRangeEvent(BackMath.backMath("music_disc.sugar_crash")));
    public static final DeferredHolder<SoundEvent, SoundEvent> MUSIC_DISC_O_ONIBUS_VAI_DERRAPAR = SOUNDS.register("music_disc.o_onibus_vai_derrapar", () -> SoundEvent.createVariableRangeEvent(BackMath.backMath("music_disc.o_onibus_vai_derrapar")));
    public static final DeferredHolder<SoundEvent, SoundEvent> MUSIC_DISC_IEVAN_POLKKA = SOUNDS.register("music_disc.ievan_polkka", () -> SoundEvent.createVariableRangeEvent(BackMath.backMath("music_disc.ievan_polkka")));
    public static final DeferredHolder<SoundEvent, SoundEvent> MUSIC_DISC_NEVER_GONNA_GIVE_YOU_UP = SOUNDS.register("music_disc.never_gonna_give_you_up", () -> SoundEvent.createVariableRangeEvent(BackMath.backMath("music_disc.never_gonna_give_you_up")));
    public static final DeferredHolder<SoundEvent, SoundEvent> MUSIC_DISC_BEGGIN = SOUNDS.register("music_disc.beggin", () -> SoundEvent.createVariableRangeEvent(BackMath.backMath("music_disc.beggin")));
    public static final DeferredHolder<SoundEvent, SoundEvent> MUSIC_DISC_STAY = SOUNDS.register("music_disc.stay", () -> SoundEvent.createVariableRangeEvent(BackMath.backMath("music_disc.stay")));
    public static final DeferredHolder<SoundEvent, SoundEvent> MUSIC_DISC_THE_FLYING_ARM = SOUNDS.register("music_disc.the_flying_arm", () -> SoundEvent.createVariableRangeEvent(BackMath.backMath("music_disc.the_flying_arm")));
    public static final DeferredHolder<SoundEvent, SoundEvent> MUSIC_DISC_DRAMATIC_INTRO_1 = SOUNDS.register("music_disc.dramatic_intro_1", () -> SoundEvent.createVariableRangeEvent(BackMath.backMath("music_disc.dramatic_intro_1")));
    public static final DeferredHolder<SoundEvent, SoundEvent> MUSIC_DISC_DRAMATIC_INTRO_2 = SOUNDS.register("music_disc.dramatic_intro_2", () -> SoundEvent.createVariableRangeEvent(BackMath.backMath("music_disc.dramatic_intro_2")));
    public static final DeferredHolder<SoundEvent, SoundEvent> MUSIC_DISC_DRAMATIC_INTRO_3 = SOUNDS.register("music_disc.dramatic_intro_3", () -> SoundEvent.createVariableRangeEvent(BackMath.backMath("music_disc.dramatic_intro_3")));
}
