package dev.slne.surf.parkour.minestom

import dev.slne.surf.api.minestom.inventory.framework.register
import dev.slne.surf.api.minestom.plugin.SurfMinestomPlugin
import dev.slne.surf.parkour.core.client.ClientParkourInstance
import dev.slne.surf.parkour.core.client.service.ParkourRunsService
import dev.slne.surf.parkour.core.client.service.ParkourService
import dev.slne.surf.parkour.core.client.service.ParkourTexturesService
import dev.slne.surf.parkour.minestom.command.parkourCommand
import dev.slne.surf.parkour.minestom.listener.ParkourListener
import dev.slne.surf.parkour.minestom.menu.view.ParkourActivePlayersView
import dev.slne.surf.parkour.minestom.menu.view.ParkourLeaderboardView
import dev.slne.surf.parkour.minestom.menu.view.ParkourOverviewView
import dev.slne.surf.parkour.minestom.service.ParkourActionBarTask

object ParkourMinestomPlugin : SurfMinestomPlugin() {
    override suspend fun onLoad() {
        ParkourOverviewView.register()
        ParkourLeaderboardView.register()
        ParkourActivePlayersView.register()

        ClientParkourInstance.clientLoader.onLoad()
        ClientParkourInstance.clientLoader.onEnable()

        ParkourService.loadParkours()
        ParkourRunsService.loadStats()
        ParkourTexturesService.loadTextures()
        ParkourActionBarTask.startUpdating()
    }

    override suspend fun onEnable() {
        parkourCommand()

        ParkourListener.register(eventNode)
    }

    override suspend fun onDisable() {
        ParkourActionBarTask.stopUpdating()
        ClientParkourInstance.clientLoader.onDisable()
    }
}
