package index

import app.*
import kotlinext.js.*
import react.dom.*
import kotlin.browser.*

fun main(args: Array<String>) {
    requireAll(require.context("src", true, js("/\\.css$/")))
    require("json-simple")

    render(document.getElementById("root")) {
        app()
    }
}
