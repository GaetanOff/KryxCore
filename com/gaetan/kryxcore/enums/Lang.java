package com.gaetan.kryxcore.enums;

import com.gaetan.api.message.Message;

public enum Lang {
    PREFIX(Message.GOLD + Message.BOLD + "KryxMC" + Message.WHITE + Message.BOLD + " � "),
    STAFF_ON(Message.GREEN + "Vous venez d'activer le StaffMode."),
    STAFF_OFF(Message.RED + "Vous venez de d�sactiver le StaffMode."),
    VANISH_ON(Message.GREEN + "Vous venez d'activer le vanish."),
    VANISH_OFF(Message.RED + "Vous venez de d�sactiver le vanish."),
    FREEZE_MESSAGE(Message.GREEN + "Vous venez de %freeze% %s."),
    FREEZE(Message.YELLOW + "Freeze"),
    QUIT(Message.RED + "Quitter le StaffMode"),
    KB_SWORD(Message.YELLOW + "KB Sword"),
    WTP(Message.YELLOW + "Teleporter"),
    FREEZE_TARGET_MESSAGE(Message.RED + "Vous �tes actuellement freeze!"),
    TELEPORTATION(Message.YELLOW + "TP Random"),
    NOBODY_ONLINE(Message.RED + "Aucune personne n'a �t� trouv�."),
    STAFF(Message.YELLOW + "Staff en ligne"),
    VANISH(Message.YELLOW + "Vanish"),
    STAFF_ONLINE(Message.GOLD + "Staff en ligne"),
    STAFF_TP_UNK(Message.RED + "Ce staff vient de se d�connecter."),
    NO_XP(Message.RED + "Vous n'avez aucune XP."),
    XP_ADD(Message.GREEN + "Vous venez de r�cup�rer vos XP."),
    INVENTORY_FULL(Message.RED + "Votre inventaire est plein."),
    INSPECTER_INV(Message.YELLOW + "Inventaire"),
    VOTE_PARTY_MAX("VoteParty max updated (%s)"),
    VOTE_PARTY_ADD("VoteParty add updated (%s)"),
    VOTE_PARTY_REMOVE("VoteParty remove updated (%s)"),
    COOLDOWN(Message.RED + "Attendez encore {sec} secondes."),
    PLAYER_NULL(Message.RED + "Joueur hors-ligne."),
    RANDOMTP_NOLOC(Message.RED + "Aucune location n'a �t� trouv�, r�essayer."),
    STATS_OF(Message.GOLD + Message.BOLD + "Statistiques de "),
    KILLS(Message.WHITE + Message.BOLD + "� " + Message.GOLD + "Kills" + Message.GRAY + ": " + Message.YELLOW),
    DEATHS(Message.WHITE + Message.BOLD + "� " + Message.GOLD + "Deaths" + Message.GRAY + ": " + Message.YELLOW),
    RATIO(Message.WHITE + Message.BOLD + "� " + Message.GOLD + "Ratio" + Message.GRAY + ": " + Message.YELLOW),
    KB_DISABLED(Message.RED + "L'enchant Knockback est d�sactiv� sur KryxMC."),
    EP_DISABLED_WZ(Message.RED + "Les enderpearls sont d�sactiv�es en war zone."),
    SPAWN_TELEPORTING(PREFIX.getText() + Message.WHITE + "T�l�portation vers" + Message.YELLOW + " spawn" + Message.WHITE + "."),
    INVEST_TELEPORTING(PREFIX.getText() + Message.WHITE + "T�l�portation vers" + Message.YELLOW + " invest" + Message.WHITE + "."),
    INVEST_COUNTER(PREFIX.getText() + Message.YELLOW + "La t�l�portation commence dans 5 seconde. Ne bougez pas."),
    INVEST_CANCELLED(PREFIX.getText() + Message.RED + "Requete de t�l�portation annul�e."),
    ITEM_DISABLED(PREFIX.getText() + Message.RED + "Cet item est d�sactiv� !"),
    MONEY_ZONE_EARN(PREFIX.getText() + Message.YELLOW + "Vous avez gagn� %s �een restant dans cette zone."),
    CL_COUNTER(PREFIX.getText() + Message.RED + "Suppression des entit�s dans %s."),
    CL_CLEAR_SUCESS(PREFIX.getText() + Message.GREEN + "%s entit�s supprim�es."),
    ATOUT_INVENTORY_HASTE("Haste II"),
    ATOUT_INVENTORY_JUMP("NoFall"),
    ATOUT_INVENTORY_SPEED("Speed II"),
    ATOUT_INVENTORY_STR("Force I"),
    ATOUT_INVENTORY_FIRE("Fire r�sistance II"),
    ATOUT_INVENTORY_WB("WaterBreathing"),
    ATOUT_ON(PREFIX.getText() + Message.GREEN + "Vous venez d'activer l'atout %s."),
    ATOUT_OFF(PREFIX.getText() + Message.RED + "Vous venez de d�sactiver l'atout %s."),
    ATOUT_DONT_HAVE(PREFIX.getText() + Message.RED + "Vous ne possedez pas cet atout. " + Message.GRAY + "(/boutique)"),
    ATOUT_DONT_HAVE_ANY(PREFIX.getText() + Message.RED + "Vous ne possedez aucun atouts. " + Message.GRAY + "(/boutique)"),
    ATOUT_INVENTORY_NAME(PREFIX.getText() + Message.YELLOW + Message.BOLD + "Atouts"),
    CLASSEMENT_INVENTORY_NAME(PREFIX.getText() + Message.YELLOW + Message.BOLD + "Classement"),
    CLASSEMENT_INVENTORY_PVP("PvP"),
    CLASSEMENT_INVENTORY_FARM("Farm"),
    BAGPACK_INVENTORY_NAME(PREFIX.getText() + Message.YELLOW + Message.BOLD + "Bag Pack"),
    SPAWN_SET(Message.GREEN + "Point de spawn plac� !");

    private final String text;

    Lang(final String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }
}
