//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package tb.module.chat;

import java.awt.Color;
import java.util.Optional;

import net.md_5.bungee.api.ChatColor;
import org.jetbrains.annotations.NotNull;
import taboolib.module.chat.StandardColors;

public class HexColor {

    private static boolean isLegacy = false;

    @NotNull
    public static String translate(String in) {
        if (isLegacy) {
            return ChatColor.translateAlternateColorCodes('&', in).replace("&#", "§#");
        } else {
            StringBuilder builder = new StringBuilder();
            char[] chars = in.toCharArray();

            for (int i = 0; i < chars.length; ++i) {
                if (i + 1 < chars.length && chars[i] == '&' && chars[i + 1] == '{') {
                    ChatColor chatColor = null;
                    char[] match = new char[0];

                    for (int j = i + 2; j < chars.length && chars[j] != '}'; ++j) {
                        match = arrayAppend(match, chars[j]);
                    }

                    if (match.length != 11 || match[3] != ',' && match[3] != '-' || match[7] != ',' && match[7] != '-') {
                        if (match.length == 7 && match[0] == '#') {
                            try {
                                chatColor = ChatColor.of(toString(match));
                            } catch (IllegalArgumentException ignored) {
                            }
                        } else {
                            Optional<StandardColors> knownColor = StandardColors.match(toString(match));
                            if (knownColor.isPresent()) {
                                chatColor = knownColor.get().toChatColor();
                            }
                        }
                    } else {
                        chatColor = ChatColor.of(new Color(toInt(match, 0, 3), toInt(match, 4, 7), toInt(match, 8, 11)));
                    }

                    if (chatColor != null) {
                        builder.append(chatColor);
                        i += match.length + 2;
                    }
                } else {
                    builder.append(chars[i]);
                }
            }

            return ChatColor.translateAlternateColorCodes('&', builder.toString());
        }
    }

    public static String getColorCode(int color) {
        return ChatColor.of(new Color(color)).toString();
    }

    private static char[] arrayAppend(char[] chars, char in) {
        char[] newChars = new char[chars.length + 1];
        System.arraycopy(chars, 0, newChars, 0, chars.length);
        newChars[chars.length] = in;
        return newChars;
    }

    private static String toString(char[] chars) {
        StringBuilder builder = new StringBuilder();

        for (char c : chars) {
            builder.append(c);
        }

        return builder.toString();
    }

    private static int toInt(char[] chars, int start, int end) {
        StringBuilder builder = new StringBuilder();

        for (int i = start; i < end; ++i) {
            builder.append(chars[i]);
        }

        return Integer.parseInt(builder.toString());
    }

    static {
        try {
            ChatColor.of(Color.BLACK);
        } catch (NoSuchMethodError var1) {
            isLegacy = true;
        }

    }

}