package melonystudios.backmath.component.custom;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import melonystudios.backmath.util.BMUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.UUID;

public record SuperchargeSettings(List<ConnectedPlayer> connectedPlayers, int duration) {
    public static final SuperchargeSettings DEFAULT = new SuperchargeSettings(Lists.newArrayList(), -1);
    private static final Codec<SuperchargeSettings> FULL_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ConnectedPlayer.CODEC.listOf().fieldOf("connected_players").forGetter(SuperchargeSettings::connectedPlayers),
            ExtraCodecs.intRange(-1, Integer.MAX_VALUE).optionalFieldOf("duration", -1).forGetter(SuperchargeSettings::duration)).apply(instance, SuperchargeSettings::new));
    public static final Codec<SuperchargeSettings> CODEC = Codec.withAlternative(FULL_CODEC, ConnectedPlayer.CODEC.listOf(), connectedPlayer -> new SuperchargeSettings(connectedPlayer, -1));
    public static final StreamCodec<FriendlyByteBuf, SuperchargeSettings> STREAM_CODEC = StreamCodec.composite(ConnectedPlayer.STREAM_CODEC.apply(ByteBufCodecs.list()), SuperchargeSettings::connectedPlayers, ByteBufCodecs.INT,
            SuperchargeSettings::duration, SuperchargeSettings::new);

    public SuperchargeSettings tick(int increment) {
        return new SuperchargeSettings(this.connectedPlayers, this.duration + increment);
    }

    public SuperchargeSettings addPlayer(Player player, int duration) {
        List<ConnectedPlayer> players = this.connectedPlayers;
        players.add(new ConnectedPlayer(player.getUUID()));
        return new SuperchargeSettings(players, duration);
    }

    public SuperchargeSettings clearPlayers() {
        return new SuperchargeSettings(Lists.newArrayList(), this.duration);
    }

    public record ConnectedPlayer(UUID playerUUID) {
        public static final Codec<ConnectedPlayer> CODEC = RecordCodecBuilder.create(instance -> instance.group(BMUtils.UUID_CODEC.fieldOf("uuid").forGetter(ConnectedPlayer::playerUUID))
                .apply(instance, ConnectedPlayer::new));
        public static final StreamCodec<FriendlyByteBuf, ConnectedPlayer> STREAM_CODEC = StreamCodec.composite(BMUtils.STREAM_UUID_CODEC, ConnectedPlayer::playerUUID, ConnectedPlayer::new);
    }
}
