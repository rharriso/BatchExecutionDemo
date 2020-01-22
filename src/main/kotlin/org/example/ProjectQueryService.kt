package org.example

import kotlin.random.Random
import com.github.javafaker.Faker

data class TocNode(
    val id: Long,
    val type: String,
    val title: String? = null,
    val positionPath: List<Long> = listOf(),
    val linkIdPath: List<Long> = listOf(),
    val parentId: Long? = null,
    val linkId: Long? = null
)

val faker = Faker()
val lotr = faker.lordOfTheRings()
val phrases = listOf("Adventures in", "Trials of", "Interlude in", "Passage through")

class ProjectQueryService {
    @Suppress("unused")
    fun getProjectToc(): List<TocNode> {
        val tocNodes = (1..2000).map {
            TocNode(
                id = Random.nextLong(1, 10000),
                type = listOf("Project", "Chapter", "Section", "Page").random(),
                title = "$it: ${lotr.character()} ${phrases.random()} ${lotr.location()}",
                parentId = Random.nextLong(1, 10000),
                linkId = Random.nextLong(1, 10000),
                positionPath = listOf(Random.nextLong(1, 10000), Random.nextLong(1, 10000), Random.nextLong(1, 10000)),
                linkIdPath = listOf(Random.nextLong(1, 10000), Random.nextLong(1, 10000), Random.nextLong(1, 10000))
            )
        }

        return tocNodes;
    }
}

