package id.kharozim.firechat.models

data class PayloadModelBody(
    val data: DataModel,
    val to: String
)

data class DataModel(
    val title: String,
    val body: String,
    val email: String
)
