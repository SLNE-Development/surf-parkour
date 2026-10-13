package dev.slne.surf.parkour.minestom.instance

import dev.slne.surf.api.minestom.extension.InstanceManager
import dev.slne.surf.parkour.core.client.model.geometry.ParkourLocation
import dev.slne.surf.parkour.core.client.model.geometry.ParkourVector
import net.kyori.adventure.key.Key
import net.minestom.server.coordinate.Point
import net.minestom.server.coordinate.Pos
import net.minestom.server.instance.Instance
import net.minestom.server.tag.Tag

/**
 * The tag the lobby server stores the key of the world an instance was loaded from in.
 */
private val WORLD_KEY = Tag.String("surf_lobby_world_key")

/**
 * The key of the stored world this instance was loaded from, or `null` if it was not loaded from one.
 */
val Instance.worldKey: Key? get() = getTag(WORLD_KEY)?.let { Key.key(it) }

/**
 * The lobby world, the only instance the lobby server loads from a stored world.
 *
 * `null` while the server has not created it yet.
 */
val lobbyInstance: Instance? get() = InstanceManager.instances.firstOrNull { it.worldKey != null }

/**
 * The name a parkour addresses this instance's world by.
 */
val Instance.parkourWorldName: String? get() = worldKey?.value()

/**
 * Returns the loaded instance holding the world named [world].
 *
 * A world is addressable both by its plain name and by the full key it was loaded under, so a
 * config written on another platform keeps working.
 */
fun findParkourInstance(world: String): Instance? = InstanceManager.instances.firstOrNull {
    val key = it.worldKey ?: return@firstOrNull false
    key.value() == world || key.asString() == world
}

/**
 * The instance this location lies in, or `null` while its world is not loaded.
 */
fun ParkourLocation.instance(): Instance? = findParkourInstance(world)

/**
 * This location as a Minestom position.
 */
fun ParkourLocation.toPos() = Pos(x, y, z, yaw, pitch)

/**
 * This point as a platform-neutral parkour vector.
 */
fun Point.toParkourVector() = ParkourVector(x(), y(), z())
