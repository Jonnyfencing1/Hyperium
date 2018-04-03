package cc.hyperium.mods.autogg;

import cc.hyperium.event.ChatEvent;
import cc.hyperium.event.InvokeEvent;
import net.minecraft.util.EnumChatFormatting;

public class GGListener
{
    private static String unformattedMessage;

    @InvokeEvent
    public void onChat(final ChatEvent event) {
        if (!AutoGG.getInstance().isHypixel() || !AutoGG.getInstance().isToggled() || AutoGG.getInstance().isRunning() || AutoGG.getInstance().getTriggers().isEmpty()) {
            return;
        }
        GGListener.unformattedMessage = event.getChat().getUnformattedText();
        GGListener.unformattedMessage = EnumChatFormatting.getTextWithoutFormattingCodes(GGListener.unformattedMessage);
        if (AutoGG.getInstance().getTriggers().stream().anyMatch(trigger -> GGListener.unformattedMessage.contains(trigger.toString())) && GGListener.unformattedMessage.startsWith(" ")) {
            AutoGG.getInstance().setRunning(true);
            AutoGG.THREAD_POOL.submit(new GGThread());
        }
    }
}