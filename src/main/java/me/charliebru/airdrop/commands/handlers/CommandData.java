package me.charliebru.airdrop.commands.handlers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CommandData {
    String getName();
    String getDescription();
    String getUsage();
    String getPermission();
    boolean isConsole();
    boolean isPlayer();
    int getLength();
}
