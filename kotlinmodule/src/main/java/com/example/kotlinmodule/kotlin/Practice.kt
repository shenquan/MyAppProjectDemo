package com.example.kotlinmodule.kotlin

/**
 * Created by sqhan on 2017/9/7.
 */
class Practice {
    var x: Int = 1
    var y: Double = 1.2
    var z: String = "3"

    constructor()

    constructor(x: Int, y: Double, z: String) {
        this.x = x
        this.y = y
        this.z = z
    }


    fun sum(a: Int, b: Int): Int {
        return a + b
    }

    fun main(args: Array<String>) {
        print("sum of 3 and 5 is ")
        println(sum(3, 5))
    }

}