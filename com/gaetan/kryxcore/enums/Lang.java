package com.gaetan.kryxcore.enums;

import com.gaetan.api.message.Message;

public enum Lang {
    PREFIX(Message.GOLD + Message.BOLD + "KryxMC" + Message.WHITE + Message.BOLD + " » "),
    STAFF_ON(Message.GREEN + "Vous venez d'activer le StaffMode."),
    STAFF_OFF(Message.RED + "Vous venez de désactiver le StaffMode."),
    VANISH_ON(Message.GREEN + "Vous venez d'activer le vanish."),
    VANISH_OFF(Message.RED + "Vous venez de désactiver le vanish."),
    FREEZE_MESSAGE(Message.GREEN + "Vous venez de %freeze% %s."),
    FREEZE(Message.YELLOW + "Freeze"),
    QUIT(Message.RED + "Quitter le StaffMode"),
    KB_SWORD(Message.YELLOW + "KB Sword"),
    WTP(Message.YELLOW + "Teleporter"),
    FREEZE_TARGET_MESSAGE(Message.RED + "Vous êtes actuellement freeze!"),
    TELEPORTATION(Message.YELLOW + "TP Random"),
    NOBODY_ONLINE(Message.RED + "Aucune personne n'a été trouvé."),
    STAFF(Message.YELLOW + "Staff en ligne"),
    VANISH(Message.YELLOW + "Vanish"),
    STAFF_ONLINE(Message.GOLD + "Staff en ligne"),
    STAFF_TP_UNK(Message.RED + "Ce staff vient de se déconnecter."),
    NO_XP(Message.RED + "Vous n'avez aucune XP."),
    XP_ADD(Message.GREEN + "Vous venez de récupèrer vos XP."),
    INVENTORY_FULL(Message.RED + "Votre inventaire est plein."),
    INSPECTER_INV(Message.YELLOW + "Inventaire"),
    VOTE_PARTY_MAX("VoteParty max updated (%s)"),
    VOTE_PARTY_ADD("VoteParty add updated (%s)"),
    VOTE_PARTY_REMOVE("VoteParty remove updated (%s)"),
    COOLDOWN(Message.RED + "Attendez encore {sec} secondes."),
    PLAYER_NULL(Message.RED + "Joueur hors-ligne."),
    RANDOMTP_NOLOC(Message.RED + "Aucune location n'a été trouvé, réessayer."),
    STATS_OF(Message.GOLD + Message.BOLD + "Statistiques de "),
    KILLS(Message.WHITE + Message.BOLD + "» " + Message.GOLD + "Kills" + Message.GRAY + ": " + Message.YELLOW),
    DEATHS(Message.WHITE + Message.BOLD + "» " + Message.GOLD + "Deaths" + Message.GRAY + ": " + Message.YELLOW),
    RATIO(Message.WHITE + Message.BOLD + "» " + Message.GOLD + "Ratio" + Message.GRAY + ": " + Message.YELLOW),
    KB_DISABLED(Message.RED + "L'enchant Knockback est désactivé sur KryxMC."),
    EP_DISABLED_WZ(Message.RED + "Les enderpearls sont désactivées en war zone."),
    SPAWN_TELEPORTING(PREFIX.getText() + Message.WHITE + "Téléportation vers" + Message.YELLOW + " spawn" + Message.WHITE + "."),
    INVEST_TELEPORTING(PREFIX.getText() + Message.WHITE + "Téléportation vers" + Message.YELLOW + " invest" + Message.WHITE + "."),
    INVEST_COUNTER(PREFIX.getText() + Message.YELLOW + "La téléportation commence dans 5 seconde. Ne bougez pas."),
    INVEST_CANCELLED(PREFIX.getText() + Message.RED + "Requete de téléportation annulée."),
    ITEM_DISABLED(PREFIX.getText() + Message.RED + "Cet item est désactivé !"),
    MONEY_ZONE_EARN(PREFIX.getText() + Message.YELLOW + "Vous avez gagné %s §een restant dans cette zone."),
    CL_COUNTER(PREFIX.getText() + Message.RED + "Suppression des entités dans %s."),
    CL_CLEAR_SUCESS(PREFIX.getText() + Message.GREEN + "%s entités supprimées."),
    ATOUT_INVENTORY_HASTE("Haste II"),
    ATOUT_INVENTORY_JUMP("NoFall"),
    ATOUT_INVENTORY_SPEED("Speed II"),
    ATOUT_INVENTORY_STR("Force I"),
    ATOUT_INVENTORY_FIRE("Fire résistance II"),
    ATOUT_INVENTORY_WB("WaterBreathing"),
    ATOUT_ON(PREFIX.getText() + Message.GREEN + "Vous venez d'activer l'atout %s."),
    ATOUT_OFF(PREFIX.getText() + Message.RED + "Vous venez de désactiver l'atout %s."),
    ATOUT_DONT_HAVE(PREFIX.getText() + Message.RED + "Vous ne possedez pas cet atout. " + Message.GRAY + "(/boutique)"),
    ATOUT_DONT_HAVE_ANY(PREFIX.getText() + Message.RED + "Vous ne possedez aucun atouts. " + Message.GRAY + "(/boutique)"),
    ATOUT_INVENTORY_NAME(PREFIX.getText() + Message.YELLOW + Message.BOLD + "Atouts"),
    CLASSEMENT_INVENTORY_NAME(PREFIX.getText() + Message.YELLOW + Message.BOLD + "Classement"),
    CLASSEMENT_INVENTORY_PVP("PvP"),
    CLASSEMENT_INVENTORY_FARM("Farm"),
    BAGPACK_INVENTORY_NAME(PREFIX.getText() + Message.YELLOW + Message.BOLD + "Bag Pack"),
    SPAWN_SET(Message.GREEN + "Point de spawn placé !");

    private final String text;

    Lang(final String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }
}
