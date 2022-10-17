package zaym.`in`.card5min.models

import java.io.Serializable

data class ModelJSON(
    var title: String,
    var logo: String,
    var description: String,
    var url: String,
    var sum: String,
    var rating: String,
    var percent: String,
    var open: String) : Serializable