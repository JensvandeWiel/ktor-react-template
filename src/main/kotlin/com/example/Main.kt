package com.example

import picocli.CommandLine


fun main(args: Array<String>) {
    CommandLine(RootCommand(args)).execute(*args)
}