package com.denizenscript.ddiscordbot.events;

import com.denizenscript.ddiscordbot.DiscordScriptEvent;
import com.denizenscript.ddiscordbot.objects.DiscordGroupTag;
import com.denizenscript.ddiscordbot.objects.DiscordUserTag;
import com.denizenscript.denizencore.objects.ObjectTag;
import com.denizenscript.denizencore.objects.core.ElementTag;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateNicknameEvent;

public class DiscordUserNicknameChangeScriptEvent extends DiscordScriptEvent {

    // <--[event]
    // @Events
    // discord user nickname changes
    //
    // @Switch for:<bot> to only process the event for a specified Discord bot.
    // @Switch group:<group_id> to only process the event for a specified Discord group.
    //
    // @Triggers when a Discord user's nickname change.
    //
    // @Plugin dDiscordBot
    //
    // @Group Discord
    //
    // @Context
    // <context.bot> returns the relevant DiscordBotTag.
    // <context.group> returns the DiscordGroupTag.
    // <context.user> returns the DiscordUserTag.
    // <context.old_name> returns the user's previous nickname (if any).
    // <context.new_name> returns the user's new nickname (if any).
    // -->

    public static DiscordUserNicknameChangeScriptEvent instance;

    public DiscordUserNicknameChangeScriptEvent() {
        instance = this;
        registerCouldMatcher("discord user nickname changes");
        registerSwitches("group");
    }

    public GuildMemberUpdateNicknameEvent getEvent() {
        return (GuildMemberUpdateNicknameEvent) event;
    }

    @Override
    public boolean matches(ScriptPath path) {
        if (!tryGuild(path, getEvent().getGuild())) {
            return false;
        }
        return super.matches(path);
    }

    @Override
    public ObjectTag getContext(String name) {
        switch (name) {
            case "group":
                return new DiscordGroupTag(botID, getEvent().getGuild().getIdLong());
            case "user":
                return new DiscordUserTag(botID, getEvent().getUser().getIdLong());
            case "old_name":
                if (getEvent().getOldNickname() == null) {
                    return null;
                }
                return new ElementTag(getEvent().getOldNickname());
            case "new_name":
                if (getEvent().getNewNickname() == null) {
                    return null;
                }
                return new ElementTag(getEvent().getNewNickname());
        }
        return super.getContext(name);
    }
}
