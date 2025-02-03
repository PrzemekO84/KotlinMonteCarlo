import kotlin.random.Random
import java.util.concurrent.*

data class PI_Results(val threads: Int, val iterations: Long, val PINumber: Double, val time: Long)

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        println()
        println("Hello, this is a program that let estimate the number PI by Monte Carlo method")
        println()
        println("At the end, the program will compare the threading Monte Carlo method and the Monte Carlo method without threading")
        println()
        println("But firstly please choose the number of THREADS that you want to use to estimate the number PI")
        println()

        val userThreads = validateTheNumberOfThreads()

        val MonteCarloIterations = validateTheNumberOfIterations()


        val parallelMonteCarloResult: PI_Results = threadMonteCarloPI(userThreads, MonteCarloIterations)

        val monteCarloResult: PI_Results = MonteCarloPI(MonteCarloIterations)

        displayResults(parallelMonteCarloResult, "with threading")

        displayResults(monteCarloResult, "without threading")

        println()

        compareResults(monteCarloResult, parallelMonteCarloResult, userThreads, MonteCarloIterations)
    }

    fun validateTheNumberOfThreads(): Int {
        val PCThreadCount = Runtime.getRuntime().availableProcessors()

        println("The number of available threads on your PC: $PCThreadCount")

        do {
            try {
                println()
                println("Please choose the number of threads you would like to use: ")
                val userChoiceThreads = readln().toInt()
                if (userChoiceThreads < 1) {
                    println("You need to choose number higher than 0!")
                } else if (userChoiceThreads > PCThreadCount) {
                    println("You don't have this number of threads on your computer!")
                    println()
                    println("The number of available threads: $PCThreadCount")
                } else {
                    println("The number of threads chosen by user: $userChoiceThreads")
                    return userChoiceThreads
                }
            } catch (e: Exception) {
                println("Please enter a number.")
            }
        } while (true)
    }

    fun validateTheNumberOfIterations(): Long {
        do {
            try {
                println()
                println("Please choose the number of iterations you would like to go through (NOTE: High number of iterations can take long time to execute): ")
                return readln().toLong()
            } catch (e: Exception) {
                println("Please enter a number.")
            }
        } while (true)
    }

    fun threadMonteCarloPI(threadCount: Int, iterations: Long): PI_Results {
        val executor = Executors.newFixedThreadPool(threadCount)
        val results: MutableList<Future<Long>> = ArrayList()

        val iterationsForThread = (iterations / threadCount)


        val startTime = System.nanoTime()

        for (i in 0 until threadCount) {
            results.add(executor.submit<Long> {
                var pointsInCircle: Long = 0
                val random = Random.Default
                for (j in 0 until iterationsForThread) {
                    val x = random.nextDouble()
                    val y = random.nextDouble()

                    if (x * x + y * y <= 1) {
                        pointsInCircle++
                    }
                }
                pointsInCircle
            })
        }

        var totalPointsInCircle: Long = 0
        for (result in results) {
            totalPointsInCircle += result.get()
        }

        val endTime = System.nanoTime()

        executor.shutdown()

        val PINumber = 4.0 * totalPointsInCircle / iterations

        val time = (endTime - startTime) / 1000000

        return PI_Results(threadCount, iterations, PINumber, time)
    }

    fun MonteCarloPI(iterations: Long): PI_Results {
        val random = Random.Default
        var pointsInCircle = 0

        val startTime = System.nanoTime()

        for (i in 0 until iterations) {
            val x = random.nextDouble()
            val y = random.nextDouble()

            if (x * x + y * y <= 1) {
                pointsInCircle++
            }
        }

        val endTime = System.nanoTime()

        val time = (endTime - startTime) / 1000000

        val PINumber = 4.0 * pointsInCircle / iterations

        return PI_Results(1, iterations, PINumber, time)
    }

    fun displayResults(result: PI_Results, method: String) {
        println()
        println("Monte Carlo $method")
        println("-- The number of used threads: ${result.threads}")
        println("-- Iterations: %,d".format(result.iterations))
        println("-- π result: ${result.PINumber}")
        println("-- Execution time: ${result.time} ms")
    }

    fun compareResults(withoutThreading: PI_Results, withThreading: PI_Results, threads: Int, iterations: Long) {
        if (withoutThreading.time < withThreading.time) {
            println("The method WITHOUT threading was faster by ${withThreading.time - withoutThreading.time}ms.")
        } else {
            println("The method WITH threading was faster by ${withoutThreading.time - withThreading.time}ms while using $threads threads.")
        }
    }
}
