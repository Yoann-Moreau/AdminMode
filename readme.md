# AdminMode

AdminMode is a PaperMC plugin for Minecraft 26.2. Its purpose is to allow server admins
to separate their player life from their admin life.

When entering Admin Mode players will have their inventory, position, gamemode and XP saved 
in order to retrieve them unchanged when exiting Admin Mode.

Admin Mode itself replaces the player inventory by their Admin Mode inventory previously
saved or the default one set by an admin. It will also put the player in Creative gamemode.
The goal being to allow them to do their admin duties and get back to their player 
activities once done.


## Dependencies

LuckPerms and Multiverse-Inventories are optional dependencies.

Without LuckPerms the admins have the same permissions in admin mode and player mode.
If LuckPerms is enabled, two groups are used to allow admin mode admins and admin mode
members to have different permissions. For now these groups are not configurable and
must therefore be named "adminmode-admin" and "adminmode-member".

Without Multiverse-Inventories the same player inventories are shared between worlds.
With Multiverse-Inventories the player inventories are managed by Multiverse but AdminMode
allows the adminmode users to travel between world without changing their admin mode
inventory while keeping their different players inventories unchanged.
See Multiverse-Inventories and LuckPerms requirements for more info.


## Multiverse-Inventories and LuckPerms requirements

If Multiverse-Inventories is enabled you need to set "enable-bypass-permissions" to "true"
in its config.yml file.

With LuckPerms create two new groups called "adminmode-admin" and "adminmode-member" with
the permissions of your choice but containing at least "mvinv.bypass.\*" and 
"mv.bypass.gamemode.\*".

For a player to be able to enter the group adminmode-admin they must have the 
"adminmode.admin" permission. For others, they need to have the "adminmode.use" permission.

## Installation

Put the AdminMode jar in your "plugins" directory. If you use Multiverse-Inventories
and LuckPerms follow the Multiverse-Inventories and LuckPerms requirements.

## Commands

### /adminmode

###### Permission adminmode.use

Toggles Admin Mode.

### /adminmode save-inventory

###### Permission adminmode.use

Allows an Admin Mode user to save their current inventory as Admin Mode inventory. When
entering Admin Mode this saved inventory will replace the current one.

### /adminmode save-default-inventory

###### Permission adminmode.savedefaultinventory

Allows an Admin Mode user to save their current inventory as the default Admin Mode
inventory. If no user Admin Mode inventory is found this inventory will be used instead
when entering Admin Mode.
