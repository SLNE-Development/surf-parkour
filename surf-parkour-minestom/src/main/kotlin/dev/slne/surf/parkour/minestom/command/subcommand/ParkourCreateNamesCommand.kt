package dev.slne.surf.parkour.minestom.command.subcommand

import dev.slne.surf.api.minestom.command.dsl.anyExecutorSuspend
import dev.slne.surf.api.minestom.command.dsl.subcommand
import dev.slne.surf.parkour.core.client.command.recreateMissingPlayerNames
import dev.slne.surf.parkour.core.client.permission.ParkourPermissions

fun parkourCreateNamesCommand() = subcommand("reCreateNames") {
    withPermission(ParkourPermissions.COMMAND_PARKOUR_NAMES)
    anyExecutorSuspend { sender, _ ->
        recreateMissingPlayerNames(sender)
    }
}
