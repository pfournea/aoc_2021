package be.aco_2021

import java.io.File

class Day12_1 {
    fun execute() {
        val listOfPairs = File("src/main/resources/day12.txt")
            .readLines()
            .map { line ->
                val edgpoints = line.split("-")
                edgpoints[0] to edgpoints[1]
            }
        val visitedPoints = mutableListOf<String>()
        println(countRoutes("start", listOfPairs, visitedPoints))


    }

    private fun countRoutes(
        startPoint: String,
        listOfEdges: List<Pair<String, String>>,
        visitedPoints: MutableList<String>
    ): Int {
        var counter = 0
        visitedPoints.add(startPoint)
        //retrieve the possible edges
        val possibleNextPoints = listOfEdges.filter { p ->
            (p.first == startPoint && canAddedToTheCurrentRoute(
                p.second,
                visitedPoints
            )) || (p.second == startPoint && canAddedToTheCurrentRoute(p.first, visitedPoints))
        }
            .map { edge -> if (edge.first == startPoint) edge.second else edge.first }
        for (point in possibleNextPoints) {
            if (point == "end") {
                counter++
            } else {
                counter += countRoutes(point, listOfEdges, visitedPoints.toList().toMutableList())
            }
        }
        return counter
    }

    private fun canAddedToTheCurrentRoute(point: String, visitedPoints: MutableList<String>): Boolean {
        return point.toCharArray()[0].isUpperCase() || visitedPoints.contains(point).not()
    }
}

class Day12_2 {
    fun execute() {
        val listOfPairs = File("src/main/resources/day12.txt")
            .readLines()
            .map { line ->
                val edgpoints = line.split("-")
                edgpoints[0] to edgpoints[1]
            }
        val visitedPoints = mutableListOf<String>()
        val context = RouteFindingContext(pointsInRoute = visitedPoints)
        println(countRoutes("start", listOfPairs, context))


    }

    private fun countRoutes(
        startPoint: String,
        listOfEdges: List<Pair<String, String>>,
        context: RouteFindingContext
    ): Int {
        var counter = 0
        context.addNodeToRoute(startPoint)
        //retrieve the possible edges
        val possibleNextPoints = listOfEdges.filter { p ->
            (p.first == startPoint && context.checkIfNodeCanBeAddedToCurrentRoute(
                p.second
            )) || (p.second == startPoint && context.checkIfNodeCanBeAddedToCurrentRoute(p.first))
        }
            .map { edge -> if (edge.first == startPoint) edge.second else edge.first }
        for (point in possibleNextPoints) {
            if (point == "end") {
                counter++
            } else {
                counter += countRoutes(point, listOfEdges, context.clone())
            }
        }
        return counter
    }

    inner class RouteFindingContext(var mode: Mode = Mode.MULTIPLE, var pointsInRoute: MutableList<String>) {

        fun checkIfNodeCanBeAddedToCurrentRoute(node: String): Boolean {
            return if (node == "start" || node == "end") {
                !pointsInRoute.contains(node)
            } else {
                node.toCharArray()[0].isUpperCase() || (when (mode) {
                    Mode.SINGLE -> pointsInRoute.contains(node).not()
                    Mode.MULTIPLE -> pointsInRoute.count { it == node } < 2
                })
            }
        }

        fun addNodeToRoute(node: String) {
            pointsInRoute.add(node)
            if (node[0].isLowerCase() && pointsInRoute.count { it == node } == 2) {
                mode = Mode.SINGLE
            }
        }

        fun clone() : RouteFindingContext {
            return RouteFindingContext(mode = this.mode, pointsInRoute = this.pointsInRoute.toList().toMutableList())
        }
    }

    enum class Mode {
        SINGLE,
        MULTIPLE
    }
}

fun main(args: Array<String>) {
    Day12_1().execute()
    Day12_2().execute()
}

