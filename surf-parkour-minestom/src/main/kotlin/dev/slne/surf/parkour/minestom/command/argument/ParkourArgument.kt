package dev.slne.surf.parkour.minestom.command.argument

import dev.slne.surf.api.minestom.command.CommandAPICommand
import dev.slne.surf.api.minestom.command.argument.Argument
import dev.slne.surf.api.minestom.command.argument.CustomArgument
import dev.slne.surf.api.minestom.command.argument.StringArgument
import dev.slne.surf.api.minestom.command.exception.CommandSyntaxException
import dev.slne.surf.api.minestom.command.suggestion.ArgumentSuggestions
import dev.slne.surf.api.core.messages.adventure.buildText
import dev.slne.surf.parkour.core.client.command.appendUnknownParkour
import dev.slne.surf.parkour.core.client.model.parkour.Parkour
import dev.slne.surf.parkour.core.client.service.ParkourService

class ParkourArgument(nodeName: String) : CustomArgument<Parkour, String>(
    StringArgument(nodeName),
    { info ->
        ParkourService.getParkour(info.baseValue)
            ?: throw CommandSyntaxException(
                buildText { appendUnknownParkour(info.baseValue) }
            )
    }) {
    init {
        replaceSuggestions(ArgumentSuggestions.stringCollection {
            ParkourService.parkours.map { it.identifier }
        })
    }
}

inline fun CommandAPICommand.parkourArgument(
    nodeName: String,
    optional: Boolean = false,
    block: Argument<Parkour>.() -> Unit = {}
): CommandAPICommand = withArguments(ParkourArgument(nodeName).setOptional(optional).apply(block))
