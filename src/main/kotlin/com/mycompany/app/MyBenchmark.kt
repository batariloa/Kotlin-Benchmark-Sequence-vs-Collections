/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.mycompany.app

import Gender
import Person
import org.openjdk.jmh.annotations.*
import java.util.concurrent.TimeUnit

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(value = 2, jvmArgs = ["-Xms2G", "-Xmx2G"])
@Warmup(iterations = 3)
@Measurement(iterations = 8)
open class MyBenchmark {


    var friendGroup = listOf<Person>()

    @Setup
    fun setUp(){
         friendGroup = createData()
    }


    @Benchmark
    fun collectionBenchmark(){

        //checking performance without sequence
        repeat(100000) {
            friendGroup
                .filter { it.gender == Gender.MALE }  //only guys
                .map { person -> person.copy(name = "Sir ${person.gender}") } //add sir
                .map { person -> person.copy(name = person.name.uppercase()) }//uppercase
                .first { it.driversLicence }

        }

    }
    @Benchmark
    fun sequenceBenchmark() {

        //checking performance with sequence
        repeat(100000) {
            friendGroup
                .asSequence()
                .filter { it.gender == Gender.MALE  }  //only guys
                .map { person -> person.copy(name = "Sir ${person.gender}") } //add sir
                .map { person -> person.copy(name = person.name.uppercase()) } //uppercase
                .first { it.driversLicence }

        }
    }

    private fun createData():List<Person>{
        return listOf<Person>(
            Person("Mike", 25, false, Gender.MALE),
            Person("Laurel", 26, false, Gender.FEMALE),
            Person("Johnny", 30, false, Gender.MALE),
            Person("Monica", 22, true, Gender.FEMALE),
            Person("Latiedo", 28, true, Gender.MALE),
            Person("Kaleb", 30, false, Gender.MALE),
            Person("Jay", 25, false, Gender.MALE),
            Person("Fernando", 26, false, Gender.FEMALE),
            Person("Nicolas", 22, true, Gender.FEMALE),
            Person("Michael", 28, true, Gender.MALE),
        )
    }
}
