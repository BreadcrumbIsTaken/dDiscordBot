package com.denizenscript.ddiscordbot.events;

import com.denizenscript.ddiscordbot.DiscordScriptEvent;
import com.denizenscript.ddiscordbot.objects.*;
import com.denizenscript.denizencore.objects.ObjectTag;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;

public class DiscordMessageReactionAddScriptEvent extends DiscordScriptEvent {

    // <--[event]
    // @Events
    // discord message reaction added
    //
    // @Switch for:<bot> to only process the event for a specified Discord bot.
    // @Switch channel:<channel_id> to only process the event when it occurs in a specified Discord channel.
    // @Switch group:<group_id> to only process the event for a specified Discord group.
    //
    // @Triggers when a Discord user added a reaction to a message.
    //
    // @Plugin dDiscordBot
    //
    // @Group Discord
    //
    // @Context
    // <context.bot> returns the relevant DiscordBotTag.
    // <context.channel> returns the DiscordChannelTag.
    // <context.group> returns the DiscordGroupTag.
    // <context.message> returns the message.
    // <context.user> returns the user that added the reaction.
    // <context.reaction> returns the new reaction.
    //
    // -->

    public static DiscordMessageReactionAddScriptEvent instance;

    public DiscordMessageReactionAddScriptEvent() {
        instance = this;
        registerCouldMatcher("discord message reaction added");
        registerSwitches("channel", "group");
    }

    public MessageReactionAddEvent getEvent() {
        return (MessageReactionAddEvent) event;
    }

    @Override
    public boolean matches(ScriptPath path) {
        if (!tryChannel(path, getEvent().getChannel())) {
            return false;
        }
        if (!tryGuild(path, getEvent().isFromGuild() ? getEvent().getGuild() : null)) {
            return false;
        }
        return super.matches(path);
    }

    @Override
    public ObjectTag getContext(String name) {
        switch (name) {
            case "channel":
                return new DiscordChannelTag(botID, getEvent().getChannel());
            case "group":
                if (getEvent().isFromGuild()) {
                    return new DiscordGroupTag(botID, getEvent().getGuild());
                }
                break;
            case "message":
                return new DiscordMessageTag(botID, getEvent().getChannel().getIdLong(), getEvent().getMessageIdLong());
            case "reaction":
                return new DiscordReactionTag(botID, getEvent().getChannel().getIdLong(), getEvent().getMessageIdLong(), getEvent().getReaction());
            case "user":
                return new DiscordUserTag(botID, getEvent().getUserIdLong());
        }
        return super.getContext(name);
    }
}
